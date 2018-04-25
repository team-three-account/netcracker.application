<%--
  Created by IntelliJ IDEA.
  User: Alina
  Date: 25.04.2018
  Time: 14:41
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>Settings</title>
    <link href="${contextPath}/resources/bootstrap3/css/bootstrap.min.css" rel="stylesheet">
    <!-- Custom fonts for this template-->
    <link href="${contextPath}/resources/css/style.css" rel="stylesheet">
    <!-- Custom styles for this template-->
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <script src="https://apis.google.com/js/platform.js" async defer></script>
    <meta name="google-signin-client_id"
          content="829562763201-3hn7scp4j1c9u7hvlherebi8e56pv9va.apps.googleusercontent.com">
</head>
<body>

<div class="row">
    <jsp:include page="${contextPath}/WEB-INF/views/account/navbar/navbar.jsp"/>
    <div class="col-md-3">
        <jsp:include page="${contextPath}/WEB-INF/views/account/menu/menu.jsp"/>
    </div>

<div class="col-md-9 content">
    <div class="card card-register mx-auto mt-5 col-md-6">
        <div class="card-body">
            <form:form method="POST" modelAttribute="user"
                       class="forms_form" action="/account/settings">
        </div>
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
            <label class="control-label">Date of birthday</label>
            <div class="input-group date" data-provide="datepicker">
                <form:input type="text" class="form-control" path="birthdayDate"/>
                <div class="input-group-addon">
                    <span class="glyphicon glyphicon-th"></span>
                </div>
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
        <input type="submit" value="Save" class="btn btn-dark text-center">
        </form:form>
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
<script src="${contextPath}/resources/js/google.js"></script>
</body>
</html>
