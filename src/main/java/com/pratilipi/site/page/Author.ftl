<!DOCTYPE html>
<html lang="${lang}">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">

		<#-- Page Title & Favicon -->
		<title>${ author.name } / ${ author.nameEn } &#0171 ${ _strings.pratilipi }</title>		
		<link rel="shortcut icon" type="image/png" href="/theme.pratilipi/favicon.png">

		<#-- Third-Party Library -->
		<#list resourceList as resource>
			${ resource }
		</#list>

		<#-- Polymer 1.0 Custom Elements -->
		<link rel='import' href='/elements.${lang}/pratilipi-header.html'>
		<link rel='import' href='/elements.${lang}/pratilipi-search-bar.html'>
		<link rel='import' href='/elements.${lang}/pratilipi-navigation.html'>

		<link rel='import' href='/elements.${lang}/pratilipi-author.html'>
		<link rel='import' href='/elements.${lang}/pratilipi-social.html'>

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
		
		<div class="container" style="margin-top:10px">
			<pratilipi-navigation class="pull-left hidden-xs hidden-sm"></pratilipi-navigation>
			<pratilipi-author author='${ authorJson }'></pratilipi-author>
		</div>
		
	</body>
</html>
