<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
    <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>

    <title>Drivers</title>
    <style> <%@include file="../css/body.css" %> </style>
    <style> <%@include file="../css/tables.css" %> </style>
    <style> <%@include file="../css/nav.css" %> </style>
    <style> <%@include file="../css/right.css" %> </style>
    <style> <%@include file="../css/buttons.css" %> </style>
    <script>
        function confirmDelete() {
            return confirm("Delete driver?");
        }
    </script>
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
    <table>
        <tr>
            <td>
                <c:if test="${fn:length(list) > 0}">
                    <table class="resultTable">
                        <caption><h1>Drivers</h1></caption>
                        <th>ID</th>
                        <th>First name</th>
                        <th>Last name</th>
                        <th>Month hours</th>
                        <th>Status</th>
                        <th>City</th>
                        <th>Order ID</th>
                        <th></th>
                        <c:forEach var="driver" items="${list}">
                            <tr>
                                <td><a href="editDriver?uid=${driver.uid}" class="tableRef"> ${driver.uid}</a></td>
                                <td><a href="editDriver?uid=${driver.uid}"
                                       class="tableRef"> ${driver.user.firstName}</a></td>
                                <td><a href="editDriver?uid=${driver.uid}" class="tableRef"> ${driver.user.lastName}</a>
                                </td>
                                <td><a href="editDriver?uid=${driver.uid}" class="tableRef"> ${driver.monthHours}</a>
                                </td>
                                <td><a href="editDriver?uid=${driver.uid}" class="tableRef"> ${driver.status}</a></td>
                                <td><a href="editCity?cityName=${driver.city.name}"
                                       class="tableRef"> ${driver.city.name}</a>
                                </td>
                                <td>
                                    <a href="editOrder?uid=${driver.order.uid}"> ${driver.order.uid}</a>
                                </td>
                                <td><a href="drivers?deleteDriver=true&uid=${driver.uid}"
                                       onclick="return confirmDelete()">Delete</a></td>
                            </tr>
                        </c:forEach>
                    </table>
                    <br>
                </c:if>
                <c:if test="${empty list}">
                    <h3>No drivers found in the database</h3>
                    <br>
                </c:if>
            </td>
        </tr>
        <tr>
            <td>
                <form action="editDriver" method="get">
                    <input type="submit" value="Add new driver" class="buttonAddToTable">
                    <input type="hidden" name="editDriver" value="true">
                </form>
            </td>
        </tr>
    </table>

</div>
<br>
<br>
</body>
</html>
