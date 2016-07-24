<%--
  Created by IntelliJ IDEA.
  User: mvolkov
  Date: 24.07.2016
  Time: 20:57
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

    <title>Cities</title>
    <style> <%@include file="../css/body.css" %> </style>
    <style> <%@include file="../css/tables.css" %> </style>
    <style> <%@include file="../css/nav.css" %> </style>
    <style> <%@include file="../css/right.css" %> </style>
    <style> <%@include file="../css/buttons.css" %> </style>

    <script> <%@include file="../js/validateCityInput.js" %> </script>

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

    <table>
        <tr>
            <td>
                <table class="resultTable">
                    <caption><h1>Cities</h1></caption>
                    <th>City</th>
                    <th></th>
                    <tr>
                        <form action="cities" method="post">
                            <td><input type="text" name="newCity" id="newCity"></td>
                            <td>
                                <input type="submit" value="Add new city" class="buttonInCell" onclick="return validateCity()">
                            </td>
                        </form>
                    </tr>
                    <c:forEach var="city1" items="${list}">
                        <tr>
                            <td><a href="editCity?cityName=${city1.name}" class="tableRef"> ${city1.name}</a></td>
                            <td><a href="cities?deleteCity=true&name=${city1.name}"
                                   onclick="return confirmDeleteCity()">Delete</a></td>
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
