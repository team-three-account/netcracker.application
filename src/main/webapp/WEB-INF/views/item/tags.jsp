<%--
  Created by IntelliJ IDEA.
  User: Alina
  Date: 13.05.2018
  Time: 07:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <c:forEach var="tag" items="${tags}" >
        <tr>
            <td>#${tag.name}</td>
        </tr>
    </c:forEach>
    <td>
        <a class="btn btn-success" type="submit" data-toggle="collapse"
           href="/account/tagsEdit-${itemId}" role="button">Edit</a>
    </td>
</body>
</html>
