<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
    <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>

    <title>Edit driver</title>
    <style> <%@include file="../css/body.css" %> </style>
    <style> <%@include file="../css/resultTable.css" %> </style>
    <style> <%@include file="../css/nav.css" %> </style>
    <style> <%@include file="../css/right.css" %> </style>

    <script> <%@include file="../js/validateDriverInput.js" %> </script>

</head>
<body>

<!-- Site navigation menu -->
<ul class="navbar">
    <li><a href="vehicles" class="active">Vehicles</a></li>
    <li><a href="drivers">Drivers</a></li>
    <li><a href="orders">Orders</a></li>
</ul>



<div class="right">

    <form onsubmit="return validateDriverInput()">
        <table>
            <caption><h1>Edit driver</h1></caption>
            <tr>
                <td>UID</td>
                <td><input type="text" name="uid" id="uid" value="${uid}"></td>
            </tr>
            <tr>
                <td>Month hours</td>
                <td><input type="text" name="monthHours"  id="monthHours" value="${monthHours}"></td>
            </tr>
            <tr>
                <td>City</td>
                <td>
                    <select name="city">
                        <c:forEach var="city1" items="${cities}">
                            <option value="${city1.city}" ${city1.city == driverCity ? 'selected="selected"' : ''}>${city1.city}</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr>
                <td>Status</td>
                <td>
                    <select name="status1">
                        <option value="driving" ${status == "driving" ? 'selected="selected"':''}>driving</option>
                        <option value="busy" ${status == "busy" ? 'selected="selected"':''}>busy</option>
                        <option value="free" ${status == "free" ? 'selected="selected"':''}>free</option>
                    </select>
                </td>
            </tr>
        </table>
        <input type="hidden" name="saveDriver" value="true">
        <input type="submit" value="Save">

    </form>

</div>


</body>
</html>
