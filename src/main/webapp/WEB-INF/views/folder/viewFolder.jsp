<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>View Folder</title>
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
    <div class="container-fluid">
        <div class="col-md-6">
            <div class="panel panel-success">
                <div class="panel-heading">
                    <h3 class="panel-title">Folder - ${folder.name}</h3>
                </div>
                <div class="panel-body viewEvent">
                    <ul class="list-unstyled mt-3 mb-4">
                        <li>Name : ${folder.name}</li>
                        <li>Notes: <a href="/account/folder-${folder.folderId}/notes">notes</a> </li>

                        <c:if test="${auth_user.id.equals(user_creator.id)}">
                            <li>
                                <a href="/account/eventList/editFolder-${folder.folderId}">
                                    <input type="submit" class="btn btn-success text-center" value="Edit Folder"></a>
                                <a href="/account/eventList/deleteFolder-${folder.folderId}">
                                    <input type="submit" class="btn btn-danger text-center" value="Delete Folder"></a>
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

