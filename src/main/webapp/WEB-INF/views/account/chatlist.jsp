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
    <div class="col-md-2" style="height:100vh;">
        <jsp:include page="${contextPath}/WEB-INF/views/account/menu/menu.jsp"/>
    </div>

    <div class="col-md-10 content">
        <jsp:include page="${contextPath}/WEB-INF/views/account/notification.jsp"></jsp:include>
        <div class="col-md-offset-1 col-md-5" style="">
            <c:forEach items="${allChats}" var="chat">
            <ul class="list-group">
                <c:if test="${chat.creatorEvent==true}">
                <ul>
                    <li class="list-group-item">
                        <a href="/account/eventList/event-${chat.event.eventId}"
                           style="!important;text-decoration: none; color: black;display: inline;">
                            <img class="img-circle" style="width: 50px;height: 50px"
                                 src="<c:url value="${chat.event.photo}"/>"> Event: ${chat.event.name}</a>

                    </li>

                    <li class="list-group-item">
                        <div class="text-left">
                            <a style="!important;text-decoration: none;"
                               href="/account/eventList/eventChat/main/${chat.chatId}-${chat.event.eventId}">
                                <div style="border-radius: 10px;    margin: 15px;background: powderblue;padding-top: 3%;padding-bottom: 3%;padding-left: 3%;">
                                    <img class="img-circle" style="width: 50px;height: 50px"
                                         src="<c:url value="${chat.lastMessage.senderPhoto}"/>">

                                    <p style="display: inline;border-radius: 10px;margin: 15px;background: powderblue; color: black">${chat.lastMessage.text}</p>
                                </div>
                            </a>

                            <div class="text-right">
                                    ${chat.lastMessage.time}
                            </div>
                        </div>

                    </li>
                </ul>
                </c:if>
                <c:if test="${chat.creatorEvent==false}">
                <ul class="list-group">
                    <ul>
                        <li class="list-group-item"><p style="display: inline">
                            <a href="/account/eventList/event-${chat.event.eventId}"
                               style="!important;text-decoration: none; color: black">
                                <img class="img-circle" style="width: 50px;height: 50px"
                                     src="<c:url value="${chat.event.photo}"/>"> Event: ${chat.event.name}
                            </a>
                        </li>

                        <li class="list-group-item">
                            <div class="text-left">
                                <a style="!important;text-decoration: none;"
                                   href="/account/eventList/eventChat/main/${chat.chatId}-${chat.event.eventId}">
                                    <div style="border-radius: 10px;    margin: 15px;background: powderblue;padding-top: 3%;padding-bottom: 3%;padding-left: 3%;">
                                        <img class="img-circle" style="width: 50px;height: 50px"
                                             src="<c:url value="${chat.lastMessage.senderPhoto}"/>">

                                        <p style="display: inline;border-radius: 10px;margin: 15px;background: powderblue; color: black">${chat.lastMessage.text}</p>
                                    </div>
                                </a>

                                <div class="text-right">
                                        ${chat.lastMessage.time}
                                </div>
                            </div>

                        </li>
                    </ul>
                    </c:if>
                </ul>
                </c:forEach>
        </div>
    </div>
</div>
</body>
</html>
