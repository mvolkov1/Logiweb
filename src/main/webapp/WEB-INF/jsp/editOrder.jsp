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

    <script> <%@include file="../js/validateOrderInput.js" %> </script>


    <title>Edit order</title>
</head>
<body>

<!-- Site navigation menu -->
<ul class="navbar">
    <li><a href="vehicles">Vehicles</a></li>
    <li><a href="drivers">Drivers</a></li>
    <li><a href="orders" class="active">Orders</a></li>
</ul>

<div class="right">


    <form action="editOrder" method="post" onsubmit="return validateUid()">
        <h4>Order UID</h4>
        <input type="text" name="uid" value="${uid}" id="uid" placeholder="enter UID">
        <input type="submit" value="Save" class="buttonSingle">
        <input type="hidden" name="oldUid" value="${uid}">
        <input type="hidden" name="step" value="uid">
    </form>
    <br>


    <h4>Start date and time</h4>
    <form method="post">
        <table>
            <tr>
                <td>
                    <table class="editOrderTable">
                        <th align="center">Start date</th>
                        <th align="center">Start time</th>
                        <tr>
                            <td align="center">
                                <select name="day" id="day">
                                    <c:forEach var="day1" items="${days}">
                                        <option value="${day1}"
                                        ${day1.equals(startDay)? 'selected="selected"' : ''}>${day1}</option>
                                    </c:forEach>
                                </select>
                                <select name="month" id="month">
                                    <c:forEach var="month1" items="${months}">
                                        <option value="${month1}"
                                            ${month1.equals(startMonth)? 'selected="selected"' : ''}>${month1}</option>
                                    </c:forEach>
                                </select>
                                <select name="year" id="year">
                                    <c:forEach var="year1" items="${years}">
                                        <option value="${year1}"
                                            ${year1.equals(startYear)? 'selected="selected"' : ''}>${year1}</option>
                                    </c:forEach>
                                </select>
                            </td>
                            <td align="center">
                                <select name="hour" id="hour">
                                    <c:forEach var="hour1" items="${hours}">
                                        <option value="${hour1}"
                                            ${hour1.equals(startHour)? 'selected="selected"' : ''}>${hour1}</option>
                                    </c:forEach>
                                </select>
                            </td>
                        </tr>
                    </table>
                </td>
                <td>
                    &nbsp
                    <input type="submit" name="startTime" value="Save" class="buttonSingle">
                    <input type="hidden" name="uid" value="${uid}">
                    <input type="hidden" name="step" value="startTime">
                </td>
            </tr>
        </table>
    </form>
    <br>

    <h4>Order items</h4>
    <table class="editOrderTable" id="itemsTable">
        <th>Item</th>
        <th>City</th>
        <th>Distance, km</th>
        <th>Full distance, km</th>
        <th> Full time, h</th>
        <th></th>
        <c:forEach var="item" items="${items}">
            <tr>
                <td style="width:8ch"> ${item.itemNumber} </td>
                <td> ${item.city.name} </td>
                <td>${item.distance}</td>
                <td>${item.fullDistance}</td>
                <td>${item.fullTime}</td>
                <td>
                    <c:if test="${item.itemNumber eq items.size()}">
                        <form action="editOrder" method="post" onsubmit="return confirmDeleteItem()">
                            <input type="submit" value="Delete" class="buttonInCell">
                            <input type="hidden" name="step" value="deleteItem">
                            <input type="hidden" name="itemNumber" value="${item.itemNumber}">
                            <input type="hidden" name="uid" value="${uid}">
                        </form>
                    </c:if>
                </td>
            </tr>
        </c:forEach>
        <tr>
            <form action="editOrder" method="post" onsubmit="return validateItem()">
                <td>
                    <input type="text" readonly="readonly" placeholder="${nextItemNumber}"
                           class="editInCell">
                </td>
                <td>
                    <select name="city" id="city" class="selectInCell">
                        <option value="" disabled selected hidden>Select city...</option>
                        <c:forEach var="city1" items="${cities}">
                            <option value="${city1.name}">${city1.name}</option>
                        </c:forEach>
                    </select>
                </td>
                <td></td>
                <td></td>
                <td></td>
                <td>
                    <input type="submit" name="addItem" value="Add order item" class="buttonInCell">
                    <input type="hidden" name="uid" value="${uid}">
                    <input type="hidden" name="step" value="item">
                </td>
            </form>
        </tr>
    </table>
    <br>

    <h4><c:out value="End time: ${endTime}"></c:out></h4>
    <br>


    <h4>Cargoes</h4>
    <table class="editOrderTable" id="cargoesTable">
        <th style="width:15ch">UID</th>
        <th style="width:20ch">Title</th>
        <th style="width:8ch">Mass</th>
        <th style="width:10ch">From item</th>
        <th style="width:10ch">To item</th>
        <th style="width:12ch">Status</th>
        <th style="width:12ch"></th>
        <c:forEach var="cargo" items="${cargoes}">
            <tr>
                <td> ${cargo.uid} </td>
                <td> ${cargo.title} </td>
                <td> ${cargo.mass} </td>
                <td> ${cargo.itemStart.itemNumber} </td>
                <td> ${cargo.itemEnd.itemNumber} </td>
                <td>${cargo.status}</td>
                <td>
                    <form action="editOrder" method="post" onsubmit="return confirmDeleteCargo()">
                        <input type="submit" value="Delete" class="buttonInCell">
                        <input type="hidden" name="step" value="deleteCargo">
                        <input type="hidden" name="cargoUid" value="${cargo.uid}">
                        <input type="hidden" name="uid" value="${uid}">
                    </form>
                </td>
            </tr>
        </c:forEach>
        <tr>
            <form action="editOrder" method="post" onsubmit="return validateCargo()">
                <td><input type="text" name="cargoUid" id="cargoUid"
                           placeholder="Cargo ID" class="editInCell">
                </td>
                <td><input type="text" name="cargoTitle" id="cargoTitle"
                           placeholder="Title" class="editInCell">
                </td>
                <td><input type="text" name="cargoMass" id="cargoMass"
                           placeholder="Mass" class="editInCell">
                </td>
                <td><select name="cargoItem1" id="cargoItem1" class="selectInCell">
                    </option>
                    <c:forEach var="item1" items="${itemNumbers}">
                        <option value="${item1}">${item1}</option>
                    </c:forEach>
                </select></td>
                <td><select name="cargoItem2" id="cargoItem2" class="selectInCell">
                    </option>
                    <c:forEach var="item2" items="${itemNumbers}">
                        <option value="${item2}">${item2}</option>
                    </c:forEach>
                </select></td>
                <td><select name="status" id="status" class="selectInCell">
                    </option>
                    <c:forEach var="status" items="${statusList}">
                        <option value="${status}" ${status.equals("Prepared")? 'selected="selected"' : ''}>${status}</option>
                    </c:forEach>
                </select></td>
                <td>
                    <input type="submit" value="Add cargo" class="buttonInCell">
                    <input type="hidden" name="uid" value="${uid}">
                    <input type="hidden" name="step" value="cargo">
                </td>
            </form>
        </tr>
    </table>
    <br>


    <h4>Vehicle</h4>
    <table class="editOrderTable">
        <c:if test="${not empty vehicle}">
            <tr>
                <td>
                        ${vehicle.vin}; ${vehicle.numberOfDrivers} drivers;
                        ${vehicle.capacity} t; ${vehicle.city.name}
                </td>
                <td>
                    <form action="editOrder" method="post">
                        <input type="submit" value="Delete vehicle" class="buttonInCell">
                        <input type="hidden" name="uid" value="${uid}">
                        <input type="hidden" name="step" value="deleteVehicle">
                    </form>
                </td>
            </tr>
        </c:if>
        <tr>
            <form action="editOrder" method="post" onsubmit="return validateVehicle()">
                <td>
                    <select name="vin" id="vin">
                        <option value="" disabled selected hidden>Select vehicle...</option>
                        <c:forEach var="vehicle1" items="${vehicles}">
                            <option value="${vehicle1.vin}" ${vehicle1.vin.equals(vehicleVin)? 'selected="selected"' : ''}>
                                    ${vehicle1.vin}; ${vehicle1.numberOfDrivers} drivers;
                                    ${vehicle1.capacity} t; ${vehicle1.city.name}
                            </option>
                        </c:forEach>
                    </select>
                </td>
                <td>
                    <input type="submit" value="Set vehicle" class="buttonInCell">
                    <input type="hidden" name="uid" value="${uid}">
                    <input type="hidden" name="step" value="vehicle">
                </td>
            </form>
        </tr>
    </table>
    <br>


    <h4>Drivers</h4>
    <table class="editOrderTable">
        <c:forEach var="driver1" items="${drivers}">
            <tr>
                <td>
                    <c:out value="${driver1.user.firstName} ${driver1.user.lastName} (${driver1.uid});
                    ${driver1.city.name}; ${driver1.monthHours} h"></c:out>
                </td>
                <td align="center" method="post">
                    <form action="editOrder" method="post" onsubmit="return confirmDeleteDriver()">
                        <input type="submit" value="Delete" class="buttonInCell">
                        <input type="hidden" name="step" value="deleteDriver">
                        <input type="hidden" name="driverUid" value="${driver1.uid}">
                        <input type="hidden" name="uid" value="${uid}">
                    </form>
                </td>
            </tr>
        </c:forEach>
        <c:if test="${not empty possibleDrivers}">
            <tr>
                <form method="post" onsubmit="return validateDriver()">
                    <td>
                        <select name="driverUid" id="driverUid">
                            <option value="" disabled selected hidden>Select driver...</option>
                            <c:forEach var="driver1" items="${possibleDrivers}">
                                <option value="${driver1.uid}">
                                        ${driver1.user.firstName} ${driver1.user.lastName} (${driver1.uid});
                                        ${driver1.city.name}; ${driver1.monthHours} h
                                </option>
                            </c:forEach>
                        </select>
                    </td>
                    <td>
                        <input type="submit" value="Add driver" class="buttonInCell">
                        <input type="hidden" name="uid" value="${uid}">
                        <input type="hidden" name="step" value="driver">
                    </td>
                </form>
            </tr>
        </c:if>
    </table>
    <br>

</div>
</body>
</html>
