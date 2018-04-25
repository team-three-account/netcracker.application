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
</head>
<body>
<table>
    <tbody>
    <tr>
        <td>Name:</td>
        <td>${user.name}</td>
    </tr>
    <tr>
        <td>Surname</td>
        <td>${user.surname}</td>
    </tr>
    <tr>
        <td>Date of Birth</td>
        <td>${user.birthdayDate}</td>
    </tr>
    <tr>
        <td>Email</td>
        <td>${user.email}</td>
    </tr>
    <tr>
        <td>Phone Number</td>
        <td>${user.phone}</td>
    </tr>
    </tbody>
    <a href="<c:url value="/account/settings"/>">
        <input type="button" value="Edit information" >
    </a>
    <a href="<c:url value="/account/changePassword"/>">
        <input type="button" value="Change password" >
    </a>



</table>
</body>
</html>
