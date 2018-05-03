<%--
  Created by IntelliJ IDEA.
  User: anyat
  Date: 02.05.2018
  Time: 23:47
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Notes</title>
    <link href="${contextPath}/resources/bootstrap3/css/bootstrap.min.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/style.css" rel="stylesheet">
</head>
<body>
<div class="row">
    <jsp:include page="${contextPath}/WEB-INF/views/account/navbar/navbar.jsp"/>
    <div class="col-md-3"
    <jsp:include page="${contextPath}/WEB-INF/views/account/menu/menu.jsp"/>
</div>

<div class="col-md-9 content">
    <sec:authorize access="hasRole('USER')">
        <a class="btn btn-primary" href="<c:url value='/account/eventList/createNote' />">Add Note</a>
    </sec:authorize>
    <div class="row">
        <table class="table">
            <c:forEach var="note" items="${noteList}">
                <a href="<c:url value='/account/eventList/note-${note.noteId}' />">
                    <ul class="list-unstyled mt-3 mb-4 eventCardItem">
                        <li>${note.name}</li>
                    </ul>
                </a>
            </c:forEach>

        </table>
    </div>
</div>
</div>
</div>
</body>
</html>