<%-- 
    Document   : challenge
    Created on : Apr 24, 2014, 12:55:41 PM
    Author     : Adrian, Zi sheng Wu
--%>


<%@ page language="java" import="java.util.*,api.bean.SessionUserBean" pageEncoding="UTF-8"%>

<% 
    SessionUserBean user = (SessionUserBean)session.getAttribute("user");
    String str = request.getParameter("challenge_id");
    int challenge_id = 0;
    if (user == null) {
        response.sendRedirect("/index.jsp");
    } else if (str == null) {
        response.sendRedirect("/profile.jsp");
    } else {
        int len = str.length();
        for (int i=0; i<len; i++) {
            if (!Character.isDigit(str.charAt(i))) {
                response.sendRedirect("/profile.jsp");
            }
        }
        challenge_id = Integer.parseInt(str);
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
        
        <title>Rosetta Code | Javengers</title>
        
        <!-- <link href="css/bootstrap-dialog.css" rel="stylesheet"> -->
        <!-- Bootstrap core CSS -->
        <link href="css/bootstrap.css" rel="stylesheet">

        <!-- Custom styles for jumbotron -->
        <link href="css/jumbotron.css" rel="stylesheet">

        <!-- Custom styles -->
        <link href="css/custom.css" rel="stylesheet">
        <link href="css/afpcustom.css" rel="stylesheet">

        <!-- Just for debugging purposes. Don't actually copy this line! -->
        <!--[if lt IE 9]><script src="../../assets/js/ie8-responsive-file-warning.js"></script><![endif]-->

        <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
        <!--[if lt IE 9]>
          <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
          <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
        <![endif]-->

        <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
        <link rel="stylesheet" href="http://netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css">
        <script type="text/javascript" src="js/TimeCircles.js"></script>
        <link rel="stylesheet" href="css/TimeCircles.css" />
    </head>
    <body style="background-color: #002B36">
        <header>
            <div class="navbar navbar-custom navbar-fixed-top" role="navigation">
                <div class="container">
                    <div class="navbar-header">
                        <a class="brand" href="#"><img id="logo" src="images/RosettaCode.png"></a>
                    </div>
                    <div class="navbar-collapse collapse" id="user-name">
                        <button class="btn btn-default" onclick="back()">Back</button>
                    </div>
                    <!--/.navbar-collapse -->
                </div>
            </div>
        </header>


        <!--challenge container-->
        <div class="container-fixed">  
            <!--Challenger Profile -->
            <div class="row round" style="border: solid grey">
                <div class="col-xs-4 round" id="challenger">
                    <div class="row" id="challenger-top">
                        <ul class="nav nav-tabs" id="myTab">
                            <li class="active"><a href="#home" data-toggle="tab">Prompt</a></li>
                            <li><a href="#messages" data-toggle="tab">Information</a></li>
                        </ul>
                        <div class="tab-content">
                            <div class="tab-pane active" id="home">
                                <h3 id="codelevel">Easy</h3>
                                <p id="codedescription">
                                    Create an array that contains the first 5 multiples of five. We will do the printing for you.
                                </p>
                            </div><!--/tab-pane-->
                            <div class="tab-pane" id="messages">
                                <div class="col-xs-6" id="challenger-info">
                                    <h3>Challenger 1:</h3>
                                    <h4 id="offense_name"> JohnDoe</h4>
                                    <cite title="San Francisco, USA">San Francisco, USA&nbsp; <i class="glyphicon glyphicon-map-marker"></i>
                                    </cite>
                                    <br/>
                                    <i class="glyphicon glyphicon-tower"></i>&nbsp;Rank: <a id="offense_level"></a>
                                    <br/>
                                    <i class="glyphicon glyphicon-gift"></i>&nbsp;Since <a id="offense_register_date"></a>
                                    <br/><br/>
                                </div>
                                <div class="col-xs-6" id="challenger-info">
                                    <h3>Challenger 2:</h3>
                                    <h4 id="defense_name"> JaneDoe</h4>
                                    <cite title="San Francisco, USA">San Francisco, USA&nbsp; <i class="glyphicon glyphicon-map-marker"></i>
                                    </cite>
                                    <br/>
                                    <i class="glyphicon glyphicon-tower"></i>&nbsp;Rank: <a id="defense_level"></a>
                                    <br/>
                                    <i class="glyphicon glyphicon-gift"></i>&nbsp;Since <a id="defense_register_date"></a>
                                    <br/><br/>
                                </div>
                            </div><!--/tab-pane-->
                        </div><!--/tab-pane-->
                    </div>
                    <!--IMAGE TIMER-->
                    <div class="row">
                        <div id="timer">  
                            <!--<div id="PageOpenTimer" data-timer="180"></div>-->
                        </div>
                    </div>
                    <br>
                </div>

                 <!-- Challenge Editor & Output -->
                <div class="col-xs-8 round" id="console">
                    <div class="row label-mid-top">Language: Java</div>
                    <div id="editor" class="row">public class Main {
    public static void main(String[] args) {
        // Insert your code on the next line ...
        
    }
}</div>
                    <div class="row label-mid">Output:</div>
                    <div id="output" class="row output">
                        OUTPUT HERE
                    </div>
                    <div class="run-button">
                        <button type="button" class="btn btn-danger btn-md" onclick="clearCode()">
                            Clear Code</button>
                        <button type="button" class="btn btn-success btn-md" onclick="sendCode()">
                            Run Code</button>
                    </div>
                </div>
            </div>
        </div>
        <!-- end container-->

        <footer id="footer2">
            &copy; Javengers 2014
        </footer>


        <!-- Bootstrap core JavaScript
        ================================================== -->
        <!-- Placed at the end of the document so the pages load faster -->
        <script src="src-noconflict/ace.js" type="text/javascript" charset="utf-8"></script>
        <script src="js/bootstrap.min.js"></script>
        <!-- <script src="js/bootstrap-dialog.min.js"></script> -->
        
        <script>
            var editor = ace.edit("editor");
            editor.setTheme("ace/theme/solarized_dark");
            editor.getSession().setMode("ace/mode/java");
            editor.setOption("minLines", 18);
            editor.setOption("maxLines", 18);
        </script>
        
        <!-- functions for run/clear buttons-->
        <script>
            $(document).ready(function() {
                startChallenge();
            });
            
            
            function startChallenge() {
                var reqObj = new Object();
                reqObj.challenge_id = <%= challenge_id %>;
                $.ajax({
                    type: "POST",
                    dataType: "json",
                    contentType: "application/json",
                    url: "api/ChallengeStart.do",
                    data: JSON.stringify(reqObj)})
                        .done(function(data) {
                            if (!data.status) {
                                //BootstrapDialog.alert("Failure. " + data.message);
                                alert("Failure. " + data.message);
                                window.location = "profile.jsp";
                            }
                            $("#timer").html("<div id='PageOpenTimer' data-timer='"+data.challengeinfo.seconds+"'></div>");
                            $("#PageOpenTimer").TimeCircles({time: {Days: {show: false}, Hours: {show: false}}});
                            $("#PageOpenTimer").TimeCircles({count_past_zero: false}).addListener(function(unit, value, total) {
                                if(total <= 0) {
                                    alert("Time out! Back to profile page.");
                                    window.location = "profile.jsp";
                                }
                            });
                            $("#codelevel").html(data.challengeinfo.level);
                            $("#codedescription").html(data.challengeinfo.description);
                            $("#offense_name").html(data.challenge.offense_name);
                            $("#offense_level").html(data.challenge.offense_level);
                            $("#offense_register_date").html(data.challenge.offense_register_date.substring(0,10));
                            $("#defense_name").html(data.challenge.defense_name);
                            $("#defense_level").html(data.challenge.defense_level);
                            $("#defense_register_date").html(data.challenge.defense_register_date.substring(0,10));
                        }).fail(function(data) {
                            alert(data.status + " failure");
                            window.location = "profile.jsp";
                        });
            }
            function clearCode() {
                editor.getSession().setValue("public class Main { \n\tpublic static void main(String[] args) {\n\t\t// Insert your code on the next line ...\n\n\t}\n}");
            }
            function sendCode() {
                $("#output").html("Communicating with server...");
                var code = new Object();
                code.sourcecode = editor.getSession().getValue();
                $.ajax({
                    type: "POST",
                    dataType: "json",
                    contentType: "application/json",
                    url: "api/ChallengeCode.do",
                    data: JSON.stringify(code)})
                        .done(function(data) {
                            if (data.errorcode == 2) {
                                //BootstrapDialog.alert("Failure. " + data.message);
                                alert("Failure. " + data.message);
                                window.location = "profile.jsp";
                            } 
                            var html;
                            html = "attempt: " + data.attemp + "<br>> " + data.compiler_command;
                            html += "<br>> " + data.execution_command;
                            if (data.execution_error != "") {
                                html += "<br>> " + data.execution_error;
                            } else {
                                html += "<br>> " + data.execution_output;
                            }
                            if (data.correct) {
                                html += "<br><br> Correct!";
                            } else {
                                html += "<br><br> No Correct!";
                            }
                            $("#output").html(html);
                            if (data.correct) {
                                //BootstrapDialog.alert("You got the answer. Click here back to profile page.");
                                alert("You got the answer. Click here back to profile page.");
                                window.location = "profile.jsp";
                            }
                        }).fail(function(data) {
                            $("#output").html("Failure, please try again.");
                        });
            }
            
            function back() {
                window.location = "profile.jsp";
                /*
                BootstrapDialog.show({
                    title: 'Information',
                    message: 'Are you sure to go back to profile page?',
                    type: BootstrapDialog.TYPE_PRIMARY,
                    closable: false,
                    buttons: [{
                        label: 'OK',
                        cssClass: 'btn-primary',
                        action: function() {
                            window.location = "profile.jsp";
                        }
                    }, {
                        label: 'Cancel',
                        cssClass: 'btn-default',
                        action: function() {
                            
                        }
                    }]
                });
                */
            }
        </script>


        <script>
            //$("#PageOpenTimer").TimeCircles({time: {Days: {show: false}, Hours: {show: false}}});
            //$("#PageOpenTimer").TimeCircles({count_past_zero: false});
        </script>
    </body>
</html>
