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
    <script src="${contextPath}/resources/bootstrap3/js/bootstrap.min.js"></script>
    <script src="${contextPath}/resources/bootstrap3/js/bootstrap.js"></script>
    <script src="${contextPath}/resources/vendor/bootstrap/js/jquery-1.11.1.min.js"></script>
    <link href="${contextPath}/resources/css/style.css" rel="stylesheet">
    <script src="https://cdn.polyfill.io/v1/polyfill.js?features=Element.prototype.closest"></script>
    <script src="${contextPath}/resources/vendor/bootstrap/js/jquery-1.11.1.min.js"></script>
    <script src="${contextPath}/resources/js/DragManager.js"></script>
    <link href="${contextPath}/resources/css/drag.css" rel="stylesheet">
    <script>
        DragManager.onDragCancel = function(dragObject) {
            dragObject.avatar.rollback();
        };

        DragManager.onDragEnd = function(dragObject, dropElem) {
            dragObject.elem.style.display = 'none';
            dropElem.classList.add('folder-highlight');
            setTimeout(function() {
                dropElem.classList.remove('folder-highlight');
            }, 200);
        };
    </script>
</head>
<body>
<div class="row">
    <jsp:include page="${contextPath}/WEB-INF/views/account/navbar/navbar.jsp"/>
    <div class="col-md-2">
    <jsp:include page="${contextPath}/WEB-INF/views/account/menu/menu.jsp"/>
</div>

<div class="col-md-10 content">
    <jsp:include page="${contextPath}/WEB-INF/views/account/notification.jsp"></jsp:include>
    <sec:authorize access="hasRole('USER')">
        <a class="btn btn-primary" href="<c:url value='/account/createNote' />">Add Note</a>
        <a class="btn btn-primary" href="<c:url value='/account/createFolder' />">Create Folder</a>
        <a class="btn btn-primary" href="<c:url value='/account/sharedFoldersToMe' />">Shared to me</a>
    </sec:authorize>
    <div class="row">
        <table class="table">
            <h1>Folders</h1>
            <c:forEach var="folder" items="${folderList}">
            <div class="droppable">
                <input type="hidden" class="folderId" value="${folder.folderId}">
                <a class="notes-item" href="<c:url value='/account/folder-${folder.folderId}' />">
                    <img src="${contextPath}/resources/img/001-folder.svg" alt="folder">
                    <ul class="list-unstyled mt-3 mb-4 eventCardItem notesEventCardItem">
                        <li>${folder.name}</li>
                    </ul>
                </a>
            </div>
            </c:forEach>
            <h1>Notes without folder</h1>
            <c:forEach var="note" items="${noteList}">
            <div class="draggable">
                <input type="hidden" class="noteId" value="${note.noteId}">
                <a class="notes-item" href="<c:url value='/account/note-${note.noteId}' />">
                    <img src="${contextPath}/resources/img/003-notepad.svg" alt="notepad">
                    <ul class="list-unstyled mt-3 mb-4 eventCardItem notesEventCardItem">
                        <li>${note.name}</li>
                    </ul>
                </a>
            </div>
            </c:forEach>
        </table>
    </div>
</div>
</body>
</html>