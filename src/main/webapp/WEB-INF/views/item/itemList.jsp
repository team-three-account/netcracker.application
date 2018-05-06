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
                    <c:forEach var="item" items="${itemList}">
                        <div class="item-columns">
                            <a href="<c:url value='/account/getItem-${item.itemId}' />">
                            <ul class="list-unstyled mt-3 mb-4 itemCard ">

                                <li>Item name <b>${item.name}</b></li>
                                <%--<li>ItemId: <b><a href="/account/getItem-${item.itemId}">${item.itemId}</a></b></li>--%>
                                <li>Description: <b>${item.description}</b></li>
                                <li>Link: <b>${item.link}</b></li>
                                <li>Due_date: <b>${item.dueDate}</b></li>
                                <li>Priority: <b>${item.priority}</b></li>
                                <a href="/account/itemList/deleteItem-${item.itemId}">
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
    <%--</div>--%>

    <%--<h1>Item list</h1>--%>
    <%--<table>--%>
    <%--<tr>--%>
    <%--<th>       </th>--%>
    <%--<th>Item ID</th>--%>
    <%--<th>Person ID</th>--%>
    <%--<th>Booker ID</th>--%>
    <%--<th>Item Name</th>--%>
    <%--<th>Description</th>--%>
    <%--<th>Link</th>--%>
    <%--<th>Due_date</th>--%>
    <%--<th>Priority</th>--%>
    <%--<th>Root</th>--%>
    <%--</tr>--%>
    <%--<c:forEach var="item" items="${itemList}">--%>
    <%--<tr>--%>
    <%--<td><form method="update" action="/account/update-${item.name}">--%>
    <%--<input type="submit" value="edit"></form></td>--%>
    <%--<td>${item.itemId}</td>--%>
    <%--<td><a href="/account/personItemList-${item.personId}">${item.personId}</a></td>--%>
    <%--<td>${item.booker}</td>--%>
    <%--<td><a href="/account/getItem-${item.name}">${item.name}</a></td>--%>
    <%--<td>${item.description}</td>--%>
    <%--<td>${item.link}</td>--%>
    <%--<td><c:out value="${item.dueDate}" escapeXml="false"/></td>--%>
    <%--<td><c:out value="${item.priority}" escapeXml="false"/></td>--%>
    <%--<td><c:out value="${item.root}" escapeXml="false"/></td>--%>
    <%--&lt;%&ndash;<td><a href="/deleteItem/${item.itemId}">Delete</a></td>&ndash;%&gt;--%>
    <%--<td><form method="delete" action="/account/itemList/deleteItem-${item.itemId}">--%>
    <%--<input type="submit" value="✘"></form></td>--%>
    <%--</tr>--%>
    <%--</c:forEach>--%>
    <%--</table>--%>

    <%--<form method="link" action="/account/addItem">--%>
    <%--<input type="submit" value="add Item">--%>
    <%--</form>--%>
    <%--<form method="homeLink" action="/account">--%>
    <%--<input type="submit" value="home">--%>
    <%--</form>--%>
</body>
</html>


