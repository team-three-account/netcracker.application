<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Notes</title>
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
        <table class="table">
            <h1>Folders:</h1>
            <form:form method="post" modelAttribute="newNoteIntoFolder">
                <div class="form-group">
                    <form:select path="folder" class="form-control">
                        <form:options items="${listFolders}" itemValue="folderId" itemLabel="name"/>
                    </form:select>
                    <form:errors path="folder" cssClass="error"/>
                </div>
                <button id="add" class="btn btn-success text-center">Add to folder</button>
            </form:form>
        </table>
</div>
</body>
</html>