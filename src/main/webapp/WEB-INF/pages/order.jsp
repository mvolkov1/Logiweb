<%--
  Created by IntelliJ IDEA.
  User: mvolkov
  Date: 17.06.2016
  Time: 13:01
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
    <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>

    <title>Order</title>
    <style> <%@include file="../css/body.css" %> </style>
    <style> <%@include file="../css/resultTable.css" %> </style>
    <style> <%@include file="../css/nav.css" %> </style>
    <style> <%@include file="../css/right.css" %> </style>
</head>
<body>
<!-- Site navigation menu -->
<ul class="navbar">
    <li><a href="http://localhost:8080/Logiweb/vehicles">Vehicles</a></li>
    <li><a href="http://localhost:8080/Logiweb/drivers">Drivers</a></li>
    <li><a href="http://localhost:8080/Logiweb/orders" class="active">Orders</a></li>
</ul>

<div class="right">
    <table class="resultTable">
        <caption><h1>Order # <c:out value="${orderId}"></c:out></h1></caption>
        <th>Order item</th>
        <th>City</th>
        <%--<th>Cargo to load</th>--%>
        <%--<th>Cargo to deliver</th>--%>
        <c:forEach var="item" items="${list}">
            <tr>
                <td> ${item.itemNumber} </td>
                <td> ${item.city.city} </td>
            </tr>
        </c:forEach>
    </table>

    <%--<form>--%>
        <%--<input type="submit" value="Add order">--%>
    <%--</form>--%>

</div>

</body>
</html>
