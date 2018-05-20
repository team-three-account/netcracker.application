<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>

<%--
  Created by IntelliJ IDEA.
  User: Alina
  Date: 24.04.2018
  Time: 02:29
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<html>
<head>
    <meta charset='utf-8'/>
    <title>Calendar</title>
    <link href='${contextPath}/resources/calendar/css/fullcalendar.min.css' rel='stylesheet'/>
    <link href='${contextPath}/resources/calendar/css/scheduler.min.css' rel='stylesheet'/>
    <script src='${contextPath}/resources/calendar/js/moment.min.js'></script>
    <script src='${contextPath}/resources/calendar/js/jquery.min.js'></script>
    <script src='${contextPath}/resources/calendar/js/fullcalendar.min.js'></script>
    <script src='${contextPath}/resources/calendar/js/scheduler.min.js'></script>

    <script>

        $(document).ready(function () {

            $('#calendar').fullCalendar({
                defaultView: 'agendaWeek',
                themeSystem: 'bootstrap3',
                schedulerLicenseKey: 'CC-Attribution-NonCommercial-NoDerivatives',
                columnHeaderFormat : 'dddd D',
                displayEventEnd : true,
                slotLabelFormat: 'HH:mm',
                timeFormat: 'HH:mm',
                eventSources: [
                    ${eventList}
                ],
            });
        });

    </script>
    <link href="${contextPath}/resources/bootstrap3/css/bootstrap.min.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/style.css" rel="stylesheet">
</head>
<body>

<div class="row">
    <jsp:include page="${contextPath}/WEB-INF/views/account/navbar/navbar.jsp"/>
    <div class="col-md-2">
        <jsp:include page="${contextPath}/WEB-INF/views/account/menu/menu.jsp"/>
    </div>

    <div class="col-md-9 content">
        <div id='calendar'></div>
    </div>
</div>

<script src="${contextPath}/resources/bootstrap3/js/bootstrap.min.js"></script>
<script src="${contextPath}/resources/bootstrap3/js/bootstrap.js"></script>
</body>
</html>


