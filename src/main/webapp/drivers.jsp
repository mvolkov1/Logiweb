<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Drivers</title>
    <style> <%@include file="/css/body.css" %> </style>
    <style> <%@include file="/css/resultTable.css" %> </style>
    <style> <%@include file="/css/nav.css" %> </style>
    <style> <%@include file="/css/right.css" %> </style>
</head>
<body>

<!-- Site navigation menu -->
<ul class="navbar">
    <li><a href="http://localhost:8080/Logiweb/vehicles">Vehicles</a></li>
    <li><a href="http://localhost:8080/Logiweb/drivers" class="active">Drivers</a></li>
    <li><a href="http://localhost:8080/Logiweb/orders">Orders</a></li>
</ul>

<div class="right">
    <table class="resultTable">
        <caption><h1>Drivers</h1></caption>
        <th>ID</th>
        <th>First name</th>
        <th>Last name</th>
        <th>Month hours</th>
        <th>Status</th>
        <th>City</th>
        <%--<th>Order ID</th>--%>
        <c:forEach var="driver" items="${list}">
            <tr>
                <td> ${driver.uid} </td>
                <td> ${driver.firstName} </td>
                <td> ${driver.lastName} </td>
                <td> ${driver.monthHours} </td>
                <td> ${driver.status} </td>
                <td> ${driver.city.city} </td>
                <%--<td> ${driver.order.uid} </td>--%>
            </tr>
        </c:forEach>
    </table>

    <form>
        <input type="submit" value="Add driver">
    </form>

</div>

</body>
</html>
