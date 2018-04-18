<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Artem
  Date: 12.04.2018
  Time: 23:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="${contextPath}/resources/css/reset.css">
    <link rel="stylesheet" href="${contextPath}/resources/css/font.css">
    <link rel="stylesheet" href="${contextPath}/resources/css/style.css">
    <title>Cracker-Time - Landing Page</title>
</head>
<body>

<header>
    <h1>See what you can achieve with Cracker-Time</h1>
    <h3>Organize time and task manager, organize events and share wish list. </h3>

    <div class="subscribe">
        <form>
            <a href="<c:url value="/login"/>">
                <input type="button" value="Sign In">
            </a>
            <a href="<c:url value="/user/registration"/>">
            <input type="button" value="Sign Up" >
            </a>
        </form>
    </div>

    <p>* This landing page and registration form are first draft. In the next few weeks our service will be more
        fascinating
        and useful.
        <br> Soon you will see &#9786;</p>
</header>
<!-- тег section контейнер для всего -->
<!-- сам по себе полезный контент в теге article -->
<section>
    <article>
        <h1>Explore more about Cracker-Time</h1>
        <img class="imgLeft" src="${contextPath}/resources/img/monitor.jpg" alt="monitor">
        <h3>Everything is a task&#9998;</h3>
        <p>We take the view that everything is a task – organize event, a phone call you need to return, a meeting with
            a fiend
            or your family, delivering a change to an architectural drawing, fixing a tap in one of your client’s
            units.They
            are all tasks and you need to either do them yourself or delegate them to someone else, and you need to make
            sure
            they are done on time.</p>
        <p>So Cracker-Time allows you to capture all your tasks quickly, categorise them by client or in-house and then
            keep track
            of their progress and record time spent on them.
        </p>
    </article>
</section>
</body>
</html>
