<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Wish list</title>
    <link href="${contextPath}/resources/bootstrap3/css/bootstrap.min.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/style.css" rel="stylesheet">
    <script src="${contextPath}/resources/bootstrap3/js/bootstrap.min.js"></script>
    <script src="${contextPath}/resources/bootstrap3/js/bootstrap.js"></script>
    <script src="${contextPath}/resources/vendor/bootstrap/js/jquery-1.11.1.min.js"></script>
    <%--<link href="${contextPath}/resources/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">--%>
</head>
<body>
<div class="row">
    <jsp:include page="${contextPath}/WEB-INF/views/account/navbar/navbar.jsp"/>
    <div class="col-md-2 col-xl-2" style="height:100vh;">>
        <jsp:include page="${contextPath}/WEB-INF/views/account/menu/menu.jsp"/>
    </div>
    <div class="col-md-10 content">
        <c:choose>
            <c:when test="${auth_user.id.equals(ownerId)}">
                <a class="btn btn-primary" data-toggle="collapse" href="/account/addItem" role="button">Add new item</a>
            </c:when>
        </c:choose>
        <jsp:include page="${contextPath}/WEB-INF/views/account/notification.jsp"></jsp:include>
        <div class="row form-inline">
            <c:choose>
                <c:when test="${wishList.isEmpty()}">
                    <div class="col-lg-8 col-md-6 col-xl-10">
                        <h3 class="caption"> Wishlist is empty.</h3>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="col-lg-8 col-md-6 col-xl-10">
                        <h3 class="caption"> Wish List</h3>
                        <div class="parent">
                            <c:forEach var="item" items="${wishList}">
                                <div class="col-sm-6 col-md-6 col-lg-4 col-xl-3" style="display: inline">
                                    <div class="thumbnail child">
                                        <img class="img-circle" style="width: 200px;height: 200px" src="${item.image}"
                                             alt="">
                                        <div class="caption">
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
                                                    <td><a href="/account/item-${item.itemId}"><span
                                                            style="font-size: 24px;"> ${item.name} </span></a></td>
                                                </tr>
                                                <li class="list-group-item"
                                                    style="  white-space: nowrap;overflow: hidden;text-overflow: ellipsis;">${item.description}</li>
                                                <li class="list-group-item">Actual to : ${item.dueDate}</li>
                                                <li class="list-group-item"
                                                    style="  white-space: nowrap;overflow: hidden;text-overflow: ellipsis;">
                                                    Tags
                                                    :
                                                    <c:forEach var="tag" items="${item.tags}">
                                                        <a href="/account/search-tag/${tag.tagId}">#${tag.name}</a>
                                                    </c:forEach>
                                                </li>
                                                <li class="list-group-item">Likes: ${item.likes}
                                                    <c:if test="${item.isLiked == true}">
                                                        <form action="/account/eventWishList/dislike" method="POST">
                                                            <input type="hidden" name="eventId" value="${eventId}">
                                                            <input type="hidden" name="userId" value="${auth_user.id}">
                                                            <button type="submit" class="btn btn-danger text-center">
                                                                <input type="hidden" name="itemId"
                                                                       value="${item.itemId}"/>
                                                                Dislike
                                                            </button>
                                                        </form>
                                                    </c:if>
                                                    <c:if test="${item.isLiked == false}">
                                                        <form action="/account/eventWishList/like" method="POST">
                                                            <input type="hidden" name="eventId" value="${eventId}">
                                                            <input type="hidden" name="userId" value="${auth_user.id}">
                                                            <button type="submit" class="btn btn-success">
                                                                <input type="hidden" name="itemId"
                                                                       value="${item.itemId}"/>
                                                                Like
                                                            </button>
                                                        </form>
                                                    </c:if>
                                                </li>
                                                <li class="list-group-item">
                                                    <c:choose>
                                                        <c:when test="${auth_user.id.equals(item.personId)}">
                                                            <td>
                                                                <a class="btn btn-success" type="submit"
                                                                   data-toggle="collapse"
                                                                   href="/account/update-${item.itemId}" role="button">Edit</a>
                                                            </td>
                                                            <td>
                                                                <a href="/account/wishList/deleteItem-${item.itemId}">
                                                                    <input type="submit"
                                                                           class="btn btn-danger text-center"
                                                                           value="Delete"></a>
                                                            </td>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <td>
                                                                <a href="/account/copy-${item.itemId}">
                                                                    <input type="submit"
                                                                           class="btn btn-success text-center"
                                                                           value="Copy to my wish list"></a>
                                                            </td>
                                                            <br>
                                                            <c:choose>
                                                                <c:when test="${item.booker.equals(0)}">
                                                                    <td>
                                                                        <a href="/account/event-${eventId}-${item.personId}/item-${item.itemId}/book">
                                                                            <input type="submit"
                                                                                   class="btn btn-success text-center"
                                                                                   value="Book"></a>
                                                                    </td>
                                                                </c:when>
                                                                <c:when test="${item.booker.equals(auth_user.id)}">
                                                                    <td>
                                                                        <a href="/account/event-${eventId}-${ownerId}/item-${item.itemId}/cancel-booking">
                                                                            <input type="submit"
                                                                                   class="btn btn-success text-center"
                                                                                   value="Cancel booking"></a>
                                                                        <div><b>Booked by you. </b></div>
                                                                    </td>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <c:choose>
                                                                        <c:when test="${item.event.equals(eventId)}">
                                                                            <td>
                                                                                <b>Booked by <a
                                                                                        href="/account/${item.booker}">
                                                                                    participant </a>
                                                                                </b>
                                                                            <td>
                                                                        </c:when>
                                                                        <c:otherwise>
                                                                            <td>
                                                                                <b>Booked</b>
                                                                            </td>
                                                                        </c:otherwise>
                                                                    </c:choose>
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </li>

                                            </ul>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </c:otherwise>
            </c:choose>
            <div class="col-lg-3 col-md-6 ">
                <div>
                    <h3>Top 5: Popular items</h3>
                    <c:forEach var="popularItem" items="${popularItems}">
                        <ul class="list-group">
                            <a href="/account/item-${popularItem.itemId}"> ${popularItem.name} </a>
                        </ul>
                    </c:forEach>
                </div>
                <div>
                    <h3>Top 5: Popular tags</h3>
                    <c:forEach var="popularTag" items="${popularTags}">
                        <ul class="list-group">
                            <li class="list-group-item">
                                <a href="account/search-tag/${popularTag.tagId}"> #${popularTag.name}</a>
                            </li>
                        </ul>
                    </c:forEach>
                </div>
            </div>
        </div>
    </div>
</body>
</html>


