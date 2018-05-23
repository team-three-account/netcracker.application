<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Edit Event </title>
    <link href="${contextPath}/resources/css/jquery.datetimepicker.min.css" rel="stylesheet">
    <link href="${contextPath}/resources/bootstrap3/css/bootstrap.min.css" rel="stylesheet">
    <!-- Custom fonts for this template-->
    <link href="${contextPath}/resources/css/style.css" rel="stylesheet">

    <%--for periodicity--%>
    <script src="${contextPath}/resources/vendor/bootstrap/js/jquery-1.11.1.min.js"></script>
    <script src="${contextPath}/resources/vendor/bootstrap/js/later.min.js"></script>
    <script src="${contextPath}/resources/vendor/bootstrap/js/moment.min.js"></script>
    <script src="${contextPath}/resources/vendor/bootstrap/js/prettycron.js"></script>
    <script src="${contextPath}/resources/js/periodicity.js"></script>

    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>

<body>
<div class="row">
    <jsp:include page="${contextPath}/WEB-INF/views/account/navbar/navbar.jsp"/>
    <div class="col-md-2"
    <jsp:include page="${contextPath}/WEB-INF/views/account/menu/menu.jsp"/>
</div>
<div class="col-md-10" style="margin-top: 5%">
    <div class="card card-register">
        <div class="card-header">Edit Event</div>
        <div class="card-body">
            <form:form id="valid_maps" method="POST" modelAttribute="editEvent" class="forms_form "
                       enctype="multipart/form-data">

            <c:choose>
                <%--<c:when test="${event.draft.equals(true)}">--%>
                <c:when test="${editEvent.isDraft().equals(true)}">
                    <div id="checkDraft"></div>
                </c:when>
            </c:choose>
            <div class="col-md-6">
                <div class="form-group">
                    <label>Photo: </label>
                    <img id="blah" class="img-circle" style="width: 200px;height: 200px"
                         src="<c:url value="${editEvent.photo}"/>">
                    <input type="hidden" name="photo" value="${editEvent.photo}">
                    <br><span class="btn btn-default btn-file">
                            Browse <input id="file" onchange="readURL(this)" type="file" name="photoFile"
                                          accept="image/*">
                            </span>
                    <span class="has-error">${message}</span>
                </div>
                <div class="form-group">
                    <label>Event Name: </label>
                    <form:input path="name" id="name" type="text" class="form-control"
                                placeholder="Enter event name" pattern="[A-Za-z0-9_]{4,50}"/>
                    <form:errors path="name" cssClass="error"/>
                </div>
                <div class="form-group">
                    <label>Description: </label>
                    <form:textarea path="description" id="description" type="text" class="form-control"
                                   placeholder="Enter event description" pattern="[A-Za-z0-9_]{4,200}"/>
                    <form:errors path="description" cssClass="error"/>
                </div>
                <div class="form-group">
                    <label>Start Date: </label>
                    <form:input path="dateStart" id="dateStart" type="text"
                                class="form-control dateValid subSeconds" onchange="changeDayOfMonth()"
                                placeholder="Enter event start date"/>
                    <form:errors path="dateStart" cssClass="error"/>
                </div>
                <div class="form-group">
                    <label>End Date: </label>
                    <form:input path="dateEnd" id="dateEnd" type="text" class="form-control dateValid subSeconds"
                                placeholder="Enter event end date"/>
                    <form:errors path="dateEnd" cssClass="error"/>
                </div>
                <c:if test="${editEvent.draft==true}">
                    <div class="form-group">
                        <label>Event type: </label>
                        <form:select path="type" class="form-control">
                            <form:options items="${eventTypes}" itemValue="typeId" itemLabel="name"/>
                        </form:select>
                        <form:errors path="type" cssClass="error"/>
                    </div>
                </c:if>
                <c:if test="${editEvent.draft==false}">
                    <form:input type="hidden" path="typeId" class="form-controll"/>
                </c:if>
                <form:input path="draft" value="${editEvent.draft}" type="hidden"></form:input>
                <input type="submit" value="Update" class="btn btn-success text-center"/>
            </div>
            <div class="col-md-6">
                <form:input path="width" type="hidden" id="latitude"></form:input>
                <form:input path="longitude" type="hidden" id="longitude"></form:input>
                <div class="form-group">
                    <label>Event place</label>
                    <form:input path="eventPlaceName" id="eventPlaceName" type="text" class="form-control"/>
                    <div id="map"></div>
                </div>
                <div class="form-group">
                    <label>Periodicity:</label>
                    <input id="periodicity" class="form-control" type="text" readonly></input>
                    <form:input path="periodicity" type="hidden" id="cron"></form:input>
                    <input value="Show periodicity options" class="btn btn-primary" type="button"
                           id="isPeriodical"
                           name="isPeriodical"
                           data-toggle="modal" data-target=".bs-example-modal-lg"
                           onclick="changePeriodicity()"/>
                    <input type="button" class="btn btn-danger" onclick="deletePeriodicity()"
                           value="Delete periodicity">
                    <div class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog"
                         aria-labelledby="myLargeModalLabel" aria-hidden="true">
                        <div class="modal-dialog modal-lg" style="margin-top: 10%">
                            <div class="modal-content">
                                <div class="modal-body">
                                    <div id="crontabs">
                                        <div class="cron-option" style="padding-bottom:10px">
                                            <input type="radio" id="cronEveryDay" name="cronOptions"
                                                   checked="checked">
                                            <label for="cronEveryDay" class="nofloat">Every day</label>
                                        </div>
                                        <div class="cron-option" style="padding-bottom:10px">
                                            <input type="radio" id="cronDomIncrement" name="cronOptions">
                                            <label for="cronDomIncrement" class="nofloat">Every
                                                <select id="cronDomIncrementIncrement" style="width:50px;">
                                                </select> day(s) starting on the <span
                                                        id="dayOfMonth"></span> of the month
                                            </label>
                                        </div>
                                        <div class="cron-option" style="padding-bottom:10px">
                                            <input type="radio" id="cronDowSpecific" name="cronOptions">
                                            <label for="cronDowSpecific" class="nofloat">Specific day of
                                                week
                                                (choose one or
                                                many)</label>
                                            <div style="margin-left:50px;">
                                                <label for="cronDowSun" class="nofloat">Sunday</label>
                                                <input type="checkbox" id="cronDowSun"
                                                       name="cronDowSpecificSpecific"
                                                       value="SUN"/>
                                                <label for="cronDowMon" class="nofloat">Monday</label>
                                                <input type="checkbox" id="cronDowMon"
                                                       name="cronDowSpecificSpecific"
                                                       value="MON"/>
                                                <label for="cronDowTue" class="nofloat">Tuesday</label>
                                                <input type="checkbox" id="cronDowTue"
                                                       name="cronDowSpecificSpecific"
                                                       value="TUE"/>
                                                <label for="cronDowWed" class="nofloat">Wednesday</label>
                                                <input type="checkbox" id="cronDowWed"
                                                       name="cronDowSpecificSpecific"
                                                       value="WED"/>
                                                <label for="cronDowThu" class="nofloat">Thursday</label>
                                                <input type="checkbox" id="cronDowThu"
                                                       name="cronDowSpecificSpecific"
                                                       value="THU"/>
                                                <label for="cronDowFri" class="nofloat">Friday</label>
                                                <input type="checkbox" id="cronDowFri"
                                                       name="cronDowSpecificSpecific"
                                                       value="FRI"/>
                                                <label for="cronDowSat" class="nofloat">Saturday</label>
                                                <input type="checkbox" id="cronDowSat"
                                                       name="cronDowSpecificSpecific"
                                                       value="SAT"/>
                                            </div>
                                        </div>
                                        <div class="cron-option" style="padding-bottom:10px">
                                            <input type="radio" id="cronEveryMonth" name="cronOptions">
                                            <label for="cronEveryMonth" class="nofloat">Every month</label>
                                        </div>
                                        <div class="cron-option" style="padding-bottom:10px">
                                            <input type="radio" id="cronEveryYear" name="cronOptions">
                                            <label for="cronEveryYear" class="nofloat">Every year</label>
                                        </div>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-default" data-dismiss="modal">
                                            Close
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    </form:form>
</div>

<script>
    $(function () {
        cron2text();
        $('#crontabs input, #crontabs select').change(cron);
        changeDayOfMonth();
    });
</script>
</body>
<script src='${contextPath}/resources/js/imageUpload.js'></script>
<%--<script type="text/javascript" src="${contextPath}/resources/js/nicEdit.js"></script>--%>
<%--<script src='${contextPath}/resources/js/textEditorInitAllArea.js'></script>--%>
<script src='${contextPath}/resources/js/pamCode.js'></script>
<script type="text/javascript"
        src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAw5DcnwHgQpslV50vf6yTeqBE7jgBTYpo&callback=initMap&language=en&libraries=places"></script>
<script src="https://cloud.tinymce.com/stable/tinymce.min.js"></script>
<script>tinymce.init({selector: 'textarea'});</script>
<script
        src="https://code.jquery.com/jquery-3.3.1.min.js"
        integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8="
        crossorigin="anonymous"></script>
<script src='${contextPath}/resources/js/jquery.datetimepicker.full.min.js'></script>
<script src='${contextPath}/resources/js/datetime.js'></script>
<script src='${contextPath}/resources/bootstrap3/js/bootstrap.min.js'></script>
</html>
