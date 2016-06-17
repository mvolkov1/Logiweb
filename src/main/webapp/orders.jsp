<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <li><a href="http://localhost:8080/Logiweb/vehicles">Vehicles</a></li>
    <li><a href="http://localhost:8080/Logiweb/drivers">Drivers</a></li>
    <li><a href="http://localhost:8080/Logiweb/orders" class="active">Orders</a></li>
</ul>


<div class="right">
    <table class="resultTable">
        <caption><h1>Orders</h1></caption>
        <th>ID</th>
        <th>Number of items</th>
        <th>Last name</th>
        <c:forEach var="order" items="${list}">
            <tr>
                <td> <a href="http://localhost:8080/Logiweb/order?orderId=${order.uid}" class="tableRef"> ${order.uid}</a>  </td>
                <td> ${order.numberOfItems} </td>
                <td> ${order.isCompleted} </td>
            </tr>
        </c:forEach>
    </table>

    <form>
        <input type="submit" value="Add order">
    </form>

</div>

</body>
</html>
