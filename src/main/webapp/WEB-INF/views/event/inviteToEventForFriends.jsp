<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: anyat
  Date: 04.05.2018
  Time: 16:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Invite friends</title>
    <link href="${contextPath}/resources/bootstrap3/css/bootstrap.min.css" rel="stylesheet">
    <!-- Custom fonts for this template-->
    <link href="${contextPath}/resources/css/style.css" rel="stylesheet">
    <!-- Custom styles for this template-->
</head>
<body>
<div class="row">
    <jsp:include page="${contextPath}/WEB-INF/views/account/navbar/navbar.jsp"/>
    <div class="col-md-2"
    <jsp:include page="${contextPath}/WEB-INF/views/account/menu/menu.jsp"/>
</div>

<div class="col-md-10 content">
    <p>
        <a href="/account/eventList/event-${eventId}">
            <input type="submit" class="btn btn-primary text-center" value="< Back to event"></a>
    </p>
    <h1>${message}</h1>

    <div class="row">
        <table class="table">

            <c:forEach var="friend" items="${friendsToInvite}">
                <tr>
                    <td><img class="img-circle" style="width: 50px;height: 50px" src="<c:url value="${friend.photo}.jpg"/>"></td>
                    <td><a href="/account/${friend.id}">${friend.name} ${friend.surname}</a></td>
                    <td>
                        <form action="/account/${eventId}/invite-to-public" method="POST">
                            <button type="submit"  class="btn btn-success">
                                <input type="hidden" name="userId" value=${friend.id} />
                                Invite </span>
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
