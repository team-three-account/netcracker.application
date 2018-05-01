<%--
  Created by IntelliJ IDEA.
  User: Vladimir
  Date: 28.04.2018
  Time: 12:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Update item</title>
</head>
<body>

<form name="item" action="/account/updateItem" method="post">
    <p>itemId</p>
    <input title="itemId" type="number" name="itemId" value="${item.itemId}">
    <p>personId</p>
    <input title="personId" type="number" name="personId" value="${item.personId}">
    <p>booker</p>
    <input title="booker" type="number" name="booker" value="${item.booker}">
    <p>itemName</p>
    <input title="itemName" type="text" name="itemName" value="${item.itemName}">
    <p>description</p>
    <input title="description" type="text" name="description" value="${item.description}">
    <p>link</p>
    <input title="link" type="text" name="link" value="${item.link}">
    <p>dueDate</p>
    <input title="dueDate" type="date" name="dueDate" value="${item.dueDate}">
    <p>priority</p>
    <input title="priority" type="number" name="priority" value="${item.priority}">
    <p>root</p>
    <input title="root" type="number" name="root" value="${item.root}">
    <input type="submit" value="update item">
</form>
</body>
</html>
