<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
    <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>

    <title>Drivers</title>
    <style> <%@include file="../css/body.css" %> </style>
    <style> <%@include file="../css/resultTable.css" %> </style>
    <style> <%@include file="../css/nav.css" %> </style>
    <style> <%@include file="../css/right.css" %> </style>
    <style> <%@include file="../css/editOrDelete.css" %> </style>
    <script>
        function confirmDelete() {
            return confirm("Delete driver?");
        }
    </script>
</head>
<body>

<!-- Site navigation menu -->
<ul class="navbar">
    <li><a href="vehicles">Vehicles</a></li>
    <li><a href="drivers" class="active">Drivers</a></li>
    <li><a href="orders">Orders</a></li>
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
        <th>Order ID</th>
        <c:forEach var="driver" items="${list}">
            <tr>
                <td> ${driver.uid} </td>
                <td> ${driver.user.firstName} </td>
                <td> ${driver.user.lastName} </td>
                <td> ${driver.monthHours} </td>
                <td> ${driver.status} </td>
                <td> ${driver.city.city} </td>
                <td>
                    <a href="order?orderId=${driver.order.uid}"> ${driver.order.uid}</a>
                </td>
                <td align="center">
                    <form>
                        <input type="submit" value="Edit" class="editOrDelete">
                        <input type="hidden" name="editDriver" value="true">
                        <input type="hidden" name="uid" value=${driver.uid}>
                    </form>
                </td>
                <td align="center">
                    <form onsubmit="return confirmDelete()">
                        <input type="submit" value="Delete" class="editOrDelete">
                        <input type="hidden" name="deleteDriver" value="true">
                        <input type="hidden" name="uid" value=${driver.uid}>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
    <%--<p>--%>
    <%--<form>--%>
        <%--<input type="submit" value="Add new driver" class="editOrDelete">--%>
        <%--<input type="hidden" name="newDriver" value="true">--%>
    <%--</form>--%>
    <%--</p>--%>

</div>

</body>
</html>
