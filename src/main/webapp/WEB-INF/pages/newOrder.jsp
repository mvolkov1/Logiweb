<%--
  Created by IntelliJ IDEA.
  User: mvolkov
  Date: 19.06.2016
  Time: 20:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
    <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>

    <style> <%@include file="../css/body.css" %> </style>
    <style> <%@include file="../css/resultTable.css" %> </style>
    <style> <%@include file="../css/nav.css" %> </style>
    <style> <%@include file="../css/right.css" %> </style>

    <script>
        function checkUid() {
            var length = document.getElementById("orderUid").value.length;
            if (length < 8) {
                alert('UID should contain 8 alphanumeric characters!');
                return false;
            }
        }
    </script>


    <title>New order</title>
</head>
<body>

<!-- Site navigation menu -->
<ul class="navbar">
    <li><a href="vehicles">Vehicles</a></li>
    <li><a href="drivers">Drivers</a></li>
    <li><a href="orders" class="active">Orders</a></li>
</ul>

<div class="right">

    <br>
    <br>

    <form onsubmit="return checkUid()">

        <input type="text" name="orderUid" id="orderUid" placeholder="enter UID">


        <br>

        <table class="resultTable" width='100%'>
            <caption><h1>Items</h1></caption>
            <th>Item number</th>
            <th>City</th>
            <c:forEach var="item" items="${items}" varStatus="status">
                <tr>
                    <td> ${item.index + 1} </td>
                    <td> ${item.city} </td>
                </tr>
            </c:forEach>
        </table>


        <p>


            <select name="newCity">
                <c:forEach var="city" items="${cities}">
                    <option value="${city.city}">${city.city}</option>
                </c:forEach>
            </select>
            <input type="submit" value="Add order item" class="editOrDelete">
            <input type="hidden" name="addOrder" value="true">


        </p>

    </form>

</div>
</body>
</html>
