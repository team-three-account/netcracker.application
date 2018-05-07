<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Update item</title>
    <link href="${contextPath}/resources/bootstrap3/css/bootstrap.min.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/style.css" rel="stylesheet">

    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<div class="row">
    <jsp:include page="${contextPath}/WEB-INF/views/account/navbar/navbar.jsp"/>
    <div class="col-md-3"
    <jsp:include page="${contextPath}/WEB-INF/views/account/menu/menu.jsp"/>
</div>
<div align="left" class="col-md-9 content">
    <div class="card card-register">
        <div class="card-header">Edit Item</div>
        <div class="card-body">
            <form:form method="POST" modelAttribute="updateItem" class="forms_form">
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
                <form:select path="priority" style="width: 30%" class="form-control">
                    <form:options items="${priorities}" style="width: 30%" itemValue="priorityId" itemLabel="name"/>
                </form:select>
                <form:errors path="priority" cssClass="error"/>
            </div>

            <input type="submit" value="Edit" class="btn btn-success text-center"/>
        </div>
    </div>
</div>
</form:form>
</div>
<script type="text/javascript" src="http://js.nicedit.com/nicEdit-latest.js"></script>
<script src='${contextPath}/resources/js/textEditorInit.js'></script>
</html>
