<%--
  Created by IntelliJ IDEA.
  User: mvolkov
  Date: 17.06.2016
  Time: 15:08
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

    <title>Edit vehicle</title>
    <style> <%@include file="../css/body.css" %> </style>
    <style> <%@include file="../css/tables.css" %> </style>
    <style> <%@include file="../css/nav.css" %> </style>
    <style> <%@include file="../css/right.css" %> </style>

    <script> <%@include file="../js/validateVehicleInput.js" %> </script>

</head>
<body>

<!-- Site navigation menu -->
<ul class="navbar">
    <li><a href="vehicles" class="active">Vehicles</a></li>
    <li><a href="drivers">Drivers</a></li>
    <li><a href="orders">Orders</a></li>
</ul>



<div class="right">

    <form onsubmit="return validateVehicleInput()" action="vehicles" method="post">
        <table>
            <caption><h1>Edit vehicle</h1></caption>
            <tr>
                <td>VIN</td>
                <td><input type="text" name="vin" id="vin" value="${vin}" ></td>
            </tr>
            <tr>
                <td>Capacity</td>
                <td><input type="text" name="capacity"  id="capacity" value="${capacity}" ></td>
            </tr>
            <tr>
                <td >Number of drivers</td>
                <td><input type="text" name="numberOfDrivers" id="numberOfDrivers" value="${numberOfDrivers}" width="300px"></td>
            </tr>
            <tr>
                <td>City</td>
                <td>
                    <select name="city" class="edit" id="city">
                        <c:forEach var="city1" items="${cities}">
                            <option value="" disabled hidden  ${vehicleCity == null ? 'selected="selected"' : ''} >Select city...</option>
                            <option value="${city1.city}" ${city1.city == vehicleCity ? 'selected="selected"' : ''}>${city1.city}</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr>
                <td>Is available?</td>
                <td>
                    <select name="isAvailable1" class="edit">
                        <option value="yes" ${isAvailable == "yes" ? 'selected="selected"':''}>yes</option>
                        <option value="no" ${isAvailable == "no" ? 'selected="selected"':''}>no</option>
                    </select>
                </td>
            </tr>
        </table>
        <input type="hidden" name="saveVehicle" value="true">
        <input type="submit" value="Save">
        <input type="hidden" name="oldVin" value="${vin}">

    </form>

</div>


</body>
</html>
