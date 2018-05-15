<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Wish list</title>
    <link href="${contextPath}/resources/bootstrap3/css/bootstrap.min.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/style.css" rel="stylesheet">
    <%--<link href="${contextPath}/resources/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">--%>
</head>
<body>
<div class="row">
    <jsp:include page="${contextPath}/WEB-INF/views/account/navbar/navbar.jsp"/>
    <div class="col-lg-3"
    <jsp:include page="${contextPath}/WEB-INF/views/account/menu/menu.jsp"/>
</div>
<div class="col-lg-9 content">

    <div class="card lg-12 text-center">
        <h3 align="center">Wish list</h3>
        <div class="center">
            <div class="card-group col-md-7 box-shadow itemCategory">
                <div class="card-header">
                </div>

                <div class="card-body eventCard">
                    <c:forEach var="item" items="${wishList}">
                        <div class="item-columns">
                            <ul class="list-unstyled mt-9 mb-4 itemCard ">
                                <tr>
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
                                            <td>${item.priority}</td>
                                            <c:set var="color" value="grey"/>
                                        </c:otherwise>
                                    </c:choose>

                                    <td> <div style="width: 25px; height: 25px;background: ${color}; border-radius: 15px; display: inline-block; "></div></td>
                                    <td><a href="/account/getItem-${item.itemId}"> Name : ${item.name} </a></td>
                                    <c:choose>
                                        <c:when  test="${auth_user.id.equals(item.personId)}">
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
                                            <c:choose>
                                                <c:when  test="${item.booker.equals(0)}">
                                                    <td>
                                                        <a href="/account/event-${eventId}-${item.personId}/item-${item.itemId}/book">
                                                            <input type="submit" class="btn btn-success text-center" value="Book"></a>
                                                    </td>
                                                </c:when>
                                                <c:when  test="${item.booker.equals(auth_user.id)}">
                                                    <td> <b>Booked by you. </b>
                                                            <a href="/account/user-${ownerId}/item-${item.itemId}/cancel-booking">
                                                            <input type="submit" class="btn btn-success text-center" value="Cancel booking"></a>

                                                    </td>
                                                </c:when>
                                                <c:otherwise>
                                                    <c:choose>
                                                        <c:when  test="${item.event.equals(eventId)}">
                                                            <td>
                                                                <b>Booked by <a href="/account/${item.booker}"> participant </a> </b>
                                                            <td>
                                                        </c:when>
                                                        <c:otherwise>
                                                             <td>
                                                                <b>Booked</b>
                                                             <td>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:otherwise>
                                    </c:choose>
                                </tr>

                            </ul>
                        </div>
                    </c:forEach>
                    <h3 align="center">Popular items :</h3>

                    <div class="row">
                        <table class="table">
                            <tr>
                                <c:forEach var="popular" items="${popularItems}">
                                    <td>
                                        <a class="btn btn-primary" data-toggle="collapse" href="/account/getItem-${popular.itemId}" role="button"> ${popular.name} </a>
                                    </td>
                                </c:forEach>
                            </tr>
                        </table>
                    </div>

                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>


