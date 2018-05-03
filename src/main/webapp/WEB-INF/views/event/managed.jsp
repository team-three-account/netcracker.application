<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: anyat
  Date: 21.04.2018
  Time: 21:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Managed events</title>
    <link href="${contextPath}/resources/bootstrap3/css/bootstrap.min.css" rel="stylesheet">
    <!-- Custom fonts for this template-->
    <link href="${contextPath}/resources/css/style.css" rel="stylesheet">
    <!-- Custom styles for this template-->
</head>
<body>
<div class="row">
    <jsp:include page="${contextPath}/WEB-INF/views/account/navbar/navbar.jsp"/>
    <div class="col-md-3"
    <jsp:include page="${contextPath}/WEB-INF/views/account/menu/menu.jsp"/>
</div>


<div class="col-md-9 content">
    <p>
        <a class="btn btn-primary" data-toggle="collapse" href="/account/available" role="button">All events</a>
        <a class="btn btn-primary" data-toggle="collapse" href="/account/subscriptions" role="button">Subscriptions</a>
        <a class="btn btn-primary" data-toggle="collapse" href="/account/managed" role="button">Managed events</a>
        <a class="btn btn-primary" data-toggle="collapse" href="/account/draft" role="button">Drafts</a>

    </p>
    <div>
        <sec:authorize access="hasRole('USER')">
            <a class="btn btn-primary" href="<c:url value='/account/eventList/createNewEvent' />">Add new event</a>
        </sec:authorize>
    </div>

        <div class="card-deck mb-3 text-center">
            <div class="card col-md-3 box-shadow eventCategory">
                <div class="card-header">
                    <h4 class="my-0 font-weight-normal eventCategory">Public Events</h4>
                </div>

                <div class="card-body eventCard">
                    <c:forEach var="emp" items="${publicEventList}">
                        <a href="<c:url value='/account/eventList/event-${emp.eventId}' />">
                            <ul class="list-unstyled mt-3 mb-4 eventCardItem">
                                <li><img class="img-circle" style="width: 20px;height: 20px"
                                         src="<c:url value="/account/image/${emp.photo}.jpg"/>"></li></li>
                                <li>${emp.name}</li>
                                <li>Start ${emp.dateStart}</li>
                                <li>End ${emp.dateEnd}</li>
                            </ul>
                        </a>

                        <form action="/account/participate" method="POST">
                            <button type="submit" class="btn btn-success">
                                <input type="hidden" name="event_id" value="${emp.eventId}"/>
                                Participate </span>
                            </button>
                        </form>

                    </c:forEach>
                </div>

            </div>

            <div class="card col-md-3 box-shadow eventCategory">
                <div class="card-header">
                    <h4 class="my-0 font-weight-normal eventCategory">Friend Events</h4>
                </div>
                <div class="card-body eventCard">

                    <div class="card-body eventCard">
                        <c:forEach var="emp" items="${friendsEventList}">
                            <a href="<c:url value='/account/eventList/event-${emp.eventId}' />">
                                <ul class="list-unstyled mt-3 mb-4 eventCardItem">
                                    <li>${emp.name}</li>
                                    <li>Start ${emp.dateStart}</li>
                                    <li>End ${emp.dateEnd}</li>
                                </ul>
                            </a>

                            <form action="/account/participate" method="POST">
                                <button type="submit" class="btn btn-success">
                                    <input type="hidden" name="event_id" value="${emp.eventId}"/>
                                    Participate </span>
                                </button>
                            </form>

                        </c:forEach>
                    </div>


                </div>
            </div>

            <div class="card col-md-3 box-shadow eventCategory">
                <div class="card-header">
                    <h4 class="my-0 font-weight-normal eventCategory">Private Events</h4>
                </div>
                <div class="card-body eventCard">

                    <div class="card-body eventCard">
                        <c:forEach var="emp" items="${privateEventList}">
                            <a href="<c:url value='/account/eventList/event-${emp.eventId}' />">
                                <ul class="list-unstyled mt-3 mb-4 eventCardItem">
                                    <li>${emp.name}</li>
                                    <li>Start ${emp.dateStart}</li>
                                    <li>End ${emp.dateEnd}</li>
                                </ul>
                            </a>

                            <form action="/account/participate" method="POST">
                                <button type="submit" class="btn btn-success">
                                    <input type="hidden" name="event_id" value="${emp.eventId}"/>
                                    Participate </span>
                                </button>
                            </form>

                        </c:forEach>
                    </div>


                </div>
            </div>


            <div class="row">
        <table class="table">
            <c:forEach var="friends" items="${friendsEventList}">
                <tbody>
                <tr>
                    <td><img class="img-circle" style="width: 200px;height: 200px"
                             src="<c:url value="/account/image/${friends.photo}.jpg"/>"> </td>
                    <td> <a href="/account/eventList/event-${friends.eventId}"> ${friends.name} </a></td>
                    <td> Date : ${friends.dateStart} - ${friends.dateEnd} </td>
                </tr>
                </tbody>
            </c:forEach>

        </table>
    </div>
</div>
<script src="${contextPath}/resources/bootstrap3/js/bootstrap.min.js"></script>
<script src="${contextPath}/resources/bootstrap3/js/bootstrap.js"></script>
<script src="${contextPath}/resources/vendor/bootstrap/js/jquery-1.11.1.min.js"></script>

</body>
</html>
