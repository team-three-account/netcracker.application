<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: anyat
  Date: 25.04.2018
  Time: 10:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Incoming requests</title>
    <link href="${contextPath}/resources/bootstrap3/css/bootstrap.min.css" rel="stylesheet">
    <!-- Custom fonts for this template-->
    <link href="${contextPath}/resources/css/style.css" rel="stylesheet">
    <!-- Custom styles for this template-->
</head>
<body>

<div class="row">
    <jsp:include page="${contextPath}/WEB-INF/views/account/navbar/navbar.jsp"/>
    <div class="col-md-3"
    <jsp:include page="${contextPath}/WEB-INF/views/account/menu/menu.jsp"/>
</div>


<div class="col-md-9 content">
    <p>
        <a class="btn btn-primary" data-toggle="collapse" href="/account/friends" role="button">All Friends</a>
        <a class="btn btn-primary" data-toggle="collapse" href="/account/friends/incoming" role="button">Incoming
            request</a>
        <a class="btn btn-primary" data-toggle="collapse" href="/account/friends/outgoing" role="button">Outgoing
            request</a>
    </p>
    <h1>${message}</h1>
    <c:forEach var="friend" items="${incomingList}">
        <div class="card friend" style="width: 100%; display: inline-flex">
            <img class="card-img-top" src="" alt="Card image cap">
            <div class="card-body" style="margin-left: 10%;">
                <p class="card-text"><a href="/account//${friend.id}">${friend.name} ${friend.surname}</a></p>
            </div>
            <form action="/account/friends/accept-request" method="POST">
                <button type="submit" class="btn btn-success">
                    <input type="hidden" name="friend_id" value=${friend.id} />
                    Accept request
                </button>
            </form>
        </div>
    </c:forEach>
</div>
</body>
</html>
