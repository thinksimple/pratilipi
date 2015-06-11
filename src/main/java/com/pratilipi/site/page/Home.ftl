<!DOCTYPE html>
<html lang="${lang}">
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
		<link rel='import' href='/elements.${lang}/pratilipi-header.html'>
		<link rel='import' href='/elements.${lang}/pratilipi-search-bar.html'>
		<link rel='import' href='/elements.${lang}/pratilipi-navigation.html'>
		<link rel='import' href='/elements.${lang}/pratilipi-rating.html'>
		<link rel='import' href='/elements.${lang}/pratilipi-card.html'>
		<link rel='import' href='/elements.${lang}/pratilipi-card-grid.html'>

		<#-- Custom Stylesheets -->
		<link type="text/css" rel="stylesheet" href="/stylesheets/main.css">
		<link type="text/css" rel="stylesheet" href="/stylesheets/palettes.css">
		<link type="text/css" rel="stylesheet" href="/stylesheets/pratilipi.css">

	</head>
	<body>

		<div class="primary-500">
			<div class="container">
				<pratilipi-header>
					<pratilipi-search-bar/>
				</pratilipi-header>
			</div>
		</div>
		
		<div class="container" style="margin-top:20px">
			<div class="secondary-500 pull-left" style="width:250px">
				<pratilipi-navigation/>
			</div>
			<div class="secondary-500" style="margin-left:270px">
				<div>
					<pratilipi-card-grid grid-title="${ _strings.featured }">
						<#list featuredList as featured>
							<pratilipi-card pratilipi='${ featured }'></pratilipi-card>
						</#list>
					</pratilipi-card-grid>
				</div>
			</div>
		</div>
		
	</body>
</html>
