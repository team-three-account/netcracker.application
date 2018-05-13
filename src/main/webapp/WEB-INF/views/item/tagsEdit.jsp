<%--
  Created by IntelliJ IDEA.
  User: Alina
  Date: 13.05.2018
  Time: 07:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="/account/tagsEdit-${itemId}" method="post">
<input name="tags" type="text">
    <input type="submit" value="Submit">
</form>
</body>
</html>
