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
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <title>Authorization</title>
  <link href="${contextPath}/resources/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
  <!-- Custom fonts for this template-->
  <link href="${contextPath}/resources/vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
  <!-- Custom styles for this template-->
  <link href="${contextPath}/resources/css/sb-admin.css">
  <link href="${contextPath}/resources/css/style.css" rel="stylesheet">

  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <script src="https://apis.google.com/js/platform.js" async defer></script>
  <meta name="google-signin-client_id"
        content="829562763201-p13uc8h30h5kavae3iqkls8dpjkutspv.apps.googleusercontent.com">

</head>
<body>


<div class="container">
  <div class="card card-register mx-auto mt-5 col-md-6">
    <div class="card-header">Login an Account</div>
    <div class="card-body">
      <form method="POST"
            class="forms_form" action="/login">

        <div style="text-align: center">Using Google</div>
        <div class="g-signin2 " data-onsuccess="onSignIn" id="myP" style="float: left; margin-bottom: 15px">Google</div>

        <div style="float: right">
          <a href="#" onclick="signOut();">Sign out</a>
        </div>
        <div style="clear: both; border: 1px solid darkgrey; margin-bottom: 5%;"></div>
        <div class="form-group">
          <label id="l1">Email address</label>
          <input name="username" id="username" class="form-control"
                 placeholder="Enter email"/>
        </div>
        <div id="status">
        </div>
        <div class="form-group">
          <label id="l2">Password</label>
          <input name="password" id="password" class="form-control"
                 type="password"
                 placeholder="Password"/>
          <span id="error">${error}</span>
          <span id="message">${message}</span>
        </div>

        <input type="submit" value="Login" class="btn btn-dark">

        <div style="float: right">
          <a class="btn btn-dark" href="/user/registration">Sign Up</a>
        </div>
      </form>
    </div>
  </div>
</div>
<!-- Bootstrap core JavaScript-->
<script src="${contextPath}/resources/vendor/jquery/jquery.min.js"></script>
<script src="${contextPath}/resources/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
<!-- Core plugin JavaScript-->
<script src="${contextPath}/resources/vendor/jquery-easing/jquery.easing.min.js"></script>
<script src="${contextPath}/resources/js/google.js"></script>
</body>
</html>
