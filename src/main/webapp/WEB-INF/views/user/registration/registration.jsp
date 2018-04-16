<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: Artem
  Date: 14.04.2018
  Time: 15:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
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
    <div class="card card-register mx-auto mt-5">
        <div class="card-header">Register an Account</div>
        <div class="card-body">
            <form:form method="POST" modelAttribute="registrationForm"
                       class="forms_form" action="/user/registration">
                <div class="form-group">
                    <div class="col-md-6">
                        <label for="exampleInputEmail1">Email address</label>
                        <form:input path="email" class="form-control" id="exampleInputEmail1" type="email"
                                    aria-describedby="emailHelp"
                                    placeholder="Enter email"/>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-md-6">
                        <label for="exampleInputPassword1">Password</label>
                        <form:input path="password" class="form-control" id="exampleInputPassword1" type="password"
                                    placeholder="Password"/>
                    </div>
                </div>
                <input type="submit" value="Register" class="btn btn-dark" href="/">
            </form:form>
            <div class="text-center">
                <a class="d-block small mt-3" href="">Login Page</a>
                <a class="d-block small" href="">Forgot Password?</a>
            </div>
        </div>
    </div>
</div>
<!-- Bootstrap core JavaScript-->
<script src="${contextPath}/resources/vendor/jquery/jquery.min.js"></script>
<script src="${contextPath}/resources/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
<!-- Core plugin JavaScript-->
<script src="${contextPath}/resources/vendor/jquery-easing/jquery.easing.min.js"></script>
</body>
</html>
