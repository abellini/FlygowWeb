<%@ page
    language="java" 
    contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" 
	import="java.util.List"
	import="java.util.ArrayList"
	import="java.util.Collection"
	import="java.util.Set"
	import="br.com.flygonow.entities.*"
%>

<html>
<head>
    <meta charset="UTF-8">
    <title>Flygow - Go Fly Now!</title>
    <!-- <x-compile> -->
        <!-- <x-bootstrap> Ext theme default
            <link rel="stylesheet" href="serverApp/bootstrap.css">
            <script src="serverApp/ext/ext-all.js"></script>
            <script src="serverApp/bootstrap.js"></script>
        <!-- </x-bootstrap> -->
		<link rel="stylesheet" type="text/css" href="staticResources/extjs/resources/css/ext-all-neptune.css">
        <script src="staticResources/extjs/ext-all.js"></script>
        <script type="text/javascript" src="staticResources/extjs/ext-theme-neptune.js"></script>

		<!--NOTIFICACOES-->
		<script type="text/javascript" src="staticResources/js/notification.js"></script>

        <!--WEBSOCKET-->
        <script type="text/javascript" src="staticResources/js/sockjs.js"></script>
        <script type="text/javascript" src="staticResources/js/stomp.js"></script>

		<! JQUERY -->
		<script type="text/javascript" src="staticResources/js/jquery.min.js"></script>

        <!-- CHARTS -->
        <script type="text/javascript" src="staticResources/js/highcharts.js"></script>

		<%
			if (request.getLocale().getLanguage().matches("^es.*")) {

		%><script type="text/javascript" src="serverApp/language/lang_ES.js"></script>
		<%
			} else if (request.getLocale().getLanguage().matches("^pt.*")) {

		%><script type="text/javascript" src="serverApp/language/lang_PT.js"></script>
		<%
			} else {

		%><script type="text/javascript" src="serverApp/language/lang_EN.js"></script>
		<%
			}
			Attendant user = (Attendant) request.getAttribute("LoggedUserInfo");
			String wallpaperImage = (String) request.getAttribute("Wallpaper");
		%>
		<script>
			var loggedUser = {
				name: '',
				modules: [],
				wallpaperImageName: ''
			};
		<%
			Collection<Role> roles = user.getRoles();
			Collection<Long> idsAllModules = new ArrayList<Long>();
			for(Role role : roles){
				Collection<Module> modulesFromRole = role.getModules();
				if(!modulesFromRole.isEmpty()){
					for(Module module : modulesFromRole){
						if(module.getActive()){
							if(!idsAllModules.contains(module.getId())){
								idsAllModules.add(module.getId());
								%>
								loggedUser.modules.push({
									name: '<%= module.getName() %>',
									iconCls: '<%= module.getIconCls() %>',
									module: '<% out.print(module.getName() + "-win"); %>'	
								});
						<%	}
						}
					}
				}
			}
		%>	
			
			loggedUser.name = '<%= user.getName() %>';
			loggedUser.wallpaperImageName = '<%= wallpaperImage %>';
		</script>
        <script type="text/javascript" src="serverApp/app/app.js"></script>
    <!-- </x-compile> -->
    <link rel="stylesheet" href="serverApp/resources/css/desktop.css">
    <link rel="stylesheet" href="serverApp/resources/css/desktop-icons.css">
    <link rel="stylesheet" href="serverApp/resources/css/grid-mouse-over.css">
    <link rel="stylesheet" href="serverApp/resources/css/dataview.css">
    <link rel="stylesheet" href="staticResources/css/app-icons.css">
    <link rel="stylesheet" href="staticResources/css/notification.css">
</head>
<body>
</body>
</html>