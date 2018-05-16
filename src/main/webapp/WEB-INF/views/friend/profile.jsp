<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: anyat
  Date: 15.04.2018
  Time: 20:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Profile</title>
    <link href="${contextPath}/resources/bootstrap3/css/bootstrap.min.css" rel="stylesheet">
    <!-- Custom fonts for this template-->
    <link href="${contextPath}/resources/css/style.css" rel="stylesheet">
    <!-- Custom styles for this template-->

</head>
<body>

<div class="row">
    <jsp:include page="${contextPath}/WEB-INF/views/account/navbar/navbar.jsp"/>
    <div class="col-md-2">
        <jsp:include page="${contextPath}/WEB-INF/views/account/menu/menu.jsp"/>
    </div>
    <div class="col-md-10 content ">
        <table class="table">
            <tbody>
            <tr>
                <td>Avatar:</td>
                <td><img class="img-circle" style="width: 200px;height: 200px"
                         src="<c:url value="${friend.photo}"/>"></td>
            </tr>
            <tr>
                <td>Name:</td>
                <td>${friend.name}</td>
            </tr>
            <tr>
                <td>Surname</td>
                <td>${friend.surname}</td>
            </tr>
            <tr>
                <td>Date of Birth</td>
                <td>${friend.birthdayDate}</td>
            </tr>
            <tr>
                <td>Email</td>
                <td>${friend.email}</td>
            </tr>
            <tr>
                <td>Phone Number</td>
                <td>${friend.phone}</td>
            </tr>
            <tr>
                <td><a href="/account/user-${friend.id}/wishList">Wish List</a></td>
            </tr>
            </tbody>
        </table>
    </div>
</div>
<script src="${contextPath}/resources/bootstrap3/js/bootstrap.min.js"></script>
<script src="${contextPath}/resources/bootstrap3/js/bootstrap.js"></script>
<script src="${contextPath}/resources/vendor/bootstrap/js/jquery-1.11.1.min.js"></script>
</body>
</html>
