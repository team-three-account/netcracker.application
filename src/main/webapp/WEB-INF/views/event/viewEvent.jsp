<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Artem
  Date: 26.04.2018
  Time: 11:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link href="${contextPath}/resources/bootstrap3/css/bootstrap.min.css" rel="stylesheet">
    <!-- Custom fonts for this template-->
    <link href="${contextPath}/resources/css/style.css" rel="stylesheet">
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
                </div>
                <div class="panel-body viewEvent">
                    <ul class="list-unstyled mt-3 mb-4">
                        <li>Name : ${event.name}</li>
                        <li>Creator : ${user_creator.name} ${user_creator.surname}</li>
                        <li>Description : ${event.description}</li>
                        <li>Start : ${event.dateStart}</li>
                        <li>End : ${event.dateEnd}</li>
                        <li>Place : ${event.eventPlaceName}</li>
                        <c:if test="${auth_user.id.equals(user_creator.id)}">
                            <li>
                                <a href="/account/eventList/editevent-${event.eventId}">
                                    <input type="submit" class="btn btn-success text-center" value="Edit event"></a>
                                <a href="/account/eventList/deleteEvent-${event.eventId}">
                                    <input type="submit" class="btn btn-danger text-center" value="Delete event"></a>
                            </li>
                        </c:if>
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
                        <script src='${contextPath}/resources/js/showPlace.js'></script>
                        <script
                                src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAFJb-oxFvvvPRvwubCZwYkPQC0rRUbtOM&callback=initMap&language=en">
                        </script>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
