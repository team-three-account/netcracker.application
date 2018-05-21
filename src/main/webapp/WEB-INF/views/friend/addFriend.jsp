<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<div class="row">
    <div class="col-md-2">
        <jsp:include page="${contextPath}/WEB-INF/views/account/menu/menu.jsp"/>
    </div>
    <div class="col-md-10">
        <div class="card card-register">
            <h1>Search for friends</h1>
            <form method="POST"
                  class="forms_form" action="/account/friends/add">

                <div class="form-group">
                    <input name="search" id="search" placeholder="Enter name or surname"/>
                    <input type="submit" value="Search" class="btn btn-dark" href="/">
                </div>
            </form>

            <table>
                <tr>
                    <th>Photo (id)</th>
                    <th>Name Surname</th>
                    <th>Action</th>
                </tr>

                <c:forEach var="friend" items="${foundFriends}">
                    <tr>
                        <td><img class="img-circle" style="width: 50px;height: 50px"
                                 src="<c:url value="${friend.photo}"/>"></td>
                        <td><a href="/${friend.id}">${friend.name} ${friend.surname}</a></td>
                        <td>
                            <form action="/account/friends/add-friend" method="POST">
                                <button type="submit">
                                    <input type="hidden" name="friendId" value=${friend.id}/>
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


<script src="${contextPath}/resources/bootstrap3/js/bootstrap.min.js"></script>
<script src="${contextPath}/resources/bootstrap3/js/bootstrap.js"></script>
</body>
</html>
