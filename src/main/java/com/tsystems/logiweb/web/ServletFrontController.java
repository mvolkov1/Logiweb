package com.tsystems.logiweb.web;

import com.tsystems.logiweb.dao.entity.VehicleEntity;
import com.tsystems.logiweb.services.impl.VehicleService;

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
            else if (path.equals("/Logiweb/vehicle")) {

                VehicleService vehicleService = new VehicleService();

                List<VehicleEntity> vehicles = vehicleService.getListOfVehicles();
                String vin = null;
                for (VehicleEntity vehicleEntity : vehicles)
                {
                    vin = vehicleEntity.getVin();
                }


                request.setAttribute("vin",vin);
                request.setAttribute("list", vehicles);

                request.getRequestDispatcher("/vehicle.jsp").forward(request,response);

            //    RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/vehicle.jsp");
             //   dispatcher.forward(request,response);
            }
            else if (path.equals("/Logiweb/driver")){
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/driver.jsp");
                dispatcher.forward(request,response);
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
