<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Artem
  Date: 21.04.2018
  Time: 01:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link href="${contextPath}/resources/bootstrap3/css/bootstrap.min.css" rel="stylesheet">
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