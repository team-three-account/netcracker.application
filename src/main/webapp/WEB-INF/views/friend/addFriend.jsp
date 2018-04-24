<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%--
  Created by IntelliJ IDEA.
  User: anyat
  Date: 23.04.2018
  Time: 15:08
  To change this template use File | Settings | File Templates.
--%>
<html>
<head>
    <title>Add friend</title>
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
                <li style="background-color : #dee5fc"><a href="/friends">Friends</a></li>
                <li><a href="#">Incoming requests</a></li>
                <li><a href="#">Outgoing requests</a></li>
                <li><a href="#">Events</a></li>
                <li><a href="#">Calendar</a></li>
                <li><a href="#">Wish List</a></li>
            </ul>
        </div>
        <div class="container" style="padding : 7%">
            <div class="card card-register mx-auto mt-5 col-md-6">
                <h1>Search for friends</h1>
                    <form method="POST"
                          class="forms_form" action="/friends/add">

                        <div class="form-group">
                            <input name="search" id="search"  placeholder="Enter name or surname"/>
                            <input type="submit" value="Search" class="btn btn-dark" href="/">
                        </div>
                    </form>

                <table>
                    <tr>
                        <th>Photo (id) </th>
                        <th>Name Surname </th>
                        <th>Action</th>
                    </tr>

                    <c:forEach var="friend" items="${foundFriends}">
                        <tr>
                            <td>${friend.id}</td>
                            <td><a href="/${friend.id}">${friend.name} ${friend.surname}</a></td>
                            <td>
                                <form action="/friends/add-friend" method="POST">
                                    <button type="submit" >
                                        <input type="hidden" name="friend_id" value=${friend.id} />
                                        Add </span>
                                    </button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>

                </table>
                </div>

        </div>
    </div>
</div>


<script src="${contextPath}/resources/bootstrap3/js/bootstrap.min.js"></script>
<script src="${contextPath}/resources/bootstrap3/js/bootstrap.js"></script>
</body>
</html>
