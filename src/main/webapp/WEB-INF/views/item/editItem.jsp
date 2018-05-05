<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Update item</title>
    <link href="${contextPath}/resources/bootstrap3/css/bootstrap.min.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/style.css" rel="stylesheet">

    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<div class="row">
    <jsp:include page="${contextPath}/WEB-INF/views/account/navbar/navbar.jsp"/>
    <div class="col-md-3"
    <jsp:include page="${contextPath}/WEB-INF/views/account/menu/menu.jsp"/>
</div>
<div  align="left" class="col-md-9 content">
    <div class="card card-register">
        <div class="card-header">Edit Item</div>
        <div class="card-body">
            <form:form method="POST" modelAttribute="updateItem" class="forms_form" enctype="multipart/form-data">
            <div class="form-group">
                <label>Item Name: </label>
                <form:input path="name" id="name" style="width: 30%" type="text" class="form-control decodingHtml"
                            placeholder="Enter item name"/>
                <form:errors path="name" cssClass="error"/>
            </div>

            <div class="form-group">
                <label>Person: </label>
                <form:input path="personId" id="name" style="width: 30%" type="text" class="form-control decodingHtml"
                            placeholder="Enter person"/>
                <form:errors path="personId" cssClass="error"/>
            </div>

            <div class="form-group">
                <label>Booker: </label>
                <form:input path="booker" id="name" style="width: 30%" type="text" class="form-control decodingHtml"
                            placeholder="Enter booker"/>
                <form:errors path="booker" cssClass="error"/>
            </div>

                <div class="form-group">
                    <label>Description: </label>
                    <form:input path="description" id="name" style="width: 30%" type="text"  class="form-control decodingHtml"
                                placeholder="Enter description"/>
                    <form:errors path="description" cssClass="error"/>
                </div>

                <div class="form-group">
                    <label>Link: </label>
                    <form:input path="link" id="name" style="width: 30%" type="text"  class="form-control decodingHtml"
                                placeholder="Enter link"/>
                    <form:errors path="link" cssClass="error"/>
                </div>

                <div class="form-group">
                    <label>Date: </label>
                    <form:input path="dueDate" id="name" style="width: 30%" type="date"  class="form-control decodingHtml"
                                placeholder="Enter dueDate"/>
                    <form:errors path="dueDate" cssClass="error"/>
                </div>

                <div class="form-group">
                    <label>Priority: </label>
                    <form:input path="priority" id="name" style="width: 30%" type="text"  class="form-control decodingHtml"
                                placeholder="Enter priority"/>
                    <form:errors path="priority" cssClass="error"/>
                </div>

                <div class="form-group">
                    <label>Root: </label>
                    <form:input path="root" id="name" style="width: 30%" type="text"  class="form-control decodingHtml"
                                placeholder="Enter root"/>
                    <form:errors path="root" cssClass="error"/>

                   <input type="submit" value="Edit" class="btn btn-success text-center"/>
                </div>
        </div>
    </div>
                <%--<body>--%>
                <%--<form name="item" action="/account/updateItem" method="post">--%>
                <%--<h2><b>itemID - ${item.itemId}</b></h2>--%>
                <%--<p>personId</p>--%>
                <%--<input title="personId" type="number" name="personId" value="${item.personId}">--%>
                <%--<p>booker</p>--%>
                <%--<input title="booker" type="number" name="booker" value="${item.booker}">--%>
                <%--<p>item</p>--%>
                <%--<input title="name" type="text" name="name" value="${item.name}">--%>
                <%--<p>description</p>--%>
                <%--<input title="description" type="text" name="description" value="${item.description}">--%>
                <%--<p>link</p>--%>
                <%--<input title="link" type="text" name="link" value="${item.link}">--%>
                <%--<p>dueDate</p>--%>
                <%--<input title="dueDate" type="date" name="dueDate" value="${item.dueDate}">--%>
                <%--<p>priority</p>--%>
                <%--<input title="priority" type="number" name="priority" value="${item.priority}">--%>
                <%--<p>root</p>--%>
                <%--<input title="root" type="number" name="root" value="${item.root}">--%>
                <%--<input type="submit" value="update item">--%>
                <%--</form>--%>
                <%--</body>--%>
            </form:form>
</div>
</html>
