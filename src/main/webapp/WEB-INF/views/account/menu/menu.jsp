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
<div class="container-fluid">
    <div class="row">
        <div class="sidebar menu nav-style">
            <ul class="nav nav-sidebar" style="padding-top: 150px">
                <li><img class="img-circle text-center" style="margin-left:10%;width: 40px;height: 40px" src="<c:url value="${auth_user.photo}"/>"></li>
                <li><a href="/account/profile/${auth_user.id}">${auth_user.name} ${auth_user.surname}</a>
                </li>
                <input hidden id="authUserId" value="${auth_user.id}"/>
            </ul>
            <ul class="nav nav-sidebar">
                <li><a href="/account/available">Events</a></li>
                <li><a href="/account/friends">Friends</a></li>
                <li><a href="/account/allNotes">Notes</a></li>
                <li><a href="/account/user-${auth_user.id}/wishList">Wish List</a></li>
                <li><a href="/account/calendar">Calendar</a></li>
                <li><a href="/account/timeline">Timelines</a></li>
                <li><a href="/account/eventList/chats/${auth_user.id}">Messages</a></li>
            </ul>

        </div>

    </div>
</div>

</html>
