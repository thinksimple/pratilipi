<!DOCTYPE html>
<html lang="${lang}">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">

		<#-- Page Title, Favicon & Description -->
		<title>${ _strings.home_page_title } &#0171 ${ _strings.pratilipi }</title>		
		<link rel="shortcut icon" type="image/png" href="/theme.pratilipi/favicon.png">
		<meta name="description" content="A platform to discover, read and share your favorite stories, poems and books in a language, device and format of your choice.">

		<#-- Third-Party Library -->
		<#list resourceList as resource>
			${ resource }
		</#list>

		<#-- Polymer 1.0 Custom Elements -->
		<link rel='import' href='/elements.${lang}/pratilipi-user.html'>
		<link rel='import' href='/elements.${lang}/pratilipi-header.html'>
		<link rel='import' href='/elements.${lang}/pratilipi-footer.html'>

		<#-- Custom Stylesheets -->
		<link type="text/css" rel="stylesheet" href="/resources/style.css?20151209">
		
		<#-- Fontawesome style -->
		<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css">
	</head>
	
	<body class="fullbleed layout vertical">
		<paper-header-panel class="flex" mode="waterfall">
			<div class="paper-header">
				<pratilipi-user user='{{ user }}' user-data='${ userJson }'></pratilipi-user>
				<pratilipi-header user='{{ user }}'></pratilipi-header>
			</div>
			<div class="fit" style="margin-top: 5px;">
				<div class="parent-container">
					<div class="container">
						<paper-card>
							<div class="media" style="padding: 20px;">
								<div class="media-left">
									<img src="/stylesheets/PageNotFound.png" alt="Img">
								</div>
					
								<div class="media-body" style="padding-left: 35px;">
									<h4><b>Error 404</b></h4>
									<h2>Page not found.</h2>
									<p>The page you are looking for isn't here.</p>
									<p>You can still search for your favorite content in the search bar on top<br>
									or head over to the home page.</p> <br>
									<a class="pratilipi-light-blue-button" href="http://tamil.pratilipi.com">Home</a>
				    			</div>
							</div>
						</paper-card>
					</div>
				</div>
				<div style="position: absolute; bottom: 0px; width: 100%;">
					<pratilipi-footer></pratilipi-footer>
				</div>
			</div>
		</paper-header-panel>
	</body>
</html>
