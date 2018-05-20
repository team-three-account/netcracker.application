<%--
  Created by IntelliJ IDEA.
  User: anyat
  Date: 16.05.2018
  Time: 21:05
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Shared folders to me</title>
    <link href="${contextPath}/resources/bootstrap3/css/bootstrap.min.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/style.css" rel="stylesheet">
</head>
<body>
<div class="row">
    <jsp:include page="${contextPath}/WEB-INF/views/account/navbar/navbar.jsp"/>
    <div class="col-md-2"
    <jsp:include page="${contextPath}/WEB-INF/views/account/menu/menu.jsp"/>
</div>

<div class="col-md-10 content">
    <div>
        <a class="btn btn-primary" href="<c:url value='/account/allNotes'/>"> < Back </a>
    </div>
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
        </table>
    </div>
</div>
</body>
</html>
