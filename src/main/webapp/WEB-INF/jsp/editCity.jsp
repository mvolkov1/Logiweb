<%--
  Created by IntelliJ IDEA.
  User: mvolkov
  Date: 24.07.2016
  Time: 21:11
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
    <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>

    <title>Edit city</title>
    <style> <%@include file="../css/body.css" %> </style>
    <style> <%@include file="../css/tables.css" %> </style>
    <style> <%@include file="../css/nav.css" %> </style>
    <style> <%@include file="../css/right.css" %> </style>
    <style> <%@include file="../css/buttons.css" %> </style>

</head>
<body>
<!-- Site navigation menu -->
<ul class="navbar">
    <li><a href="vehicles">Vehicles</a></li>
    <li><a href="drivers">Drivers</a></li>
    <li><a href="orders">Orders</a></li>
    <li><a href="cities" class="active">Cities</a></li>
</ul>


<div class="right">

    <h3>Neighbor cities for ${cityName}</h3>
    <br>

    <table>
        <tr>
            <td>

                <table class="resultTable">
                    <th>City</th>
                    <th>Distance</th>
                    <th></th>
                    <tr>
                        <form action="editCity" method="post">
                            <td>
                                <select name="newNeighbor" id="newNeighbor">
                                    <option value="" disabled selected hidden>Select city...</option>
                                    <c:forEach var="possibleNeighbor" items="${possibleNeighbors}">
                                        <option value="${possibleNeighbor.name}">${possibleNeighbor.name}</option>
                                    </c:forEach>
                                </select>
                            </td>
                            <td>
                                <input type="text" name="distance">
                            </td>
                            <td>
                                <input type="submit" name="addItem" value="Add distance" class="buttonInCell">
                                <input type="hidden" name="cityName" value="${cityName}">
                            </td>
                        </form>
                    </tr>
                    <c:forEach var="neighbor" items="${neighbors}">
                        <tr>
                            <td><a href="editCity?cityName=${neighbor.key}" class="tableRef"> ${neighbor.key}</a></td>
                            <td>${neighbor.value}</td>
                            <td><a href="editCity?deleteCity=true&cityName=${neighbor.key}"
                                   onclick="return confirmDelete()">Delete</a></td>
                        </tr>
                    </c:forEach>
                </table>
                <br>

            </td>
        </tr>

    </table>


</div>
<br>
<br>
</body>
</html>
