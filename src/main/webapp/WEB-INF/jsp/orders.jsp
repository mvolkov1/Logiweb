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
    <script>
        function confirmDelete() {
            return confirm("Delete order?");
        }
    </script>
</head>
<body>
<!-- Site navigation menu -->
<ul class="navbar">
    <li><a href="vehicles">Vehicles</a></li>
    <li><a href="drivers">Drivers</a></li>
    <li><a href="orders" class="active">Orders</a></li>
</ul>


<div class="right">
    <table>
        <tr><td>
            <c:if test="${fn:length(list) > 0}">
                <table class="resultTable">
                    <caption><h1>Orders</h1></caption>
                    <th>ID</th>
                    <th>Vehicle</th>
                    <th>Status</th>
                    <th></th>
                    <c:forEach var="order" items="${list}">
                        <tr>
                            <td><a href="editOrder?uid=${order.uid}" class="tableRef"> ${order.uid}</a></td>
                            <td><a href="editVehicle?vin=${order.getVehicleVin()}"> ${order.getVehicleVin()}</a></td>
                            <td> ${order.getIsCompleted()==0 ? 'not completed' : 'completed'} </td>
                            <td><a href="orders?deleteOrder=true&uid=${order.uid}">Delete</a></td>
                        </tr>
                    </c:forEach>
                </table>
                <br>
            </c:if>
            <c:if test="${empty list}">
                <h3>No orders found in the database</h3>
                <br>
            </c:if>
        </td></tr>
        <tr>
            <td>
            <form action="editOrder" method="get">
                <input type="submit" value="Add order" class="buttonAddToTable">
            </form>
            </td>
        </tr>
    </table>

</div>
<br>
<br>
</body>
</html>
