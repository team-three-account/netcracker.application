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
    <meta charset='utf-8' />
    <link href='../resources/calendar/css/fullcalendar.min.css' rel='stylesheet' />
    <link href='../resources/calendar/css/fullcalendar.print.min.css' rel='stylesheet' media='print' />
    <script src='../resources/calendar/js/moment.min.js'></script>
    <script src='../resources/calendar/js/jquery.min.js'></script>
    <script src='../resources/calendar/js/fullcalendar.min.js'></script>
    <script src='../resourses/calendar/js/gcal.js'></script>

    <script>

        $(document).ready(function() {

            $('#calendar').fullCalendar({
                editable: false,
                eventLimit: true, // allow "more" link when too many events
                events: ${eventList}

            });

        });

    </script>
    <style>

        body {
            margin: 40px 10px;
            padding: 0;
            font-family: "Lucida Grande",Helvetica,Arial,Verdana,sans-serif;
            font-size: 14px;
        }

        #calendar {
            max-width: 700px;
            margin: 0 auto;
        }

    </style>
    <link href="${contextPath}/resources/bootstrap3/css/bootstrap.min.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/style.css" rel="stylesheet">
</head>
<body>

<div class="row">
    <jsp:include page="${contextPath}/WEB-INF/views/account/navbar/navbar.jsp"/>
    <div class="col-md-3"
    <jsp:include page="${contextPath}/WEB-INF/views/account/menu/menu.jsp"/>
</div>

<div class="col-md-9 content">
    <form:form method="POST" modelAttribute="filter">
        <table>

            <tr>
                <td>Choose the priority you like:</td>
                <td><form:checkboxes path="priorities" items="${priorities}" itemValue="priorityId" itemLabel="name" />
                </td>
            </tr>
            <tr>
                <td><input type="submit" name="submit" value="Submit"></td>
            </tr>
            <tr>
        </table>
    </form:form>


    <div id='calendar'></div>
</div>
    <script src="${contextPath}/resources/bootstrap3/js/bootstrap.min.js"></script>
    <script src="${contextPath}/resources/bootstrap3/js/bootstrap.js"></script>
</body>
</html>


