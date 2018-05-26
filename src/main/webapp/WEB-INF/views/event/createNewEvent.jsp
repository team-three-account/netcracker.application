<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Create New Event </title>
    <link href="${contextPath}/resources/css/jquery.datetimepicker.min.css" rel="stylesheet">
    <link href="${contextPath}/resources/bootstrap3/css/bootstrap.min.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/style.css" rel="stylesheet">
    <%--for periodicity--%>
    <script src="${contextPath}/resources/vendor/bootstrap/js/jquery-1.11.1.min.js"></script>
    <script src="${contextPath}/resources/vendor/bootstrap/js/later.min.js"></script>
    <script src="${contextPath}/resources/vendor/bootstrap/js/moment.min.js"></script>
    <script src="${contextPath}/resources/vendor/bootstrap/js/prettycron.js"></script>
    <script src="${contextPath}/resources/js/periodicity.js"></script>
    <script src="https://cloud.tinymce.com/stable/tinymce.min.js"></script>
    <script>tinymce.init({selector: 'textarea'});</script>
</head>
<div class="row">
    <jsp:include page="${contextPath}/WEB-INF/views/account/navbar/navbar.jsp"/>
    <div class="col-md-2"
    <jsp:include page="${contextPath}/WEB-INF/views/account/menu/menu.jsp"/>
</div>

<div class="col-md-10 content">
    <div class="card card-register">
        <div class="card-header">Create New Event</div>
        <div class="card-body">
            <jsp:include page="${contextPath}/WEB-INF/views/account/notification.jsp"></jsp:include>
            <%--action="/account/eventList/createNewEvent"--%>
            <form:form id="valid_maps" method="POST" modelAttribute="createNewEvent" class="forms_form"
                       enctype="multipart/form-data">
                <div class="row">
                    <div class="col-md-6">
                        <div class="form-group">
                            <label>Photo: </label>

                            <img class="img-circle" style="width: 200px;height: 200px" id="blah"
                                 src="<c:url value="${createNewEvent.photo}.jpg"/>">
                            <input type="hidden" name="photoInput" value="${createNewEvent.photo}">
                            <br><span class="btn btn-default btn-file">
                            Browse <input type="file" onchange="readURL(this)" id="file" name="photoFile"
                                          accept="image/*">
                            </span>
                            <span>${message}</span>
                        </div>
                        <div class="form-group">
                            <label>Event Name*: </label>
                            <form:input path="name" id="name" type="text" class="form-control"
                                        placeholder="Enter event name"/>
                            <form:errors path="name" cssClass="error"/>
                        </div>
                        <div class="form-group">
                            <label>Description*: </label>
                            <form:textarea path="description" name="description" id="description" type="text"
                                           class="form-control"
                                           placeholder="Enter event description"/>
                            <form:errors path="description" cssClass="error"/>
                        </div>
                        <div class="form-group">
                            <label>Start Date*: </label>
                            <form:input path="dateStart" id="dateStart" type="text" class="form-control dateValid "
                                        onchange="changeDayOfMonth()" placeholder="Enter event start date"/>
                            <form:errors path="dateStart" cssClass="error"/>
                        </div>
                        <div class="form-group">
                            <label>End Date*: </label>
                            <form:input path="dateEnd" id="dateEnd " type="text" class="form-control dateValid"
                                        placeholder="Enter event end date"/>
                            <form:errors path="dateEnd" cssClass="error"/>
                        </div>

                        <div class="form-group">
                            <label>Event type*: </label>
                            <form:select path="type" class="form-control">
                                <form:options items="${eventTypes}" itemValue="typeId" itemLabel="name"/>
                            </form:select>
                            <form:errors path="type" cssClass="error"/>
                        </div>
                    </div>
                    <div class="col-lg-6">
                        <form:input path="width" type="hidden" id="latitude"></form:input>
                        <form:input path="longitude" type="hidden" id="longitude"></form:input>
                        <div class="form-group">
                            <label>Event place*</label>
                            <form:input path="eventPlaceName" id="eventPlaceName" type="text" class="form-control"/>
                            <form:errors path="eventPlaceName" cssClass="error"/>
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
                                                <div class="form-group">
                                                    <label>End Repeat: </label>
                                                    <form:input path="endRepeat" id="endRepeat" type="text"
                                                                class="form-control dateValid"
                                                                placeholder="Enter event end date"/>
                                                    <form:errors path="endRepeat" cssClass="error"/>
                                                </div>
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
                <input id="hidden" name="hidden" type="hidden" value="">
                <button id="create" class="btn btn-success text-center">Create</button>
                <button id="draft" class="btn btn-danger text-center">Draft</button>
            </form:form>
        </div>
    </div>
</div>
<script>
    $(function () {
        $('#crontabs input, #crontabs select').change(cron);
        cron();
        isCrontabsHidden = false;
        changePeriodicity();
        deletePeriodicity();
    });

    var hid = document.getElementById('hidden');
    var create = document.getElementById('create');
    var draft = document.getElementById('draft');
    create.onclick = function () {
        hid.value = false;
    };
    draft.onclick = function () {
        hid.value = true;
    };
</script>
</body>
<%--<script type="text/javascript" src="${contextPath}/resources/js/nicEdit.js"></script>--%>
<%--<script src='${contextPath}/resources/js/textEditorInitAllArea.js'></script>--%>
<script src='${contextPath}/resources/js/pamCode.js'></script>
<script src='${contextPath}/resources/bootstrap3/js/bootstrap.min.js'></script>
<script type="text/javascript"
        src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAw5DcnwHgQpslV50vf6yTeqBE7jgBTYpo&callback=initMap&language=en&libraries=places&region=en"></script>

<script src='${contextPath}/resources/js/imageUpload.js'></script>
<script src='${contextPath}/resources/js/jquery.datetimepicker.full.min.js'></script>
<script src='${contextPath}/resources/js/datetime.js'></script>
</html>