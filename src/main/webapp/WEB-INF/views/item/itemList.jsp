<%--
  Created by IntelliJ IDEA.
  User: Vladimir
  Date: 24.04.2018
  Time: 12:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>All items</title>
</head>
<body>
<h1>Item list</h1>
<table>
    <tr>
        <th>       </th>
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
    <c:forEach var="item" items="${itemList}">
        <tr>
            <td><form method="update" action="/update/${item.itemName}">
                <input type="submit" value="edit"></form></td>
            <td>${item.itemId}</td>
            <td><a href="/personItemList/${item.personId}">${item.personId}</a></td>
            <td><c:out value="${item.booker}" escapeXml="false"/></td>
            <td><a href="/update/${item.itemName}">${item.itemName}</a></td>
            <td>${item.description}</td>
            <td>${item.link}</td>
            <td><c:out value="${item.dueDate}" escapeXml="false"/></td>
            <td><c:out value="${item.priority}" escapeXml="false"/></td>
            <td><c:out value="${item.root}" escapeXml="false"/></td>
            <%--<td><a href="/deleteItem/${item.itemId}">Delete</a></td>--%>
            <td><form method="delete" action="/deleteItem/${item.itemId}">
                <input type="submit" value="✘"></form></td>
        </tr>
    </c:forEach>
</table>

<form method="link" action="/addItem">
    <input type="submit" value="add Item">
</form>
    <form method="homeLink" action="/account">
        <input type="submit" value="home">
</form>
</body>
</html>


<%--<a href="delete.html?id=2" target="_blank" onclick="return confirmDelete(this);" class="delete-link need-to-confirm">--%>
    <%--<span class="maintext">✘ Delete</span>--%>
    <%--<span class="confirmation">✔ Sure?</span>--%>
<%--</a>--%>