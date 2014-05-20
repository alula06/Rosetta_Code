<%-- 
    Document   : lesson
    Created on : May 5, 2014, 4:15:47 PM
    Author     : abeshue
--%>

<%@ page language="java" import="java.util.*,api.bean.SessionUserBean" pageEncoding="UTF-8"%>
<%
    SessionUserBean user = (SessionUserBean)session.getAttribute("user");
    String str = request.getParameter("activity_id");
    int activity_id = 0;
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
        activity_id = Integer.parseInt(str);
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
    <body style="background-color:#002B36">
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
                <div class="col-xs-4 round" id="lesson">
                    <ul class="nav nav-tabs" id="myTab">
                        <li class="active"><a href="#home" data-toggle="tab">Lesson</a></li>
                        <li><a href="#messages" data-toggle="tab">Exercises</a></li>
                    </ul>

                    <div class="tab-content">
                        <div class="tab-pane active" id="home">
                            <h3 id="aname">
                                
                            </h3>
                            <div id="adescription">
                            <br>
                            
                            </div>
                        </div><!--/tab-pane-->

                        <div class="tab-pane" id="messages">
                            <div>
                                
                                <p><h4>#<a id="eprogress">1</a> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Score:<a id="ecredit">20</a> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Level:<a id="elevel">2</a></h4></p>
                                <h5>Description</h5>
                                <p id="edescription">saikdjklj askdjlkaj adkjkl adsakkl akdjalk2 kasjd2323 2313 12kjksjkl2 kdjkla</p>
                                <br>
                            </div>
                            <div class="table-responsive">
                                <table class="table table-hover">
                                    <tbody class="white" id="exerciselist">
                                        <tr>
                                            <th>No.</th>
                                            <th>Level</th>
                                            <th>Points</th>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div><!--/tab-pane-->
                    </div><!--/tab-pane-->
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
                    <div class="row output" id="output">
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
        <script>
                            var editor = ace.edit("editor");
                            editor.setTheme("ace/theme/solarized_dark");
                            editor.getSession().setMode("ace/mode/java");
                            editor.setOption("minLines", 18);
                            editor.setOption("maxLines", 18);
        </script>
        <script src="js/bootstrap.min.js"></script>
        <!--  <script src="js/bootstrap-dialog.min.js"></script> -->
        <!-- functions for run/clear buttons-->
        <script>
            var exerciselist;
            var progress;
            $(document).ready(function() {
                getActivity();
            });
            
            function getActivity() {
                var reqObj = new Object();
                reqObj.activity_id = <%= activity_id %>;
                $.ajax({
                    type: "POST",
                    dataType: "json",
                    contentType: "application/json",
                    url: "api/ActivityGet.do",
                    data: JSON.stringify(reqObj)})
                        .done(function(data) {
                            if (!data.status) {
                                //BootstrapDialog.alert("Failure. " + data.message);
                                alert("Failure. " + data.message);
                                window.location = "profile.jsp";
                            }
                            $("#aname").html(data.activities[0].lesson.name);
                            $("#adescription").html(data.activities[0].lesson.description);
                            progress = data.activities[0].progress;
                            exerciselist = data.exercises;
                            for (var i=0; i<exerciselist.length; i++) {
                                makeExerciseItem(i+1, exerciselist[i]);
                            }
                            $("#eprogress").html(progress+1);
                            $("#ecredit").html(exerciselist[progress].credit);
                            $("#elevel").html(exerciselist[progress].level);
                            $("#edescription").html(exerciselist[progress].description);
                        }).fail(function(data) {
                            alert(data.status + " failure");
                        });
            }
            function makeExerciseItem(index, exercise) {
                $("#exerciselist").append("<tr><th>"+index+"</th><th>"+exercise.level+"</th><th>"+exercise.credit+"</th></tr>");
                
            }
            
            function clearCode() {
                editor.getSession().setValue("public class Main { \n\tpublic static void main(String[] args) {\n\t\t// Insert your code on the next line ...\n\n\t}\n}");
            }
            function sendCode() {
                $("#output").html("Communicating with server...");
                var code = new Object();
                code.activity_id = <%= activity_id %>;
                code.exercise_id = exerciselist[progress].id;
                code.sourcecode = editor.getSession().getValue();
                $.ajax({
                    type: "POST",
                    dataType: "json",
                    contentType: "application/json",
                    url: "api/ExerciseCode.do",
                    data: JSON.stringify(code)})
                        .done(function(data) {
                            var html;
                            html = "<br>> " + data.compiler_command;
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
                                html += "<br> Correct Answer: " + data.correct_output;
                            }
                            $("#output").html(html);
                            if (data.correct) {
                                progress++;
                                if (progress >= exerciselist.length) {
                                    //BootstrapDialog.alert("Congratulation! You finished this lesson.");
                                    alert("Congratulation! You finished this lesson.");
                                    window.location = "profile.jsp";
                                }
                                //BootstrapDialog.alert("You got the answer and earned " + data.credit + " pts.");
                                alert("You got the answer and earned " + data.credit + " pts.");
                                $("#eprogress").html(progress+1);
                                $("#ecredit").html(exerciselist[progress].credit);
                                $("#elevel").html(exerciselist[progress].level);
                                $("#edescription").html(exerciselist[progress].description);
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
                    //closable: false,
                    buttons: [{
                        label: 'OK',
                        cssClass: 'btn-primary',
                        action: function() {
                            window.location = "profile.jsp";
                        }
                    }, {
                        label: 'Cancel',
                        cssClass: 'btn-default',
                        action: function(DialogRef) {
                            DialogRef.close();
                        }
                    }]
                });
                */
            }
        </script>


    </body>
</html>

