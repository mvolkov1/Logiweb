<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
    <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>

    <title>Vehicles</title>
    <style> <%@include file="../css/body.css" %> </style>
    <style> <%@include file="../css/tables.css" %> </style>
    <style> <%@include file="../css/nav.css" %> </style>
    <style> <%@include file="../css/right.css" %> </style>
    <style> <%@include file="../css/buttons.css" %> </style>
    <script>
        function confirmDelete() {
            return confirm("Delete vehicle?");
        }
    </script>
</head>
<body>

<!-- Site navigation menu -->
<ul class="navbar">
    <li><a href="vehicles" class="active">Vehicles</a></li>
    <li><a href="drivers">Drivers</a></li>
    <li><a href="orders">Orders</a></li>
</ul>

<div class="right">

    <table>
        <tr>
            <td>
                <c:if test="${fn:length(list) > 0}">
                    <table class="resultTable">
                        <caption><h1>Vehicles</h1></caption>
                        <th>VIN</th>
                        <th>Capacity</th>
                        <th>Number of drivers</th>
                        <th>City</th>
                        <th>Status</th>
                        <th>Order ID</th>
                        <th></th>
                        <c:forEach var="vehicle" items="${list}">
                            <tr>
                                <td><a href="editVehicle?vin=${vehicle.vin}" class="tableRef"> ${vehicle.vin}</a></td>
                                <td><a href="editVehicle?vin=${vehicle.vin}" class="tableRef"> ${vehicle.capacity}</a>
                                </td>
                                <td><a href="editVehicle?vin=${vehicle.vin}"
                                       class="tableRef"> ${vehicle.numberOfDrivers}</a></td>
                                <td><a href="editVehicle?vin=${vehicle.vin}" class="tableRef"> ${vehicle.city.name}</a>
                                </td>
                                <td><a href="editVehicle?vin=${vehicle.vin}"
                                       class="tableRef"> ${vehicle.isAvailable == 0 ? "not available" : "available"}</a>
                                </td>
                                <td><a href="editOrder?uid=${vehicle.order.uid}"> ${vehicle.order.uid}</a>
                                </td>
                                <td><a href="vehicles?deleteVehicle=true&vin=${vehicle.vin}"
                                       onclick="return confirmDelete()">Delete</a></td>
                            </tr>
                        </c:forEach>
                    </table>
                    <br>
                </c:if>
                <c:if test="${empty list}">
                    <h3>No vehicles found in the database</h3>
                    <br>
                </c:if>
            </td>
        </tr>
        <tr>
            <td>
                <form action="editVehicle" method="get">
                    <input type="submit" value="Add new vehicle" class="buttonAddToTable">
                    <input type="hidden" name="editVehicle" value="true">
                </form>
            </td>
        </tr>

    </table>

</div>
<br>
<br>
</body>
</html>
