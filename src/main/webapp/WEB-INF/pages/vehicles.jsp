<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
    <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>

    <title>Vehicles</title>
    <style> <%@include file="../css/body.css" %> </style>
    <style> <%@include file="../css/resultTable.css" %> </style>
    <style> <%@include file="../css/nav.css" %> </style>
    <style> <%@include file="../css/right.css" %> </style>
    <style> <%@include file="../css/editOrDelete.css" %> </style>
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
    <table class="resultTable" width='100%'>
        <caption><h1>Vehicles</h1></caption>
        <th>VIN</th>
        <th>Capacity</th>
        <th>Number of drivers</th>
        <th>City</th>
        <th>Is available? </th>
        <th>Order ID</th>


        <c:forEach var="vehicle" items="${list}">
            <tr>
                <td> ${vehicle.vin} </td>
                <td> ${vehicle.capacity} </td>
                <td> ${vehicle.numberOfDrivers} </td>
                <td> ${vehicle.city.city} </td>
                <td>${vehicle.isAvailable == 0 ? "no" : "yes"}</td>
                <td><a href="order?orderId=${vehicle.order.uid}"> ${vehicle.order.uid}</a>
                </td>
                <td align="center">
                    <form>
                        <input type="submit" value="Edit" class="editOrDelete">
                        <input type="hidden" name="editVehicle" value="true">
                        <input type="hidden" name="vin" value=${vehicle.vin}>
                    </form>
                </td>
                <td align="center">
                    <form onsubmit="return confirmDelete()">
                        <input type="submit" value="Delete" class="editOrDelete">
                        <input type="hidden" name="deleteVehicle" value="true">
                        <input type="hidden" name="vin" value=${vehicle.vin}>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>


    <p>

    <form>
        <input type="submit" value="Add new vehicle"  class="editOrDelete">
        <input type="hidden" name="newVehicle" value="true">
    </form>

    </p>

</div>

</body>
</html>
