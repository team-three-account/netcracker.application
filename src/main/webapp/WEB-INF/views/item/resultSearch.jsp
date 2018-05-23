<%--
  Created by IntelliJ IDEA.
  User: Alina
  Date: 17.05.2018
  Time: 16:52
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
    <div class="col-lg-2 col-md-2 col-xl-2"
    <jsp:include page="${contextPath}/WEB-INF/views/account/menu/menu.jsp"/>
</div>
<div class="col-lg-10 col-md-10 content">
    <div class="row form-inline">
        <h3 class="caption"> Result search in my items</h3>
        <div class="parent">
            <c:forEach var="item" items="${resultSearchMyItem}">
                <div class="col-sm-6 col-md-2 col-lg-2 col-xl-3" style="display: inline-block">

                    <div class="thumbnail child">
                        <img class="img-circle" style="width: 200px;height: 200px" src="${item.image}" alt="">
                        <div class="caption" style="  white-space: nowrap;overflow: hidden;text-overflow: ellipsis;">
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
                            <ul class="list-group">
                                <tr>
                                    <td>
                                        <div style="width: 18px; height: 18px;background: ${color}; border-radius: 10px; display: inline-block; "></div>
                                    </td>
                                    <td><a style="  white-space: nowrap;overflow: hidden;text-overflow: ellipsis;" href="/account/item-${item.itemId}"><span
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

                            <c:choose>
                                <c:when test="${auth_user.id.equals(item.personId)}">
                                    <td>
                                        <a class="btn btn-success" type="submit" data-toggle="collapse"
                                           href="/account/update-${item.itemId}" role="button">Edit</a>
                                    </td>
                                    <td>
                                        <a href="/account/wishList/deleteItem-${item.itemId}">
                                            <input type="submit" class="btn btn-danger text-center" value="Delete"></a>
                                    </td>
                                </c:when>
                                <c:otherwise>
                                    <td>
                                        <a href="/account/copy-${item.itemId}">
                                            <input type="submit" class="btn btn-success text-center"
                                                   value="Copy to my wish list"></a>
                                    </td>
                                </c:otherwise>
                            </c:choose>
                            </ul>
                        </div>

                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
    <div class="row form-inline">
            <h3 class="caption"> Result search items</h3>
        <div class="parent">
            <c:forEach var="item" items="${resultSearchItem}">

            <div class="col-sm-6 col-md-3 col-lg-2 col-xl-3" style="display: inline">
                <div class="thumbnail child">
                    <img class="img-circle" style="width: 200px;height: 200px" src="${item.image}" alt="">
                    <div class="caption" style="  white-space: nowrap;overflow: hidden;text-overflow: ellipsis;">
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
                        <ul class="list-group">
                            <tr>
                                <td>
                                    <div style="width: 18px; height: 18px;background: ${color}; border-radius: 10px; display: inline-block; "></div>
                                </td>
                                <td><a style="  white-space: nowrap;overflow: hidden;text-overflow: ellipsis;" href="/account/item-${item.itemId}"><span
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

                        <c:choose>
                            <c:when test="${auth_user.id.equals(item.personId)}">
                                <td>
                                    <a class="btn btn-success" type="submit" data-toggle="collapse"
                                       href="/account/update-${item.itemId}" role="button">Edit</a>
                                </td>
                                <td>
                                    <a href="/account/wishList/deleteItem-${item.itemId}">
                                        <input type="submit" class="btn btn-danger text-center" value="Delete"></a>
                                </td>
                            </c:when>
                            <c:otherwise>
                                <td>
                                    <a href="/account/copy-${item.itemId}">
                                        <input type="submit" class="btn btn-success text-center"
                                               value="Copy to my wish list"></a>
                                </td>
                            </c:otherwise>
                        </c:choose>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
        </c:forEach>
    </div>
</div>


<%--<div class="col-lg-3 col-md-6 ">--%>
<%--<div>--%>
<%--<h3>Top 5: Popular items</h3>--%>
<%--<c:forEach var="popularItem" items="${popularItems}">--%>
<%--<ul class="list-group">--%>
<%--<li class="list-group-item">${popularItem.name}</li>--%>
<%--</ul>--%>
<%--</c:forEach>--%>
<%--</div>--%>
<%--<div>--%>
<%--<h3>Top 5: Popular tags</h3>--%>
<%--<c:forEach var="popularItem" items="${popularItems}">--%>
<%--<ul class="list-group">--%>
<%--<li class="list-group-item">IN WORKS!!!!</li>--%>
<%--</ul>--%>
<%--</c:forEach>--%>
<%--</div>--%>
<%--</div>--%>

</body>
</html>



