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


<div class="col-md-10 content">
    <div class="card card-register">
        <div class="card-header">Edit Event</div>
        <div class="card-body">
            <form:form id="valid_maps" method="POST" modelAttribute="editEvent" class="forms_form" enctype="multipart/form-data">
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
                        <form:errors path="name" cssClass="error"/>
                    </div>
                    <div class="form-group">
                        <label>Event Name: </label>
                        <form:input path="name" id="name" type="text" class="form-control"
                                    placeholder="Enter event name"/>
                        <form:errors path="name" cssClass="error"/>
                    </div>
                    <div class="form-group">
                        <label>Description: </label>
                        <form:textarea path="description" id="description" type="text" class="form-control"
                                       placeholder="Enter event description"/>
                        <form:errors path="description" cssClass="error"/>
                    </div>
                    <div class="form-group">
                        <label>Start Date: </label>
                        <form:input path="dateStart" id="dateStart" type="text" class="form-control dateValid"
                                    placeholder="Enter event start date"/>
                        <form:errors path="dateStart" cssClass="error"/>
                    </div>
                    <div class="form-group">
                        <label>End Date: </label>
                        <form:input path="dateEnd" id="dateEnd" type="text" class="form-control dateValid"
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
                    <div class="form-group">
                        <label>Periodicity:</label>
                        <input id="periodicity" class="form-control" type="text" readonly></input>
                        <form:input path="periodicity" type="hidden" id="cron"
                                    value="${editEvent.periodicity}"></form:input>
                            <%--<input type="checkbox" id="isPeriodical" name="isPeriodical" onclick="changePeriodicity()">Show--%>
                            <%--periodicity options</br>--%>
                        <button type="button" id="isPeriodical" name="isPeriodical" onclick="changePeriodicity()">
                            Show periodicity options
                        </button>
                        <button type="button" onclick="deletePeriodicity()">Delete periodicity</button>
                        <div id="crontabs" style="display: none;">
                            <div>
                                <div class="cron-option" style="padding-bottom:10px;">
                                    <label for="time" class="nofloat">Time</label>
                                    <input type="time" id="time" name="time" value="00:00">
                                </div>
                                <div class="cron-option" style="padding-bottom:10px">
                                    <input type="radio" id="cronEveryDay" name="cronDay" checked="checked">
                                    <label for="cronEveryDay" class="nofloat">Every day</label>
                                </div>
                                <div class="cron-option" style="padding-bottom:10px">
                                    <input type="radio" id="cronDowIncrement" name="cronDay">
                                    <label for="cronDowIncrement" class="nofloat">Every
                                        <select id="cronDowIncrementIncrement" style="width:50px;">
                                            <option value="1">1</option>
                                            <option value="2">2</option>
                                            <option value="3">3</option>
                                            <option value="4">4</option>
                                            <option value="5">5</option>
                                            <option value="6">6</option>
                                            <option value="7">7</option>
                                        </select> day(s) starting on
                                        <select id="cronDowIncrementStart" style="width:125px;">
                                            <option value="1">Sunday</option>
                                            <option value="2">Monday</option>
                                            <option value="3">Tuesday</option>
                                            <option value="4">Wednesday</option>
                                            <option value="5">Thursday</option>
                                            <option value="6">Friday</option>
                                            <option value="7">Saturday</option>
                                        </select>
                                    </label>
                                </div>
                                <div class="cron-option" style="padding-bottom:10px">
                                    <input type="radio" id="cronDomIncrement" name="cronDay">
                                    <label for="cronDomIncrement" class="nofloat">Every
                                        <select id="cronDomIncrementIncrement" style="width:50px;">
                                            <option value="1">1</option>
                                            <option value="2">2</option>
                                            <option value="3">3</option>
                                            <option value="4">4</option>
                                            <option value="5">5</option>
                                            <option value="6">6</option>
                                            <option value="7">7</option>
                                            <option value="8">8</option>
                                            <option value="9">9</option>
                                            <option value="10">10</option>
                                            <option value="11">11</option>
                                            <option value="12">12</option>
                                            <option value="13">13</option>
                                            <option value="14">14</option>
                                            <option value="15">15</option>
                                            <option value="16">16</option>
                                            <option value="17">17</option>
                                            <option value="18">18</option>
                                            <option value="19">19</option>
                                            <option value="20">20</option>
                                            <option value="21">21</option>
                                            <option value="22">22</option>
                                            <option value="23">23</option>
                                            <option value="24">24</option>
                                            <option value="25">25</option>
                                            <option value="26">26</option>
                                            <option value="27">27</option>
                                            <option value="28">28</option>
                                            <option value="29">29</option>
                                            <option value="30">30</option>
                                            <option value="31">31</option>
                                        </select> day(s) starting on the
                                        <select id="cronDomIncrementStart" style="width:50px;">
                                            <option value="1">1st</option>
                                            <option value="2">2nd</option>
                                            <option value="3">3rd</option>
                                            <option value="4">4th</option>
                                            <option value="5">5th</option>
                                            <option value="6">6th</option>
                                            <option value="7">7th</option>
                                            <option value="8">8th</option>
                                            <option value="9">9th</option>
                                            <option value="10">10th</option>
                                            <option value="11">11th</option>
                                            <option value="12">12th</option>
                                            <option value="13">13th</option>
                                            <option value="14">14th</option>
                                            <option value="15">15th</option>
                                            <option value="16">16th</option>
                                            <option value="17">17th</option>
                                            <option value="18">18th</option>
                                            <option value="19">19th</option>
                                            <option value="20">20th</option>
                                            <option value="21">21st</option>
                                            <option value="22">22nd</option>
                                            <option value="23">23rd</option>
                                            <option value="24">24th</option>
                                            <option value="25">25th</option>
                                            <option value="26">26th</option>
                                            <option value="27">27th</option>
                                            <option value="28">28th</option>
                                            <option value="29">29th</option>
                                            <option value="30">30th</option>
                                            <option value="31">31st</option>
                                        </select>
                                        of the month
                                    </label>
                                </div>
                                <div class="cron-option" style="padding-bottom:10px">
                                    <input type="radio" id="cronDowSpecific" name="cronDay">
                                    <label for="cronDowSpecific" class="nofloat">Specific day of week (choose one or
                                        many)</label>
                                    <div style="margin-left:50px;">
                                        <label for="cronDowSun" class="nofloat">Sunday</label>
                                        <input type="checkbox" id="cronDowSun" name="cronDowSpecificSpecific"
                                               value="SUN"/>
                                        <label for="cronDowMon" class="nofloat">Monday</label>
                                        <input type="checkbox" id="cronDowMon" name="cronDowSpecificSpecific"
                                               value="MON"/>
                                        <label for="cronDowTue" class="nofloat">Tuesday</label>
                                        <input type="checkbox" id="cronDowTue" name="cronDowSpecificSpecific"
                                               value="TUE"/>
                                        <label for="cronDowWed" class="nofloat">Wednesday</label>
                                        <input type="checkbox" id="cronDowWed" name="cronDowSpecificSpecific"
                                               value="WED"/>
                                        <label for="cronDowThu" class="nofloat">Thursday</label>
                                        <input type="checkbox" id="cronDowThu" name="cronDowSpecificSpecific"
                                               value="THU"/>
                                        <label for="cronDowFri" class="nofloat">Friday</label>
                                        <input type="checkbox" id="cronDowFri" name="cronDowSpecificSpecific"
                                               value="FRI"/>
                                        <label for="cronDowSat" class="nofloat">Saturday</label>
                                        <input type="checkbox" id="cronDowSat" name="cronDowSpecificSpecific"
                                               value="SAT"/>
                                    </div>
                                </div>
                                <div class="cron-option" style="padding-bottom:10px">
                                    <input type="radio" id="cronDomSpecific" name="cronDay">
                                    <label for="cronDomSpecific" class="nofloat">Specific day of month (choose one
                                        or many)</label>
                                    <div style="margin-left:50px;">
                                        <div>
                                            <label for="cronDom1" class="nofloat">01</label>
                                            <input type="checkbox" id="cronDom1" name="cronDomSpecificSpecific"
                                                   value="1"/>
                                            <label for="cronDom2" class="nofloat">02</label>
                                            <input type="checkbox" id="cronDom2" name="cronDomSpecificSpecific"
                                                   value="2"/>
                                            <label for="cronDom3" class="nofloat">03</label>
                                            <input type="checkbox" id="cronDom3" name="cronDomSpecificSpecific"
                                                   value="3"/>
                                            <label for="cronDom4" class="nofloat">04</label>
                                            <input type="checkbox" id="cronDom4" name="cronDomSpecificSpecific"
                                                   value="4"/>
                                            <label for="cronDom5" class="nofloat">05</label>
                                            <input type="checkbox" id="cronDom5" name="cronDomSpecificSpecific"
                                                   value="5"/>
                                            <label for="cronDom6" class="nofloat">06</label>
                                            <input type="checkbox" id="cronDom6" name="cronDomSpecificSpecific"
                                                   value="6"/>
                                            <label for="cronDom7" class="nofloat">07</label>
                                            <input type="checkbox" id="cronDom7" name="cronDomSpecificSpecific"
                                                   value="7"/>
                                            <label for="cronDom8" class="nofloat">08</label>
                                            <input type="checkbox" id="cronDom8" name="cronDomSpecificSpecific"
                                                   value="8"/>
                                            <label for="cronDom9" class="nofloat">09</label>
                                            <input type="checkbox" id="cronDom9" name="cronDomSpecificSpecific"
                                                   value="9"/>
                                            <label for="cronDom10" class="nofloat">10</label>
                                            <input type="checkbox" id="cronDom10" name="cronDomSpecificSpecific"
                                                   value="10"/>
                                        </div>
                                        <div>
                                            <label for="cronDom11" class="nofloat">11</label>
                                            <input type="checkbox" id="cronDom11" name="cronDomSpecificSpecific"
                                                   value="11"/>
                                            <label for="cronDom12" class="nofloat">12</label>
                                            <input type="checkbox" id="cronDom12" name="cronDomSpecificSpecific"
                                                   value="12"/>
                                            <label for="cronDom13" class="nofloat">13</label>
                                            <input type="checkbox" id="cronDom13" name="cronDomSpecificSpecific"
                                                   value="13"/>
                                            <label for="cronDom14" class="nofloat">14</label>
                                            <input type="checkbox" id="cronDom14" name="cronDomSpecificSpecific"
                                                   value="14"/>
                                            <label for="cronDom15" class="nofloat">15</label>
                                            <input type="checkbox" id="cronDom15" name="cronDomSpecificSpecific"
                                                   value="15"/>
                                            <label for="cronDom16" class="nofloat">16</label>
                                            <input type="checkbox" id="cronDom16" name="cronDomSpecificSpecific"
                                                   value="16"/>
                                            <label for="cronDom17" class="nofloat">17</label>
                                            <input type="checkbox" id="cronDom17" name="cronDomSpecificSpecific"
                                                   value="17"/>
                                            <label for="cronDom18" class="nofloat">18</label>
                                            <input type="checkbox" id="cronDom18" name="cronDomSpecificSpecific"
                                                   value="18"/>
                                            <label for="cronDom19" class="nofloat">19</label>
                                            <input type="checkbox" id="cronDom19" name="cronDomSpecificSpecific"
                                                   value="19"/>
                                            <label for="cronDom20" class="nofloat">20</label>
                                            <input type="checkbox" id="cronDom20" name="cronDomSpecificSpecific"
                                                   value="20"/>
                                        </div>
                                        <div>
                                            <label for="cronDom21" class="nofloat">21</label>
                                            <input type="checkbox" id="cronDom21" name="cronDomSpecificSpecific"
                                                   value="21"/>
                                            <label for="cronDom22" class="nofloat">22</label>
                                            <input type="checkbox" id="cronDom22" name="cronDomSpecificSpecific"
                                                   value="22"/>
                                            <label for="cronDom23" class="nofloat">23</label>
                                            <input type="checkbox" id="cronDom23" name="cronDomSpecificSpecific"
                                                   value="23"/>
                                            <label for="cronDom24" class="nofloat">24</label>
                                            <input type="checkbox" id="cronDom24" name="cronDomSpecificSpecific"
                                                   value="24"/>
                                            <label for="cronDom25" class="nofloat">25</label>
                                            <input type="checkbox" id="cronDom25" name="cronDomSpecificSpecific"
                                                   value="25"/>
                                            <label for="cronDom26" class="nofloat">26</label>
                                            <input type="checkbox" id="cronDom26" name="cronDomSpecificSpecific"
                                                   value="26"/>
                                            <label for="cronDom27" class="nofloat">27</label>
                                            <input type="checkbox" id="cronDom27" name="cronDomSpecificSpecific"
                                                   value="27"/>
                                            <label for="cronDom28" class="nofloat">28</label>
                                            <input type="checkbox" id="cronDom28" name="cronDomSpecificSpecific"
                                                   value="28"/>
                                            <label for="cronDom29" class="nofloat">29</label>
                                            <input type="checkbox" id="cronDom29" name="cronDomSpecificSpecific"
                                                   value="29"/>
                                            <label for="cronDom30" class="nofloat">30</label>
                                            <input type="checkbox" id="cronDom30" name="cronDomSpecificSpecific"
                                                   value="30"/>
                                            <label for="cronDom31" class="nofloat">31</label>
                                            <input type="checkbox" id="cronDom31" name="cronDomSpecificSpecific"
                                                   value="31"/>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div>
                                <div class="cron-option" style="padding-bottom:10px">
                                    <input type="radio" id="cronEveryMonth" name="cronMonth" checked="checked">
                                    <label for="cronEveryMonth" class="nofloat">Every month</label>
                                </div>
                                <div class="cron-option" style="padding-bottom:10px">
                                    <input type="radio" id="cronMonthIncrement" name="cronMonth">
                                    <label for="cronMonthIncrement" class="nofloat">Every
                                        <select id="cronMonthIncrementIncrement" style="width:50px;">
                                            <option value="1">1</option>
                                            <option value="2">2</option>
                                            <option value="3">3</option>
                                            <option value="4">4</option>
                                            <option value="5">5</option>
                                            <option value="6">6</option>
                                            <option value="7">7</option>
                                            <option value="8">8</option>
                                            <option value="9">9</option>
                                            <option value="10">10</option>
                                            <option value="11">11</option>
                                            <option value="12">12</option>
                                        </select> month(s) starting in
                                        <select id="cronMonthIncrementStart" style="width:125px;">
                                            <option value="1">January</option>
                                            <option value="2">February</option>
                                            <option value="3">March</option>
                                            <option value="4">April</option>
                                            <option value="5">May</option>
                                            <option value="6">June</option>
                                            <option value="7">July</option>
                                            <option value="8">August</option>
                                            <option value="9">September</option>
                                            <option value="10">October</option>
                                            <option value="11">November</option>
                                            <option value="12">December</option>
                                        </select>
                                    </label>
                                </div>
                                <div class="cron-option" style="padding-bottom:10px">
                                    <input type="radio" id="cronMonthSpecific" name="cronMonth">
                                    <label for="cronMonthSpecific" class="nofloat">Specific month (choose one or
                                        many)</label>
                                    <div style="margin-left:50px;">
                                        <div>
                                            <div>
                                                <label for="cronMonth1"
                                                       class="nofloat">January&nbsp;&nbsp;&nbsp;&nbsp;</label>
                                                <input type="checkbox" id="cronMonth1"
                                                       name="cronMonthSpecificSpecific" value="JAN"/>

                                                <label for="cronMonth2" class="nofloat">February&nbsp;&nbsp;</label>
                                                <input type="checkbox" id="cronMonth2"
                                                       name="cronMonthSpecificSpecific" value="FEB"/>

                                                <label for="cronMonth3" class="nofloat">March</label>
                                                <input type="checkbox" id="cronMonth3"
                                                       name="cronMonthSpecificSpecific" value="MAR"/>

                                                <label for="cronMonth4" class="nofloat">April</label>
                                                <input type="checkbox" id="cronMonth4"
                                                       name="cronMonthSpecificSpecific" value="APR"/>

                                                <label for="cronMonth5" class="nofloat">May</label>
                                                <input type="checkbox" id="cronMonth5"
                                                       name="cronMonthSpecificSpecific" value="MAY"/>

                                                <label for="cronMonth6" class="nofloat">June</label>
                                                <input type="checkbox" id="cronMonth6"
                                                       name="cronMonthSpecificSpecific" value="JUN"/>

                                                <label for="cronMonth7" class="nofloat">July</label>
                                                <input type="checkbox" id="cronMonth7"
                                                       name="cronMonthSpecificSpecific" value="JUL"/>

                                                <label for="cronMonth8" class="nofloat">August</label>
                                                <input type="checkbox" id="cronMonth8"
                                                       name="cronMonthSpecificSpecific" value="AUG"/>

                                                <label for="cronMonth9" class="nofloat">September</label>
                                                <input type="checkbox" id="cronMonth9"
                                                       name="cronMonthSpecificSpecific" value="SEP"/>

                                                <label for="cronMonth10" class="nofloat">October</label>
                                                <input type="checkbox" id="cronMonth10"
                                                       name="cronMonthSpecificSpecific" value="OCT"/>
                                            </div>
                                            <div>
                                                <label for="cronMonth11" class="nofloat">November</label>
                                                <input type="checkbox" id="cronMonth11"
                                                       name="cronMonthSpecificSpecific" value="NOV"/>

                                                <label for="cronMonth12" class="nofloat">December</label>
                                                <input type="checkbox" id="cronMonth12"
                                                       name="cronMonthSpecificSpecific" value="DEC"/>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-md-6">
                    <form:input path="width" type="hidden" id="latitude"></form:input>
                    <form:input path="longitude" type="hidden" id="longitude"></form:input>
                    <div class="form-group">
                        <label>Event place</label>
                        <form:input path="eventPlaceName" id="eventPlaceName" type="text" class="form-control"/>
                        <div id="map"></div>
                        <script src='${contextPath}/resources/js/pamCode.js' async defer></script>
                        <script type="text/javascript"
                                src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAw5DcnwHgQpslV50vf6yTeqBE7jgBTYpo&callback=initMap&language=en&libraries=places"></script>
                        <script>
                            setMarkerFromInput();
                        </script>
                    </div>
                </div>
                <input type="submit" value="Update" class="btn btn-primary"/>
            </form:form>
        </div>
    </div>
</div>
<script>
    $(function () {
        cron2text();
        $('#crontabs input, #crontabs select').change(cron);
    });
</script>
</body>
<script src='${contextPath}/resources/js/imageUpload.js'></script>
<%--<script type="text/javascript" src="${contextPath}/resources/js/nicEdit.js"></script>--%>
<%--<script src='${contextPath}/resources/js/textEditorInitAllArea.js'></script>--%>
<script src="https://cloud.tinymce.com/stable/tinymce.min.js"></script>
<script>tinymce.init({ selector:'textarea' });</script>
<script src='${contextPath}/resources/js/jquery.datetimepicker.full.min.js'></script>
<script src='${contextPath}/resources/js/datetime.js'></script>
</html>
