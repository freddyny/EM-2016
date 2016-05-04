<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.google.appengine.api.datastore.DatastoreService" %>
<%@ page import="com.google.appengine.api.datastore.DatastoreServiceFactory" %>
<%@ page import="com.google.appengine.api.datastore.Entity" %>
<%@ page import="com.google.appengine.api.datastore.FetchOptions" %>
<%@ page import="com.google.appengine.api.datastore.Key" %>
<%@ page import="com.google.appengine.api.datastore.KeyFactory" %>
<%@ page import="com.google.appengine.api.datastore.Query" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<html>
<head>
    <link type="text/css" rel="stylesheet" href="/stylesheets/bootstrap.min.css"/''>
    <link type="text/css" rel="stylesheet" href="/stylesheets/bootstrap-theme.min.css"/''>
    <link type="text/css" rel="stylesheet" href="/stylesheets/template.css"/>
    <link type="text/css" rel="stylesheet" href="/stylesheets/form.css"/''>
    <script src="<c:url value="/js/jquery1.11.1.min.js"/>"></script>
    <script src="<c:url value="/js/bootstrap.min.js"/>"></script>
    <link rel="shortcut icon" href="http://www.uefa.com/imgml/favicon/comp/euro2016.ico">
    <link rel="icon" href="http://www.uefa.com/imgml/favicon/comp/euro2016.ico" type="image/x-icon">
</head>
<body>
<script>

</script>
    <div class="blackbackground">

        <nav class="navbar navbar-inverse">
          <div class="container">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header">
              <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
              </button>
              <a class="navbar-brand" href="/"><img class="brand-img"src="img/euro_2016.jpg" alt="EM2016"</img></a>
            </div>

            <!-- Collect the nav links, forms, and other content for toggling -->
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
              <ul class="nav navbar-nav">
                <li ><a href="/yourBet">Ditt Spill</a></li>
                <li ><a href="/matches">Kampoppsett </a></li>
                <li ><a href="/rules">Regler </a></li>
                <li ><a href="/scores">Leaderboard</a></li>
                <!--<li class="dropdown">
                  <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Dropdown <span class="caret"></span></a>
                  <ul class="dropdown-menu">
                    <li><a href="#">Action</a></li>
                  </ul>
                </li>-->
              </ul>
              <ul class="nav navbar-nav navbar-right">
                  <%
                      UserService userService = UserServiceFactory.getUserService();
                      User user = userService.getCurrentUser();
                      if (user != null) {
                          pageContext.setAttribute("user", user);
                      }
                  %>
                <li><a href="<%= userService.createLogoutURL("/") %>">Sign out</a></li>
              </ul>
            </div><!-- /.navbar-collapse -->
          </div><!-- /.container-fluid -->
        </nav>
        <div class="container">
            <div class="jumbotron">
                <div id="content-main" style="margin-top: 50px;">
                    <jsp:include page="${pageToInclude}"/>
                </div>
            </div>
        </div>
    </div>

</body>
</html>
