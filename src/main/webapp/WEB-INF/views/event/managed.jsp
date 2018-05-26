<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Event List</title>
    <link href="${contextPath}/resources/bootstrap3/css/bootstrap.min.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/style.css" rel="stylesheet">
    <script src="${contextPath}/resources/vendor/bootstrap/js/jquery-1.11.1.min.js"></script>
</head>
<body>
<div class="row">
    <jsp:include page="${contextPath}/WEB-INF/views/account/navbar/navbar.jsp"/>
    <div class="col-md-2">
        <jsp:include page="${contextPath}/WEB-INF/views/account/menu/menu.jsp"/>
    </div>
    <div class="col-md-10 content ">
        <jsp:include page="${contextPath}/WEB-INF/views/account/notification.jsp"></jsp:include>
        <p>
            <a class="btn btn-primary" href="/account/available" role="button">All events</a>
            <a class="btn btn-primary" href="/account/subscriptions" role="button">Subscriptions</a>
            <a class="btn btn-primary" href="/account/managed" role="button">Managed events</a>
            <a class="btn btn-primary" href="/account/draft" role="button">Drafts</a>
            <sec:authorize access="hasRole('USER')">
                <a class="btn btn-success" href="<c:url value='/account/eventList/createNewEvent' />">Add new event</a>
            </sec:authorize>
        </p>


        <h3>${message}</h3>
        <div class="card-deck mb-3 text-center">
            <div class="card col-md-3 box-shadow eventCategory">
                <div class="card-header">
                    <h4 class="my-0 font-weight-normal eventCategory">Public events</h4>
                </div>

                <div class="card-body eventCard">
                    <c:forEach var="emp" items="${publicEventList}">
                        <a href="<c:url value='/account/eventList/event-${emp.eventId}' />">
                            <ul class="list-unstyled mt-3 mb-4 eventCardItem">
                                <li><img class="img-circle" style="width: 20px;height: 20px"
                                         src="<c:url value="${emp.photo}"/>"></li>
                                </li>
                                <li>${emp.name}</li>
                                <li>Start :<span class="subSeconds">${emp.dateStart}</span></li>
                                <li>End :<span class="subSeconds">${emp.dateEnd}</span></li>
                            </ul>
                        </a>
                    </c:forEach>
                </div>

            </div>

            <div class="card col-md-3 box-shadow eventCategory">
                <div class="card-header">
                    <h4 class="my-0 font-weight-normal eventCategory">Only for friends events</h4>
                </div>
                <div class="card-body eventCard">

                    <div class="card-body eventCard">
                        <c:forEach var="emp" items="${friendsEventList}">
                            <a href="<c:url value='/account/eventList/event-${emp.eventId}' />">
                                <ul class="list-unstyled mt-3 mb-4 eventCardItem">
                                    <li><img class="img-circle" style="width: 20px;height: 20px"
                                             src="<c:url value="${emp.photo}"/>"></li>
                                    </li>
                                    <li>${emp.name}</li>
                                    <li>Start :<span class="subSeconds">${emp.dateStart}</span></li>
                                    <li>End :<span class="subSeconds">${emp.dateEnd}</span></li>
                                </ul>
                            </a>
                        </c:forEach>
                    </div>

                </div>
            </div>

            <div class="card col-md-3 box-shadow eventCategory">
                <div class="card-header">
                    <h4 class="my-0 font-weight-normal eventCategory">Private events</h4>
                </div>
                <div class="card-body eventCard">

                    <div class="card-body eventCard">
                        <c:forEach var="emp" items="${privateEventList}">
                            <a href="<c:url value='/account/eventList/event-${emp.eventId}' />">
                                <ul class="list-unstyled mt-3 mb-4 eventCardItem">
                                    <li><img class="img-circle" style="width: 20px;height: 20px"
                                             src="<c:url value="${emp.photo}"/>"></li>
                                    </li>
                                    <li>${emp.name}</li>
                                    <li>Start :<span class="subSeconds">${emp.dateStart}</span></li>
                                    <li>End :<span class="subSeconds">${emp.dateEnd}</span></li>
                                </ul>
                            </a>

                        </c:forEach>
                    </div>

                </div>
            </div>
        </div>
    </div>
</div>

<script src='${contextPath}/resources/js/datetime.js'></script>
</body>
