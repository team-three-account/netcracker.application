<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Event registration</title>
</head>
<body>
<form:form method="POST" modelAttribute="createNewEvent">
    <div>
        <label>name</label>
        <form:input path="name" id="name" type="text"/>
        <form:errors path="name"/>
    </div>
    <div>
        <label>Description</label>
        <form:input path="description" id="description" type="text"/>
        <form:errors path="description"/>
    </div>
    <div>
        <label>start_date</label>
        <form:input path="dateStart" id="dateStart" type="date"/>
    </div>
    <div>
        <label>end_date</label>
        <form:input path="dateEnd" id="dateEnd" type="date"/>
    </div>
    <div>
        <label>place_id</label>
        <form:input path="placeId" id="placeId" type="text"/>
    </div>
    <div>
        <label>place_Address</label>
        <form:input path="placeAddress" id="placeAddress" type="text"/>
    </div>
    <div>
        <label>period</label>
        <form:input path="periodicity" id="periodicity" type="text"/>
    </div>

    <div>
        <label>is_Draft</label>
        <form:input path="draft" id="draft" type="text"/>
    </div>
    <div>
        <label>folder</label>
        <form:input path="folder" id="folder" type="text"/>
    </div>
    <input type="submit" value="Save"/>
</form:form>

</body>
</html>
