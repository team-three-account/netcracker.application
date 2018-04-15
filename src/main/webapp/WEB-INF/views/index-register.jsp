<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<!-- saved from url=(0058)https://thunder-team.com/friend-finder/index-register.html -->
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="This is social network html5 template available in themeforest......">
    <meta name="keywords" content="Social Network, Social Media, Make Friends, Newsfeed, Profile Page">
    <meta name="robots" content="index, follow">
    <title>Friend Finder | A Complete Social Network Template</title>

    <!-- Stylesheets
    ================================================= -->
    <link rel="stylesheet" href="/static/css/bootstrap.min.css">
    <link rel="stylesheet" href="/static/css/styleV2.css">
    <link rel="stylesheet" href="/static/css/ionicons.min.css">
    <link rel="stylesheet" href="/static/css/font-awesome.min.css">

    <!--Google Font-->
    <link href="/static/css/fonts.css" rel="stylesheet">

    <!--Favicon-->
    <link rel="shortcut icon" type="image/png" href="/static/images/fav.png">
    <style type="text/css">/*.lleo_errorSelection *::-moz-selection,
.lleo_errorSelection *::selection,
.lleo_errorSelection *::-webkit-selection {
    background-color: red !important;
    color: #fff !important;;
}*/

    #lleo_dialog,
    #lleo_dialog * {
        color: #000 !important;
        font: normal 13px Arial, Helvetica !important;
        line-height: 15px !important;
        margin: 0 !important;
        padding: 0 !important;
        background: none !important;
        border: none 0 !important;
        position: static !important;
        vertical-align: baseline !important;
        overflow: visible !important;
        width: auto !important;
        height: auto !important;
        max-width: none !important;
        max-height: none !important;
        float: none !important;
        visibility: visible !important;
        text-align: left !important;
        text-transform: none !important;
        border-collapse: separate !important;
        border-spacing: 2px !important;
        box-sizing: content-box !important;
        box-shadow: none !important;
        opacity: 1 !important;
        text-shadow: none !important;
        letter-spacing: normal !important;
        -webkit-filter: none !important;
        -moz-filter: none !important;
        filter: none !important;
    }

    #lleo_dialog *:before,
    #lleo_dialog *:after {
        content: '';
    }

    #lleo_dialog iframe {
        height: 0 !important;
        width: 0 !important;
    }

    #lleo_dialog {
        position: absolute !important;
        background: #fff !important;
        border: solid 1px #ccc !important;
        padding: 7px 0 0 !important;
        left: -999px;
        top: -999px;
        width: 440px !important;
        overflow: hidden;
        display: block !important;
        z-index: 999999999 !important;
        box-shadow: 8px 16px 30px rgba(0, 0, 0, 0.16) !important;
        border-radius: 3px !important;
        opacity: 0 !important;
        -webkit-transform: translateY(15px);
        -moz-transform: translateY(15px);
        -ms-transform: translateY(15px);
        -o-transform: translateY(15px);
        transform: translateY(15px);
    }

    #lleo_dialog.lleo_show_small {
        width: 150px !important;
    }

    #lleo_dialog.lleo_show {
        opacity: 1 !important;
        -webkit-transform: translateY(0);
        -moz-transform: translateY(0);
        -ms-transform: translateY(0);
        -o-transform: translateY(0);
        transform: translateY(0);
        -webkit-transition: -webkit-transform 0.3s, opacity 0.3s !important;
        -moz-transition: -moz-transform 0.3s, opacity 0.3s !important;
        -ms-transition: -ms-transform 0.3s, opacity 0.3s !important;
        -o-transition: -o-transform 0.3s, opacity 0.3s !important;
        transition: transform 0.3s, opacity 0.3s !important;
    }

    #lleo_dialog.lleo_collapse {
        opacity: 0 !important;
        -webkit-transform: scale(0.25, 0.1) translate(-550px, 100px);
        -moz-transform: scale(0.25, 0.1) translate(-550px, 100px);
        -ms-transform: scale(0.25, 0.1) translate(-550px, 100px);
        -o-transform: scale(0.25, 0.1) translate(-550px, 100px);
        transform: scale(0.25, 0.1) translate(-550px, 100px);
        -webkit-transition: -webkit-transform 0.4s, opacity 0.4s !important;
        -moz-transition: -moz-transform 0.4s, opacity 0.4s !important;
        -ms-transition: -ms-transform 0.4s, opacity 0.4s !important;
        -o-transition: -o-transform 0.4s, opacity 0.4s !important;
        transition: transform 0.4s, opacity 0.4s !important;
    }

    #lleo_dialog input::-webkit-input-placeholder {
        color: #aaa !important;
    }

    #lleo_dialog .lleo_has_pic #lleo_word {
        margin-right: 80px !important;
    }

    #lleo_dialog #lleo_translationsContainer1 {
        position: relative !important;
    }

    #lleo_dialog #lleo_translationsContainer2 {
        padding: 7px 0 0 !important;
        vertical-align: middle !important;
    }

    #lleo_dialog #lleo_word {
        color: #000 !important;
        margin: 0 5px 2px 0 !important;
        /*float: left !important;*/
    }

    #lleo_dialog .lleo_has_sound #lleo_word {
        margin-left: 30px !important;
    }

    #lleo_dialog #lleo_text {
        font-weight: bold !important;
        color: #d56e00 !important;
        text-decoration: none !important;
        cursor: default !important;
    }

    /*
    #lleo_dialog #lleo_text.lleo_known {
        cursor: pointer !important;
        text-decoration: underline !important;
    }
    */
    /*#lleo_dialog #lleo_closeBtn {
        position: absolute !important;
        right: 6px !important;
        top: 5px !important;
        line-height: 1px !important;
        text-decoration: none !important;
        font-weight: bold !important;
        font-size: 0 !important;
        color: #aaa !important;
        display: block !important;
        z-index: 9999999999 !important;
        width: 7px !important;
        height: 7px !important;
        padding: 0 !important;
        margin: 0 !important;
    }*/

    #lleo_dialog #lleo_optionsBtn {
        position: absolute !important;
        right: 3px !important;
        top: 5px !important;
        line-height: 1px !important;
        text-decoration: none !important;
        font-weight: bold !important;
        font-size: 13px !important;
        color: #aaa !important;
        padding: 2px !important;
        display: none;
    }

    #lleo_dialog.lleo_optionsShown #lleo_optionsBtn {
        display: block !important;
    }

    #lleo_dialog #lleo_optionsBtn img {
        width: 12px !important;
        height: 12px !important;
    }

    #lleo_dialog #lleo_sound {
        float: left !important;
        width: 16px !important;
        height: 16px !important;
        margin-left: 9px !important;
        margin-right: 3px !important;
        background: 0 0 no-repeat !important;
        cursor: pointer !important;
        display: none !important;
        background: url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAfNJREFUeNq0U01IVFEU/u57Oo5WhBRIBBptykWLYBa2soWiEKQQEbrSFsGbVRQKQc2iFqGitXqvjQxCoCJCqyI0aBUtZILaJNUuYWYWo8HovJ/707nP94bnz0rxwvfOuefd8517fi5TSuE4i50YwZ3l37ZhNlpgzFkaaM/G9sHF1YskNrT+7l4PjMOcb78t2JL71uxgB+2UlfxHTtq5N94fIOh/88kWgWfl73ZCSQkpeGg3H091JY6dI2S00qA/N3KO3dDUYhFgEmZGurG+w9FqApIHsVM7kaTF9Nhn0r8Q7hPWQgIRuNaH3AMUA4W/Lkdh04cpFS43G0TgxQTvCdMETVAk3KynIHwXZU/ge8XDt7KH9bKLjU0P2zVO5LsEpSejVRJ9UR18EtfqKegovs9R3Q6w9c/H1o4Aa2Jwm1lIvn9RJ4w9RdRRzqcYrpwycCll4Cy1lnkS3Bc6vfBg28v8aRIfI78zhB/1GygROH3jLyyzMQ0zlUZuZBSlKkeLoegGtTjYLcJ8pF+NakHOFC2J6w+f25mxSfWrWFF/ShXVPTGvtN14NNkVnxlYWJkgZEL7/vwKr55lKSVnaGYWkuYgrgG172uUv47+U7fw0EHaJXmalUQy/HqO6lBzEsVjJC4Q8kd6TETQpjuaGOvjv8b/AgwA/ij1XMx58NIAAAAASUVORK5CYII=) !important;
    }

    #lleo_dialog .lleo_has_sound #lleo_sound {
        display: block !important;
    }

    #lleo_dialog #lleo_soundWave {
        border: solid 5px #4495CC !important;
        border-radius: 5px !important;
        position: absolute !important;
        left: -5px !important;
        top: -5px !important;
        right: -5px !important;
        bottom: -5px !important;
        z-index: 0 !important;
        opacity: 0.9 !important;
        display: none !important;
    }

    #lleo_dialog #lleo_soundWave.lleo_beforePlaying {
        display: block !important;
    }

    #lleo_dialog #lleo_soundWave.lleo_playing {
        opacity: 0 !important;
        border-width: 20px !important;
        border-radius: 30px !important;

        -webkit-transform: scale(1.07, 1.1) !important;
        -moz-transform: scale(1.07, 1.1) !important;
        -ms-transform: scale(1.07, 1.1) !important;
        transform: scale(1.07, 1.1) !important;

        -webkit-transition: all 0.6s !important;
        -moz-transition: all 0.6s !important;
        -ms-transition: all 0.6s !important;
        transition: all 0.6s !important;
    }

    #lleo_dialog #lleo_picOuter {
        position: absolute !important;
        float: right !important;
        top: 4px;
        right: 5px;
        z-index: 9 !important;
        display: none !important;
        width: 100px !important;
    }

    #lleo_dialog.lleo_optionsShown #lleo_picOuter {
        right: 25px;
    }

    #lleo_dialog .lleo_has_pic #lleo_picOuter {
        display: block !important;
    }

    #lleo_dialog #lleo_picOuter:hover {
        width: auto !important;
        z-index: 11 !important;
    }

    #lleo_dialog #lleo_pic,
    #lleo_dialog #lleo_picBig {
        position: absolute !important;
        top: 0 !important;
        right: 0 !important;
        border: solid 2px #fff !important;
        -webkit-border-radius: 2px !important;
        -moz-border-radius: 2px !important;
        border-radius: 2px !important;
        z-index: 1 !important;
    }

    #lleo_dialog #lleo_pic {
        position: relative !important;
        border: none !important;
        width: 30px !important;
    }

    #lleo_dialog #lleo_picBig {
        box-shadow: -1px 2px 4px rgba(0, 0, 0, 0.3);
        z-index: 2 !important;
        opacity: 0 !important;
        visibility: hidden !important;
    }

    #lleo_dialog #lleo_picOuter:hover #lleo_picBig {
        visibility: visible !important;
        opacity: 1 !important;
        -webkit-transition: opacity 0.3s !important;
        -webkit-transition-delay: 0.3s !important;
    }

    #lleo_dialog #lleo_transcription {
        margin: 0 80px 4px 31px !important;
        color: #aaaaaa !important;
    }

    #lleo_dialog .lleo_no_trans {
        color: #aaa !important;
    }

    #lleo_dialog .ll-translation-counter {
        float: right !important;
        font-size: 11px !important;
        color: #aaa !important;
        padding: 2px 2px 1px 10px !important;
    }

    #lleo_dialog .ll-translation-text {
        float: left !important;
        /*width: 80% !important;*/
    }

    #lleo_dialog #lleo_trans a {
        color: #3F669F !important;
        text-decoration: none !important;
        text-overflow: ellipsis !important;
        padding: 1px 4px !important;
        overflow: hidden !important;
        float: left !important;
        width: 320px !important;
    }

    #lleo_dialog .ll-translation-item {
        color: #3F669F !important;
        border: solid 1px #fff !important;
        padding: 3px !important;
        width: 100% !important;
        float: left !important;
        -moz-border-radius: 2px !important;
        -webkit-border-radius: 2px !important;
        border-radius: 2px !important;
    }

    #lleo_dialog .ll-translation-item:hover {
        border: solid 1px #9FC2C9 !important;
        background: #EDF4F6 !important;
        cursor: pointer !important;
    }

    #lleo_dialog .ll-translation-item:hover .ll-translation-counter {
        color: #83a0a6 !important;
    }

    #lleo_dialog .ll-translation-marker {
        background: url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAQAAAAECAYAAACp8Z5+AAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsQAAA7EAZUrDhsAAAAWSURBVBhXY7RPm/+fAQkwIXNAbMICAJQ8AkvqWg/SAAAAAElFTkSuQmCC) !important;
        display: inline-block !important;
        width: 4px !important;
        height: 4px !important;
        margin: 7px 5px 2px 2px !important;
        float: left !important;
    }

    #lleo_dialog #lleo_icons {
        color: #aaa !important;
        font-size: 11px !important;
        background: #f8f8f8 !important;
        padding: 10px 10px 10px 16px !important;
    }

    #lleo_icons a {
        display: inline-block !important;
        width: 16px !important;
        height: 16px !important;
        margin: 0 10px -4px 3px !important;
        text-decoration: none !important;
        opacity: 0.5 !important;
        background: url(data:image/png;base64,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) !important;
    }

    #lleo_icons a:hover {
        opacity: 1 !important;
    }

    #lleo_icons a.lleo_google {
        background-position: -34px 0 !important;
    }

    #lleo_icons a.lleo_multitran {
        background-position: -64px 0 !important;
    }

    #lleo_icons a.lleo_lingvo {
        background-position: -51px 0 !important;
        width: 12px !important;
    }

    #lleo_icons a.lleo_dict {
        background-position: -17px 0 !important;
    }

    #lleo_icons a.lleo_linguee {
        background-position: -81px 0 !important;
    }

    #lleo_icons a.lleo_michaelis {
        background-position: -98px 0 !important;
    }

    #lleo_dialog #lleo_contextContainer {
        margin: 0 !important;
        padding: 3px 15px 8px 10px !important;
        background: #eee !important;
        background: -webkit-gradient(linear, left top, left bottom, from(#fff), to(#eee)) !important;
        background: -moz-linear-gradient(-90deg, #fff, #eee) !important;
        border-bottom: solid 1px #ddd !important;
        border-top-left-radius: 3px !important;
        border-top-right-radius: 3px !important;
        display: none !important;
        overflow: hidden !important;
    }

    #lleo_dialog .lleo_has_context #lleo_contextContainer {
        display: block !important;
    }

    #lleo_dialog #lleo_context {
        color: #444 !important;
        text-shadow: 1px 1px 0 #f4f4f4 !important;
        line-height: 12px !important;
        font-size: 11px !important;
        margin-left: 2px !important;
    }

    #lleo_dialog #lleo_context b {
        line-height: 12px !important;
        color: #000 !important;
        font-weight: bold !important;
        font-size: 11px !important;
    }

    /*#lleo_dialog #lleo_gBrand {
        color: #aaa !important;
        font-size: 10px !important;
        *//*padding-right: 52px !important;*//*
        padding-bottom: 14px !important;
        margin: -3px 4px 0 4px !important;
        background: left bottom url(data:image/png;base64,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) no-repeat !important;
        display: inline-block !important;
        float: right !important;
    }
    #lleo_dialog #lleo_gBrand.hidden {
        display: none !important;
    }*/
    #lleo_dialog #lleo_translateContextLink {
        color: #444 !important;
        text-shadow: 1px 1px 0 #f4f4f4 !important;
        background: -webkit-gradient(linear, left top, left bottom, from(#f4f4f4), to(#ddd)) !important;
        background: -moz-linear-gradient(-90deg, #f4f4f4, #ddd) !important;
        border: solid 1px !important;
        box-shadow: 1px 1px 0 #f6f6f6 !important;
        border-color: #999 #aaa #aaa #999 !important;
        -moz-border-radius: 2px !important;
        -webkit-border-radius: 2px !important;
        border-radius: 2px !important;
        padding: 0 3px !important;
        font-size: 11px !important;
        text-decoration: none !important;
        margin: 1px 5px 0 !important;
        display: inline-block !important;
        white-space: nowrap !important;
    }

    #lleo_dialog #lleo_translateContextLink:hover {
        background: #f8f8f8 !important;
    }

    #lleo_dialog #lleo_translateContextLink.hidden {
        visibility: hidden !important;
    }

    #lleo_dialog #lleo_setTransForm {
        display: block !important;
        margin-top: 3px !important;
        padding-top: 5px !important;
        /* Set position and background because the form might be overlapped by an image when no translations */
        position: relative !important;
        background: #fff !important;
        z-index: 10 !important;
        padding-bottom: 10px !important;
        padding-left: 16px !important;
    }

    #lleo_dialog .lleo-custom-translation {
        padding: 4px 5px !important;
        border: solid 1px #ddd !important;
        border-radius: 2px !important;
        width: 90% !important;
        min-width: 270px !important;
        background: -webkit-gradient(linear, 0 0, 0 20, from(#f1f1f1), to(#fff)) !important;
        background: -moz-linear-gradient(-90deg, #f1f1f1, #fff) !important;
        font: normal 13px Arial, Helvetica !important;
        line-height: 15px !important;
    }

    #lleo_dialog .lleo-custom-translation:hover {
        border: solid 1px #aaa !important;
    }

    #lleo_dialog .lleo-custom-translation:focus {
        background: #FFFEC9 !important;
    }

    #lleo_dialog *.hidden {
        display: none !important;
    }

    #lleo_dialog .infinitive {
        color: #D56E00 !important;
        text-decoration: none;
        border-bottom: 1px dotted #D56E00 !important;
    }

    #lleo_dialog .infinitive:hover {
        border: none !important;
    }

    #lleo_dialog .lleo_separator {
        height: 1px !important;
        background: #eee;
        margin-top: 10px !important;
        background: -webkit-linear-gradient(left, rgba(255, 255, 255, 1) 0%, #eee 8%, rgba(255, 255, 255, 1) 80%) !important;
        background: -moz-linear-gradient(left, rgba(255, 255, 255, 1) 0%, #eee 8%, rgba(255, 255, 255, 1) 80%) !important;
        background: -ms-linear-gradient(left, rgba(255, 255, 255, 1) 0%, #eee 8%, rgba(255, 255, 255, 1) 80%) !important;
        background: linear-gradient(to right, rgba(255, 255, 255, 1) 0%, #eee 8%, rgba(255, 255, 255, 1) 80%) !important;
    }

    #lleo_dialog #lleo_trans {
        /*border-top: 1px solid #eeeeee !important;*/
        padding: 5px 30px 0 14px !important;
        zoom: 1;
    }

    #lleo_dialog .lleo_clearfix {
        display: block !important;
        clear: both !important;
        visibility: hidden !important;
        height: 0 !important;
        font-size: 0 !important;
    }

    #lleo_dialog #lleo_picOuter table {
        width: 44px !important;
        position: absolute !important;
        right: 0 !important;
        top: 0 !important;
        vertical-align: middle !important;
    }

    #lleo_dialog #lleo_picOuter td {
        width: 38px !important;
        height: 38px !important;
        /*border: 1px solid #eeeeee !important;*/
        vertical-align: middle !important;
        text-align: center !important;
    }

    #lleo_dialog #lleo_picOuter td div {
        height: 38px !important;
        overflow: hidden !important;
    }

    #lleo_dialog .lleo_empty {
        margin: 0 5px 7px !important;
    }

    #lleo_youtubeExportBtn {
        margin-left: 10px;
        height: 24px;
    }

    #lleo_youtubeExportBtn i {
        display: inline-block;
        width: 16px;
        height: 16px;
        background: 0 0 url(https://d144fqpiyasmrr.cloudfront.net/plugins/all/images/i16.png) !important;
    }

    #lleo_youtubeExportBtn .yt-uix-button-content {
        font-size: 12px;
        line-height: 2px;
    }

    /*** Parsed Lyrics Content *****************************/

    .lleo_lyrics tran {
        background: transparent !important;
        border-radius: 2px !important;
        text-shadow: none !important;
        cursor: pointer !important;
    }

    .lleo_lyrics tran:hover {
        color: #fff !important;
        background: #C77213 !important;
        -webkit-transition: all 0.1s !important;
        -moz-transition: all 0.1s !important;
        -ms-transition: all 0.1s !important;
        -o-transition: all 0.1s !important;
        transition: all 0.1s !important;
    }

    .lleo_songName {
        border: solid 1px #ffd47c;
        background: #fff1c2;
        border-radius: 2px;
    }

    .lleo_hidden_iframe {
        visibility: hidden;
    }</style>
