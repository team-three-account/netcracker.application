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
    <title>${auth_user.name} ${auth_user.surname}</title>
    <link href="${contextPath}/resources/bootstrap3/css/bootstrap.min.css" rel="stylesheet">
    <!-- Custom fonts for this template-->
    <link href="${contextPath}/resources/css/style.css" rel="stylesheet">
    <!-- Custom styles for this template-->

</head>
<body>

<jsp:include page="${contextPath}/WEB-INF/views/account/navbar/navbar.jsp"/>
<div class="container-fluid">
    <div class="row">
        <div class="col-sm-3 col-md-2 sidebar menu">
            <ul class="nav nav-sidebar">
                <li style="background-color : #dee5fc"><a href="/account">${auth_user.name} ${auth_user.surname}</a></li>
                <li><a href="#">${auth_user.email}</a></li>
            </ul>
            <ul class="nav nav-sidebar">
                <li><a href="/account/eventlist">Events</a></li>
                <li><a href="/account/friends">Friends</a></li>
                <li><a href="/account/itemsList">Wish List</a></li>
                <li><a href="#">Events</a></li>
                <li><a href="#">Calendar</a></li>
                <li><a href="#">Wish List</a></li>
            </ul>
        </div>
    </div>
</div>
<script src="${contextPath}/resources/bootstrap3/js/bootstrap.min.js"></script>
<script src="${contextPath}/resources/bootstrap3/js/bootstrap.js"></script>
<script src="${contextPath}/resources/vendor/bootstrap/js/jquery-1.11.1.min.js"></script>
</body>
</html>
