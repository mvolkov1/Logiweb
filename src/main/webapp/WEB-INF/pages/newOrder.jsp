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
    <style> <%@include file="../css/tables.css" %> </style>
    <style> <%@include file="../css/nav.css" %> </style>
    <style> <%@include file="../css/right.css" %> </style>
    <style> <%@include file="../css/buttons.css" %> </style>


    <script>
        function checkUid() {
            var length = document.getElementById("uid").value.length;
            if (length < 8) {
                alert('UID should contain 8 alphanumeric characters!');
                return false;
            }
        }
    </script>

    <script>
        function onAddOrderItem() {
            if (!document.getElementById("newCity").value) {
                alert('Select city!');
                return false;
            }
            if (!document.getElementById("uid").value) {
                alert('Enter UID first!');
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

    <table class="resultTable">
        <tr>
            <td>
                <c:if test="${empty uid}">
                    <h4>Order UID</h4>
                    <form onsubmit="return checkUid()" action="newOrder" method="post">
                        <input type="text" name="uid" id="uid" placeholder="enter UID">
                        <input type="submit" value="Save" class="buttonSingle">
                        <input type="hidden" name="saveId" value="true">
                        <input type="hidden" name="editOrder" value="true">
                    </form>
                </c:if>
                <c:if test="${not empty uid}">
                    <h4>Order # ${uid}</h4>
                </c:if>

            </td>
        </tr>

        <tr>
            <td>
                <h4>Order items</h4>
                <c:if test="${fn:length(items) > 0}">
                    <table class="resultTable">
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
                <c:if test="${empty items}">
                    No items found in the database for this order
                </c:if>
                <br>
                <c:if test="${empty vehicle}">
                    <form onsubmit="return onAddOrderItem()" action="newOrder" method="post">
                        <select name="newCity" id="newCity">
                            <option value="" disabled selected hidden>Select city...</option>
                            <c:forEach var="city" items="${cities}">
                                <option value="${city.city}">${city.city}</option>
                            </c:forEach>
                        </select>
                        <input type="submit" value="Add order item" class="buttonSingle">
                        <input type="hidden" name="editOrder" value="true">
                        <input type="hidden" name="saveOrderItem" value="true">
                        <input type="hidden" name="uid" value="${uid}">
                    </form>
                </c:if>

            </td>
        </tr>

        <tr>
            <td>
                <h4>Cargos</h4>
                <c:if test="${fn:length(cargos) > 0}">
                    <table class="resultTable">
                            <%--<caption></caption>--%>
                        <th>Id</th>
                        <th>Title</th>
                        <th>Mass</th>
                        <th>Start city</th>
                        <th>End City</th>
                        <c:forEach var="cargo" items="${cargos}">
                            <tr>
                                <td> ${cargo.uid} </td>
                                <td> ${cargo.title} </td>
                                <td> ${cargo.mass} </td>
                                <td> ${cargo.startCity.city} </td>
                                <td> ${cargo.endCity.city} </td>
                                    <%--private String status;--%>
                            </tr>
                        </c:forEach>
                    </table>
                </c:if>
                <c:if test="${empty items}">
                    No cargos found in the database for this order
                </c:if>
                <br>
                <c:if test="${empty vehicle}">
                    <table>
                        <tr>
                            <form action="newOrder" method="post">
                                <td>
                                    <table class="cargoTable">
                                        <tr>
                                            <td><input type="text" name="cargoId" id="cargoId" placeholder="Cargo ID">
                                            </td>
                                        </tr>
                                        <tr>
                                            <td><input type="text" name="cargoTitle" id="cargoTitle"
                                                       placeholder="Title">
                                            </td>
                                        </tr>
                                        <tr>
                                            <td><input type="text" name="cargoMass" id="cargoMass" placeholder="Mass">
                                            </td>
                                        </tr>
                                        <tr>
                                            <td><select name="cargoCity1" id="cargoCity1" class="edit">
                                                <option value="" disabled selected hidden>Select start city...</option>
                                                <c:forEach var="city1" items="${citiesForOrder}">
                                                    <option value="${city1.city}">${city1.city}</option>
                                                </c:forEach>
                                            </select></td>
                                        </tr>
                                        <tr>
                                            <td><select name="cargoCity2" id="cargoCity2" class="edit">
                                                <option value="" disabled selected hidden>Select end city...</option>
                                                <c:forEach var="city2" items="${citiesForOrder}">
                                                    <option value="${city2.city}">${city2.city}</option>
                                                </c:forEach>
                                            </select></td>
                                        </tr>
                                    </table>

                                </td>
                                <td>
                                    <input type="submit" value="Add cargo" class="buttonSingle">
                                    <input type="hidden" name="editOrder" value="true">
                                    <input type="hidden" name="saveCargo" value="true">
                                    <input type="hidden" name="uid" value="${uid}">
                                </td>
                            </form>
                        </tr>
                    </table>
                </c:if>

            </td>
        </tr>

        <tr>
            <td>
                <h4>Vehicle</h4>
                <c:if test="${empty vehicle}">
                    <form action="newOrder" method="post">
                        <select name="vin" id="orderVehicle">
                            <option value="" disabled selected hidden>Select vehicle...</option>
                            <c:forEach var="vehicle1" items="${vehicles}">
                                <option value="${vehicle1.vin}">
                                        ${vehicle1.vin}; number of drivers = ${vehicle1.numberOfDrivers}; capacity
                                    = ${vehicle1.capacity}
                                </option>
                            </c:forEach>
                        </select>
                        <input type="submit" value="Set vehicle" class="buttonSingle">
                        <input type="hidden" name="editOrder" value="true">
                        <input type="hidden" name="saveVehicle" value="true">
                        <input type="hidden" name="uid" value="${uid}">
                    </form>
                </c:if>
                <c:if test="${not empty vehicle}">
                    <c:out value="${vehicle.vin}; number of drivers = ${vehicle.numberOfDrivers}; capacity
                                    = ${vehicle.capacity}"></c:out>
                </c:if>
            </td>
        </tr>

        <tr>
            <td>
                <h4>Drivers</h4>
                <form action="newOrder" method="post">
                    <select name="orderVehicle">
                        <option value="" disabled selected hidden>Select driver...</option>
                        <c:forEach var="driver" items="${drivers}">
                            <option value="${vehicle1.vin}">
                                    ${driver.firstName} ${driver.lastName}; monthHours = ${driver.monthHours};
                            </option>
                        </c:forEach>
                    </select>
                    <input type="submit" value="Add driver" class="buttonSingle">
                    <input type="hidden" name="editOrder" value="true">
                </form>
            </td>
        </tr>

    </table>


</div>
<br>
<br>
</body>
</html>
