<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link href="${contextPath}/resources/bootstrap3/css/bootstrap.min.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/style.css" rel="stylesheet">

    <%--for periodicity--%>
    <script src="${contextPath}/resources/vendor/bootstrap/js/jquery-1.11.1.min.js"></script>
    <script src="${contextPath}/resources/vendor/bootstrap/js/later.min.js"></script>
    <script src="${contextPath}/resources/vendor/bootstrap/js/moment.min.js"></script>
    <script src="${contextPath}/resources/vendor/bootstrap/js/prettycron.js"></script>
    <script src="${contextPath}/resources/js/periodicity.js"></script>
</head>
<body>

<div class="row">
    <jsp:include page="${contextPath}/WEB-INF/views/account/navbar/navbar.jsp"/>
    <div class="col-md-3"
    <jsp:include page="${contextPath}/WEB-INF/views/account/menu/menu.jsp"/>
</div>
<div class="col-md-9 content">
    <div class="container-fluid">
        <div class="col-md-6">
            <div class="panel panel-success">
                <div class="panel-heading">
                    <h3 class="panel-title">Event - ${event.name}</h3>
                    <c:if test="${isParticipated == true && (event.type.equals(3)|| event.type.equals(2))}">
                        <a type="button" class="btn btn-primary"
                           href="<c:url value="/account/eventList/eventChat/main/${chatWithCreator.chatId}-${event.eventId}"/>">
                            <p>Main Chat</p>
                        </a>
                        <c:if test="${auth_user.id != event.creator}">
                            <a type="button" class="btn btn-primary"
                               href="<c:url value="/account/eventList/eventChat/subscriptions/${chatWithOutCreator.chatId}-${event.eventId}"/>">
                                <p>Subscriptions Chat</p>
                            </a>
                        </c:if>
                    </c:if>
                </div>
                <div class=" panel-body viewEvent">
                    <input id="cron" type="hidden" value="${event.periodicity}">
                    <ul class="list-unstyled mt-3 mb-4">
                        <li>Photo: <img class="img-circle" style="width: 200px;height: 200px"
                                        src="<c:url value="${photo}"/>"></li>
                        <li>Name : ${event.name}</li>
                        <li>Creator : ${user_creator.name} ${user_creator.surname}</li>
                        <li>Description : ${event.description}</li>
                        <li>Start : ${event.dateStart}</li>
                        <li>End : ${event.dateEnd}</li>
                        <li id="periodicity"></li>
                        <li>Place : ${event.eventPlaceName}</li>
                        <li>Participants : <a href="/account/event-${event.eventId}/participants"> ${participants}
                            people</a></li>
                        <li><a href="/account/event-${event.eventId}-${event.creator}/wishList">Wish List</a>
                        <li>
                            <c:choose>
                            <c:when test="${auth_user.id.equals(user_creator.id)}">
                        <li>
                            <c:if test="${event.type == '2'}">
                                <a href="/account/public/event-${event.eventId}/invite">
                                    <input type="submit" class="btn btn-success text-center"
                                           value="Invite user"></a>
                            </c:if>
                            <c:if test="${event.type == '3'}">
                                <a href="/account/for-friends/event-${event.eventId}/invite">
                                    <input type="submit" class="btn btn-success text-center"
                                           value="Invite friend"></a>
                            </c:if>

                            <a href="/account/eventList/editevent-${event.eventId}">
                                <input type="submit" class="btn btn-success text-center" value="Edit event"></a>
                            <a href="/account/eventList/deleteEvent-${event.eventId}">
                                <input type="submit" class="btn btn-danger text-center"
                                       value="Delete event"></a>
                        </li>
                        </c:when>
                        <c:otherwise>
                            <c:if test="${isParticipated == true}">
                                <form action="/account/unsubscribe" method="POST">
                                    <button type="submit" class="btn btn-danger text-center">
                                        <input type="hidden" name="event_id" value="${event.eventId}"/>
                                        Unsubscribe </span>
                                    </button>
                                </form>
                            </c:if>
                            <c:if test="${isParticipated == false}">
                                <form action="/account/participate" method="POST">
                                    <button type="submit" class="btn btn-success">
                                        <input type="hidden" name="event_id" value="${event.eventId}"/>
                                        Subscribe </span>
                                    </button>
                                </form>
                            </c:if>
                        </c:otherwise>
                        </c:choose>
                    </ul>
                </div>
            </div>
        </div>
        <div class="col-md-6 pointer">
            <div class="panel panel-success">
                <div class="panel-heading">
                    <h3 class="panel-title">Event Place</h3>
                </div>
                <div class="panel-body viewEvent">
                    <div class="form-group">
                        <label>Event place</label>
                        <div id="map" disabled=""></div>
                        <input type="hidden" value="${event.width}" id="latitude">
                        <input type="hidden" value="${event.longitude}" id="longitude">
                        <script src='${contextPath}/resources/js/pamCode.js'></script>
                        <script
                                src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAFJb-oxFvvvPRvwubCZwYkPQC0rRUbtOM&callback=initMap&language=en">
                        </script>
                    </div>
                </div>
            </div>
        </div>
        <c:choose>
        <c:when test="${isParticipated == true}">
        <div class="col-md-6 pointer">
            <div class="panel panel-success">
                <div class="panel-heading">
                    <h3 class="panel-title">Priority</h3>
                </div>
                <div class="panel-body viewEvent">
                    <div class="form-group">
                        <label>Priority</label>
                        <td>
                            <form:form method="POST" modelAttribute="participant">
                                <tr>
                                    <form:radiobuttons path="priority" items="${priorities}" itemValue="priorityId"
                                                       itemLabel="name"/>
                                </tr>
                                <input type="submit" name="submit" value="Submit">
                            </form:form>
                        </td>
                    </div>
                </div>
            </div>
        </div>
    </div>
    </c:when>
    <c:otherwise></c:otherwise>
    </c:choose>
</div>
<script>
    $(function () {
        cron2text();
    })
</script>
</body>
</html>