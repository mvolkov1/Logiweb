<%--
  Created by IntelliJ IDEA.
  User: mvolkov
  Date: 19.06.2016
  Time: 20:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
            var length = document.getElementById("uid").value.length;
            if (length < 8) {
                alert('UID should contain 8 alphanumeric characters!');
                return false;
            }
        }
    </script>

    <style>
        select:invalid {
            color: gray;
        }
    </style>


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
    <c:if test="${empty uid}">
        <form onsubmit="return checkUid()">
            <h2>Order UID</h2>
            <input type="text" name="uid" id="uid" placeholder="enter UID">
            <input type="submit" value="Save" class="editOrDelete">
            <input type="hidden" name="saveId" value="true">
            <input type="hidden" name="editOrder" value="true">
        </form>
    </c:if>
    <c:if test="${not empty uid}">
        <h1>Order # ${uid}</h1>
    </c:if>

    <br>
    <br>
    <h2>Items</h2>
    <form>
        <c:if test="${fn:length(items) > 0}">
            <table class="resultTable" width='100%'>
                    <%--<caption></caption>--%>
                <th>Item number</th>
                <th>City</th>
                <c:forEach var="item" items="${items}">
                    <tr>
                        <td> ${item.itemNumber} </td>
                        <td> ${item.city.city} </td>
                    </tr>
                </c:forEach>
            </table>
        </c:if>
        <select name="newCity">
            <option value="" disabled selected hidden>Select city...</option>
            <c:forEach var="city" items="${cities}">
                <option value="${city.city}">${city.city}</option>
            </c:forEach>
        </select>
        <input type="submit" value="Add order item" class="editOrDelete">
        <input type="hidden" name="editOrder" value="true">
        <input type="hidden" name="saveOrderItem" value="true">
        <input type="hidden" name="uid" value="${uid}">
    </form>


    <form>
        <br>
        <br>
        <h2>Vehicle</h2>
        <select name="orderVehicle">
            <option value="" disabled selected hidden>Select vehicle...</option>
            <c:forEach var="vehicle1" items="${vehicles}">
                <option value="${vehicle1.vin}">
                        ${vehicle1.vin}; number of drivers = ${vehicle1.numberOfDrivers}; capacity
                    = ${vehicle1.capacity}
                </option>
            </c:forEach>
        </select>
        <input type="submit" value="Save" class="editOrDelete">
        <input type="hidden" name="addOrder" value="true">


        <br>
        <br>
        <h2>Cargos</h2>
        <c:if test="${fn:length(cargos) > 0}">
            <table class="resultTable" width='100%'>
                    <%--<caption></caption>--%>
                <th>Id</th>
                <th>Title</th>
                <th>Mass</th>
                <th>Start city</th>
                <th>End City</th>
                <c:forEach var="cargo" items="${cargos}">
                    <tr>
                        <td> ${cargo. uid} </td>
                        <td> ${cargo. title} </td>
                        <td> ${cargo. mass} </td>
                        <td> ${cargo. startCity.city} </td>
                        <td> ${cargo. endCity.city} </td>
                            <%--private String status;--%>
                    </tr>
                </c:forEach>
            </table>
        </c:if>
        <input type="text" name="cargoId" placeholder="Cargo ID">
        <input type="submit" value="Add cargo" class="editOrDelete">
        <input type="hidden" name="addOrder" value="true"><br>
        <input type="text" name="cargoTitle" placeholder="Title"><br>
        <input type="text" name="cargoMass" placeholder="Mass"><br>
        <select name="cargoCity1">
            <option value="" disabled selected hidden>Select start city...</option>
            <c:forEach var="item" items="${items}">
                <option value="${item.city.city}">${item.city.city}</option>
            </c:forEach>
        </select><br>
        <select name="cargoCity2">
            <option value="" disabled selected hidden>Select end city...</option>
            <c:forEach var="item" items="${items}">
                <option value="${item.city.city}">${item.city.city}</option>
            </c:forEach>
        </select>

        <br>
        <br>
        <h2>Drivers</h2>

    </form>

</div>
</body>
</html>
