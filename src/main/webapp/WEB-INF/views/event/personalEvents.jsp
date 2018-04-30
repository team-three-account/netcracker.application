<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Event List</title>
    <link href="${contextPath}/resources/bootstrap3/css/bootstrap.min.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/style.css" rel="stylesheet">
</head>
<body>
<div class="row">
    <jsp:include page="${contextPath}/WEB-INF/views/account/navbar/navbar.jsp"/>
    <div class="col-md-3"
    <jsp:include page="${contextPath}/WEB-INF/views/account/menu/menu.jsp"/>
</div>
<div class="col-md-9 content ">

    <div class="card-deck mb-3 text-center">
        <div class="card col-md-3 box-shadow eventCategory">
            <div class="card-header">
                <h4 class="my-0 font-weight-normal eventCategory">My Events</h4>
            </div>
            <div class="card-body eventCard">
                <c:forEach var="emp" items="${personalEventList}">
                    <a href="<c:url value='/account/eventList/event-${emp.eventId}' />">
                        <ul class="list-unstyled mt-3 mb-4 eventCardItem">
                            <li>${emp.name}</li>
                            <li>Start ${emp.dateStart}</li>
                            <li>End ${emp.dateEnd}</li>
                        </ul>
                    </a>
                </c:forEach>
            </div>
        </div>

        </div>
    </div>
</div>
<div>
    <sec:authorize access="hasRole('USER')">
        <a class="btn btn-primary" href="<c:url value='/account/eventList/createNewEvent' />">Add new event</a>
    </sec:authorize>
</div>

</body>
