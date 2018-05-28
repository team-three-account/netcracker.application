<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="catalina" value="<%!Sys%>]"/>
<html>
<head>
    <title>Item</title>
    <link href="${contextPath}/resources/bootstrap3/css/bootstrap.min.css" rel="stylesheet">
    <!-- Custom fonts for this template-->
    <link href="${contextPath}/resources/css/style.css" rel="stylesheet">
    <!-- Custom styles for this template-->
    <script src="${contextPath}/resources/bootstrap3/js/bootstrap.min.js"></script>
    <script src="${contextPath}/resources/bootstrap3/js/bootstrap.js"></script>
    <script src="${contextPath}/resources/vendor/bootstrap/js/jquery-1.11.1.min.js"></script>
</head>
<body>
<div class="row">
    <jsp:include page="${contextPath}/WEB-INF/views/account/navbar/navbar.jsp"/>
    <div class="col-md-2">
        <jsp:include page="${contextPath}/WEB-INF/views/account/menu/menu.jsp"/>
    </div>
    <div class="col-md-10 content">
        <jsp:include page="${contextPath}/WEB-INF/views/account/notification.jsp"></jsp:include>
        <table class="table">
            <tbody>
                <c:choose>
                    <c:when test="${item.priority=='1'}">
                        <c:set var="color" value="red"/>
                    </c:when>
                    <c:when test="${item.priority=='2'}">
                        <c:set var="color" value="yellow"/>
                    </c:when>
                    <c:when test="${item.priority=='3'}">
                        <c:set var="color" value="green"/>
                    </c:when>
                    <c:otherwise>
                        <c:set var="color" value="grey"/>
                    </c:otherwise>
                </c:choose>
            <tr>
                <td>
                    <div style="width: 25px; height: 25px;background: ${color}; border-radius: 15px; display: inline-block; "></div>
                </td>
                <td><img class="img-circle" style="width: 200px;height: 200px" src="<c:url value="${item.image}"/>"></td>
            </tr>
            <tr>
                <td>Name:</td>
                <td>${item.name}</td>
            </tr>
            <tr>
                <td>Description:</td>
                <td>${item.description}</td>
            </tr>
            <tr>
                <td>Link:</td>
                <td>${item.link}</td>
            </tr>
            <tr>
                <td>Actual to:</td>
                <td>${item.dueDate}</td>
            </tr>
                <tr>
                    <td>Likes: ${likes}</td>
                    <td><c:if test="${isLiked == true}">
                            <form action="/account/viewItem/dislike" method="POST">
                                <button type="submit" class="btn btn-danger text-center">
                                    <input type="hidden" name="item_id" value="${item.itemId}"/>
                                    Dislike
                                </button>
                            </form>
                        </c:if>
                        <c:if test="${isLiked == false}">
                            <form action="/account/viewItem/like" method="POST">
                                <button type="submit" class="btn btn-success">
                                    <input type="hidden" name="item_id" value="${item.itemId}"/>
                                    Like
                                </button>
                            </form>
                        </c:if>
                    </td>
                </tr>
            <c:choose>
                <c:when test="${auth_user.id.equals(item.personId)}">
                    <td>
                        <a class="btn btn-success" type="submit" data-toggle="collapse"
                           href="/account/update-${item.itemId}" role="button">Edit</a>
                        <a href="/account/wishList/deleteItem-${item.itemId}">
                            <input type="submit" class="btn btn-danger text-center"
                                   value="Delete"></a>
                    </td>
                    <td>
                    </td>
                </c:when>
                <c:otherwise>
                    <td>
                        <a href="/account/copy-${item.itemId}">
                        <input type="submit" class="btn btn-success text-center" value="Copy to my wish list"></a>
                    </td>
                </c:otherwise>
            </c:choose>
            </tbody>
        </table>
    </div>
</body>
</html>

