<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>View Folder</title>
    <link href="${contextPath}/resources/bootstrap3/css/bootstrap.min.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/style.css" rel="stylesheet">
    <script src="${contextPath}/resources/bootstrap3/js/bootstrap.min.js"></script>
    <script src="${contextPath}/resources/bootstrap3/js/bootstrap.js"></script>
    <script src="${contextPath}/resources/vendor/bootstrap/js/jquery-1.11.1.min.js"></script>
</head>
<body>
<div class="row">
    <jsp:include page="${contextPath}/WEB-INF/views/account/navbar/navbar.jsp"/>
    <div class="col-md-2" style="height:100vh;">
        <jsp:include page="${contextPath}/WEB-INF/views/account/menu/menu.jsp"/>
    </div>
    <div class="col-md-10 main-content">
        <jsp:include page="${contextPath}/WEB-INF/views/account/notification.jsp"></jsp:include>
        <div class="d-flex">
            <h3>Folder - ${folder.name}</h3>
            <c:if test="${auth_user.id.equals(folder.creator)}">
                <a href="/account/share-${folder.folderId}">
                    <input type="submit" class="btn btn-success text-center" value="Share"></a>
                <a href="/account/editFolder-${folder.folderId}">
                    <input type="submit" class="btn btn-success text-center" value="Edit Folder"></a>
                <a href="/account/deleteFolder-${folder.folderId}">
                    <input type="submit" class="btn btn-danger text-center" value="Delete Folder"></a>
            </c:if>
        </div>
        <h3>${message}</h3>
        <c:forEach var="note" items="${listNotesIntoFolder}">
            <a class="notes-item" href="<c:url value='/account/note-${note.noteId}' />">
                <img src="${contextPath}/resources/img/003-notepad.svg" alt="notepad">
                <ul class="list-unstyled mt-3 mb-4 eventCardItem notesEventCardItem">
                    <li>${note.name}</li>
                </ul>
            </a>
        </c:forEach>
    </div>
</div>
</body>
</html>

