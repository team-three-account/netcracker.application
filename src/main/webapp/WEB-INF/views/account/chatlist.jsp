<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Artem
  Date: 27.05.2018
  Time: 00:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Chats ${auth_user.name} ${auth_user.surname}</title>
    <link href="${contextPath}/resources/bootstrap3/css/bootstrap.min.css" rel="stylesheet">
    <!-- Custom fonts for this template-->
    <link href="${contextPath}/resources/css/style.css" rel="stylesheet">
    <script src="${contextPath}/resources/vendor/bootstrap/js/jquery-1.11.1.min.js"></script>
</head>
<body>
<div class="row">
    <jsp:include page="${contextPath}/WEB-INF/views/account/navbar/navbar.jsp"/>
    <div class="col-md-2">
        <jsp:include page="${contextPath}/WEB-INF/views/account/menu/menu.jsp"/>
    </div>

    <div class="col-md-10 content" style="">
        <jsp:include page="${contextPath}/WEB-INF/views/account/notification.jsp"></jsp:include>
        <div class="col-md-offset-1 col-md-5">
        <c:forEach items="${allChats}" var="chat">
            <ul class="list-group">
                <a href="/account/eventList/eventChat/main/${chat.chatId}-${chat.eventId}"><li class="list-group-item">
                    <img class="img-circle" style="width: 50px;height: 50px" src="<c:url value="${chat.image}"/>">${chat.eventName}</li></a>
            </ul>
        </c:forEach>
        </div>
    </div>

</div>
</body>
</html>
