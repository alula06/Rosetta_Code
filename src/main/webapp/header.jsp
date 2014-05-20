<%-- 
    Document   : header
    Created on : May 7, 2014, 5:07:24 PM
    Author     : Alvaro
--%>

<%@ page language="java" import="java.util.*,api.bean.SessionUserBean" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="description" content="">
        <meta name="author" content="">
        <link rel="shortcut icon" href="../../assets/ico/favicon.ico">

        <title>Rosetta Code | Javengers</title>

        <!-- Bootstrap core CSS -->
        <link href="css/bootstrap.css" rel="stylesheet">

        <!-- Custom styles for jumbotron -->
        <link href="css/jumbotron.css" rel="stylesheet">

        <!-- Custom styles -->
        <link href="css/custom.css" rel="stylesheet">

        <!-- Just for debugging purposes. Don't actually copy this line! -->
        <!--[if lt IE 9]><script src="../../assets/js/ie8-responsive-file-warning.js"></script><![endif]-->

        <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
        <!--[if lt IE 9]>
          <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
          <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
        <![endif]-->
    </head>
    <%
        SessionUserBean user = (SessionUserBean) session.getAttribute("user");
        if (user != null) {
            response.sendRedirect("/profile.jsp");
        }
    %>
    <body>
        
        <div id="nav" class="navbar navbar-inverse navbar-fixed-top" role="navigation">
            <div class="container">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a id="homebtn" class="navbar-brand glyphicon glyphicon-home" href="#"></a>
                </div>
                <div id="loginstatus" class="navbar-collapse collapse">
                    <% if (user == null) { %>
                    <div id="loginform" class="navbar-form navbar-right" role="form">
                        <div class="form-group">
                            <input id="loginemail" name="loginemail" type="text" placeholder="Email" class="form-control">
                        </div>
                        <div class="form-group">
                            <input id="loginpassword" name="loginpassword" type="password" placeholder="Password" class="form-control">
                        </div>
                        <button id="sign-in" class="btn btn-default" onclick="login()">Sign in</button>
                    </div>
                    <div id="logoutform" class="navbar-form navbar-right" role="form" hidden>
                        <div>
                            <a id="profile_info" href="#"></a>
                            <button id="sign-out" class="btn btn-default" onclick="logout()">Sign out</button>
                        </div>
                    </div>
                    <% } else { %>
                    <div id="loginform" class="navbar-form navbar-right" role="form" hidden>
                        <div class="form-group">
                            <input id="loginemail" name="loginemail" type="text" placeholder="Email" class="form-control">
                        </div>
                        <div class="form-group">
                            <input id="loginpassword" name="loginpassword" type="password" placeholder="Password" class="form-control">
                        </div>
                        <button id="sign-in" class="btn btn-default" onclick="login()">Sign in</button>
                    </div>
                    <div id="logoutform" class="navbar-form navbar-right" role="form">
                        <div>
                            <a id="profile_info" href="#"><% out.print(user.getUsername()); %> signed in at <% out.print(user.getLogin_date()); %></a><a>&nbsp;&nbsp;</a>
                            <button class="btn btn-default" onclick="redirectProfile()">Profile</button>
                            <button id="sign-out" class="btn btn-default" onclick="logout()">Sign out</button>
                        </div>
                    </div>
                    <% } %>
                </div><!--/.navbar-collapse -->
            </div>
        </div>

        
    </body>
