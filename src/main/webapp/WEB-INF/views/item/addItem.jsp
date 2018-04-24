<%--
  Created by IntelliJ IDEA.
  User: Vladimir
  Date: 24.04.2018
  Time: 13:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add new item</title>
</head>
<body>

<form name="item" action="/account/eventList/addItem" method="post">
    <p>itemId</p>
    <input title="itemId" type="text" name="itemId">
    <p>personId</p>
    <input title="personId" type="text" name="personId">
    <p>booker</p>
    <input title="booker" type="text" name="bookerName">
    <p>itemName</p>
    <input title="itemName" type="text" name="itemName">
    <p>description</p>
    <input title="description" type="text" name="description">
    <p>link</p>
    <input title="link" type="text" name="link">
    <p>dueDate</p>
    <input title="dueDate" type="text" name="dueDate">
    <p>priority</p>
    <input title="priority" type="text" name="priority">
    <p>root</p>
    <input title="root" type="text" name="root">
    <input type="submit" value="save user">
</form>
</body>
</html>
