<%--
  Created by IntelliJ IDEA.
  User: Ihor
  Date: 20.05.2018
  Time: 18:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Notification settings</title>
    <link href="${contextPath}/resources/css/jquery.datetimepicker.min.css" rel="stylesheet">
    <link href="${contextPath}/resources/bootstrap3/css/bootstrap.min.css" rel="stylesheet">
    <!-- Custom fonts for this template-->
    <link href="${contextPath}/resources/css/style.css" rel="stylesheet">

    <script src="${contextPath}/resources/vendor/bootstrap/js/jquery-1.11.1.min.js"></script>
    <%--for periodicity--%>
    <script src="${contextPath}/resources/vendor/bootstrap/js/later.min.js"></script>
    <script src="${contextPath}/resources/vendor/bootstrap/js/moment.min.js"></script>
    <script src="${contextPath}/resources/vendor/bootstrap/js/prettycron.js"></script>
    <script src="${contextPath}/resources/js/periodicity.js"></script>
</head>
<body>
<div class="row">
    <jsp:include page="${contextPath}/WEB-INF/views/account/navbar/navbar.jsp"/>
    <div class="col-md-2"
    <jsp:include page="${contextPath}/WEB-INF/views/account/menu/menu.jsp"/>
</div>
<div class="col-md-10" style="padding-top: 3%">
    <div class="card-header"><h2>Notification settings</h2></div>
    <div class="card-body">
        <div class="card card-register">
            <form:form method="POST" action="/account/notificationSettings/save"
                       modelAttribute="userNotificationOptions"
                       class="forms_form ">
                <div class="col-md-3">
                    <div class="form-group">
                        <label for="isNotificationsEnabled" class="nofloat">Enable notifications</label>
                        <c:if test="${userNotificationOptions.notificationPeriodicity!=null}">
                            <input type="checkbox" onchange="changeOptionsVisibility()" id="isNotificationsEnabled"
                                   name="isNotificationsEnabled" checked value="true"/>
                        </c:if>
                        <c:if test="${userNotificationOptions.notificationPeriodicity==null}">
                            <input type="checkbox" onchange="changeOptionsVisibility()" id="isNotificationsEnabled"
                                   name="isNotificationsEnabled" value="false"/>
                        </c:if>
                        <input type="submit" value="Save settings" class="btn btn-success text-center"/>
                    </div>
                </div>
                <div id="notificationOptions" class="col-md-6">
                    <form:input path="id" type="hidden"
                                id="userId" value="${userNotificationOptions.id}"></form:input>
                    <div class="form-group">
                        <label>Start Date: </label>
                        <form:input path="notificationStartDate" id="dateStart" type="text"
                                    class="form-control dateValid subSeconds"
                                    value="${userNotificationOptions.notificationStartDate}"
                                    onchange="changeDayOfMonth()"
                                    placeholder="Enter notification start date"/>
                        <form:errors path="notificationEndDate" cssClass="error"/>
                    </div>
                    <div class="form-group">
                        <label>End Date: </label>
                        <form:input path="notificationEndDate" id="notificationEndDate" type="text"
                                    class="form-control dateValid subSeconds"
                                    value="${userNotificationOptions.notificationEndDate}"
                                    placeholder="Enter notification end date"/>
                        <form:errors path="notificationEndDate" cssClass="error"/>
                    </div>
                    <div class="form-group">
                        <label>Periodicity:</label>
                        <input id="periodicity" class="form-control" type="text" readonly></input>
                        <form:input path="notificationPeriodicity" type="hidden" id="cron"
                                    value="${userNotificationOptions.notificationPeriodicity}"></form:input>
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
                    </div>
                </div>
            </form:form>
        </div>
    </div>
</div>
<script>
    var isNotificationOptionsHidden;
    $(function () {
        $('#crontabs input, #crontabs select').change(cron);
        cron2text();
        isNotificationOptionsHidden = $("#isNotificationsEnabled").prop("checked");
        changeOptionsVisibility();
    });

    function changeOptionsVisibility() {
        var notificationOptions = $("#notificationOptions");
        var isNotificationsEnabled = $("#isNotificationsEnabled");
        if (isNotificationOptionsHidden) {
            notificationOptions.show();
            isNotificationsEnabled.val(true);
            isNotificationOptionsHidden = false;
        } else {
            notificationOptions.hide();
            isNotificationsEnabled.val(false);
            isNotificationOptionsHidden = true;
        }
    }
</script>
<script src='${contextPath}/resources/js/jquery.datetimepicker.full.min.js'></script>
<script src='${contextPath}/resources/js/datetime.js'></script>
</body>
</html>
