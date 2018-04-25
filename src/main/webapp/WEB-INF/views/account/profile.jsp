<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Alina
  Date: 25.04.2018
  Time: 13:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Info</title>
    <link href="${contextPath}/resources/bootstrap3/css/bootstrap.min.css" rel="stylesheet">
    <!-- Custom fonts for this template-->
    <link href="${contextPath}/resources/css/style.css" rel="stylesheet">
    <!-- Custom styles for this template-->
</head>
<body>
<div class="row">
    <jsp:include page="${contextPath}/WEB-INF/views/account/navbar/navbar.jsp"/>
    <div class="col-md-3">
        <jsp:include page="${contextPath}/WEB-INF/views/account/menu/menu.jsp"/>
    </div>
    <div class="col-md-9 content">
        <table class="table">
            <tbody>
            <tr>
                <td>Name:</td>
                <td>${auth_user.name}</td>
            </tr>
            <tr>
                <td>Surname</td>
                <td>${auth_user.surname}</td>
            </tr>
            <tr>
                <td>Date of Birth</td>
                <td>${auth_user.birthdayDate}</td>
            </tr>
            <tr>
                <td>Email</td>
                <td>${auth_user.email}</td>
            </tr>
            <tr>
                <td>Phone Number</td>
                <td>${auth_user.phone}</td>
            </tr>
            </tbody>
            <a href="<c:url value="/account/settings"/>">
                <input type="button" class="btn btn-dark" value="Edit information">
            </a>
            <a href="<c:url value="/account/resetpassword"/>">
                <input type="button" class="btn btn-outline-dark" value="Change password">
            </a>
        </table>
    </div>
</body>
</html>
