<%--
  Created by IntelliJ IDEA.
  User: Alina
  Date: 25.04.2018
  Time: 14:41
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>Settings</title>
    <link href="${contextPath}/resources/bootstrap3/css/bootstrap.min.css" rel="stylesheet">
    <!-- Custom fonts for this template-->
    <link href="${contextPath}/resources/css/style.css" rel="stylesheet">
    <!-- Custom styles for this template-->
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <script src="https://apis.google.com/js/platform.js" async defer></script>
    <meta name="google-signin-client_id"
          content="829562763201-3hn7scp4j1c9u7hvlherebi8e56pv9va.apps.googleusercontent.com">
</head>
<body>

<div class="row">
    <jsp:include page="${contextPath}/WEB-INF/views/account/navbar/navbar.jsp"/>
    <div class="col-md-3">
        <jsp:include page="${contextPath}/WEB-INF/views/account/menu/menu.jsp"/>
    </div>

    <div class="col-md-9 content">
        <form action="<c:url value="/account/update"/>" method="post" enctype="multipart/form-data">
            <table class="table">
                <tr>
                    <td>Avatar:</td>

                    <td><img class="img-circle" style="width: 200px;height: 200px"
                             src="<c:url value="/account/image/${auth_user.photo}.jpg"/>">
                        <input type="hidden" name="photo" value="${auth_user.photo}">
                        <br><span class="btn btn-default btn-file">
    Browse <input type="file" name="photoFile" accept="image/*">
</span>
                    </td>
                </tr>
                <tr>
                    <th>Name:</th>
                    <td>
                        <input type="hidden" name="id" value="${auth_user.id}" required>
                        <input name="name decodingHtml" class="form-control"
                               readonly="readonly" value="${auth_user.name}"
                               placeholder="Enter name" required>
                    </td>
                </tr>
                <tr>
                    <th>Surname:</th>
                    <td>
                        <input class="form-control"
                               name="surname decodingHtml" maxlength="500" required value="${auth_user.surname}">
                    </td>
                </tr>
                <tr>
                    <th>Email:</th>
                    <td>
                        <input class="form-control" name="email"
                               readonly="readonly" maxlength="500" required value="${auth_user.email}">
                    </td>
                </tr>
                <tr>
                    <th>Phone</th>
                    <td>
                        <input name="phone" class="form-control phone"
                               type="phone" value="${auth_user.phone}"
                               placeholder="Enter phone" required>

                    </td>
                </tr>
                <tr>
                    <th></th>
                    <td>
                        <button class="btn btn-success" type="submit">
                            Save
                        </button>
                    </td>
                </tr>
            </table>
        </form>
    </div>

</div>
</div>
</div>
<!-- Bootstrap core JavaScript-->
<script src="${contextPath}/resources/vendor/bootstrap/js/jquery-1.11.1.min.js"></script>
<script src="${contextPath}/resources/vendor/bootstrap/js/jquery.appear.js"></script>
<script src="${contextPath}/resources/vendor/bootstrap/js/jquery.maskedinput.min.js"></script>
<script type="text/javascript"
        src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.4.1/js/bootstrap-datepicker.min.js"></script>
<link rel="stylesheet"
      href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.4.1/css/bootstrap-datepicker3.css"/>
<script src="${contextPath}/resources/vendor/jquery-easing/jquery.easing.min.js"></script>
<script src="${contextPath}/resources/js/google.js"></script>
<script src='${contextPath}/resources/js/pamCode.js'></script>
</body>
</html>
