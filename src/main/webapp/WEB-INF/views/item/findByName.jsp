<%--
  Created by IntelliJ IDEA.
  User: Vladimir
  Date: 28.04.2018
  Time: 18:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>Item info</h1>
<table>
    <tr>
        <td>ID</td>
        <td>${item.itemId}</td>
    </tr>
    <tr>
        <td>PersonID</td>
        <td>${item.personId}</td>
    </tr>
    <tr>
        <td>Booker</td>
        <td>${item.booker}</td>
    </tr>
    <tr>
        <td>Name</td>
        <td>${item.itemName}</td>
    </tr>
    <tr>
        <td>Description</td>
        <td>${item.description}</td>
    </tr>
    <tr>
        <td>Link</td>
        <td>${item.link}</td>
    </tr>
    <tr>
        <td>DueDate</td>
        <td>${item.dueDate}</td>
    </tr>
    <tr>
        <td>Priority</td>
        <td>${item.priority}</td>
    </tr>
    <tr>
        <td>Root</td>
        <td>${item.root}</td>
    </tr>
</table>

</body>
</html>
