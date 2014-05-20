<%-- 
    Document   : index
    Created on : Apr 30, 2014, 12:18:57 AM
    Author     : Zi sheng Wu
--%>

<%@ page language="java" import="java.util.*,api.bean.SessionUserBean" pageEncoding="UTF-8"%>

<%
    SessionUserBean user = (SessionUserBean)session.getAttribute("user"); 
    if (user != null) {
        response.sendRedirect("/profile.jsp");
    }
%>
    
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
        
        <link href="css/bootstrap-dialog.css" rel="stylesheet">

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
    
    <body style="background-color: lightgrey">

        <div class="navbar navbar-custom navbar-fixed-top" role="navigation">
            <div class="container">
                <div class="navbar-header">
                    <a class="brand" href="#"><img id="logo" src="images/RosettaCode.png"></a>
                </div>
                <div class="navbar-collapse collapse">
                    <div class="navbar-form navbar-right" role="form">
                        <div class="form-group">
                            <input id="loginemail" name="loginemail" type="text" placeholder="Email" class="form-control">
                        </div>
                        <div class="form-group">
                            <input id="loginpassword" name="loginpassword" type="password" placeholder="Password" class="form-control">
                        </div>
                        <button id="sign-in" class="btn btn-default" onclick="login()">Sign in</button>
                    </div>
                </div><!--/.navbar-collapse -->
            </div>
        </div>

        <!-- Main jumbotron for a primary marketing message or call to action -->
        <div class="jumbotron">
            <div id="jumbo" class="container">
                <h1>Welcome to <i>Rosetta Code</i>!</h1>
                <p>
                    Ever wish you could create your own software applications?
                    <br>
                    Don't have the time or money to go back to school?
                    <br>
                    <i>Rosetta Code</i> is a fun and exciting way to easily learn how to code!
                </p>
                <p style="text-align: right"><a id="reg" href="#" data-toggle="modal" data-target="#regModal" class="btn btn-default btn-lg" role="button">Register Here &raquo;</a></p>
            </div>
        </div>

        <!-- Register Modal -->
        <!-- Modal -->
        <div   class="modal fade" id="regModal" tabindex="-1" role="dialog" aria-labelledby="regLabel" aria-hidden="true">
            <div  class="modal-dialog">
                <div  class="modal-content">
                    <div style="background-image: url(../images/AB2.jpg);" class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4  class="modal-title" id="jumbo">Register for Rosetta Code!</h4>
                    </div>
                    <div  style="color: #0461C4" class="modal-body">
                        <form class="form-horizontal" role="form">
                            <div class="form-group">
                                <label for="name" class="col-sm-3">Your Name</label>
                                <div class="col-sm-9">
                                    <input type="text" class="form-control" id="reg_name" placeholder="Your name">

                                </div>
                            </div>
                            <div class="form-group">
                                <label for="email" class="col-sm-3">Email address</label>
                                <div class="col-sm-9">
                                    <input type="email" class="form-control" id="reg_email" placeholder="Enter email">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="password1" class="col-sm-3">Password</label>
                                <div class="col-sm-9">
                                    <input type="password" class="form-control" id="reg_password1" placeholder="Password">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="password2" class="col-sm-3"> Confirm Password</label>
                                <div class="col-sm-9">
                                    <input type="password" class="form-control" id="reg_password2" placeholder="Password">
                                </div>
                            </div>
                        </form>
                    </div>
                    <div style="background-image: url(../images/AB2.jpg);" class="modal-footer">
                        <button style="font-weight: bold" type="button" class="btn btn-default" data-dismiss="modal" id="cancel">Cancel</button>
                        <button style="font-weight: bold" type="button" class="btn btn-default" data-dismiss="modal" id="reg" onclick="register()">Register</button>
                    </div>
                </div>
            </div>
        </div>

        <!-- Details Modal -->
        <!-- Modal -->
        <div   class="modal fade" id="detModal1" tabindex="-1" role="dialog" aria-labelledby="regLabel" aria-hidden="true">
            <div  class="modal-dialog">
                <div  class="modal-content">
                    <div style="background-image: url(../images/AB2.jpg);" class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4  class="modal-title" id="jumbo">Learn</h4>
                    </div>
                    <div  style="color: #0461C4" class="modal-body">
                        <form class="form-horizontal" role="form">
                            <div class="form-group">
                                <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus placerat venenatis libero, vitae molestie ligula tincidunt a. Maecenas quis libero eu ligula condimentum placerat in ut libero. Proin dictum nibh nisi, vel eleifend neque laoreet non. In tellus justo, rhoncus ac pretium ac, tincidunt id ante. Proin placerat vel libero nec varius. Morbi lacus dui, sollicitudin a orci nec, semper bibendum nunc. Integer faucibus et sem in feugiat.</p>
                                <br>
                                <p>Proin porta faucibus arcu, at malesuada risus ultrices quis. Nullam vitae urna massa. Interdum et malesuada fames ac ante ipsum primis in faucibus. Donec posuere purus vel felis venenatis, in vehicula velit elementum. Fusce orci lacus, ultrices sit amet est id, elementum pellentesque felis. Quisque mi nibh, placerat sit amet scelerisque sit amet, iaculis ut neque. Phasellus id elit lobortis, sollicitudin ipsum a, condimentum urna. Nam nec tempor nisl, dictum eleifend purus. Nunc commodo mauris ut lacus fringilla, a auctor mi dictum. Duis vulputate fermentum felis. Duis euismod quam at turpis feugiat aliquet. Morbi venenatis rutrum eros et varius. Mauris nec tincidunt tellus, ac tempor elit. Curabitur luctus nec justo eu gravida. Sed nec velit eros. Vestibulum a sollicitudin sapien.</p>

                            </div>
                        </form>
                    </div>
                    <div style="background-image: url(../images/AB2.jpg);" class="modal-footer">
                        <button style="font-weight: bold" type="button" class="btn btn-default" data-dismiss="modal" id="reg">Exit</button>
                    </div>
                </div>
            </div>
        </div>
        <div   class="modal fade" id="detModal2" tabindex="-1" role="dialog" aria-labelledby="regLabel" aria-hidden="true">
            <div  class="modal-dialog">
                <div  class="modal-content">
                    <div style="background-image: url(../images/AB2.jpg);" class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4  class="modal-title" id="jumbo">Languages</h4>
                    </div>
                    <div  style="color: #0461C4" class="modal-body">
                        <form class="form-horizontal" role="form">
                            <div class="form-group">
                                <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus placerat venenatis libero, vitae molestie ligula tincidunt a. Maecenas quis libero eu ligula condimentum placerat in ut libero. Proin dictum nibh nisi, vel eleifend neque laoreet non. In tellus justo, rhoncus ac pretium ac, tincidunt id ante. Proin placerat vel libero nec varius. Morbi lacus dui, sollicitudin a orci nec, semper bibendum nunc. Integer faucibus et sem in feugiat.</p>
                                <br>
                                <p>Proin porta faucibus arcu, at malesuada risus ultrices quis. Nullam vitae urna massa. Interdum et malesuada fames ac ante ipsum primis in faucibus. Donec posuere purus vel felis venenatis, in vehicula velit elementum. Fusce orci lacus, ultrices sit amet est id, elementum pellentesque felis. Quisque mi nibh, placerat sit amet scelerisque sit amet, iaculis ut neque. Phasellus id elit lobortis, sollicitudin ipsum a, condimentum urna. Nam nec tempor nisl, dictum eleifend purus. Nunc commodo mauris ut lacus fringilla, a auctor mi dictum. Duis vulputate fermentum felis. Duis euismod quam at turpis feugiat aliquet. Morbi venenatis rutrum eros et varius. Mauris nec tincidunt tellus, ac tempor elit. Curabitur luctus nec justo eu gravida. Sed nec velit eros. Vestibulum a sollicitudin sapien.</p>


                            </div>
                        </form>
                    </div>
                    <div style="background-image: url(../images/AB2.jpg);" class="modal-footer">
                        <button style="font-weight: bold" type="button" class="btn btn-default" data-dismiss="modal" id="reg">Exit</button>
                    </div>
                </div>
            </div>
        </div>

        <div   class="modal fade" id="detModal3" tabindex="-1" role="dialog" aria-labelledby="regLabel" aria-hidden="true">
            <div  class="modal-dialog">
                <div  class="modal-content">
                    <div style="background-image: url(../images/AB2.jpg);" class="modal-header">
                        <button id="x" type="button" class="close" data-dismiss="modal" aria-hidden="true">x</button>
                        <h4  class="modal-title" id="jumbo">Lessons</h4>
                    </div>
                    <div  style="color: #0461C4" class="modal-body">
                        <form class="form-horizontal" role="form">
                            <div class="form-group">
                                <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus placerat venenatis libero, vitae molestie ligula tincidunt a. Maecenas quis libero eu ligula condimentum placerat in ut libero. Proin dictum nibh nisi, vel eleifend neque laoreet non. In tellus justo, rhoncus ac pretium ac, tincidunt id ante. Proin placerat vel libero nec varius. Morbi lacus dui, sollicitudin a orci nec, semper bibendum nunc. Integer faucibus et sem in feugiat.</p>
                                <br>
                                <p>Proin porta faucibus arcu, at malesuada risus ultrices quis. Nullam vitae urna massa. Interdum et malesuada fames ac ante ipsum primis in faucibus. Donec posuere purus vel felis venenatis, in vehicula velit elementum. Fusce orci lacus, ultrices sit amet est id, elementum pellentesque felis. Quisque mi nibh, placerat sit amet scelerisque sit amet, iaculis ut neque. Phasellus id elit lobortis, sollicitudin ipsum a, condimentum urna. Nam nec tempor nisl, dictum eleifend purus. Nunc commodo mauris ut lacus fringilla, a auctor mi dictum. Duis vulputate fermentum felis. Duis euismod quam at turpis feugiat aliquet. Morbi venenatis rutrum eros et varius. Mauris nec tincidunt tellus, ac tempor elit. Curabitur luctus nec justo eu gravida. Sed nec velit eros. Vestibulum a sollicitudin sapien.</p>


                            </div>
                        </form>
                    </div>
                    <div style="background-image: url(../images/AB2.jpg);" class="modal-footer">
                        <button style="font-weight: bold" type="button" class="btn btn-default" data-dismiss="modal" id="reg">Exit</button>
                    </div>
                </div>
            </div>
        </div>

        <div class="container">

            <!-- Example row of columns -->
            <div class="row" style="color: #002B36">
                <div class="col-md-4">
                    <h2>Learn</h2>
                    <p>Develop basic to intermediate programming skills! Enough to get you comfortable with learning the variety of programming languages on your own.  </p>
                    <p style="padding-top: 50px"><a id="dets" class="btn btn-default" href="#" data-toggle="modal" data-target="#detModal1" role="button">View details &raquo;</a></p>
                </div>
                <div class="col-md-4">
                    <h2>Languages</h2>
                    <ul>
                        <li>Java</li>
                        <li><i>JavaScript*</i></li>
                        <li><i>C*</i></li>
                        <li><i>C++*</i></li>
                    </ul>
                    <p>* -- Coming Soon</p>
                    <p><a id="dets" class="btn btn-default" href="#" data-toggle="modal" data-target="#detModal2" role="button">View details &raquo;</a></p>
                </div>
                <div class="col-md-4">
                    <h2>Lessons</h2>
                    <p>Take part in the stimulating yet exciting lessons we offer:</p>
                    <ul>
                        <li>Fill-in-the-blank</li>
                        <li>Guess the output</li>
                        <li>Debugging</li>
                        <li>Writing your own code from scratch!</li>
                    </ul>
                    <p><a id="dets" class="btn btn-default" href="#" data-toggle="modal" data-target="#detModal3" role="button">View details &raquo;</a></p>
                </div>
            </div>

            <hr>

            <footer>
                <center><p>&copy; Javengers 2014</p></center>
            </footer>

        </div> <!-- /container -->

        
        
        <script>
            
            function animation(xhr, message) {
                BootstrapDialog.alert({
                    title: 'Information',
                    message: message,
                    type: BootstrapDialog.TYPE_PRIMARY,
                    closable: false,
                    buttonLabel: 'Cancel',
                    callback: function() {
                        xhr.abort();
                        //dialogRef.close();
                    }
                });
            }
            
            function messageDialog(title, message) {
                BootstrapDialog.closeAll();
                BootstrapDialog.alert({
                    title: title,
                    message: message,
                    type: BootstrapDialog.TYPE_PRIMARY,
                    closable: true,
                    buttonLabel: 'OK',
                    callback: function() {
                        //dialogRef.close();
                    }
                });
            }
            
            function register() {
                if ($("#reg_name").val() != "" && $("#reg_email").val() != "" && $("#reg_password1").val() != "" && $("#reg_password1").val() == $("#reg_password2").val()) {
                    var regObj = new Object();
                    regObj.username = $("#reg_name").val();
                    regObj.email = $("#reg_email").val();
                    regObj.password = $("#reg_password1").val();
                    var xhr = $.ajax({
                        type:"POST",
                        dataType: "json",
                        contentType: "application/json",
                        url: "api/Register.do",
                        data: JSON.stringify(regObj)})
                            .done(function(data){
                                if (data.status) {
                                    window.location = "profile.jsp";
                                } else {
                                    messageDialog("Failure", data.message);
                                }   
                        });
                    animation(xhr, "Communicating with Server...");
                } else {
                    BootstrapDialog.alert("Failure. " + "Please complete the information.");
                }
            }
            
            function login() {
                if ($("#loginemail").val() != "" && $("#loginpassword").val() != "") {
                    var loginObj = new Object();
                    loginObj.email = $("#loginemail").val();
                    loginObj.password = $("#loginpassword").val();
                    var xhr = $.ajax({
                        type:"POST",
                        dataType: "json",
                        contentType: "application/json",
                        url: "api/Login.do",
                        data: JSON.stringify(loginObj)})
                            .done(function(data){
                                if (data.status) {
                                    window.location = "profile.jsp";
                                } else {
                                    messageDialog("Failure", data.message);
                                }
                        });
                    animation(xhr, "Trying to login...");
                } else {
                    BootstrapDialog.alert("Failure. " + "Please complete the information.");
                }
            }
            
        </script>

        <!-- Bootstrap core JavaScript
        ================================================== -->
        <!-- Placed at the end of the document so the pages load faster -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <script src="js/bootstrap-dialog.min.js"></script>
    </body>
</html>

