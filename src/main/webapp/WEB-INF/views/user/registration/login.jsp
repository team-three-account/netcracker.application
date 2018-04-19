<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: Artem
  Date: 15.04.2018
  Time: 20:07
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
<div class="container">
    <div class="card card-register mx-auto mt-5 col-md-6">
        <div class="card-header">Login an Account</div>
        <div class="card-body">
            <form method="POST"
                  class="forms_form" action="/login">
                <div class="form-group">
                    <label>Email address</label>
                    <input name="username" class="form-control"
                           type="email"
                           aria-describedby="emailHelp"
                           placeholder="Enter email"/>
                </div>
                <div class="form-group">
                    <label>Password</label>
                    <input name="password" class="form-control"
                           type="password"
                           placeholder="Password"/>
                    <span id="error">${error}</span>
                    <span id="message">${message}</span>
                </div>
                <input type="submit" value="Login" class="btn btn-dark" href="/">
            </form>
        </div>
    </div>
</div>
<!-- Bootstrap core JavaScript-->
<script src="${contextPath}/resources/vendor/bootstrap/js/jquery-1.11.1.min.js"></script>
<script src="${contextPath}/resources/vendor/bootstrap/js/jquery.appear.js"></script>
<script src="${contextPath}/resources/vendor/bootstrap/js/jquery.maskedinput.min.js"></script>
<script src="${contextPath}/resources/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
</body>
</html>
