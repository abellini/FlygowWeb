<!DOCTYPE html>
<html lang="en">
    <head>
		<meta charset="UTF-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> 
		<meta name="viewport" content="width=device-width, initial-scale=1.0"> 
		<title>Login</title>
	<link rel="stylesheet" type="text/css" href="staticResources/css/login_style.css" />
	<script src="staticResources/js/modernizr.custom.63321.js"></script>
	<!--[if lte IE 7]><style>.main{display:none;} .support-note .note-ie{display:block;}</style><![endif]-->
	<style>
		@import url(http://fonts.googleapis.com/css?family=Ubuntu:400,700);
		body {
			background: #563c55 url(staticResources/images/blurred.jpg) no-repeat center top;
			-webkit-background-size: cover;
			-moz-background-size: cover;
			background-size: cover;
		}
		.container > header h1,
		.container > header h2 {
			color: #fff;
			text-shadow: 0 1px 1px rgba(0,0,0,0.7);
		}
	</style>
</head>
<body>
<div class="container">
	<section class="main">
		<form class="form-3" action="j_spring_security_check" method="POST">
			<p class="clearfix">
				<label for="login">Username</label>
				<input type="text" name="j_username" required="required" id="login" placeholder="Username">
			</p>
			<p class="clearfix">
				<label for="password">Password</label>
				<input type="password" name="j_password" required="required" id="password" placeholder="Password"> 
			</p>
			<p class="clearfix">
				<input type="checkbox" name="remember" id="remember">
				<label for="remember">Remember me</label>
			</p>
			<p class="clearfix">
				<input type="submit" name="submit" value="Sign in">
			</p>
		</form>
	</section>
</div>
</body>
</html>