<%--
  Created by IntelliJ IDEA.
  User: Vladimir
  Date: 24.04.2018
  Time: 12:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>Person items</title>
</head>
<body>
<table border="1">
    <%--<thead></thead>--%>
    <tr>
        <th>Item ID</th>
        <th>Person ID</th>
        <th>Booker ID</th>
        <th>Item Name</th>
        <th>Description</th>
        <th>Link</th>
        <th>Due_date</th>
        <th>Priority</th>
        <th>Root</th>
    </tr>
    <%--</thead>--%>
    <%--<#list items as item>--%>
    <c:forEach var="personItem" items="${personItemList}" >
        <tr>
            <td>${personItem.itemId}</td>
            <td>${personItem.personId}</td>
            <td><c:out value="${personItem.booker}" escapeXml="false"/></td>
            <td>${personItem.name}</td>
            <td>${personItem.description}</td>
            <td><c:out value="${personItem.link}" escapeXml="false"/></td>
            <td><c:out value="${personItem.dueDate}" escapeXml="false"/></td>
            <td><c:out value="${personItem.priority}" escapeXml="false"/> </td>
            <td><c:out value="${personItem.root}" escapeXml="false"/> </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>

