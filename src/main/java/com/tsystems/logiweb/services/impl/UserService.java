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


    private void addUser(String login, String password, String role, String firstName, String lastName) {
        try {
            TransactionManager.beginTransaction();

            UserEntity userEntity = new UserEntity();
            userEntity.setLogin(login);
            userEntity.setPassword(password);
            userEntity.setRole(role);
            userEntity.setFirstName(firstName);
            userEntity.setLastName(lastName);
            new UserDaoImpl(TransactionManager.getEntityManager()).save(userEntity);
            TransactionManager.commit();
        } catch (Exception e) {
            TransactionManager.rollback();
        }
    }

    public void addManager(String login, String password, String role, String firstName, String lastName) {
        addUser(login, password, role, firstName, lastName);
    }

    public static String handleLoginPassword(String login, String password, StringBuilder firstName, StringBuilder lastName) {
        String address = null;
        try {
            TransactionManager.beginTransaction();
            UserEntity userEntity = new UserDaoImpl(TransactionManager.getEntityManager()).findByLogin(login);
            if (userEntity != null && userEntity.getPassword().equals(password)) {
                firstName.append(new StringBuilder(userEntity.getFirstName()));
                lastName.append(new StringBuilder(userEntity.getLastName()));
                if (userEntity.getRole().equals("manager")) {
                    address = "/WEB-INF/jsp/managerView.jsp";
                } else if (userEntity.getRole().equals("driver")) {
                    address = "/WEB-INF/jsp/driverView.jsp";
                }
            } else {
                address = "login.jsp";
            }
            TransactionManager.commit();
        } catch (Exception e) {
            TransactionManager.rollback();
            address = "/WEB-INF/jsp/login.jsp";
        }
        return address;
    }

}
