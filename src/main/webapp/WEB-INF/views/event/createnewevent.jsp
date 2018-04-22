<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Create New Event </title>
    <link href="${contextPath}/resources/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <!-- Custom fonts for this template-->
    <link href="${contextPath}/resources/vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
    <!-- Custom styles for this template-->
    <link href="${contextPath}/resources/css/sb-admin.css">

    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<div class="container">
    <div class="card card-register mx-auto mt-5 col-md-6">
        <div class="card-header">Create New Event</div>
        <div class="card-body">
            <form:form method="POST" modelAttribute="createNewEvent" class="forms_form">
                <div class="form-group">
                    <label>Event Name: </label>
                    <form:input path="name" id="name" type="text" class="form-control" placeholder="Enter event name"/>
                    <form:errors path="name" cssClass="error"/>
                </div>
                <div class="form-group">
                    <label>Description: </label>
                    <form:input path="description" id="description" type="text" class="form-control"
                                placeholder="Enter event description"/>
                    <form:errors path="description" cssClass="error"/>
                </div>
                <div class="form-group">
                    <label>Start_date: </label>
                    <form:input path="dateStart" id="dateStart" type="date" class="form-control"
                                placeholder="Enter event start date"/>
                    <form:errors path="dateStart" cssClass="error"/>
                </div>
                <div class="form-group">
                    <label>End_date: </label>
                    <form:input path="dateEnd" id="dateEnd" type="date" class="form-control"
                                placeholder="Enter event end date"/>
                    <form:errors path="dateEnd" cssClass="error"/>
                </div>
                <div class="form-group">
                    <label>Place_id: </label>
                    <form:input path="placeId" id="placeId" type="text" class="form-control"
                                placeholder="Enter place id"/>
                    <form:errors path="placeId" cssClass="error"/>
                </div>
                <div class="form-group">
                    <label>Place_Address: </label>
                    <form:input path="placeAddress" id="placeAddress" type="text" class="form-control"
                                placeholder="Enter place adress"/>
                    <form:errors path="placeAddress" cssClass="error"/>
                </div>
                <div class="form-group">
                    <label>period</label>
                    <form:input path="periodicity" id="periodicity" type="text" class="form-control"
                                placeholder="Enter periodicity"/>
                    <form:errors path="periodicity" cssClass="error"/>
                </div>
                <div class="form-group">
                    <label>Event type: </label>
                    <form:select path="type"  class="form-control">
                        <form:options items="${eventTypes}" itemValue="typeId" itemLabel="type"/>
                    </form:select>
                </div>

                <div class="form-group">
                    <label>Is_Draft</label>
                    <form:input path="draft" id="draft" type="text" class="form-control" placeholder="Enter draft"/>
                </div>
                <div class="form-group">
                    <label>Folder</label>
                    <form:input path="folder" id="folder" type="text" class="form-control" placeholder="Enter foler"/>
                </div>
                <input type="submit" value="Create" class="btn btn-dark text-center"/>
            </form:form>
        </div>
    </div>
</div>
</html>
