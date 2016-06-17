package com.tsystems.logiweb.web;

import com.tsystems.logiweb.dao.entity.DriverEntity;
import com.tsystems.logiweb.dao.entity.OrderEntity;
import com.tsystems.logiweb.dao.entity.OrderItemEntity;
import com.tsystems.logiweb.dao.entity.VehicleEntity;
import com.tsystems.logiweb.services.impl.DriverService;
import com.tsystems.logiweb.services.impl.OrderItemService;
import com.tsystems.logiweb.services.impl.VehicleService;
import com.tsystems.logiweb.services.impl.OrderService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.List;

/**
 * Created by mvolkov on 15.06.2016.
 */
public class ServletFrontController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        StringBuffer servletUrl = request.getRequestURL();



        if(servletUrl != null)
        {
            URL url = new URL(servletUrl.toString());
            String path = url.getPath();

            if (path.equals("/Logiweb") || path.equals("/Logiweb/"))
            {
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/index.jsp");
                dispatcher.forward(request,response);
            }
            else if (path.equals("/Logiweb/vehicles")) {

                List<VehicleEntity> vehicles = new VehicleService().getListOfVehicles();
                request.setAttribute("list", vehicles);
                request.getRequestDispatcher("/vehicles.jsp").forward(request,response);
            }
            else if (path.equals("/Logiweb/drivers")){
                List<DriverEntity> drivers = new DriverService().getListOfDrivers();
                request.setAttribute("list", drivers);
                request.getRequestDispatcher("/drivers.jsp").forward(request,response);
            }
            else if (path.equals("/Logiweb/orders")){
                List<OrderEntity> orders = new OrderService().getListOfOrders();
                request.setAttribute("list", orders);
                request.getRequestDispatcher("/orders.jsp").forward(request,response);
            }
            else if (path.equals("/Logiweb/order")){
                String orderId = request.getParameter("orderId");
                List<OrderItemEntity> items = new OrderItemService().getListOfItems(orderId);
                request.setAttribute("list", items);
                request.setAttribute("orderId", orderId);
                request.getRequestDispatcher("/order.jsp").forward(request,response);
            }
            else {
                PrintWriter out = null;
                response.setContentType("text/html");
                out = response.getWriter();
                out.println("<html>");
                out.println("<head><title>"+"</title></head>");
                out.println("<body>");
                out.println("Wrong request: " + path);
                out.println("</body>");
                out.println("</html>");
                // TODO wrong address response
            }
        }
    }
}
