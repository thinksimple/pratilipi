<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">

		<#-- Page Title, Favicon & Description -->
		<title>${ _strings.home_page_title } &#0187 ${ _strings.pratilipi }</title>		
		<link rel="shortcut icon" type="image/png" href="/theme.pratilipi/favicon.png">
		<meta name="description" content="A platform to discover, read and share your favorite stories, poems and books in a language, device and format of your choice.">

		<#-- jQuery 2.1.3 -->
		<script src='//jquery.3p.pratilipi.net/jquery-2.1.3/jquery-2.1.3.min.js'></script>

		<#-- Bootstrap 3.3.4 -->
		<script src='//bootstrap.3p.pratilipi.net/bootstrap-3.3.4/js/bootstrap.min.js'></script>
		<link rel='stylesheet' href='//bootstrap.3p.pratilipi.net/bootstrap-3.3.4/css/bootstrap.min.css'>
		
		<#-- Polymer 1.0 -->
		<script src='//polymer.3p.pratilipi.net/polymer-1.0/webcomponentsjs/webcomponents-lite.js'></script>
		<link rel='import' href='//polymer.3p.pratilipi.net/polymer-1.0/polymer/polymer.html'>

		<#-- Polymer 1.0 Custom Elements -->
		<link rel='import' href='/elements.en/pratilipi-header.html'>
		<link rel='import' href='/elements.en/pratilipi-search-bar.html'>
		
		<#-- Custom Stylesheets -->
		<link type="text/css" rel="stylesheet" href="/stylesheets/main.css">
		<link type="text/css" rel="stylesheet" href="/stylesheets/palettes.css">

	</head>
	<body>

		<div class="primary-500">
			<pratilipi-header>
				<pratilipi-search-bar/>
			</pratilipi-header>
		</div>

	</body>
</html>
