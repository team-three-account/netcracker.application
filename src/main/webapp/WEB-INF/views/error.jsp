<%--
  Created by IntelliJ IDEA.
  User: Artem
  Date: 21.04.2018
  Time: 16:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>CrackerTime Error Page</title>

    <link href="${contextPath}/resources/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <!-- Custom fonts for this template-->
    <link href="${contextPath}/resources/vendor/bootstrap/css/bootstrap.css" rel="stylesheet">
    <link href="${contextPath}/resources/vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
</head>
<body>
<div class="container">
    <div class="card card-register mx-auto mt-5 col-md-6">
        <div class="card-header">Error page</div>
        <div class="card-body">
            <h2>${text_error}</h2>
            <a href="/">Home</a>
        </div>
    </div>
</div>
</body>
</html>