</head>
<body>

<!-- Header
================================================= -->
<header id="header-inverse">
    <nav class="navbar navbar-default navbar-fixed-top menu">
        <div class="container">

            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                        data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="https://thunder-team.com/friend-finder/index-register.html"><img
                        src="/static/images/logo.png" alt="logo"></a>
            </div>

            <!-- Collect the nav links, forms, and other content for toggling -->
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <ul class="nav navbar-nav navbar-right main-menu">
                    <li class="dropdown">
                        <a href="https://thunder-team.com/friend-finder/index-register.html#" class="dropdown-toggle"
                           data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Home
                            <span><img src="/static/images/down-arrow.png" alt=""></span></a>
                        <ul class="dropdown-menu newsfeed-home">
                            <li><a href="https://thunder-team.com/friend-finder/index.html">Landing Page 1</a></li>
                            <li><a href="https://thunder-team.com/friend-finder/index-register.html">Landing Page 2</a>
                            </li>
                        </ul>
                    </li>
                    <li class="dropdown">
                        <a href="https://thunder-team.com/friend-finder/index-register.html#" class="dropdown-toggle"
                           data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Newsfeed
                            <span><img src="./index-register_files/down-arrow.png" alt=""></span></a>
                        <ul class="dropdown-menu newsfeed-home">
                            <li><a href="https://thunder-team.com/friend-finder/newsfeed.html">Newsfeed</a></li>
                            <li><a href="https://thunder-team.com/friend-finder/newsfeed-people-nearby.html">Poeple
                                Nearly</a></li>
                            <li><a href="https://thunder-team.com/friend-finder/newsfeed-friends.html">My friends</a>
                            </li>
                            <li><a href="https://thunder-team.com/friend-finder/newsfeed-messages.html">Chatroom</a>
                            </li>
                            <li><a href="https://thunder-team.com/friend-finder/newsfeed-images.html">Images</a></li>
                            <li><a href="https://thunder-team.com/friend-finder/newsfeed-videos.html">Videos</a></li>
                        </ul>
                    </li>
                    <li class="dropdown">
                        <a href="https://thunder-team.com/friend-finder/index-register.html#" class="dropdown-toggle"
                           data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Timeline
                            <span><img src="/static/images/down-arrow.png" alt=""></span></a>
                        <ul class="dropdown-menu login">
                            <li><a href="https://thunder-team.com/friend-finder/timeline.html">Timeline</a></li>
                            <li><a href="https://thunder-team.com/friend-finder/timeline-about.html">Timeline About</a>
                            </li>
                            <li><a href="https://thunder-team.com/friend-finder/timeline-album.html">Timeline Album</a>
                            </li>
                            <li><a href="https://thunder-team.com/friend-finder/timeline-friends.html">Timeline
                                Friends</a></li>
                            <li><a href="https://thunder-team.com/friend-finder/edit-profile-basic.html">Edit: Basic
                                Info</a></li>
                            <li><a href="https://thunder-team.com/friend-finder/edit-profile-work-edu.html">Edit:
                                Work</a></li>
                            <li><a href="https://thunder-team.com/friend-finder/edit-profile-interests.html">Edit:
                                Interests</a></li>
                            <li><a href="https://thunder-team.com/friend-finder/edit-profile-settings.html">Account
                                Settings</a></li>
                            <li><a href="https://thunder-team.com/friend-finder/edit-profile-password.html">Change
                                Password</a></li>
                        </ul>
                    </li>
                    <li class="dropdown">
                        <a href="https://thunder-team.com/friend-finder/index-register.html#"
                           class="dropdown-toggle pages" data-toggle="dropdown" role="button" aria-haspopup="true"
                           aria-expanded="false">All Pages <span><img src="/static/images/down-arrow.png" alt=""></span></a>
                        <ul class="dropdown-menu page-list">
                            <li><a href="https://thunder-team.com/friend-finder/index.html">Landing Page 1</a></li>
                            <li><a href="https://thunder-team.com/friend-finder/index-register.html">Landing Page 2</a>
                            </li>
                            <li><a href="https://thunder-team.com/friend-finder/newsfeed.html">Newsfeed</a></li>
                            <li><a href="https://thunder-team.com/friend-finder/newsfeed-people-nearby.html">Poeple
                                Nearly</a></li>
                            <li><a href="https://thunder-team.com/friend-finder/newsfeed-friends.html">My friends</a>
                            </li>
                            <li><a href="https://thunder-team.com/friend-finder/newsfeed-messages.html">Chatroom</a>
                            </li>
                            <li><a href="https://thunder-team.com/friend-finder/newsfeed-images.html">Images</a></li>
                            <li><a href="https://thunder-team.com/friend-finder/newsfeed-videos.html">Videos</a></li>
                            <li><a href="https://thunder-team.com/friend-finder/timeline.html">Timeline</a></li>
                            <li><a href="https://thunder-team.com/friend-finder/timeline-about.html">Timeline About</a>
                            </li>
                            <li><a href="https://thunder-team.com/friend-finder/timeline-album.html">Timeline Album</a>
                            </li>
                            <li><a href="https://thunder-team.com/friend-finder/timeline-friends.html">Timeline
                                Friends</a></li>
                            <li><a href="https://thunder-team.com/friend-finder/edit-profile-basic.html">Edit
                                Profile</a></li>
                            <li><a href="https://thunder-team.com/friend-finder/contact.html">Contact Us</a></li>
                            <li><a href="https://thunder-team.com/friend-finder/faq.html">FAQ Page</a></li>
                            <li><a href="https://thunder-team.com/friend-finder/404.html">404 Not Found</a></li>
                        </ul>
                    </li>
                    <li class="dropdown"><a href="https://thunder-team.com/friend-finder/contact.html">Contact</a></li>
                </ul>
                <form class="navbar-form navbar-right hidden-sm">
                    <div class="form-group">
                        <i class="icon ion-android-search"></i>
                        <input type="text" class="form-control" placeholder="Search friends, photos, videos">
                    </div>
                </form>
            </div><!-- /.navbar-collapse -->
        </div><!-- /.container -->
    </nav>
