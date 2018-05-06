<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
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

<div  align="left" class="col-md-9 content">
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

    <%--<div class="form-group">--%>
    <%--<label>Person: </label>--%>
    <%--<form:input path="personId" id="name" style="width: 30%" type="text" class="form-control decodingHtml"--%>
                <%--placeholder="Enter person"/>--%>
    <%--<form:errors path="personId" cssClass="error"/>--%>
    <%--</div>--%>

    <%--<div class="form-group">--%>
    <%--<label>Booker: </label>--%>
    <%--<form:input path="booker" id="name" style="width: 30%" type="text" class="form-control decodingHtml"--%>
                <%--placeholder="Enter booker"/>--%>
    <%--<form:errors path="booker" cssClass="error"/>--%>
    <%--</div>--%>

    <div class="form-group">
    <label>Description: </label>
    <form:input path="description" id="name" style="width: 30%" type="text"  class="form-control decodingHtml"
                placeholder="Enter description"/>
    <form:errors path="description" cssClass="error"/>
    </div>

    <div class="form-group">
    <label>Link: </label>
    <form:input path="link" id="name" style="width: 30%" type="text"  class="form-control decodingHtml"
                placeholder="Enter link"/>
    <form:errors path="link" cssClass="error"/>
    </div>

    <div class="form-group">
    <label>Date: </label>
    <form:input path="dueDate" id="name" style="width: 30%" type="date"  class="form-control decodingHtml"
                placeholder="Enter dueDate"/>
    <form:errors path="dueDate" cssClass="error"/>
    </div>

    <div class="form-group">
    <label>Priority: </label>
    <form:input path="priority" id="name" style="width: 30%" type="text"  class="form-control decodingHtml"
                placeholder="Enter priority"/>
    <form:errors path="priority" cssClass="error"/>
    </div>

    <%--<div class="form-group">--%>
    <%--<label>Root: </label>--%>
    <%--<form:input path="root" id="name" style="width: 30%" type="text"  class="form-control decodingHtml"--%>
                <%--placeholder="Enter root"/>--%>
    <%--<form:errors path="root" cssClass="error"/>--%>

    <input type="submit" value="Edd" class="btn btn-success text-center"/>
    </div>
    </div>
    </div>
    </form:form>
</div>
</html>
