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
		<link rel='import' href='/elements.${lang}/pratilipi-header.html'>
		<link rel='import' href='/elements.${lang}/pratilipi-search-bar.html'>

		<#-- Custom Stylesheets -->
		<link type="text/css" rel="stylesheet" href="/stylesheets/main.css">
		<link type="text/css" rel="stylesheet" href="/stylesheets/palettes.css">
		
		<#-- Fontawesome style -->
		<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css">
	</head>
	<body>

		<div class="primary-500">
			<div class="container">
				<pratilipi-header>
					<pratilipi-search-bar/>
				</pratilipi-header>
			</div>
		</div>
		
		<div class="container" style="margin-top: 20px; ">
			<div class="secondary-500" style="margin-left: 150px;">
				<div class="media" >
					<div class="media-left">
						<i class="fa fa-ban fa-5x"></i>
					</div>
		
					<div class="media-body">
						<h4><b>Error!</b></h4>
						<h1><strong>Not authorized.</strong></h1>
						<p><b>Sorry! We can't allow you to view this page.</b></p>
						<p><b>You can still search for your favourite content in the search bar on top<br>
						or head over to teh home page</b></p> <br>
						<a class="btn btn-success" href="http://tamil.pratilipi.com">Home
			            </a>
	    			</div>
				</div>
			</div>
		</div>	
		
	</body>
</html>
