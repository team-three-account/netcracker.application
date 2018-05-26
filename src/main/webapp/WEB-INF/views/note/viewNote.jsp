<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>View Note</title>
    <link href="${contextPath}/resources/bootstrap3/css/bootstrap.min.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/style.css" rel="stylesheet">
    <script src="${contextPath}/resources/bootstrap3/js/bootstrap.min.js"></script>
    <script src="${contextPath}/resources/bootstrap3/js/bootstrap.js"></script>
    <script src="${contextPath}/resources/vendor/bootstrap/js/jquery-1.11.1.min.js"></script>
</head>
<body>
<div class="row">
    <jsp:include page="${contextPath}/WEB-INF/views/account/navbar/navbar.jsp"/>
    <div class="col-md-2">
    <jsp:include page="${contextPath}/WEB-INF/views/account/menu/menu.jsp"/>
</div>
<div class="col-md-10 content">
    <jsp:include page="${contextPath}/WEB-INF/views/account/notification.jsp"></jsp:include>
    <div class="container-fluid">
        <div class="col-md-6">
            <div class="panel panel-success">
                <div class="panel-heading">
                    <h3 class="panel-title">Note - ${note.name}</h3>
                </div>
                <div class="panel-body viewEvent">
                    <ul class="list-unstyled mt-3 mb-4">
                        <li>Name : ${note.name}</li>
                        <li>Description : <span class="description-block">${note.description}</span></li>
                        <c:if test="${auth_user.id.equals(user_creator.id)}">
                            <li class="button_block">
                                <a href="/account/editNote-${note.noteId}">
                                    <input type="submit" class="btn btn-success text-center" value="Edit Note"></a>
                                <c:if test="${note.folder!=0}">
                                    <a href="/account/deleteFF-${note.noteId}">
                                        <input type="submit" class="btn btn-danger text-center"
                                               value="Delete note from folder"></a>
                                </c:if>
                                <a href="/account/deleteNote-${note.noteId}">
                                    <input type="submit" class="btn btn-danger text-center" value="Delete Note"></a>
                                <c:if test="${note.folder==0}">
                                    <a href="/account/add-note-${note.noteId}">
                                        <input type="submit" class="btn btn-success text-center"
                                               value="Add note to folder"></a>
                                </c:if>

                                <a href="/account/translateToEvent-${note.noteId}">
                                    <input type="submit" class="btn btn-success text-center" value="Event Translate"></a>
                            </li>
                        </c:if>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
