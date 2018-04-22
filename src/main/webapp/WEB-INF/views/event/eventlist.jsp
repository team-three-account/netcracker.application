<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<h1>Simple Event List.. will be soon </h1>
<table border="2" width="100%" cellpadding="2">
    <tr>
        <th>Id</th>
        <th>Name</th>
        <th>Description</th>
        <th>creator</th>
        <th>Start</th>
        <th>End</th>
        <th>Place</th>
        <th>Address</th>
        <th>period</th>
        <th>type</th>
        <th>draft</th>
        <th>folder</th>
    </tr>
    <c:forEach var="emp" items="${eventList}">
        <c:choose>
            <c:when test="${emp.type=='public'}">
                <tr>
                    <td class="green">${emp.eventId}</td>
                    <td class="green">${emp.name}</td>
                    <td class="green">${emp.description}</td>
                    <td class="green">${emp.creator}</td>
                    <td class="green">${emp.dateStart}</td>
                    <td class="green">${emp.dateEnd}</td>
                    <td class="green">${emp.placeId}</td>
                    <td class="green">${emp.placeAddress}</td>
                    <td class="green">${emp.periodicity}</td>
                    <td class="green">${emp.type}</td>
                    <td class="green">${emp.draft}</td>
                    <td class="green">${emp.folder}</td>
                </tr>
            </c:when>
            <c:otherwise>
                <tr>
                    <td class="red">${emp.eventId}</td>
                    <td class="red">${emp.name}</td>
                    <td class="red">${emp.description}</td>
                    <td class="red">${emp.creator}</td>
                    <td class="red">${emp.dateStart}</td>
                    <td class="red">${emp.dateEnd}</td>
                    <td class="red">${emp.placeId}</td>
                    <td class="red">${emp.placeAddress}</td>
                    <td class="red">${emp.periodicity}</td>
                    <td class="red">${emp.type}</td>
                    <td class="red">${emp.draft}</td>
                    <td class="red">${emp.folder}</td>
                </tr>
            </c:otherwise>
        </c:choose>
    </c:forEach>
</table>
<br/>

<style>
    .green {
        color: green;
    }

    .red {
        color: red;
    }
</style>

<div>
    <sec:authorize access="hasRole('USER')">
        <a href="<c:url value='/account/eventList/createNewEvent' />">Add new event</a>
    </sec:authorize>
</div>

<%--<td>--%>
<%--<a href="<c:url value='/account/eventList/createNewEvent' />">Edit</a>--%>
<%--</td>--%>
<%--<td>--%>
<%--<sec:authorize access="hasRole('USER')">--%>
<%--<a href="<c:url value='account/eventList/deleteEvent-{eventId}' />">Delete</a>--%>
<%--</sec:authorize>--%>
<%--</td>--%>