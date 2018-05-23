<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Create New Note</title>

    <link href="${contextPath}/resources/bootstrap3/css/bootstrap.min.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/style.css" rel="stylesheet">

</head>
<div class="row">
    <jsp:include page="${contextPath}/WEB-INF/views/account/navbar/navbar.jsp"/>
    <div class="col-md-2"
    <jsp:include page="${contextPath}/WEB-INF/views/account/menu/menu.jsp"/>
</div>

<body>
<div class="col-md-10 content">
    <div class="card card-register">
        <div class="card-header"></div>
        <div class="card-body">

            <form:form method="POST" modelAttribute="createNote" class="forms_form">
            <div class="row">
                <div class="col-md-6">
                    <div class="form-group">
                        <label>Note Name: </label>
                        <form:input path="name" id="name" type="text" class="form-control"
                                    placeholder="Enter note name" pattern="[A-Za-z0-9\s]{4,50}"/>
                        <form:errors path="name" cssClass="error"/>
                    </div>
                    <div class="form-group">
                        <label>Description: </label>
                        <form:textarea path="description" id="description" type="text" class="form-control"
                                       placeholder="Enter event description" pattern="[A-Za-z0-9_]{4,200}"/>
                        <form:errors path="description" cssClass="error"/>
                    </div>

                    <button id="create" class="btn btn-success text-center">Create</button>
                    </form:form>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    var input = document.getElementById('name');
    input.oninvalid = function(event) {
        event.target.setCustomValidity('Note name should only contain english letters.');
    }
</script>
</body>
<%--<script type="text/javascript" src="${contextPath}/resources/js/nicEdit.js"></script>--%>
<%--<script src='${contextPath}/resources/js/textEditorInitAllArea.js'></script>--%>
<script src="https://cloud.tinymce.com/stable/tinymce.min.js"></script>
<script>tinymce.init({ selector:'textarea' });</script>
</html>
