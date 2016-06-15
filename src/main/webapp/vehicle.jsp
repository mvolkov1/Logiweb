<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Vehicles</title>
    <style> <%@include file="/css/body.css" %> </style>
    <style> <%@include file="/css/resultTable.css" %> </style>
    <style> <%@include file="/css/nav.css" %> </style>
    <style> <%@include file="/css/right.css" %> </style>
</head>
<body>

<!-- Site navigation menu -->
<ul class="navbar">
    <li><a href="http://localhost:8080/Logiweb/vehicle" class="active">Vehicles</a></li>
    <li><a href="http://localhost:8080/Logiweb/driver">Drivers</a></li>
    <li><a href="http://localhost:8080/Logiweb/order">Orders</a></li>
</ul>

<div class="right">
    <table>
        <caption><h1>Available vehicles</h1></caption>
        <th>VIN</th>
        <th>Capacity</th>
        <th>Number of drivers</th>
        <th>City</th>
        <th>Order ID</th>
        <c:forEach var="vehicle" items="${list}">
            <tr>
                <td> ${vehicle.vin} </td>
                <td> ${vehicle.capacity} </td>
                <td> ${vehicle.numberOfDrivers} </td>
                <td> ${vehicle.city.city} </td>
                <td> ${vehicle.order.uid} </td>
            </tr>
        </c:forEach>
    </table>

    <form>
        <input type="submit" value="Add vehicle">
    </form>

</div>

</body>
</html>
