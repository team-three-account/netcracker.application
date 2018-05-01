        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
                <%--
                  Created by IntelliJ IDEA.
                  User: Artem
                  Date: 23.04.2018
                  Time: 13:11
                  To change this template use File | Settings | File Templates.
                --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div class="container-fluid">
    <div class="row">
        <div class="sidebar menu">
            <ul class="nav nav-sidebar">
                <li><img class="img-circle text-center" style="width: 40px;height: 40px" src="<c:url value="/account/image/${auth_user.photo}.jpg"/>"></li>
                <li style="background-color : #dee5fc"><a href="/account/profile/${auth_user.id}">${auth_user.name} ${auth_user.surname}</a>
                </li>
            </ul>
            <ul class="nav nav-sidebar">
                <li><a href="/account/eventlist">Events</a></li>
                <li><a href="/account/myevents">My Events</a></li>
                <li><a href="/account/friends">Friends</a></li>
                <li><a href="#">Calendar</a></li>
                <li><a href="#">Wish List</a></li>
            </ul>
        </div>
    </div>
</div>
</body>
</html>
