<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<!-- CSS ================================================== -->
	<link rel="stylesheet" href="staticResources/denied/css/bootstrap.css" type="text/css" media="screen">
	<link rel="stylesheet" href="staticResources/denied/css/bootstrap-responsive.css" type="text/css" media="screen">
	<link rel="stylesheet" href="staticResources/denied/css/layout.css" type="text/css" media="screen">
	<link rel="stylesheet" href="staticResources/denied/css/background.css" type="text/css" media="screen">
<title>Access Denied</title>
<%
	String bgStyle = (String) request.getAttribute("styleBackground");
	String mainMessage = (String) request.getAttribute("MainMessage");
	String returnMessage = (String) request.getAttribute("ReturnMessage");
	String aboutUs = (String) request.getAttribute("AboutUs");
	String service = (String) request.getAttribute("Service");
	String contactUs = (String) request.getAttribute("ContactUs");
%>
</head>
<body>
<div class="<%= bgStyle %>"></div>
<div class="container">
	<div class="row">
		<header class="span12">
			<div class="logo light">
				<p>Oops!</p>
				<h1><%= mainMessage %></h1>
				<h4><%= returnMessage %></h4>
			</div>
		</header>
	</div>
	<div class="row">
		<nav class="span12">
			<div class="navigation light">
				<a href="login">Login</a> <div class="slash">/</div>
				<a href="#"><%= aboutUs%></a> <div class="slash">/</div>
				<a href="#"><%= service%></a> <div class="slash">/</div>
				<a href="#"><%= contactUs%></a>
			</div>
		</nav>
	</div>
	<div class="row">
		<div class="span6">
			<div class="socmed">
				<div class="twitter"><a href="#">Twitter</a></div>
				<div class="facebook"><a href="#">Facebook</a></div>
				<div class="tumblr"><a href="#">Tumblr</a></div>
				<div class="dribbble"><a href="#">Dribbble</a></div>
				<div class="linkedin"><a href="#">LinkedIn</a></div>
				<div class="rss"><a href="#">Rss</a></div>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="span3">
			<div class="copy light">Copyright &copy; 2014 <a href="http://www.flygow.com">Flygow!</a></div>
		</div>
	</div>
</div>
</body>
</html>