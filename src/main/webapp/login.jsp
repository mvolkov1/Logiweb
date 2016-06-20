<%--
  Created by IntelliJ IDEA.
  User: mvolkov
  Date: 19.06.2016
  Time: 16:47
  To change this template use File | Settings | File Templates.
--%>
<%--<%@ page contentType="text/html;charset=UTF-8" language="java" %>--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Logiweb</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
    <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>

    <style> <%@include file="WEB-INF/css/body.css" %> </style>


</head>
<body>

<div >
    <h1 > Welcome to Logiweb! </h1>

    <form id="auth" method="post"></form>
    <p><input name="login" placeholder="login" form="auth" value="vkarpin"></p>
    <p><input type="password" name="pass" placeholder="password" form="auth" value="12345"></p>
    <p><input type="submit" form="auth" value="Log in"></p>
</div>





</body>
</html>
