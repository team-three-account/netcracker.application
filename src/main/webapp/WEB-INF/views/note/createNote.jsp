<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Create New Note </title>

    <link href="${contextPath}/resources/bootstrap3/css/bootstrap.min.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/style.css" rel="stylesheet">

</head>
<div class="row">
    <jsp:include page="${contextPath}/WEB-INF/views/account/navbar/navbar.jsp"/>
    <div class="col-md-3"
    <jsp:include page="${contextPath}/WEB-INF/views/account/menu/menu.jsp"/>
</div>

<body>
<div class="col-md-9 content">
    <div class="card card-register">
        <div class="card-header"></div>
        <div class="card-body">

            <form:form method="POST" modelAttribute="createNote" class="forms_form">
            <div class="row">
                <div class="col-md-6">
                    <div class="form-group">
                        <label>Note Name: </label>
                        <form:input path="name" id="name" type="text" class="form-control"
                                    placeholder="Enter note name"/>
                        <form:errors path="name" cssClass="error"/>
                    </div>
                    <div class="form-group">
                        <label>Description: </label>
                        <form:textarea path="description" id="description" type="text" class="form-control"
                                       placeholder="Enter note description"/>
                        <form:errors path="description" cssClass="error"/>
                    </div>

                    <button id="create" class="btn btn-success text-center">Create</button>
                    </form:form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<script type="text/javascript" src="http://js.nicedit.com/nicEdit-latest.js"></script>
<script src='${contextPath}/resources/js/textEditorInit.js'></script>
</html>
