<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>All items</title>
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

    <a class="btn btn-primary" data-toggle="collapse" href="/account/addItem" role="button">Add new item</a>

    <div class="card lg-12 text-center">
        <h3 align="center">Wish List</h3>
        <div class="center">
            <div class="card-group col-md-7 box-shadow itemCategory">
                <div class="card-header">
                    <%--<h4 class="my-0 font-weight-normal itemCategory">Wish List</h4>--%>
                </div>

                <div class="card-body eventCard">
                    <c:forEach var="item" items="${wishList}">
                        <div class="item-columns">
                            <a href="<c:url value='/account/getItem-${item.itemId}' />">
                                <ul class="list-unstyled mt-9 mb-4 itemCard ">

                                    <li>Item name <b>${item.name}</b></li>
                                        <%--<li>ItemId: <b><a href="/account/getItem-${item.itemId}">${item.itemId}</a></b></li>--%>
                                    <li>Description: <b>${item.description}</b></li>
                                    <li>Link: <b>${item.link}</b></li>
                                    <li>Due_date: <b>${item.dueDate}</b></li>
                                    <li>Priority: <b>${item.priority}</b></li>
                                    <a href="/account/wishList/deleteItem-${item.itemId}">
                                        <input type="submit" class="btn btn-danger text-center" value="Delete"></a>
                                    <a class="btn btn-primary" type="submit" data-toggle="collapse"
                                       href="/account/update-${item.itemId}" role="button">Edit</a>
                                </ul>
                            </a>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>
    </div>
</body>
</html>


