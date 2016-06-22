<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
    <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>

    <title>Orders</title>
    <style> <%@include file="../css/body.css" %> </style>
    <style> <%@include file="../css/tables.css" %> </style>
    <style> <%@include file="../css/nav.css" %> </style>
    <style> <%@include file="../css/right.css" %> </style>
    <style> <%@include file="../css/buttons.css" %> </style>
</head>
<body>
<!-- Site navigation menu -->
<ul class="navbar">
    <li><a href="vehicles">Vehicles</a></li>
    <li><a href="drivers">Drivers</a></li>
    <li><a href="orders" class="active">Orders</a></li>
</ul>


<div class="right">
    <c:if test="${fn:length(orders) > 0}">
        <table class="resultTable">
            <caption><h1>Orders</h1></caption>
            <th>ID</th>
            <th>Number of items</th>
            <th>Number of cargos</th>
            <th>Vehicle</th>
            <th>Is completed</th>
            <c:forEach var="order" items="${orders}">
                <tr>
                    <td><a href="http://localhost:8080/Logiweb/order?orderId=${order.uid}"
                           class="tableRef"> ${order.uid}</a></td>
                    <td> ${order.numberOfItems} </td>
                    <td> ${order.cargos.size()} </td>
                    <td> ${order.vehicle.vin}</td>
                    <td> ${order.isCompleted} </td>
                </tr>
            </c:forEach>
        </table>
        <br>
    </c:if>

    <c:if test="${empty orders}">
        <h3>No orders found in the database</h3>
    </c:if>

    <form action="newOrder"  method="post" >
        <input type="submit" value="Add order" class="buttonAddToTable">
        <input type="hidden" name="editOrder" value="true">
        <input type="hidden" name="emptyOrder" value="true">
    </form>

</div>
<br>
<br>
</body>
</html>
