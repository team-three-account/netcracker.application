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
    <div class="col-lg-3"
    <jsp:include page="${contextPath}/WEB-INF/views/account/menu/menu.jsp"/>
</div>
<div class="col-lg-9 content">
    <c:choose>
        <c:when test="${auth_user.id.equals(ownerId)}">
            <a class="btn btn-primary" data-toggle="collapse" href="/account/addItem" role="button">Add new item</a>
        </c:when>
    </c:choose>
    <div class="card lg-12 text-center">
        <h3 align="center">Wish list</h3>
        <div class="center">
            <div class="card-group col-md-7 box-shadow itemCategory">
                <div class="card-header">
                    <%--<h4 class="my-0 font-weight-normal itemCategory">Wish List</h4>--%>
                </div>

                <div class="card-body eventCard">
                    <c:forEach var="item" items="${wishList}">
                        <div class="item-columns">
                            <ul class="list-unstyled mt-9 mb-4 itemCard ">

                                <tr>
                                    <td> (Кружочек приорити)</td>
                                    <td><a href="/account/getItem-${item.itemId}"> Name : ${item.name} </a></td>
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
                                                            <%--<a href="/account/user-${ownerId}/item-${item.itemId}/cancel-booking">--%>
                                                            <%--<input type="submit" class="btn btn-success text-center" value="Cancel booking"></a>--%>

                                                    </td>
                                                </c:when>
                                            </c:choose>
                                        </c:otherwise>
                                    </c:choose>
                                </tr>

                            </ul>
                            </a>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>


