<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Update Folder </title>
    <link href="${contextPath}/resources/bootstrap3/css/bootstrap.min.css" rel="stylesheet">
    <!-- Custom fonts for this template-->
    <link href="${contextPath}/resources/css/style.css" rel="stylesheet">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<div class="row">
    <jsp:include page="${contextPath}/WEB-INF/views/account/navbar/navbar.jsp"/>
    <div class="col-md-3"
    <jsp:include page="${contextPath}/WEB-INF/views/account/menu/menu.jsp"/>
</div>


<div class="col-md-9 content">
    <div class="card card-register">
        <div class="card-header">Update Note</div>
        <div class="card-body">
            <form:form method="POST" modelAttribute="editFolder" class="forms_form">
                <div class="col-md-6">
                    <div class="form-group">
                        <label>Folder Name: </label>
                        <form:input path="name" id="nameFolder" type="text" class="form-control"
                                    placeholder="Enter Folder name"/>
                        <form:errors path="name" cssClass="error"/>
                    </div>
                </div>
                <input type="submit" value="Update" class="btn btn-dark text-center"/>
            </form:form>
        </div>
    </div>
</div>
</html>