</header>
<!--Header End-->

<!-- Landing Page Contents
================================================= -->
<div id="lp-register">
    <div class="container wrapper">
        <div class="row">
            <div class="col-sm-5">
                <div class="intro-texts">
                    <h1 class="text-white">Make Cool Friends !!!</h1>
                    <p>Friend Finder is a social network template that can be used to connect people. The template
                        offers Landing pages, News Feed, Image/Video Feed, Chat Box, Timeline and lot more. <br> <br>Why
                        are you waiting for? Buy it now.</p>
                    <button class="btn btn-primary">Learn More</button>
                </div>
            </div>
            <div class="col-sm-6 col-sm-offset-1">
                <div class="reg-form-container">

                    <!-- Register/Login Tabs-->
                    <div class="reg-options">
                        <ul class="nav nav-tabs">
                            <li class="active"><a
                                    href="https://thunder-team.com/friend-finder/index-register.html#register"
                                    data-toggle="tab" aria-expanded="true">Register</a></li>
                            <li class=""><a href="https://thunder-team.com/friend-finder/index-register.html#login"
                                            data-toggle="tab" aria-expanded="false">Login</a></li>
                        </ul><!--Tabs End-->
                    </div>

                    <!--Registration Form Contents-->
                    <div class="tab-content">
                        <div class="tab-pane active" id="register">
                            <h3>Register Now !!!</h3>
                            <p class="text-muted">Be cool and join today. Meet millions</p>

                            <!--Register Form-->
                            <form:form method="POST" modelAttribute="person" name="registration_form"
                                       id="registration_form" class="form-inline"
                                       action="registerProcess">
                                <div class="row">
                                    <div class="form-group col-xs-6">
                                        <label for="name" class="sr-only">First Name</label>
                                        <input path="name" name="name" id="name" class="form-control input-group-lg"
                                               type="text"
                                               title="Enter first name" placeholder="First name">
                                    </div>
                                    <div class="form-group col-xs-6">
                                        <label for="surname" class="sr-only">Last Name</label>
                                        <input path="surname" name="surname" id="surname"
                                               class="form-control input-group-lg" type="text"
                                               title="Enter last name" placeholder="Last name">
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="form-group col-xs-12">
                                        <label for="email" class="sr-only">Email</label>
                                        <input path="email" name="email" id="email" class="form-control input-group-lg"
                                               type="text"
                                               title="Enter Email" placeholder="Your Email">
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="form-group col-xs-12">
                                        <label for="password" class="sr-only">Password</label>
                                        <input path="password" name="password" id="password"
                                               class="form-control input-group-lg" type="password"
                                               title="Enter password" placeholder="Password">
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="form-group col-xs-12">
                                        <label for="dateOfBirthday" class="sr-only">Date Of Birthday</label>
                                        <input path="dateOfBirthday" name="dateOfBirthday" id="dateOfBirthday"
                                               class="form-control input-group-lg" type="date"
                                               title="Enter Date" placeholder="Date Of Birthday">
                                    </div>
                                </div>
                                <%--<div class="row">--%>
                                <%--<p class="birth"><strong>Date of Birth</strong></p>--%>
                                <%--<div class="form-group col-sm-3 col-xs-6">--%>
                                <%--<label for="month" class="sr-only"></label>--%>
                                <%--<select class="form-control" id="day">--%>
                                <%--<option value="Day" disabled="" selected="">Day</option>--%>
                                <%--<option>1</option>--%>
                                <%--<option>2</option>--%>
                                <%--<option>3</option>--%>
                                <%--<option>4</option>--%>
                                <%--<option>5</option>--%>
                                <%--<option>6</option>--%>
                                <%--<option>7</option>--%>
                                <%--<option>8</option>--%>
                                <%--<option>9</option>--%>
                                <%--<option>10</option>--%>
                                <%--<option>11</option>--%>
                                <%--<option>12</option>--%>
                                <%--<option>13</option>--%>
                                <%--<option>14</option>--%>
                                <%--<option>15</option>--%>
                                <%--<option>16</option>--%>
                                <%--<option>17</option>--%>
                                <%--<option>18</option>--%>
                                <%--<option>19</option>--%>
                                <%--<option>20</option>--%>
                                <%--<option>21</option>--%>
                                <%--<option>22</option>--%>
                                <%--<option>23</option>--%>
                                <%--<option>24</option>--%>
                                <%--<option>25</option>--%>
                                <%--<option>26</option>--%>
                                <%--<option>27</option>--%>
                                <%--<option>28</option>--%>
                                <%--<option>29</option>--%>
                                <%--<option>30</option>--%>
                                <%--<option>31</option>--%>
                                <%--</select>--%>
                                <%--</div>--%>
                                <%--<div class="form-group col-sm-3 col-xs-6">--%>
                                <%--<label for="month" class="sr-only"></label>--%>
                                <%--<select class="form-control" id="month">--%>
                                <%--<option value="month" disabled="" selected="">Month</option>--%>
                                <%--<option>Jan</option>--%>
                                <%--<option>Feb</option>--%>
                                <%--<option>Mar</option>--%>
                                <%--<option>Apr</option>--%>
                                <%--<option>May</option>--%>
                                <%--<option>Jun</option>--%>
                                <%--<option>Jul</option>--%>
                                <%--<option>Aug</option>--%>
                                <%--<option>Sep</option>--%>
                                <%--<option>Oct</option>--%>
                                <%--<option>Nov</option>--%>
                                <%--<option>Dec</option>--%>
                                <%--</select>--%>
                                <%--</div>--%>
                                <%--<div class="form-group col-sm-6 col-xs-12">--%>
                                <%--<label for="year" class="sr-only"></label>--%>
                                <%--<select class="form-control" id="year">--%>
                                <%--<option value="year" disabled="" selected="">Year</option>--%>
                                <%--<option>2000</option>--%>
                                <%--<option>2001</option>--%>
                                <%--<option>2002</option>--%>
                                <%--<option>2004</option>--%>
                                <%--<option>2005</option>--%>
                                <%--<option>2006</option>--%>
                                <%--<option>2007</option>--%>
                                <%--<option>2008</option>--%>
                                <%--<option>2009</option>--%>
                                <%--<option>2010</option>--%>
                                <%--<option>2011</option>--%>
                                <%--<option>2012</option>--%>
                                <%--</select>--%>
                                <%--</div>--%>
                                <%--</div>--%>
                                <%--<div class="form-group gender">--%>
                                <%--<label class="radio-inline">--%>
                                <%--<input type="radio" name="optradio" checked="">Male--%>
                                <%--</label>--%>
                                <%--<label class="radio-inline">--%>
                                <%--<input type="radio" name="optradio">Female--%>
                                <%--</label>--%>
                                <%--</div>--%>
                                <div class="row">
                                    <div class="form-group col-xs-6">
                                        <label for="city" class="sr-only">City</label>
                                        <input path="city" name="city" id="city"
                                               class="form-control input-group-lg reg_name" type="text"
                                               title="Enter city" placeholder="Your city">
                                    </div>
                                    <div class="form-group col-xs-6">
                                        <label for="country" class="sr-only"></label>
                                        <select class="form-control" id="country" path="country" name="country">
                                            <option value="country" disabled="" selected="">Country</option>
                                            <option value="AFG">Afghanistan</option>
                                            <option value="ALA">�land Islands</option>
                                            <option value="ALB">Albania</option>
                                            <option value="DZA">Algeria</option>
                                            <option value="ASM">American Samoa</option>
                                            <option value="AND">Andorra</option>
                                            <option value="AGO">Angola</option>
                                            <option value="AIA">Anguilla</option>
                                            <option value="ATA">Antarctica</option>
                                            <option value="ATG">Antigua and Barbuda</option>
                                            <option value="ARG">Argentina</option>
                                            <option value="ARM">Armenia</option>
                                            <option value="ABW">Aruba</option>
                                            <option value="AUS">Australia</option>
                                            <option value="AUT">Austria</option>
                                            <option value="AZE">Azerbaijan</option>
                                            <option value="BHS">Bahamas</option>
                                            <option value="BHR">Bahrain</option>
                                            <option value="BGD">Bangladesh</option>
                                            <option value="BRB">Barbados</option>
                                            <option value="BLR">Belarus</option>
                                            <option value="BEL">Belgium</option>
                                            <option value="BLZ">Belize</option>
                                            <option value="BEN">Benin</option>
                                            <option value="BMU">Bermuda</option>
                                            <option value="BTN">Bhutan</option>
                                            <option value="BOL">Bolivia, Plurinational State of</option>
                                            <option value="BES">Bonaire, Sint Eustatius and Saba</option>
                                            <option value="BIH">Bosnia and Herzegovina</option>
                                            <option value="BWA">Botswana</option>
                                            <option value="BVT">Bouvet Island</option>
                                            <option value="BRA">Brazil</option>
                                            <option value="IOT">British Indian Ocean Territory</option>
                                            <option value="BRN">Brunei Darussalam</option>
                                            <option value="BGR">Bulgaria</option>
                                            <option value="BFA">Burkina Faso</option>
                                            <option value="BDI">Burundi</option>
                                            <option value="KHM">Cambodia</option>
                                            <option value="CMR">Cameroon</option>
                                            <option value="CAN">Canada</option>
                                            <option value="CPV">Cape Verde</option>
                                            <option value="CYM">Cayman Islands</option>
                                            <option value="CAF">Central African Republic</option>
                                            <option value="TCD">Chad</option>
                                            <option value="CHL">Chile</option>
                                            <option value="CHN">China</option>
                                            <option value="CXR">Christmas Island</option>
                                            <option value="CCK">Cocos (Keeling) Islands</option>
                                            <option value="COL">Colombia</option>
                                            <option value="COM">Comoros</option>
                                            <option value="COG">Congo</option>
                                            <option value="COD">Congo, the Democratic Republic of the</option>
                                            <option value="COK">Cook Islands</option>
                                            <option value="CRI">Costa Rica</option>
                                            <option value="CIV">C�te d'Ivoire</option>
                                            <option value="HRV">Croatia</option>
                                            <option value="CUB">Cuba</option>
                                            <option value="CUW">Cura�ao</option>
                                            <option value="CYP">Cyprus</option>
                                            <option value="CZE">Czech Republic</option>
                                            <option value="DNK">Denmark</option>
                                            <option value="DJI">Djibouti</option>
                                            <option value="DMA">Dominica</option>
                                            <option value="DOM">Dominican Republic</option>
                                            <option value="ECU">Ecuador</option>
                                            <option value="EGY">Egypt</option>
                                            <option value="SLV">El Salvador</option>
                                            <option value="GNQ">Equatorial Guinea</option>
                                            <option value="ERI">Eritrea</option>
                                            <option value="EST">Estonia</option>
                                            <option value="ETH">Ethiopia</option>
                                            <option value="FLK">Falkland Islands (Malvinas)</option>
                                            <option value="FRO">Faroe Islands</option>
                                            <option value="FJI">Fiji</option>
                                            <option value="FIN">Finland</option>
                                            <option value="FRA">France</option>
                                            <option value="GUF">French Guiana</option>
                                            <option value="PYF">French Polynesia</option>
                                            <option value="ATF">French Southern Territories</option>
                                            <option value="GAB">Gabon</option>
                                            <option value="GMB">Gambia</option>
                                            <option value="GEO">Georgia</option>
                                            <option value="DEU">Germany</option>
                                            <option value="GHA">Ghana</option>
                                            <option value="GIB">Gibraltar</option>
                                            <option value="GRC">Greece</option>
                                            <option value="GRL">Greenland</option>
                                            <option value="GRD">Grenada</option>
                                            <option value="GLP">Guadeloupe</option>
                                            <option value="GUM">Guam</option>
                                            <option value="GTM">Guatemala</option>
                                            <option value="GGY">Guernsey</option>
                                            <option value="GIN">Guinea</option>
                                            <option value="GNB">Guinea-Bissau</option>
                                            <option value="GUY">Guyana</option>
                                            <option value="HTI">Haiti</option>
                                            <option value="HMD">Heard Island and McDonald Islands</option>
                                            <option value="VAT">Holy See (Vatican City State)</option>
                                            <option value="HND">Honduras</option>
                                            <option value="HKG">Hong Kong</option>
                                            <option value="HUN">Hungary</option>
                                            <option value="ISL">Iceland</option>
                                            <option value="IND">India</option>
                                            <option value="IDN">Indonesia</option>
                                            <option value="IRN">Iran, Islamic Republic of</option>
                                            <option value="IRQ">Iraq</option>
                                            <option value="IRL">Ireland</option>
                                            <option value="IMN">Isle of Man</option>
                                            <option value="ISR">Israel</option>
                                            <option value="ITA">Italy</option>
                                            <option value="JAM">Jamaica</option>
                                            <option value="JPN">Japan</option>
                                            <option value="JEY">Jersey</option>
                                            <option value="JOR">Jordan</option>
                                            <option value="KAZ">Kazakhstan</option>
                                            <option value="KEN">Kenya</option>
                                            <option value="KIR">Kiribati</option>
                                            <option value="PRK">Korea, Democratic People's Republic of</option>
                                            <option value="KOR">Korea, Republic of</option>
                                            <option value="KWT">Kuwait</option>
                                            <option value="KGZ">Kyrgyzstan</option>
                                            <option value="LAO">Lao People's Democratic Republic</option>
                                            <option value="LVA">Latvia</option>
                                            <option value="LBN">Lebanon</option>
                                            <option value="LSO">Lesotho</option>
                                            <option value="LBR">Liberia</option>
                                            <option value="LBY">Libya</option>
                                            <option value="LIE">Liechtenstein</option>
                                            <option value="LTU">Lithuania</option>
                                            <option value="LUX">Luxembourg</option>
                                            <option value="MAC">Macao</option>
                                            <option value="MKD">Macedonia, the former Yugoslav Republic of</option>
                                            <option value="MDG">Madagascar</option>
                                            <option value="MWI">Malawi</option>
                                            <option value="MYS">Malaysia</option>
                                            <option value="MDV">Maldives</option>
                                            <option value="MLI">Mali</option>
                                            <option value="MLT">Malta</option>
                                            <option value="MHL">Marshall Islands</option>
                                            <option value="MTQ">Martinique</option>
                                            <option value="MRT">Mauritania</option>
                                            <option value="MUS">Mauritius</option>
                                            <option value="MYT">Mayotte</option>
                                            <option value="MEX">Mexico</option>
                                            <option value="FSM">Micronesia, Federated States of</option>
                                            <option value="MDA">Moldova, Republic of</option>
                                            <option value="MCO">Monaco</option>
                                            <option value="MNG">Mongolia</option>
                                            <option value="MNE">Montenegro</option>
                                            <option value="MSR">Montserrat</option>
                                            <option value="MAR">Morocco</option>
                                            <option value="MOZ">Mozambique</option>
                                            <option value="MMR">Myanmar</option>
                                            <option value="NAM">Namibia</option>
                                            <option value="NRU">Nauru</option>
                                            <option value="NPL">Nepal</option>
                                            <option value="NLD">Netherlands</option>
                                            <option value="NCL">New Caledonia</option>
                                            <option value="NZL">New Zealand</option>
                                            <option value="NIC">Nicaragua</option>
                                            <option value="NER">Niger</option>
                                            <option value="NGA">Nigeria</option>
                                            <option value="NIU">Niue</option>
                                            <option value="NFK">Norfolk Island</option>
                                            <option value="MNP">Northern Mariana Islands</option>
                                            <option value="NOR">Norway</option>
                                            <option value="OMN">Oman</option>
                                            <option value="PAK">Pakistan</option>
                                            <option value="PLW">Palau</option>
                                            <option value="PSE">Palestinian Territory, Occupied</option>
                                            <option value="PAN">Panama</option>
                                            <option value="PNG">Papua New Guinea</option>
                                            <option value="PRY">Paraguay</option>
                                            <option value="PER">Peru</option>
                                            <option value="PHL">Philippines</option>
                                            <option value="PCN">Pitcairn</option>
                                            <option value="POL">Poland</option>
                                            <option value="PRT">Portugal</option>
                                            <option value="PRI">Puerto Rico</option>
                                            <option value="QAT">Qatar</option>
                                            <option value="REU">R�union</option>
                                            <option value="ROU">Romania</option>
                                            <option value="RUS">Russian Federation</option>
                                            <option value="RWA">Rwanda</option>
                                            <option value="BLM">Saint Barth�lemy</option>
                                            <option value="SHN">Saint Helena, Ascension and Tristan da Cunha</option>
                                            <option value="KNA">Saint Kitts and Nevis</option>
                                            <option value="LCA">Saint Lucia</option>
                                            <option value="MAF">Saint Martin (French part)</option>
                                            <option value="SPM">Saint Pierre and Miquelon</option>
                                            <option value="VCT">Saint Vincent and the Grenadines</option>
                                            <option value="WSM">Samoa</option>
                                            <option value="SMR">San Marino</option>
                                            <option value="STP">Sao Tome and Principe</option>
                                            <option value="SAU">Saudi Arabia</option>
                                            <option value="SEN">Senegal</option>
                                            <option value="SRB">Serbia</option>
                                            <option value="SYC">Seychelles</option>
                                            <option value="SLE">Sierra Leone</option>
                                            <option value="SGP">Singapore</option>
                                            <option value="SXM">Sint Maarten (Dutch part)</option>
                                            <option value="SVK">Slovakia</option>
                                            <option value="SVN">Slovenia</option>
                                            <option value="SLB">Solomon Islands</option>
                                            <option value="SOM">Somalia</option>
                                            <option value="ZAF">South Africa</option>
                                            <option value="SGS">South Georgia and the South Sandwich Islands</option>
                                            <option value="SSD">South Sudan</option>
                                            <option value="ESP">Spain</option>
                                            <option value="LKA">Sri Lanka</option>
                                            <option value="SDN">Sudan</option>
                                            <option value="SUR">Suriname</option>
                                            <option value="SJM">Svalbard and Jan Mayen</option>
                                            <option value="SWZ">Swaziland</option>
                                            <option value="SWE">Sweden</option>
                                            <option value="CHE">Switzerland</option>
                                            <option value="SYR">Syrian Arab Republic</option>
                                            <option value="TWN">Taiwan, Province of China</option>
                                            <option value="TJK">Tajikistan</option>
                                            <option value="TZA">Tanzania, United Republic of</option>
                                            <option value="THA">Thailand</option>
                                            <option value="TLS">Timor-Leste</option>
                                            <option value="TGO">Togo</option>
                                            <option value="TKL">Tokelau</option>
                                            <option value="TON">Tonga</option>
                                            <option value="TTO">Trinidad and Tobago</option>
                                            <option value="TUN">Tunisia</option>
                                            <option value="TUR">Turkey</option>
                                            <option value="TKM">Turkmenistan</option>
                                            <option value="TCA">Turks and Caicos Islands</option>
                                            <option value="TUV">Tuvalu</option>
                                            <option value="UGA">Uganda</option>
                                            <option value="UKR">Ukraine</option>
                                            <option value="ARE">United Arab Emirates</option>
                                            <option value="GBR">United Kingdom</option>
                                            <option value="USA">United States</option>
                                            <option value="UMI">United States Minor Outlying Islands</option>
                                            <option value="URY">Uruguay</option>
                                            <option value="UZB">Uzbekistan</option>
                                            <option value="VUT">Vanuatu</option>
                                            <option value="VEN">Venezuela, Bolivarian Republic of</option>
                                            <option value="VNM">Viet Nam</option>
                                            <option value="VGB">Virgin Islands, British</option>
                                            <option value="VIR">Virgin Islands, U.S.</option>
                                            <option value="WLF">Wallis and Futuna</option>
                                            <option value="ESH">Western Sahara</option>
                                            <option value="YEM">Yemen</option>
                                            <option value="ZMB">Zambia</option>
                                            <option value="ZWE">Zimbabwe</option>
                                        </select>
                                    </div>
                                </div>
                                <br/>
                                <br/>
                                <button class="btn btn-primary" name="register">Register Now</button>
                                <br/>
                                <br/>
                                <p><a href="https://thunder-team.com/friend-finder/index-register.html#">Already have an
                                    account?</a></p>

                            </form:form><!--Register Now Form Ends-->
                        </div><!--Registration Form Contents Ends-->

                        <!--Login-->
                        <div class="tab-pane" id="login">
                            <c:url var="index-register" value="/login"/>
                            <h3>Login</h3>
                            <p class="text-muted">Log into your account</p>

                            <!--Login Form-->
                            <form:form action="@{/login}" method="POST" name="Login_form" id="Login_form">
                                <div class="row">
                                    <div class="form-group col-xs-12">
                                        <label for="my-email" class="sr-only">Email</label>
                                        <input id="my-email" class="form-control input-group-lg" type="text"
                                               path="email" name="Email" title="Enter Email" placeholder="Your Email">
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="form-group col-xs-12">
                                        <label for="my-password" class="sr-only">Password</label>
                                        <input id="my-password" class="form-control input-group-lg" type="password"
                                               path="password" name="password" title="Enter password"
                                               placeholder="Password">
                                    </div>
                                </div>
                                <%--<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>--%>
                                <p><a href="https://thunder-team.com/friend-finder/index-register.html#">Forgot
                                    Password?</a></p>
                                <%--<button class="btn btn-primary">Login Now</button>--%>
                                <input type="submit" class="btn btn-block btn-primary" value="Login Now"/>
                            </form:form><!--Login Form Ends-->

                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-sm-6 col-sm-offset-6">

                <!--Social Icons-->
                <ul class="list-inline social-icons">
                    <li><a href="https://thunder-team.com/friend-finder/index-register.html#"><i
                            class="icon ion-social-facebook"></i></a></li>
                    <li><a href="https://thunder-team.com/friend-finder/index-register.html#"><i
                            class="icon ion-social-twitter"></i></a></li>
                    <li><a href="https://thunder-team.com/friend-finder/index-register.html#"><i
                            class="icon ion-social-googleplus"></i></a></li>
                    <li><a href="https://thunder-team.com/friend-finder/index-register.html#"><i
                            class="icon ion-social-pinterest"></i></a></li>
                    <li><a href="https://thunder-team.com/friend-finder/index-register.html#"><i
                            class="icon ion-social-linkedin"></i></a></li>
                </ul>
            </div>
        </div>
    </div>
</div>

<!--preloader-->
<div id="spinner-wrapper" style="display: none;">
    <div class="spinner"></div>
</div>

<!--Buy button-->
<a href="https://themeforest.net/cart/add_items?item_ids=18711273&amp;ref=thunder-team" target="_blank"
   class="btn btn-buy"><span class="italy">Buy with:</span><img src="/static/images/envato_logo.png" alt=""><span
        class="price">Only $20!</span></a>

<!-- Scripts
================================================= -->
<script src="/static/js/jquery-3.1.1.min.js"></script>
<script src="/static/js/bootstrap.min.js"></script>
<script src="/static/js/jquery.appear.min.js"></script>
<script src="/static/js/jquery.incremental-counter.js"></script>
<script src="/static/js/script.js"></script>


</body>
</html>