<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Artem
  Date: 15.04.2018
  Time: 20:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${auth_user.name} ${auth_user.surname}</title>
    <link href="${contextPath}/resources/bootstrap3/css/bootstrap.min.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/style.css" rel="stylesheet">
    <!-- Custom styles for this template-->
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
                editable: false,
                eventLimit: true, // allow "more" link when too many events
                eventSources: [
                    ${eventList}
                ],
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

</head>
<body>

<div class="row">
    <jsp:include page="${contextPath}/WEB-INF/views/account/navbar/navbar.jsp"/>
    <div class="col-md-2">
        <jsp:include page="${contextPath}/WEB-INF/views/account/menu/menu.jsp"/>
    </div>

    <div class="col-md-10 content">
        <div class="row">
            <div class="col-md-6">
                <div id='calendar'></div>
            </div>
            <div class="col-md-4">
                <h3>Top 5: Popular items</h3>
                <c:forEach var="popularItem" items="${popularItems}">
                    <ul class="list-group">
                        <li class="list-group-item">
                            <a href="/account/item-${popularItem.itemId}"><img class="img-circle text-center" style="width: 20px;height: 20px" src="<c:url value="${popularItem.image}"/>">
                                    ${popularItem.name} </a>
                        </li>
                    </ul>
                </c:forEach>
            </div>
        </div>
    </div>
</div>

<script src="${contextPath}/resources/bootstrap3/js/bootstrap.min.js"></script>
<script src="${contextPath}/resources/bootstrap3/js/bootstrap.js"></script>
</body>
</html>
