<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Update Note</title>
    <link href="${contextPath}/resources/bootstrap3/css/bootstrap.min.css" rel="stylesheet">
    <!-- Custom fonts for this template-->
    <link href="${contextPath}/resources/css/style.css" rel="stylesheet">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<div class="row">
    <jsp:include page="${contextPath}/WEB-INF/views/account/navbar/navbar.jsp"/>
    <div class="col-md-2"
    <jsp:include page="${contextPath}/WEB-INF/views/account/menu/menu.jsp"/>
</div>


<div class="col-md-10 content">
    <div class="card card-register">
        <div class="card-header">Update Note</div>
        <div class="card-body">
            <form:form method="POST" modelAttribute="editNote" class="forms_form">
                <div class="col-md-6">
                    <div class="form-group">
                        <label>Note Name: </label>
                        <form:input path="name" id="nameNote" type="text" class="form-control"
                                    placeholder="Enter Note name" pattern="[A-Za-z0-9_]{4,50}"/>
                        <form:errors path="name" cssClass="error"/>
                    </div>
                    <div class="form-group">
                        <label>Description: </label>
                        <form:textarea path="description" id="descriptionNote" type="text" class="form-control"
                                    placeholder="Enter Note description" pattern="[A-Za-z0-9_]{4,50}"/>
                        <form:errors path="description" cssClass="error"/>
                    </div>
                </div>
                <input type="submit" value="Update" class="btn btn-dark text-center"/>
            </form:form>
        </div>
    </div>
</div>
<script
        src="https://code.jquery.com/jquery-3.3.1.min.js"
        integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8="
        crossorigin="anonymous"></script>
<%--<script type="text/javascript" src="${contextPath}/resources/js/nicEdit.js"></script>--%>
<%--<script src='${contextPath}/resources/js/textEditorInitAllArea.js'></script>--%>
<script src="https://cloud.tinymce.com/stable/tinymce.min.js"></script>
<script>tinymce.init({ selector:'textarea' });</script>
</html>

