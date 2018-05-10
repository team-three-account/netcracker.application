<%@ taglib prefix="form" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Artem
  Date: 04.05.2018
  Time: 15:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Chat WebSocket</title>
    <link href="${contextPath}/resources/bootstrap3/css/bootstrap.min.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/style.css" rel="stylesheet">
</head>


<body onload="connect();showMessageOutputFromData();">
<div class="row">
    <jsp:include page="${contextPath}/WEB-INF/views/account/navbar/navbar.jsp"/>
    <div class="col-md-3">
        <jsp:include page="${contextPath}/WEB-INF/views/account/menu/menu.jsp"/>
    </div>

    <div class="col-md-9">
        <div class="row container">

            <div hidden="hidden">
                <input type="text" id="from" placeholder="Choose a nickname" value="${auth_user.name}"/>
            </div>
            <br/>
            <div>
                <button id="connect" onclick="connect();">Connect</button>
                <button id="disconnect" disabled="disabled" onclick="disconnect();">
                    Disconnect
                </button>

                <input type="number" id="event" value="${event.eventId}">
                <input type="hidden" id="photo" value="${auth_user.photo}">
            </div>
            <br/>
            <div class="form-group">
                <div id="sms" class="sms">
                    <form:forEach var="message" items="${chat}">
                        <form:if test="${auth_user.id==message.senderId}">
                            <div class="text-right" id="showMessageOutputFromData" style="margin-left:50%;">

                                <p>${message.from} ${message.text} ${message.time}
                                    <img class="img-circle" style="width: 40px;height: 40px"
                                         src="<c:url value="${message.senderPhoto}"/>">
                                </p>
                            </div>
                        </form:if>
                        <form:if test="${auth_user.id!=message.senderId}">
                            <div class="text-left" id="showMessageOutputFromData">
                                <input type="hidden" id="sender" value="${message.senderId}">
                                <p><img class="img-circle" style="width: 40px;height: 40px"
                                        src="<c:url value="${message.senderPhoto}"/>">${message.from} ${message.text} ${message.time}
                                </p>
                            </div>
                        </form:if>
                    </form:forEach>
                    <p id="response"></p>
                </div>
                <input type="text" class="form-control col-md-3" id="text" placeholder="Write a message..."/>
                <input type="hidden" id="userId" value="${auth_user.id}">
                <button class="btn btn-primary" id="sendMessage" onclick="sendMessage()">Send
                </button>
            </div>
        </div>
    </div>
</div>

<script src="${contextPath}/resources/js/stomp.js"></script>
<script src="${contextPath}/resources/js/sockjs-0.3.4.js"></script>
<script src="${contextPath}/resources/js/chat.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
</body>
</html>