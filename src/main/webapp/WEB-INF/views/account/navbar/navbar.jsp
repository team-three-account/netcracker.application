<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Artem
  Date: 21.04.2018
  Time: 01:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script src="//netdna.bootstrapcdn.com/bootstrap/3.0.3/js/bootstrap.min.js"></script>
    <script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
    <link href="${contextPath}/resources/bootstrap3/css/bootstrap.min.css" rel="stylesheet">
    <script type="text/javascript">
        $(function () {

            $(".input-group-btn .dropdown-menu li a").click(function () {

                var selText = $(this).html();

                //working version - for single button //
                //$('.btn:first-child').html(selText+'<span class="caret"></span>');

                //working version - for multiple buttons //
                $(this).parents('.input-group-btn').find('.btn-search').html(selText);

            });

        });
    </script>
    <style>
        /* nav bar search box - drop down menu button */
        .navbar .navbar-search .dropdown-menu {
            min-width: 25px;
        }

        .dropdown-menu .label-icon {
            margin-left: 5px;
        }

        .btn-outline {
            background-color: transparent;
            color: inherit;
            transition: all .5s;
        }
    </style>
</head>
<body>
<div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="/">Cracker Time</a>
        </div>
        <div class="navbar-collapse collapse">
            <ul class="nav navbar-nav">
                <div class="nav nav-justified navbar-nav">

                    <form class="navbar-form navbar-search" role="search" method="post" action="/account/search">
                        <div class="input-group">

                            <div class="input-group-btn">
                                <select class="form-control" name="typeSearch">
                                    <option value="user">
                                        <span class="glyphicon glyphicon-user">
                                            Search by Users
                                        </span>
                                    </option>
                                    <option value="event" selected>
                                        <span class="glyphicon glyphicon-book">
                                            Search by Events
                                        </span>
                                    <option value="item">
                                        <span class="glyphicon glyphicon-gift">
                                            Search by Items
                                        </span>
                                    </option>
                                </select>

                            <%--<button type="button" class="btn btn-search btn-default dropdown-toggle"--%>
                                        <%--data-toggle="dropdown">--%>
                                    <%--<span class="glyphicon glyphicon-search"></span>--%>
                                    <%--<span class="label-icon">Search</span>--%>
                                    <%--<span class="caret"></span>--%>
                                <%--</button>--%>
                                <%--<ul class="dropdown-menu pull-left" role="menu">--%>
                                    <%--<li value="user">--%>
                                        <%--<a href="#">--%>
                                            <%--<span class="glyphicon glyphicon-user"></span>--%>
                                            <%--<span class="label-icon">Users</span>--%>
                                        <%--</a>--%>
                                    <%--</li>--%>
                                    <%--<li value="event">--%>
                                        <%--<a href="#">--%>
                                            <%--<span class="glyphicon glyphicon-book"></span>--%>
                                            <%--<span class="label-icon">Events</span>--%>
                                        <%--</a>--%>
                                    <%--</li>--%>
                                    <%--<li value="item">--%>
                                        <%--<a href="#">--%>
                                            <%--<span class="glyphicon glyphicon-gift"></span>--%>
                                            <%--<span class="label-icon">Items</span>--%>
                                        <%--</a>--%>
                                    <%--</li>--%>
                                    <%--<li value="friend">--%>
                                        <%--<a href="#">--%>
                                            <%--<span class="glyphicon glyphicon-heart-empty"></span>--%>
                                            <%--<span class="label-icon">Friends</span>--%>
                                        <%--</a>--%>
                                    <%--</li>--%>
                                <%--</ul>--%>
                            </div>

                            <input type="text" class="form-control" name="search">

                            <div class="input-group-btn">
                                <button type="submit" class="btn btn-search btn-default">
                                    GO
                                </button>


                            </div>
                        </div>
                    </form>
                </div>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li><a href="<c:url value="/account/profile/${auth_user.id}"/> ">Profile</a></li>
                <li><a href="/logout">Logout</a></li>
            </ul>
        </div>
    </div>
</div>
</body>
</html>
