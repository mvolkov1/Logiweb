<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
    <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>

    <title>Orders</title>
    <style> <%@include file="../css/body.css" %> </style>
    <style> <%@include file="../css/resultTable.css" %> </style>
    <style> <%@include file="../css/nav.css" %> </style>
    <style> <%@include file="../css/right.css" %> </style>
</head>
<body>
<!-- Site navigation menu -->
<ul class="navbar">
    <li><a href="vehicles">Vehicles</a></li>
    <li><a href="drivers">Drivers</a></li>
    <li><a href="orders" class="active">Orders</a></li>
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
        <input type="hidden" name="addOrder" value="true">
    </form>

</div>

</body>
</html>
