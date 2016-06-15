<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Vehicle</title>

    <style>
        table {
            width: 100%;
        }
        td {
            border: 1px solid #000;
            padding: 4px;
        }
    </style>
    <style type="text/css">
        body {
            font-family: Georgia, "Times New Roman",
            Times, serif;
            color: purple;
            background-color: #d8da3d }
        h1 {
            font-family: Helvetica, Geneva, Arial,
            SunSans-Regular, sans-serif }
    </style>

</head>
<body>
<%--<c:forEach var="vehicle" items="${ListOfVehicles}">--%>
    <%--<tr><td>${vehicle.vin}<td><td>${vehicle.vin}</td></tr>--%>
<%--</c:forEach>--%>
<%--<%String name = (String)request.getAttribute("vin"); %>--%>
<%--<%= name%>--%>

<%--<c:out value='${vin}' />--%>

<table>
    <caption>Available vehicles</caption>
    <th>VIN</th> <th>Capacity</th> <th>Number of drivers</th><th></th>
    <c:forEach var="vehicle" items="${list}">
        <tr>
            <td> ${vehicle.vin} </td>
            <td> ${vehicle.capacity} </td>
            <td> ${vehicle.numberOfDrivers} </td>
        </tr>
    </c:forEach>
</table>

<p>Add vehicle</p>
</body>
</html>
