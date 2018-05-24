<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Update Folder</title>
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
        <div class="card-header"></div>
        <div class="card-body">

            <form:form method="POST" modelAttribute="editFolder" class="forms_form">
            <div class="row">
                <div class="col-md-6">
                    <div class="form-group">
                        <label>Folder Name: </label>
                        <form:input path="name" id="name" type="text" class="form-control"
                                    placeholder="Enter folder name" pattern="[A-Za-z0-9\s]{2,50}"/>
                        <form:errors path="name" cssClass="error"/>
                    </div>
                    <button id="update" class="btn btn-success text-center">Update</button>
                    </form:form>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    var input = document.getElementById('name');
    input.oninvalid = function(event) {
        event.target.setCustomValidity('Folder name should only contain English letters.');
    }
</script>
</html>

