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
    <style> <%@include file="../css/tables.css" %> </style>
    <style> <%@include file="../css/nav.css" %> </style>
    <style> <%@include file="../css/right.css" %> </style>

    <script> <%@include file="../js/validateDriverInput.js" %> </script>


</head>
<body>

<!-- Site navigation menu -->
<ul class="navbar">
    <li><a href="vehicles">Vehicles</a></li>
    <li><a href="drivers" class="active">Drivers</a></li>
    <li><a href="orders">Orders</a></li>
    <li><a href="cities">Cities</a></li>
</ul>


<div class="right">

    <c:if test="${empty editDriver}">
        <form action="editDriver" method="get">
            <table>
                <caption><h1>Driver info</h1></caption>
                <tr>
                    <td>UID</td>
                    <td><input type="text" value="${uid}" readonly></td>
                </tr>
                <tr>
                    <td>First name</td>
                    <td>
                        <input type="text" value="${firstName}" readonly>
                    </td>
                </tr>
                <tr>
                    <td>Last name</td>
                    <td>
                        <input type="text" value="${lastName}" readonly>
                    </td>
                </tr>
                <tr>
                    <td>Login</td>
                    <td>
                        <input type="text" value="${login}">
                    </td>
                </tr>
                <tr>
                    <td>Password</td>
                    <td>
                        <input type="text"  value="${password}">
                    </td>
                </tr>
                <tr>
                    <td>Month hours</td>
                    <td><input type="text" value="${monthHours}" readonly></td>
                </tr>
                <tr>
                    <td>City</td>
                    <td><input type="text" value="${driverCity}" readonly></td>
                </tr>
                <tr>
                    <td>Status</td>
                    <td><input type="text" value="${status}" readonly></td>
                </tr>
            </table>
            <br>
            <input type="hidden" name="uid" value="${uid}">
            <input type="submit" name="editDriver" value="Edit">
            &nbsp;
            <input type="submit" name="deleteDriver" value="Delete" onclick="return confirmDeleteDriver()">
        </form>
    </c:if>

    <c:if test="${not empty editDriver}">
        <form action="drivers" method="post">
            <table>
                <caption><h1>Edit driver</h1></caption>
                <tr>
                    <td>UID</td>
                    <td><input type="text" name="uid" id="uid" value="${uid}"></td>
                </tr>
                <tr>
                    <td>First name</td>
                    <td>
                        <input type="text" name="firstName" uid="firstName" value="${firstName}">
                    </td>
                </tr>
                <tr>
                    <td>Last name</td>
                    <td>
                        <input type="text" name="lastName" uid="lastName" value="${lastName}">
                    </td>
                </tr>
                <tr>
                    <td>Login</td>
                    <td>
                        <input type="text" name="login" uid="login" value="${login}">
                    </td>
                </tr>
                <tr>
                    <td>Password</td>
                    <td>
                        <input type="text" name="password" uid="password" value="${password}">
                    </td>
                </tr>
                <tr>
                    <td>Month hours</td>
                    <td><input type="text" name="monthHours" id="monthHours" value="${monthHours}"></td>
                </tr>
                <tr>
                    <td>City</td>
                    <td>
                        <select name="city" class="edit">
                            <c:forEach var="city1" items="${cities}">
                                <option value="${city1.name}" ${city1.name.equals(driverCity) ? 'selected="selected"' : ''}>${city1.name}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>Status</td>
                    <td>
                        <select name="status1" class="edit">
                            <option value="driving" ${status == "driving" ? 'selected="selected"':''}>driving</option>
                            <option value="busy" ${status == "busy" ? 'selected="selected"':''}>busy</option>
                            <option value="free" ${status == "free" ? 'selected="selected"':''}>free</option>
                        </select>
                    </td>
                </tr>
            </table>
            <br>
            <input type="submit" name="saveDriver" value="Save" onclick="return validateDriverInput()">
            <input type="hidden" name="oldUid" value="${uid}">
            &nbsp;
            <input type="submit" name="cancelEditDriver" value="Cancel">

        </form>
    </c:if>

</div>


</body>
</html>
