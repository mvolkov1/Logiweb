package com.tsystems.logiweb.services.impl;

import com.tsystems.logiweb.services.TransactionManager;
import com.tsystems.logiweb.dao.api.UserDao;
import com.tsystems.logiweb.dao.entity.UserEntity;
import com.tsystems.logiweb.dao.impl.UserDaoImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by mvolkov on 19.06.2016.
 */
public class UserService {

    private UserDao userDao = new UserDaoImpl(TransactionManager.getEntityManager());


    private void addUser(String login, String password, String role, String firstName, String lastName) {
        try {
            TransactionManager.beginTransaction();

            UserEntity userEntity = new UserEntity();
            userEntity.setLogin(login);
            userEntity.setPassword(password);
            userEntity.setRole(role);
            userEntity.setFirstName(firstName);
            userEntity.setLastName(lastName);
            userDao.save(userEntity);
            TransactionManager.commit();
        } catch (Exception e) {
            TransactionManager.rollback();
        }
    }

    public void addManager(String login, String password, String role, String firstName, String lastName) {
        addUser(login, password, role, firstName, lastName);
    }

    public void handleLoginPassword(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("pass");
        if (login != null && password != null) {
            String address = null;
            try {
                TransactionManager.beginTransaction();
                UserEntity userEntity = userDao.findByLogin(login);
                if (userEntity != null && userEntity.getPassword().equals(password)) {
                    request.setAttribute("lastName", userEntity.getLastName());
                    request.setAttribute("firstName", userEntity.getFirstName());
                    if (userEntity.getRole().equals("manager")) {
                        address = "/WEB-INF/jsp/managerView.jsp";
                    } else if (userEntity.getRole().equals("driver")) {
                        address = "/WEB-INF/jsp/driverView.jsp";
                    }
                } else {
                    address = "/WEB-INF/jsp/login.jsp";
                }
                TransactionManager.commit();
            } catch (Exception e) {
                TransactionManager.rollback();
                address = "/WEB-INF/jsp/login.jsp";
            }
            request.getRequestDispatcher(address).forward(request, response);
        } else {
            request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
        }
    }

}
