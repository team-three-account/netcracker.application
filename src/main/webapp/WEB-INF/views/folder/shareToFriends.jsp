<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: anyat
  Date: 11.05.2018
  Time: 21:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Share folder</title>
    <link href="${contextPath}/resources/bootstrap3/css/bootstrap.min.css" rel="stylesheet">
    <!-- Custom fonts for this template-->
    <link href="${contextPath}/resources/css/style.css" rel="stylesheet">
    <script src="${contextPath}/resources/bootstrap3/js/bootstrap.min.js"></script>
    <script src="${contextPath}/resources/bootstrap3/js/bootstrap.js"></script>
    <script src="${contextPath}/resources/vendor/bootstrap/js/jquery-1.11.1.min.js"></script>
    <!-- Custom styles for this template-->
</head>
<body>
<div class="row">
    <jsp:include page="${contextPath}/WEB-INF/views/account/navbar/navbar.jsp"/>
    <div class="col-md-2"
    <jsp:include page="${contextPath}/WEB-INF/views/account/menu/menu.jsp"/>
</div>


<div class="col-md-10 content">
    <jsp:include page="${contextPath}/WEB-INF/views/account/notification.jsp"></jsp:include>
    <p>
        <a class="btn btn-primary"  href="<c:url value='/account/folder-${folderId}'/>" > < Back</a>
    </p>


    <div class="row">

        <h3>${messageForAlreadyShared}</h3>
        <table class="table">

            <c:forEach var="friend" items="${friendsThatHaveAccessList}">
                <tr>
                    <td><img class="img-circle" style="width: 50px;height: 50px" src="<c:url value="${friend.photo}"/>"></td>
                    <td><a href="/account/${friend.id}">${friend.name} ${friend.surname}</a></td>
                    <td>
                        <form action="/account/share-${folderId}/disable" method="POST">
                            <button type="submit"  class="btn btn-danger">
                                <input type="hidden" name="friendId" value=${friend.id} />
                                Disable access </span>
                            </button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>

    <h3>${messageToShare}</h3>
    <div class="row">
        <table class="table">

            <c:forEach var="user" items="${friendsToShareList}">
                <tr>
                    <td><img class="img-circle" style="width: 200px;height: 200px"
                             src="<c:url value="${user.photo}.jpg"/>"></td>
                    <td><a href="/account/${user.id}">${user.name} ${user.surname}</a></td>
                    <td>
                        <form action="/account/share-${folderId}/share" method="POST">
                            <button type="submit"  class="btn btn-success">
                                <input type="hidden" name="userId" value=${user.id} />
                                Allow access </span>
                            </button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>
</div>

</body>
</html>
