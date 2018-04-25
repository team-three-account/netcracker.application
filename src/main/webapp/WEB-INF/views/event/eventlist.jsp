<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link href="${contextPath}/resources/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<div class="container">
    <h1 class="default_h1">Simple Event List.. will be soon </h1>

    <table class="table table-striped" border="2" width="60%" cellpadding="2">
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
    <br/>

    <style>
        .green {
            background-color: rgba(0, 255, 0, 0.3);
        }

        .red {
            background-color: rgba(255, 0, 0, 0.3);
        }

        .blue {
            background-color: rgba(0, 0, 255, 0.3);
        }

        .pink {
            background-color: rgba(255, 192, 203, 0.3);
        }

        .default_h1 {
            padding: 20px 0;
        }
    </style>

    <div>
        <sec:authorize access="hasRole('USER')">
            <a class="btn btn-primary" href="<c:url value='/account/eventList/createNewEvent' />">Add new event</a>
        </sec:authorize>
    </div>
</div>


<%--<td>--%>
<%--<a href="<c:url value='/account/eventList/createNewEvent' />">Edit</a>--%>
<%--</td>--%>
<%--<td>--%>
<%--<sec:authorize access="hasRole('USER')">--%>
<%--<a href="<c:url value='account/eventList/deleteEvent-{eventId}' />">Delete</a>--%>
<%--</sec:authorize>--%>
<%--</td>--%>