<%--
  Created by IntelliJ IDEA.
  User: Artem
  Date: 26.05.2018
  Time: 17:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<body onload="loadChats();">
<script src="${contextPath}/resources/js/notification.js"></script>
<script src="${contextPath}/resources/js/stomp.js"></script>
<script src="${contextPath}/resources/js/sockjs-0.3.4.js"></script>
<div class="col-md-3" style="position: absolute;margin-left: 73%;z-index: 99999999;">
    <input type="hidden" id="authUser" value="${auth_user.id}">
    <div id="notification">
    </div>
</div>
<audio class="music" id="music" controls preload="none" hidden>
    <source src="${contextPath}/resources/js/sound/sounds/water_droplet.mp3" type="audio/mpeg">
</audio>
</body>
