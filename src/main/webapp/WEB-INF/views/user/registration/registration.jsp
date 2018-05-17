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
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>Registration</title>
    <link href="${contextPath}/resources/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <!-- Custom fonts for this template-->
    <link href="${contextPath}/resources/vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
    <!-- Custom styles for this template-->
    <link href="${contextPath}/resources/css/sb-admin.css">
    <link href="${contextPath}/resources/css/style.css" rel="stylesheet">

    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <script src="https://apis.google.com/js/platform.js" async defer></script>
    <meta name="google-signin-client_id" content="829562763201-3hn7scp4j1c9u7hvlherebi8e56pv9va.apps.googleusercontent.com">
</head>
<body>
<div class="container">
    <div class="card card-register mx-auto mt-5 col-md-6">
        <div class="card-header">Register an Account</div>
        <div class="card-body">
            <form:form method="POST" modelAttribute="registrationForm"
                       class="forms_form" action="/user/registration">

                <div style="text-align: center">Using Google</div>
                <div class="g-signin2 " data-onsuccess="onRegistration" id="myP" style="float: left; margin-bottom: 15px">Google</div>

                <div style="float: right">
                    <a href="#" onclick="signOut();">Sign out</a>
                </div>
                <div style="clear: both; border: 1px solid darkgrey; margin-bottom: 5%;"></div>
                <div class="form-group" style="clear: both">
                    <label>Name</label>
                    <form:input path="name" name="name" class="form-control"
                                type="name" readonly="readonly"
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
                    <label>Gender</label>
                    <form:select path="gender"  class="form-control">
                        <form:option value="Male">Male</form:option>
                        <form:option value="Female">Female</form:option>
                    </form:select>
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
                    <label class="control-label">Date of birthday</label>
                    <div class="form-group">
                        <form:input path="birthdayDate" id="dateEnd" type="date" class="form-control dateValid"
                                    placeholder="Enter your birth date"/>
                        <form:errors path="birthdayDate" cssClass="error"/>
                    </div>
                    <form:errors path="birthdayDate" cssClass="error"/>
                </div>
                <div class="form-group">
                    <label>Phone</label>
                    <form:input path="phone" name="phone" class="form-control phone"
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
<script src="${contextPath}/resources/vendor/bootstrap/js/jquery-1.11.1.min.js"></script>
<script src="${contextPath}/resources/vendor/bootstrap/js/jquery.appear.js"></script>
<script src="${contextPath}/resources/vendor/bootstrap/js/jquery.maskedinput.min.js"></script>
<script type="text/javascript"
        src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.4.1/js/bootstrap-datepicker.min.js"></script>
<link rel="stylesheet"
      href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.4.1/css/bootstrap-datepicker3.css"/>
<script src="${contextPath}/resources/vendor/jquery-easing/jquery.easing.min.js"></script>
<script src='${contextPath}/resources/js/pamCode.js'></script>
<script src="${contextPath}/resources/js/google.js"></script>
</body>
</html>
