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
    <div class="col-md-2">
        <jsp:include page="${contextPath}/WEB-INF/views/account/menu/menu.jsp"/>
    </div>
    <div class="col-md-10">
        <div class="row container">
            <div hidden="hidden">
                <input type="text" id="from" placeholder="Choose a nickname" value="${auth_user.name}"/>
            </div>
            <br/>
            <div>
                <button id="connect" onclick="connect();" hidden>Connect</button>
                <button id="disconnect" disabled="disabled" onclick="disconnect();" hidden>
                    Disconnect
                </button>

                <input type="number" id="chat" value="${chat.chatId}">
                <input type="number" id="event" value="${event.eventId}">
                <input type="hidden" id="chatWithCreator" value="${chat.state}">
                <input type="hidden" id="photo" value="${auth_user.photo}">
                <input type="hidden" id="authUserId" value="${auth_user.id}">
            </div>
            <br/>
            <div class="row" style="display: inline;">
                <div class="col-md-8 col-lg-8">
                    <div class="form-group container-fluid">
                        <div id="sms" class="sms text-center" onscroll="loadPrevMessages()">
                            <form:forEach var="message" items="${chatMessage}">
                                <form:if test="${auth_user.id==message.senderId}">
                                    <div class="text-right" id="showMessageOutputFromData">
                                        <p> ${message.text} ${message.time}
                                            <img class="img-circle" style="width: 40px;height: 40px"
                                                 src="${message.senderPhoto}"/>
                                        </p>
                                    </div>
                                </form:if>
                                <form:if test="${auth_user.id!=message.senderId}">
                                    <div class="text-left" id="showMessageOutputFromData">
                                        <input type="hidden" id="sender" value="${message.senderId}">
                                        <p><a href="/account/${message.senderId}"><img class="img-circle" style="width: 40px;height: 40px"
                                                                                       src="${message.senderPhoto}"></a> ${message.text} ${message.time}
                                        </p>
                                    </div>
                                </form:if>
                            </form:forEach>
                            <p id="response"></p>
                        </div>
                        <input type="text" onkeyup="checkParams()" class="form-control col-md-3" id="text"
                               placeholder="Write a message..."/>
                        <input type="hidden" id="userId" value="${auth_user.id}">
                        <button class="btn btn-primary" type="submit" id="sendMessage" onclick="sendMessage()" disabled>Send
                        </button>
                    </div>
                </div>
                <div class="col-md-4 col-lg-4">
                    <div class="text-right">
                        <h3>Participants</h3>
                        <c:forEach var="participant" items="${participants}">
                            <table class="table">
                                <tbody>
                                <form:if test="${chat.state==false}">
                                    <form:if test="${participant.id!=event.creator}">
                                        <tr>
                                            <td><a href="/account/${participant.id}"><img class="img-circle" style="width: 40px;height: 40px"
                                                                                          src="${participant.photo}"></a></td>
                                        <td class="text-right">${participant.name} ${participant.surname}</td>
                                        </tr>
                                    </form:if>
                                </form:if>
                                <form:if test="${chat.state==true}">
                                    <tr>
                                        <td><a href="/account/${participant.id}"><img class="img-circle" style="width: 40px;height: 40px"
                                                                                      src="${participant.photo}"></a></td>

                                        <td class="text-right">${participant.name} ${participant.surname}</td>
                                    </tr>
                                </form:if>
                                </tbody>
                            </table>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.10.1/jquery.min.js"></script>

<script src="${contextPath}/resources/js/stomp.js"></script>
<script src="${contextPath}/resources/js/sockjs-0.3.4.js"></script>
<script src="${contextPath}/resources/js/chat.js"></script>
</body>
</html>