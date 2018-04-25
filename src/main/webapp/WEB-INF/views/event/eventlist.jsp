<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Event List</title>
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
    <div class="container">
        <h1 class="default_h1">Simple Event List.. will be soon </h1>

        <table class="table">
            <tr>
                <th>Name</th>
                <th>creator</th>
                <th>Start</th>
                <th>End</th>
                <th>type</th>
                <th>Delete</th>
            </tr>
            <c:forEach var="emp" items="${eventList}">
                <tr>
                    <td><a href="<c:url value='/account/eventList/editevent-${emp.eventId}' />">${emp.name}</a></td>
                    <td>${emp.creator}</td>
                    <td>${emp.dateStart}</td>
                    <td>${emp.dateEnd}</td>
                    <td>${emp.type}</td>
                    <td>
                        <sec:authorize access="hasRole('USER')">
                            <a class="btn btn-danger"
                               href="<c:url value='/account/eventList/deleteEvent-${emp.eventId}' />">Delete</a>
                        </sec:authorize>
                    </td>
                </tr>

            </c:forEach>
        </table>
        <div>
            <sec:authorize access="hasRole('USER')">
                <a class="btn btn-primary" href="<c:url value='/account/eventList/createNewEvent' />">Add new event</a>
            </sec:authorize>
        </div>
    </div>
</div>
</body>

<%--<td>--%>
<%--<a href="<c:url value='/account/eventList/createNewEvent' />">Edit</a>--%>
<%--</td>--%>
<%--<td>--%>
<%--<sec:authorize access="hasRole('USER')">--%>
<%--<a href="<c:url value='account/eventList/deleteEvent-{eventId}' />">Delete</a>--%>
<%--</sec:authorize>--%>
<%--</td>--%>