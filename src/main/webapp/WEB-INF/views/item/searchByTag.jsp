<%--
  Created by IntelliJ IDEA.
  User: Alina
  Date: 23.05.2018
  Time: 03:04
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Search</title>
    <link href="${contextPath}/resources/bootstrap3/css/bootstrap.min.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/style.css" rel="stylesheet">
    <%--<link href="${contextPath}/resources/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">--%>

</head>
<body>

<div class="row">
    <jsp:include page="${contextPath}/WEB-INF/views/account/navbar/navbar.jsp"/>
    <div class="col-md-3 col-md-2 col-xl-2"
    <jsp:include page="${contextPath}/WEB-INF/views/account/menu/menu.jsp"/>
</div>
<div class="col-md-10 col-xs-3 content">
    <h3>Search for items</h3>
    <form method="POST"
          class="forms_form" action="/account/search/items">
        <div class="form-group">
            <input name="search" class="form-control" style="width: 33%" id="search"
                   placeholder="Enter query"/>
            <input type="submit" value="Search" class="btn btn-dark" href="/"
                   style="margin-top: 15px; margin-bottom: 15px">
        </div>
    </form>
    <div class="row form-inline">
        <div class="col-lg-8 col-md-6 col-xl-10">
            <h3 class="caption"> Wish List</h3>
            <div class="parent">
                <c:forEach var="item" items="${items}">

                    <div class="col-sm-6 col-md-6 col-lg-4 col-xl-4" style="display: inline">
                        <div class="thumbnail child">
                            <img class="img-circle" style="width: 200px;height: 200px" src="${item.image}" alt="">
                            <div class="caption" style="  white-space: nowrap;overflow: hidden;text-overflow: ellipsis;">
                                <ul class="list-group">
                                    <tr>
                                        <td>
                                            <div style="width: 18px; height: 18px;background: ${color}; border-radius: 10px; display: inline-block; "></div>
                                        </td>
                                        <td><a href="/account/item-${item.itemId}"><span
                                                style="font-size: 24px;white-space: nowrap;overflow: hidden;text-overflow: ellipsis;"> ${item.name} </span></a></td>
                                    </tr>
                                    <li class="list-group-item"
                                        style="  white-space: nowrap;overflow: hidden;text-overflow: ellipsis;">${item.description}</li>
                                    <li class="list-group-item">Actual to : ${item.dueDate}</li>
                                    <li class="list-group-item"
                                        style="  white-space: nowrap;overflow: hidden;text-overflow: ellipsis;">Tags :
                                        <c:forEach var="tag" items="${item.tags}">
                                            <a href="/account/search-tag/${tag.tagId}">#${tag.name}</a>
                                        </c:forEach>
                                    </li>
                                </ul>
                                <p>
                                    <c:choose>
                                    <c:when test="${auth_user.id.equals(item.personId)}">
                                <td>
                                    <a class="btn btn-success" type="submit" data-toggle="collapse"
                                       href="/account/update-${item.itemId}" role="button">Edit</a>
                                </td>
                                <td>
                                    <a href="/account/wishList/deleteItem-${item.itemId}">
                                        <input type="submit" class="btn btn-danger text-center"
                                               value="Delete"></a>
                                </td>
                                </c:when>
                                <c:otherwise>
                                    <c:choose>
                                        <c:when test="${item.booker.equals(auth_user.id)}">
                                            <td><b>Booked by you. </b>
                                                <a href="/account/user-${ownerId}/item-${item.itemId}/cancel-booking">
                                                    <input type="submit" class="btn btn-success text-center"
                                                           value="Cancel booking"></a>

                                            </td>
                                        </c:when>
                                    </c:choose>
                                    <td>
                                        <a href="/account/copy-${item.itemId}">
                                            <input type="submit" class="btn btn-success text-center"
                                                   value="Copy to my wish list"></a>
                                    </td>
                                </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>
</div>

</body>
</html>



