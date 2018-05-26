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
    <link href='../resources/calendar/css/fullcalendar.min.css' rel='stylesheet'/>
    <link href='../resources/calendar/css/fullcalendar.print.min.css' rel='stylesheet' media='print'/>
    <script src='../resources/calendar/js/moment.min.js'></script>
    <script src='../resources/calendar/js/jquery.min.js'></script>
    <script src='../resources/calendar/js/fullcalendar.min.js'></script>
    <script src='../resourses/calendar/js/gcal.js'></script>

    <script>

        $(document).ready(function () {

            $('#calendar').fullCalendar({
                themeSystem: 'bootstrap3',
                header: {
                    left: 'prev,next today',
                    center: 'title',
                    right: 'month,agendaWeek,agendaDay,listMonth'
                },
                editable: false,
                eventLimit: true, // allow "more" link when too many events
                events: function (start, end, timezone, callback) {
                    $.ajax({
                        url: '/account/getEventsWithFilter',
                        dataType: 'json',
                        data: {
                            // our hypothetical feed requires UNIX timestamps
                            filterPriority: JSON.stringify(${filter.priorities}),
                            filterTypes: JSON.stringify(${filter.eventTypes}),
                            start: start.unix(),
                            end: end.unix()
                        },
                        success: function (doc) {
                            console.log(doc);
                            callback(doc);
                        },
                        error: function (xhr, ajaxOptions, thrownError) {
                            alert(xhr.status);
                            alert(thrownError);
                        }
                    });
                },
                <%--events: ${eventList},--%>
                eventClick: function (event) {
                    if (event.url) {
                        window.open(event.url);
                        return false;
                    }
                }

            });

        });

    </script>
    <style>

        body {
            margin: 40px 10px;
            padding: 0;
            font-family: "Lucida Grande", Helvetica, Arial, Verdana, sans-serif;
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
    <div class="col-md-2">
        <jsp:include page="${contextPath}/WEB-INF/views/account/menu/menu.jsp"/>
    </div>
    <div class="col-md-10 content">
        <div class="row">
            <jsp:include page="${contextPath}/WEB-INF/views/account/notification.jsp"></jsp:include>
            <div class="col-md-8">
                <div id='calendar'></div>
            </div>

            <div class="col-md-3">
                <form:form method="POST" modelAttribute="filter">


                    <ul class="list-group">
                        <li class="list-group-item">Choose the priority you like:</li>
                        <li class="list-group-item"><form:checkboxes cssStyle="margin: 10px" path="priorities"
                                                                     items="${priorities}"
                                                                     itemValue="priorityId"
                                                                     itemLabel="name"/>
                        </li>
                        <li class="list-group-item"><input type="submit" class="btn btn-success" name="submit"
                                                           value="Submit"></li>
                    </ul>
                </form:form>
            </div>
            <div class="col-md-3">
                <form:form method="POST" modelAttribute="filter">


                    <ul class="list-group">
                        <li class="list-group-item">Choose the type you like:</li>
                        <li class="list-group-item"><form:checkboxes cssStyle="margin: 10px" path="eventTypes"
                                                                     items="${eventTypes}"
                                                                     itemValue="typeId"
                                                                     itemLabel="name"/>
                        </li>
                        <li class="list-group-item"><input type="submit" class="btn btn-success" name="submit"
                                                           value="Submit"></li>
                    </ul>
                </form:form>
            </div>
        </div>
    </div>
</div>

<script src="${contextPath}/resources/bootstrap3/js/bootstrap.min.js"></script>
<script src="${contextPath}/resources/bootstrap3/js/bootstrap.js"></script>
</body>
</html>


