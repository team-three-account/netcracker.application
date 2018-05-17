<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>My wish list</title>
    <link href="${contextPath}/resources/bootstrap3/css/bootstrap.min.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/style.css" rel="stylesheet">
    <%--<link href="${contextPath}/resources/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">--%>

</head>
<body>

<div class="row">
    <jsp:include page="${contextPath}/WEB-INF/views/account/navbar/navbar.jsp"/>
    <div class="col-md-3 col-md-2 col-xl-2 col-lg-3"
    <jsp:include page="${contextPath}/WEB-INF/views/account/menu/menu.jsp"/>
</div>
<div class="col-lg-9 col-md-10 col-xs-3 content">
    <h3>Search for items</h3>
    <form method="POST"
          class="forms_form" action="/account/items/search">
        <div class="form-group">
            <input name="search" class="form-control" style="width: 33%" id="search"
                   placeholder="Enter query"/>
            <input type="submit" value="Search" class="btn btn-dark" href="/"
                   style="margin-top: 15px; margin-bottom: 15px">
        </div>
    </form>
    <c:choose>
        <c:when test="${auth_user.id.equals(ownerId)}">
            <a class="btn btn-primary" data-toggle="collapse" href="/account/addItem" role="button">Add new item</a>
        </c:when>
    </c:choose>

    <div class="row form-inline">
        <div class="col-lg-8 col-md-6 col-xl-10">
            <h3 class="caption"> Your wishList</h3>
            <c:forEach var="item" items="${wishList}">

                <div class="col-sm-6 col-md-6 col-lg-4 col-xl-4" style="display: inline">
                    <div class="thumbnail">
                        <img class="img-circle" style="width: 200px;height: 200px" src="${item.image}" alt="">
                        <div class="caption">
                            <h3>${item.name}</h3>
                            <ul class="list-group">
                                <li class="list-group-item">${item.description}</li>
                                <li class="list-group-item">Actual to : ${item.dueDate}</li>
                                <li class="list-group-item">Priority: <c:choose>
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
                                    <div style="width: 25px; height: 25px;background: ${color}; border-radius: 15px; display: inline-block; position: absolute;"></div>
                                </li>
                                <li class="list-group-item">Tags : </li>
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
                                    <c:when test="${item.booker.equals(0)}">
                                        <td>
                                            <a href="/account/user-${ownerId}/item-${item.itemId}/book">
                                                <input type="submit" class="btn btn-success text-center"
                                                       value="Book"></a>
                                        </td>
                                    </c:when>
                                    <c:when test="${item.booker.equals(auth_user.id)}">
                                        <td><b>Booked by you. </b>
                                            <a href="/account/user-${ownerId}/item-${item.itemId}/cancel-booking">
                                                <<input type="submit" class="btn btn-success text-center" value="Cancel booking"></a>

                                        </td>
                                    </c:when>
                                </c:choose>
                            </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
        <div class="col-lg-3 col-md-6 ">
            <div>
                <h3>Top 5: Popular items</h3>
                <c:forEach var="popularItem" items="${popularItems}">
                    <ul class="list-group">
                        <li class="list-group-item">${popularItem.name}</li>
                    </ul>
                </c:forEach>
            </div>
            <div>
                <h3>Top 5: Popular tags</h3>
                <c:forEach var="popularItem" items="${popularItems}">
                    <ul class="list-group">
                        <li class="list-group-item">IN WORKS!!!!</li>
                    </ul>
                </c:forEach>
            </div>
        </div>
    </div>
</div>

</body>
</html>


