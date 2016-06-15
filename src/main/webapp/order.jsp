<%--
  Created by IntelliJ IDEA.
  User: mvolkov
  Date: 14.06.2016
  Time: 0:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Orders</title>
    <style> <%@include file="/css/body.css" %> </style>
    <style> <%@include file="/css/resultTable.css" %> </style>
    <style> <%@include file="/css/nav.css" %> </style>
    <style> <%@include file="/css/right.css" %> </style>
</head>
<body>
<!-- Site navigation menu -->
<ul class="navbar">
    <li><a href="http://localhost:8080/Logiweb/vehicle">Vehicles</a></li>
    <li><a href="http://localhost:8080/Logiweb/driver">Drivers</a></li>
    <li><a href="http://localhost:8080/Logiweb/order" class="active">Orders</a></li>
</ul>


<div class="right">
    <table>
        <caption><h1>Orders</h1></caption>
        <th>ID</th>
        <th>Number of items</th>
        <th>Last name</th>
        <th>Month hours</th>
        <th>Status</th>
        <th>City</th>
        <%--<th>Order ID</th>--%>
        <c:forEach var="order" items="${list}">
            <tr>
                <%--<td> ${driver.uid} </td>--%>
                <%--<td> ${driver.firstName} </td>--%>
                <%--<td> ${driver.lastName} </td>--%>
                <%--<td> ${driver.monthHours} </td>--%>
                <%--<td> ${driver.status} </td>--%>
                <%--<td> ${driver.city.city} </td>--%>
                    <%--<td> ${driver.order.uid} </td>--%>
            </tr>
        </c:forEach>
    </table>

    <form>
        <input type="submit" value="Add order">
    </form>

</div>

</body>
</html>
