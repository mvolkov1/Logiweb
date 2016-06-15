<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Vehicle</title>
</head>
<body>
<%--<c:forEach var="vehicle" items="${ListOfVehicles}">--%>
    <%--<tr><td>${vehicle.vin}<td><td>${vehicle.vin}</td></tr>--%>
<%--</c:forEach>--%>
<%String name = (String)request.getAttribute("vin"); %>
<%= name%>

<c:out value='${vin}' />

<table>
    <c:forEach var="vehicle" items="${list}">
        <tr>
            <td> ${vehicle} </td>
        </tr>
    </c:forEach>
</table>

<p>Add vehicle</p>
</body>
</html>
