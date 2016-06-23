package com.tsystems.logiweb.web;

import com.tsystems.logiweb.dao.entity.*;
import com.tsystems.logiweb.services.TransactionManager;
import com.tsystems.logiweb.services.impl.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URL;
import java.util.List;


/**
 * Created by mvolkov on 15.06.2016.
 */
public class ServletFrontController extends HttpServlet {


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        StringBuffer servletUrl = request.getRequestURL();

        if (servletUrl != null) {
            URL url = new URL(servletUrl.toString());
            String path = url.getPath();
            if (path.equals("/Logiweb/") || path.equals("/Logiweb")) {
                new UserService().handleLoginPassword(request, response);
            }else if (path.equals("/Logiweb/newOrder") || path.equals("/Logiweb/order")) {
                new OrderService().handleNewOrder(request, response, path);
            }else {
                request.setAttribute("path", path);
                request.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        StringBuffer servletUrl = request.getRequestURL();

        if (servletUrl != null) {
            URL url = new URL(servletUrl.toString());
            String path = url.getPath();
            if (path.equals("/Logiweb") || path.equals("/Logiweb/")) {
                request.getRequestDispatcher("/login.jsp").forward(request, response);
            } else if (path.equals("/Logiweb/vehicles")) {
                new VehicleService().handleVehiclesPage(request, response);
            } else if (path.equals("/Logiweb/drivers")) {
                new DriverService().handleDriversPage(request, response);
            } else if (path.equals("/Logiweb/orders") ) {
                new OrderService().handleOrdersPage(request, response);
            }else if (path.equals("/Logiweb/order")) {
                new OrderService().handleNewOrder(request, response, path);
            } else {
                request.setAttribute("path", path);
                request.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
            }
        }
    }

}
