<%-- 
    Document   : profile
    Created on : May 5, 2014, 4:15:57 PM
    Author     : apoblete
--%>

<%@ page language="java" import="java.util.*,api.bean.SessionUserBean" pageEncoding="UTF-8"%>
<%
    SessionUserBean user = (SessionUserBean)session.getAttribute("user"); 
    int user_id = 0;
    if (user == null) {
        response.sendRedirect("/index.jsp");
    } else {
        user_id = user.getUser_id();
    }
    response.setHeader("Cache-Control","no-cache"); 
    response.setHeader("Pragma","no-cache"); 
    response.setDateHeader ("Expires", -1);
%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE"> 
        <META HTTP-EQUIV="Expires" CONTENT="-1">
        
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="description" content="">
        <meta name="author" content="">
        <link rel="shortcut icon" href="../../assets/ico/favicon.ico">

        <title>Rosetta Code | Javengers</title>
        <link href="css/bootstrap-dialog.css" rel="stylesheet">
        <!-- Bootstrap core CSS -->
        <link href="css/bootstrap.css" rel="stylesheet">
        <link href="css/ajpcustom.css" rel="stylesheet">

        <!-- Custom styles for jumbotron -->
        <link href="css/jumbotron.css" rel="stylesheet">

        <!-- Custom styles -->
        <link href="css/custom.css" rel="stylesheet">



        <link rel="shortcut icon" href="../favicon.ico">
        <link rel="stylesheet" type="text/css" href="css/normalize.css" />
        <link rel="stylesheet" type="text/css" href="css/demo.css" />
        <link rel="stylesheet" type="text/css" href="css/component.css" />
        <script src="js/modernizr.custom.js"></script>
    </head>

    <body style = "background-color: #002B36">

        <div class="navbar navbar-custom navbar-fixed-top" role="navigation">
            <div class="container">
                <div class="navbar-header">
                    <a class="brand" href="#"><img id="logo" src="images/RosettaCode.png"></a>
                </div>
                <div class="navbar-collapse collapse" id="user-name">
                    <button class="btn btn-default" onclick="logout()">Logout</button>
                </div><!--/.navbar-collapse -->
            </div>
        </div>
        <div class="container">
            <div class="row">
                <div class="col-sm-10"><h1 class="white" id="pname">JohnDoe</h1></div>
            </div>
            <br>
            <div class="row">
                <div class="col-sm-3"><!--left col-->
                    <ul class="list-group">
                        <li class="list-group-item text-muted">Profile</li>
                        <li class="list-group-item text-right" id="blue"><span class="pull-left"><strong>Level</strong></span> <a id="plevel"></a></li>
                        <li class="list-group-item text-right" id="blue"><span class="pull-left"><strong>Score</strong></span> <a id="pscore"></a></li>
                        <li class="list-group-item text-right" id="blue"><span class="pull-left"><strong>Joined</strong></span> <a id="pregister_date"></a></li>
                    </ul> 
                    
                    <ul class="list-group">
                        <li class="list-group-item text-muted">Your Stats <i class="fa fa-dashboard fa-1x"></i></li>
                        <li class="list-group-item text-right"id="blue" ><span class="pull-left"><strong>Lessons Completed</strong></span> <a id="plessoncompleted"></a></li>
                        <li class="list-group-item text-right" id="blue"><span class="pull-left"><strong>Challenges </strong></span> <a id="pchallenges"></a></li>
                        <li class="list-group-item text-right" id="blue"><span class="pull-left"><strong>Wins</strong></span> <a id="pwins"></a></li>
                        <li class="list-group-item text-right" id="blue"><span class="pull-left"><strong>Losses</strong></span> <a id="ploses"></a></li>
                    </ul> 
                </div><!--/col-3-->
                <div class="col-sm-9">
                    <ul class="nav nav-tabs" id="myTab">
                        <li class="active"><a href="#home" data-toggle="tab" onclick="getActivityList()">Lesson</a></li>
                        <li><a href="#messages" data-toggle="tab" onclick="getChallengeList()">Challenges</a></li>
                        <li><a href="#settings" data-toggle="tab">Settings</a></li>
                    </ul>

                    <div class="tab-content">
                        <div class="tab-pane active" id="home">
                            <br>
                            <h4 class="white">Lessons</h4>
                            
                            <div class="table-responsive">
                                <table class="table table-hover">
                                    <tbody id="lessonlist" class="white">
                                        <tr>
                                            <th>No.</th>
                                            <th>Name</th>
                                            <th>Progress</th>
                                            <th>Earned Points</th>
                                            <th>Action</th>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div><!--/tab-pane-->
                        
                        <div class="tab-pane" id="messages">

                            <br>
                            <h4 class="white">Challenges <a class="btn btn-primary" onclick="newChallenge()">New Challenge</a></h4>

                            <div class="table-responsive">
                                <table class="table table-hover">

                                    <tbody class="white" id="challengelist">
                                         <tr>
                                            <th>No.</th>
                                            <th>Level</th>
                                            <th>Your Status</th>
                                            <th>Challenger Name</th>
                                            <th>Challenger level</th>
                                            <th>Challenger Status</th>
                                            <th>Points</th>
                                            <th>Action</th>
                                        </tr>
                                        
                                    </tbody>
                                </table>
                            </div>      
                        </div><!--/tab-pane-->
                        
                        
                        <div class="tab-pane" id="settings">

                            <div class="form">
                                <div class="form-group">

                                    <div class="col-xs-6">
                                        <label for="username" class="white"><h4>User Name</h4></label>
                                        <input disabled type="text" class="form-control" name="username" id="username" placeholder="YoungBoss" title="Enter your user name! Geez did you really need me to tell you that?!">
                                    </div>
                                </div>




                                <div class="form-group">

                                    <div class="col-xs-6">
                                        <label for="email" class="white"><h4>Email</h4></label>
                                        <input type="email" class="form-control" name="email" id="email" placeholder="code@resetta.com" title="Enter your email.">
                                    </div>
                                </div>

                                <div class="form-group">

                                    <div class="col-xs-6">
                                        <label for="password1" class="white"><h4>Password</h4></label>
                                        <input type="password" class="form-control" name="password1" id="password1" title="Enter your password.">
                                    </div>
                                </div>
                                <div class="form-group">

                                    <div class="col-xs-6">
                                        <label for="password2" class="white"><h4>Verify Password</h4></label>
                                        <input type="password" class="form-control" name="password2" id="password2"  title="Enter your password again. Don't mess up.">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="col-xs-12">
                                        <br>
                                        <button id="infoupdate" class="btn btn-lg btn-success" onclick="infoUpdate()"><i class="glyphicon glyphicon-ok-sign"></i> Save</button>
                                        <button id="inforeset" class="btn btn-lg" onclick="infoReset()"><i class="glyphicon glyphicon-repeat"></i> Reset</button>
                                    </div>
                                </div>
                            </div>
                        </div>

                    </div><!--/tab-pane-->
                </div><!--/tab-content-->

            </div><!--/col-9-->
        </div><!--/row-->




        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <script src="js/bootstrap-dialog.min.js"></script>
        
        <script>
            $(document).ready(function() {
                getProfile();
                getActivityList();
            });
            
            function getProfile() {
                var reqObj = new Object();
                $.ajax({
                    type: "POST",
                    dataType: "json",
                    contentType: "application/json",
                    url: "api/Profile.do",
                    data: JSON.stringify(reqObj)})
                        .done(function(data) {
                            if (data.status) {
                                $("#pname").html(data.username);
                                $("#username").val(data.username);
                                $("#plevel").html(data.level);
                                $("#pscore").html(data.score);
                                $("#pregister_date").html(data.register_date.substring(0,10));
                                $("#plessoncompleted").html(data.lessoncompleted);
                                $("#pchallenges").html(data.defense_total+data.offense_total);
                                $("#pwins").html(data.defense_win+data.offense_win);
                                $("#ploses").html(data.defense_lose+data.offense_lose);
                            } else {
                                BootstrapDialog.alert("Failure. " + data.message);
                            }
                        }).fail(function(data) {
                            alert(data.status + " failure");
                        });
            }
            
            function getActivityList() {
                var reqObj = new Object();
                reqObj.requestcode = 0;
                var index=0;
                $.ajax({
                    type: "POST",
                    dataType: "json",
                    contentType: "application/json",
                    url: "api/ActivityList.do",
                    data: JSON.stringify(reqObj)})
                        .done(function(data) {
                            if (data.status) {
                                $("#lessonlist").html("<tr><th>No.</th><th>Name</th><th>Progress</th><th>Earned Points</th><th>Action</th></tr>");
                                var len = data.activities.length;
                                for (var i=0; i<len; i++) {
                                    index++;
                                    makeActivityItem(index, data.activities[i]);
                                }
                            } else {
                                BootstrapDialog.alert("Failure. " + data.message);
                            }
                        }).fail(function(data) {
                            alert(data.status + " failure");
                        });
            }
            function makeActivityItem(index, activity) {
                var html = "<tr><td>"+index+"</td><td>";
                html += activity.lesson.name + "</td><td>";
                html += (activity.progress/activity.total*100).toFixed(2) + "%</td><td>";
                html += activity.score + " pts</td><td>";
                if (activity.progress == activity.total) {
                    html += "<button class='btn btn-primary'>Finished</button></td></tr>";
                } else if (activity.progress > 0){
                    html += "<a class='btn btn-success' href='\/lesson.jsp?activity_id="+activity.id+"'>Continue</a></td></tr>";
                } else {
                    html += "<a class='btn btn-success' href='\/lesson.jsp?activity_id="+activity.id+"'>Start!</a></td></tr>";
                }
                $("#lessonlist").append(html);
            }
            
            
            function getChallengeList() {
                var reqObj = new Object();
                var index=0;
                $.ajax({
                    type: "POST",
                    dataType: "json",
                    contentType: "application/json",
                    url: "api/ChallengeList.do",
                    data: JSON.stringify(reqObj)})
                        .done(function(data) {
                            if (data.status) {
                                $("#challengelist").html("<tr><th>No.</th><th>Level</th><th>Your Status</th><th>Challenger Name</th><th>Challenger level</th><th>Challenger Status</th><th>Points</th><th>Action</th></tr>");
                                var len = data.challenges.length;
                                for (var i=0; i<len; i++) {
                                    index++;
                                    makeChallengeItem(index, data.challenges[i]);
                                }
                            } else {
                               $("#challengelist").html("<tr><td>Please click New Challenge button to create one.</td></tr>");
                            }
                        }).fail(function(data) {
                            alert(data.status + " failure");
                        });
            }
            function makeChallengeItem(index, challenge) {
                var html = "<tr><td>"+index+"</td><td>"+challenge.level+"</td><td>";
                if (<%= user_id %> == challenge.defense_id) {
                    if (challenge.defense_closed) {
                        html += "Closed</td><td>";
                    } else {
                        html += "Opening</td><td>";
                    }
                    html += challenge.offense_name + "</td><td>";
                    html += challenge.offense_level + "</td><td>";
                    if (challenge.offense_closed) {
                        html += "Closed</td><td>";
                    } else {
                        html += "Opening</td><td>";
                    }
                    html += challenge.credit + "</td><td>";
                    html += "<a class='btn btn-success' href='\/challenge.jsp?challenge_id="+challenge.id+"'>";
                    if (challenge.defense_begin_time == null) {
                        html += "Start";
                    } else {
                        html += "Continue"
                    }
                    html += "</a></td></tr>";
                } else {
                    if (challenge.offense_closed) {
                        html += "Closed</td><td>";
                    } else {
                        html += "Opening</td><td>";
                    }
                    html += challenge.defense_name + "</td><td>";
                    html += challenge.defense_level + "</td><td>";
                    if (challenge.defense_closed) {
                        html += "Closed</td><td>";
                    } else {
                        html += "Opening</td><td>";
                    }
                    html += challenge.credit + "</td><td>";
                    html += "<a class='btn btn-success' href='\/challenge.jsp?challenge_id="+challenge.id+"'>";
                    if (challenge.offense_begin_time == null) {
                        html += "Start";
                    } else {
                        html += "Continue"
                    }
                    html += "</a></td></tr>";
                }
                $("#challengelist").append(html);
            }
            
            function newChallenge() {
                var reqObj = new Object();
                reqObj.defense_id = -1;
                $.ajax({
                    type: "POST",
                    dataType: "json",
                    contentType: "application/json",
                    url: "api/ChallengeCreate.do",
                    data: JSON.stringify(reqObj)})
                        .done(function(data) {
                            if (data.status) {
                                getChallengeList();
                            } else {
                                BootstrapDialog.alert("Failure. " + data.message);
                            }
                        }).fail(function(data) {
                            alert(data.status + " failure");
                        });
            }
            
            function logout() {
                var logoutObj = new Object();
                $.ajax({
                    type:"POST",
                    dataType: "json",
                    contentType: "application/json",
                    url: "api/Logout.do",
                    data: JSON.stringify(logoutObj)})
                        .done(function(data){
                            if (data.status) {
                                window.location = "/index.jsp";
                            } else {
                                BootstrapDialog.alert("Failure. " + data.message);
                            }
                    });
            }
            
            function infoUpdate() {
                if ($("#username").val() != "" && $("#email").val() != "" && $("#password1").val() != "" && $("#password1").val() == $("#password2").val()) {
                    $("infoupdate").addClass("disabled");
                    var regObj = new Object();
                    regObj.username = $("#username").val();
                    regObj.email = $("#email").val();
                    regObj.password = $("#password1").val();
                    $.ajax({
                        type:"POST",
                        dataType: "json",
                        contentType: "application/json",
                        url: "api/Update.do",
                        data: JSON.stringify(regObj)})
                            .done(function(data){
                                if (data.status) {
                                    BootstrapDialog.alert("Success. " + data.message);
                                    infoReset();
                                    $("infoupdate").removeClass("disabled");
                                } else {
                                    BootstrapDialog.alert("Failure. " + data.message);
                                    $("infoupdate").removeClass("disabled");
                                }   
                        });
                } else {
                    BootstrapDialog.alert("Failure. " + "Please complete the information.");
                    $("infoupdate").removeClass("disabled");
                }
            }
            
            function infoReset() {
                $("#email").val("");
                $("#password1").val("");
                $("#password2").val("");
            }
        </script>
    </body>
</html>