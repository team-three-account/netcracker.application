<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: Artem
  Date: 15.04.2018
  Time: 20:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link href="${contextPath}/resources/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <!-- Custom fonts for this template-->
    <link href="${contextPath}/resources/vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
    <!-- Custom styles for this template-->
    <link href="${contextPath}/resources/css/sb-admin.css">
</head>
<body>
<h2>${auth_user.name} ${auth_user.email}</h2>
<h2>${auth_user}</h2>
<a href="/logout" class="btn btn-dark text-center" >Logout</a>

    <a href="/account/resetpassword" type="submit" class="btn btn-dark text-center" >Change Password</a>


</body>
</html>
