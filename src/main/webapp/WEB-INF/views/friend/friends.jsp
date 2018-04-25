<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <h3>Search for friends</h3>
    <form method="POST"
          class="forms_form" action="/account/friends/search">

        <div class="form-group">
            <input name="search" id="search"  placeholder="Enter name or surname"/>
            <input type="submit" value="Search" class="btn btn-dark" href="/">
        </div>
    </form>

    <div class="row">
        <c:forEach var="friend" items="${friendList}">
            <div class="card friend" style="width: 100%; display: inline-flex">
                <img class="card-img-top" src="" alt="Card image cap">
                <div class="card-body" style="margin-left: 10%;">
                    <p class="card-text"><a href="/account/${friend.id}">${friend.name} ${friend.surname}</a></p>
                </div>
                <form action="/account/delete-friend" method="POST">
                <button class="btn btn-danger" type="submit">
                    <input type="hidden" name="friend_id" value=${friend.id} />
                    Remove from friends </span>
                </button>
                </form>
            </div>
        </c:forEach>
    </div>

    <div class="row">
        <table>
        <tr>
            <th>Photo (id) </th>
            <th>Name Surname </th>
            <th>Action</th>
        </tr>

        <c:forEach var="user" items="${foundUsers}">
            <tr>
                <td>${user.id}</td>
                <td><a href="/${user.id}">${user.name} ${user.surname}</a></td>
                <td>
                    <form action="/account/friends/add-friend" method="POST">
                        <button type="submit" >
                            <input type="hidden" name="friend_id" value=${user.id} />
                            Add </span>
                        </button>
                    </form>
                </td>
            </tr>
        </c:forEach>
        </table>
    </div>
</div>
    <script src="${contextPath}/resources/bootstrap3/js/bootstrap.min.js"></script>
    <script src="${contextPath}/resources/bootstrap3/js/bootstrap.js"></script>
    <script src="${contextPath}/resources/vendor/bootstrap/js/jquery-1.11.1.min.js"></script>

</body>
</html>
