<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>View event</title>
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
    <div class="col-md-2"
    <jsp:include page="${contextPath}/WEB-INF/views/account/menu/menu.jsp"/>
</div>
<div class="col-md-10 content">
    <div class="container-fluid">
        <div class="col-md-4">
            <table class="table">
                <tbody>
                <tr>
                    <td>
                        <c:choose>
                            <c:when test="${event.draft.equals(true)}">
                                <h3 class="panel-title">Draft - ${event.name}</h3>
                            </c:when>
                            <c:otherwise>
                                <h3 class="panel-title">Event - ${event.name}</h3>
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>


                <c:if test="${isParticipated == true &&
                   (event.typeId==3 || event.typeId==2)}">

                        <tr>
                            <td>
                                <div style="display: inline-flex">
                                <form method="get"
                                      action="/account/eventList/eventChat/main/${chatWithCreator.chatId}-${event.eventId}">
                                    <button type="submit" class="btn btn-primary">
                                        Main Chat
                                    </button>
                                </form>
                                <c:if test="${auth_user.id != event.creator}">
                                    <form method="get"
                                          action="/account/eventList/eventChat/subscriptions/${chatWithOutCreator.chatId}-${event.eventId}">
                                        <button type="submit" class="btn btn-primary">
                                            Subscriptions Chat
                                        </button>
                                    </form>
                                </c:if>
                                </div>
                            </td>
                        </tr>
                </c:if>


                <input id="cron" type="hidden" value="${event.periodicity}">
                <tr>
                    <td>Photo: <img class="img-circle" style="width: 200px;height: 200px"
                                    src="<c:url value="${photo}"/>"></td>
                </tr>
                <tr>
                    <td>Name :${event.name}
                    </td>
                </tr>
                <tr>
                    <td>Creator :<a
                            href="/account/${user_creator.id}"> ${user_creator.name} ${user_creator.surname}</a>
                    </td>
                </tr>
                <tr>
                    <td>Description : ${event.description}</td>
                </tr>
                <tr>
                    <td>Start : ${event.dateStart}</td>
                </tr>
                <tr>
                    <td>End : ${event.dateEnd}</td>
                </tr>
                <tr>
                    <td id="periodicity"></td>
                </tr>
                <tr>
                    <td>Place : ${event.eventPlaceName}</td>
                </tr>
                <tr>
                    <td>Participants : <a href="/account/event-${event.eventId}/participants"> ${participants}
                        people</a></td>
                </tr>
                <tr>
                    <td><a href="/account/event-${event.eventId}-${event.creator}/wishList">Wish List</a>
                    </td>
                </tr>
                <tr>
                    <td>
                        <c:choose>
                            <c:when test="${auth_user.id.equals(user_creator.id)}">
                                <c:if test="${event.typeId==2}">
                                    <a href="/account/public/event-${event.eventId}/invite">
                                        <input type="submit" class="btn btn-success text-center"
                                               value="Invite user"></a>
                                </c:if>
                                <c:if test="${event.typeId==3}">
                                    <a href="/account/for-friends/event-${event.eventId}/invite">
                                        <input type="submit" class="btn btn-success text-center"
                                               value="Invite friend"></a>
                                </c:if>
                                <c:choose>
                                    <c:when test="${event.draft.equals(true)}">
                                        <a href="/account/eventList/editevent-${event.eventId}">
                                            <input type="submit" class="btn btn-success text-center" value="Edit draft"></a>
                                        <a href="/account/eventList/deleteEvent-${event.eventId}">
                                            <input type="submit" class="btn btn-danger text-center"
                                                   value="Delete draft"></a>
                                        <a href="/account/eventList/convertToEvent-${event.eventId}">
                                            <input type="submit" class="btn btn-success text-center"
                                                   value="Convert to Event"></a>
                                    </c:when>
                                    <c:otherwise>
                                        <a href="/account/eventList/editevent-${event.eventId}">
                                            <input type="submit" class="btn btn-success text-center" value="Edit event"></a>
                                        <a href="/account/eventList/deleteEvent-${event.eventId}">
                                            <input type="submit" class="btn btn-danger text-center"
                                                   value="Delete event"></a>
                                    </c:otherwise>
                                </c:choose>

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
                    </td>
                </tr>
                </tbody>
            </table>
        </div>

        <div class="col-md-6 pointer">
            <div class="panel panel-success">
                <div class="panel-heading">
                    <c:choose>
                        <c:when test="${event.draft.equals(true)}">
                            <h3 class="panel-title">Draft Place</h3>
                        </c:when>
                        <c:otherwise>
                            <h3 class="panel-title">Event Place</h3>
                        </c:otherwise>
                    </c:choose>

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
                            <form:form method="POST" modelAttribute="participation">
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