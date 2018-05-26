<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Subscriptions</title>
    <link href="${contextPath}/resources/bootstrap3/css/bootstrap.min.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/style.css" rel="stylesheet">
    <script src="${contextPath}/resources/vendor/bootstrap/js/jquery-1.11.1.min.js"></script>
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
        <a class="btn btn-primary"  href="/account/available" role="button">All events</a>
        <a class="btn btn-primary"  href="/account/subscriptions" role="button">Subscriptions</a>
        <a class="btn btn-primary"  href="/account/managed" role="button">Managed events</a>
        <a class="btn btn-primary"  href="/account/draft" role="button">Drafts</a>
        <sec:authorize access="hasRole('USER')">
            <a class="btn btn-success" href="<c:url value='/account/eventList/createNewEvent' />">Add new event</a>
        </sec:authorize>
    </p>
    <h3>${message}</h3>
<div class="row">
    <table class="table">
        <c:forEach var="event" items="${eventList}">
            <tbody>
            <tr>
                <td><img class="img-circle" style="width: 200px;height: 200px"
                         src="<c:url value="${event.photo}"/>"> </td>
                <td> <a href="/account/eventList/event-${event.eventId}"> ${event.name} </a></td>
                <td> Date : <span class="subSeconds">${event.dateStart}</span> - <span class="subSeconds">${event.dateEnd}</span> </td>
            </tr>
            </tbody>
        </c:forEach>

    </table>
</div>
</div>
<script src='${contextPath}/resources/js/datetime.js'></script>
</body>
