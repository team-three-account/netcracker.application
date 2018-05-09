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
        <a class="btn btn-primary" href="<c:url value='/account/eventList/createFolder' />">Create Folder</a>
    </sec:authorize>
    <div class="row">
        <table class="table">
            <h1>Folders</h1>
            <c:forEach var="folder" items="${folderList}">
                <a class="notes-item" href="<c:url value='/account/eventList/folder-${folder.folderId}' />">
                    <img src="${contextPath}/resources/img/001-folder.svg" alt="folder">
                    <ul class="list-unstyled mt-3 mb-4 eventCardItem notesEventCardItem">
                        <li>${folder.name}</li>
                    </ul>
                </a>
            </c:forEach>
            <h1>Notes Without Folder</h1>
            <c:forEach var="note" items="${noteList}">
                <a class="notes-item" href="<c:url value='/account/eventList/note-${note.noteId}' />">
                    <img src="${contextPath}/resources/img/003-notepad.svg" alt="notepad">
                    <ul class="list-unstyled mt-3 mb-4 eventCardItem notesEventCardItem">
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