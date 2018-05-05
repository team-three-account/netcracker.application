<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link href="${contextPath}/resources/bootstrap3/css/bootstrap.min.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/style.css" rel="stylesheet">
</head>
<body>

<div class="row">
    <jsp:include page="${contextPath}/WEB-INF/views/account/navbar/navbar.jsp"/>
    <div class="col-md-3"
    <jsp:include page="${contextPath}/WEB-INF/views/account/menu/menu.jsp"/>
</div>
<div class="col-md-9 content">
<div class="container-fluid">
<div class="col-md-6">
<div class="panel panel-success">
<div class="panel-heading">
    <h3 class="panel-title">Item - ${item.name}</h3>
</div>
<div class="panel-body viewItem">
<ul class="list-unstyled mt-3 mb-4">
<%--<form:form method="GET" modelAttribute="item" class="forms_form" enctype="multipart/form-data">--%>
    <div class="form-group">
    <label>Item Name: </label>
    <%--<li>ID : ${item.itemId}</li>--%>
    <%--<form:select path="itemId" id="name" style="width: 30%" type="text" class="form-control decodingHtml"--%>
                 <%--placeholder="Enter item name"/>--%>
    <%--<form:errors path="name" cssClass="error"/>--%>
    <%--</div>--%>
    <label>Item Name: ${item.name} </label>
    <li>Name : ${item.name}</li>
    <label>Item Name: </label>
    <li>Booker : ${item.booker}</li>
    <label>Item Name: </label>
    <li>Description : ${item.description}</li>
    <label>Item Name: </label>
    <li>Link : ${item.link}</li>
    <label>Item Name: </label>
    <li>Date : ${item.dueDate}</li>
    <label>Item Name: </label>
    <li>Priority : ${item.priority}</li>
    <label>Item Name: </label>
    <li>Root : ${item.root}</li>
    </ul>
    </div>
    </div>
    </div>
    </div>
    </div>
    <%--</body>--%>
    <%--</div>--%>
<%--</div>--%>
    <%--</form:form>--%>

    <%--</html>--%>
    <%--<%@ page contentType="text/html;charset=UTF-8" language="java" %>--%>
    <%--<html>--%>
    <%--<head>--%>
    <%--<title>Title</title>--%>
    <%--<link href="${contextPath}/resources/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">--%>
    <%--</head>--%>
    <%--<body>--%>
    <%--<h1>Item info</h1>--%>
    <%--<table>--%>
    <%--<tr>--%>
    <%--<td>ID</td>--%>
    <%--<td>${item.itemId}</td>--%>
    <%--</tr>--%>
    <%--<tr>--%>
    <%--<td>PersonID</td>--%>
    <%--<td>${item.personId}</td>--%>
    <%--</tr>--%>
    <%--<tr>--%>
    <%--<td>Booker</td>--%>
    <%--<td>${item.booker}</td>--%>
    <%--</tr>--%>
    <%--<tr>--%>
    <%--<td>Name</td>--%>
    <%--<td>${item.name}</td>--%>
    <%--</tr>--%>
    <%--<tr>--%>
    <%--<td>Description</td>--%>
    <%--<td>${item.description}</td>--%>
    <%--</tr>--%>
    <%--<tr>--%>
    <%--<td>Link</td>--%>
    <%--<td>${item.link}</td>--%>
    <%--</tr>--%>
    <%--<tr>--%>
    <%--<td>DueDate</td>--%>
    <%--<td>${item.dueDate}</td>--%>
    <%--</tr>--%>
    <%--<tr>--%>
    <%--<td>Priority</td>--%>
    <%--<td>${item.priority}</td>--%>
    <%--</tr>--%>
    <%--<tr>--%>
    <%--<td>Root</td>--%>
    <%--<td>${item.root}</td>--%>
    <%--</tr>--%>
    <%--</table>--%>

    </body>
    </html>
