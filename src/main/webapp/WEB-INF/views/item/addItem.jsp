<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Add new item</title>
    <link href="${contextPath}/resources/bootstrap3/css/bootstrap.min.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/style.css" rel="stylesheet">
</head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<div class="row">
    <jsp:include page="${contextPath}/WEB-INF/views/account/navbar/navbar.jsp"/>
    <div class="col-md-3"
    <jsp:include page="${contextPath}/WEB-INF/views/account/menu/menu.jsp"/>
</div>

<div align="center" class="col-md-9 content">
    <div class="card card-register">
        <div class="card-header">Create Item</div>
        <div class="card-body">
            <form:form method="POST" modelAttribute="createItem" class="forms_form" enctype="multipart/form-data">
            <div class="form-group">
                <label>Item Name: </label>
                <form:input path="name" id="name" style="width: 30%" type="text" class="form-control decodingHtml"
                            placeholder="Enter item name"/>
                <form:errors path="name" cssClass="error"/>
            </div>

            <div class="form-group">
                <label>Description: </label>
                <form:input path="description" id="description" style="width: 30%" type="text"
                            class="form-control decodingHtml"
                            placeholder="Enter description"/>
                <form:errors path="description" cssClass="error"/>
            </div>

            <div class="form-group">
                <label>Link: </label>
                <form:input path="link" id="link" style="width: 30%" type="text" class="form-control decodingHtml"
                            placeholder="Enter link"/>
                <form:errors path="link" cssClass="error"/>
            </div>

            <div class="form-group">
                <label>Date: </label>
                <form:input path="dueDate" id="dueDate" style="width: 30%" type="date" class="form-control decodingHtml"
                            placeholder="Enter dueDate"/>
                <form:errors path="dueDate" cssClass="error"/>
            </div>

            <div class="form-group">
                <label>Priority: </label>
                <tr>
                    <form:radiobuttons path="priority" disabled="false" items="${priorities}" itemValue="priorityId" itemLabel="name"/>
                </tr>
            </div>

            <input type="submit" value="Add" class="btn btn-success text-center"/>
            <a class="btn btn-primary" href="<c:url value='/account/user-${auth_user.id}/wishList'/>">Back</a>
        </div>
    </div>
</div>
</form:form>
</div>
</html>
