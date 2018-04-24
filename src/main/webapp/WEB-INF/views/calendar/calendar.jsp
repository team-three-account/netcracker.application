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
    <link href='resources/calendar/css/fullcalendar.min.css' rel='stylesheet' />
    <link href='resources/calendar/css/fullcalendar.print.min.css' rel='stylesheet' media='print' />
    <script src='resources/calendar/js/moment.min.js'></script>
    <script src='resources/calendar/js/jquery.min.js'></script>
    <script src='resources/calendar/js/fullcalendar.min.js'></script>
    <script>

        $(document).ready(function() {

            $('#calendar').fullCalendar({
                defaultDate: '2018-03-12',
                editable: true,
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
            max-width: 900px;
            margin: 0 auto;
        }

    </style>
</head>
<body>

<div id='calendar'></div>

</body>
</html>


