<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%--
  Created by IntelliJ IDEA.
  User: anyat
  Date: 21.04.2018
  Time: 21:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Friends</title>
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
                <li><a href="/account">${auth_user.name} ${auth_user.surname}</a></li>
                <li><a href="#">${auth_user.email}</a></li>
            </ul>
            <ul class="nav nav-sidebar">
                <li ><a href="/friends">Friends</a></li>
                <li><a href="#">Incoming requests</a></li>
                <li style="background-color : #dee5fc"><a href="/friends/outgoing">Outgoing requests</a></li>
                <li><a href="#">Events</a></li>
                <li><a href="#">Calendar</a></li>
                <li><a href="#">Wish List</a></li>
            </ul>
        </div>
        <div  style="padding : 7%">
            <h1>Outgoing requests</h1>
            <h2>${message}</h2>
            <table>
                <tr>
                    <th>Photo (id) </th>
                    <th>Name Surname </th>
                    <th>Action</th>
                </tr>

                <c:forEach var="friend" items="${outgoingList}">
                    <tr>
                        <td>${friend.id}</td>
                        <td><a href="/${friend.id}">${friend.name} ${friend.surname}</a></td>
                        <td>
                            <form action="cancel-request" method="POST">
                                <button type="submit" >
                                    <input type="hidden" name="friend_id" value=${friend.id} />
                                    Cancel request </span>
                                </button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>

            </table>

        </div>
    </div>
</div>


<script src="${contextPath}/resources/bootstrap3/js/bootstrap.min.js"></script>
<script src="${contextPath}/resources/bootstrap3/js/bootstrap.js"></script>
<script src="${contextPath}/resources/vendor/bootstrap/js/jquery-1.11.1.min.js"></script>
</body>
</html>
