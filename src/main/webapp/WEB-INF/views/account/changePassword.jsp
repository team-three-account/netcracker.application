<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
        <div class="card-header">Change password</div>
        <div class="card-body">
            <form:form method="POST"
                       class="forms_form" action="/account/changePassword/${veriftoken.id}" modelAttribute="user">
                <div class="form-group">
                    <h2>${veriftoken.id}</h2>
                    <label>New Password</label>
                    <form:input id="password" name="password" class="form-control"
                                type="password"
                                placeholder="Enter new password" path="password"/>
                    <form:errors path="password"></form:errors>
                </div>
                <div class="form-group">
                    <label>Confirm Password</label>
                    <form:input name="confirmPassword" class="form-control"
                                type="password"
                                placeholder="Confirm Password" path="confirmPassword"/>
                    <form:errors path="confirmPassword"></form:errors>
                    <span id="error">${error}</span>
                </div>
                <input type="submit" value="Change Password" class="btn btn-dark">
            </form:form>
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