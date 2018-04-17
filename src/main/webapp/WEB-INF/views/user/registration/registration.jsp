<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
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
    <div class="card card-register mx-auto mt-5 col-md-6">
        <div class="card-header">Register an Account</div>
        <div class="card-body">
            <form:form method="POST" modelAttribute="registrationForm"
                       class="forms_form" action="/user/registration/post">
                <div class="form-group">
                    <label>Name</label>
                    <form:input path="name" name="name" class="form-control"
                                type="name"
                                placeholder="Enter name"/>
                    <form:errors path="name" cssClass="error"/>
                </div>
                <div class="form-group">
                    <label>Surname</label>
                    <form:input path="surname" name="surname" class="form-control"
                                type="surname"
                                placeholder="Enter surname"/>
                    <form:errors path="surname" cssClass="error"/>
                </div>
                <div class="form-group">
                    <label>Email address</label>
                    <form:input path="email" name="email" class="form-control"
                                type="email"
                                aria-describedby="emailHelp"
                                placeholder="Enter email"/>
                    <form:errors path="email" cssClass="error"/>
                </div>
                <div class="form-group">
                    <label>Password</label>
                    <form:input path="password" name="password" class="form-control"
                                type="password"
                                placeholder="Password"/>
                    <form:errors path="password" cssClass="error"/>
                </div>
                <div class="form-group">
                    <label>Confirm password</label>
                    <form:input path="confirmPassword" name="confirmPassword" class="form-control"
                                type="password"
                                placeholder="Confirm Password"/>
                    <form:errors path="confirmPassword" cssClass="error"/>
                </div>

                <div class="form-group">
                    <label class="control-label">Date</label>
                    <div class="input-group date" data-provide="datepicker">
                        <form:input type="text" class="form-control" path="birthdayDate"/>
                        <div class="input-group-addon">
                            <span class="glyphicon glyphicon-th"></span>
                            <form:errors path="birthdayDate" cssClass="error"/>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label>Phone</label>
                    <form:input path="phone" name="phone" class="form-control"
                                type="phone"
                                aria-describedby="emailHelp"
                                placeholder="Enter phone"/>
                    <form:errors path="phone" cssClass="error"/>
                </div>
                <input type="submit" value="Register" class="btn btn-dark text-center">
            </form:form>
        </div>
    </div>
</div>

<!-- Bootstrap core JavaScript-->
<script type="text/javascript" src="https://code.jquery.com/jquery-1.11.3.min.js"></script>
<script src="${contextPath}/resources/vendor/jquery/jquery.min.js"></script>
<script src="${contextPath}/resources/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
<!-- Core plugin JavaScript-->
<script type="text/javascript"
        src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.4.1/js/bootstrap-datepicker.min.js"></script>
<link rel="stylesheet"
      href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.4.1/css/bootstrap-datepicker3.css"/>
<script src="${contextPath}/resources/vendor/jquery-easing/jquery.easing.min.js"></script>
</body>
</html>
