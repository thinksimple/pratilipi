<!DOCTYPE html>
<html lang="${lang}">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">

		<#-- Page Title & Favicon -->
		<title>${ pratilipi.title } / ${ pratilipi.titleEn } &#0171 ${ pratilipi.author.name } / ${ pratilipi.author.nameEn } &#0171 ${ _strings.pratilipi }</title>		
		<link rel="shortcut icon" type="image/png" href="/theme.pratilipi/favicon.png">

		<#-- Third-Party Library -->
		<#list resourceList as resource>
			${ resource }
		</#list>

		<#-- Polymer 1.0 Custom Elements -->
		<link rel='import' href='/elements.${lang}/pratilipi-header.html'>
		<link rel='import' href='/elements.${lang}/pratilipi-navigation.html'>

		<link rel='import' href='/elements.${lang}/pratilipi-pratilipi.html'>
		<link rel='import' href='/elements.${lang}/pratilipi-rating.html'>
		<link rel='import' href='/elements.${lang}/pratilipi-user-rating.html'>
		<link rel='import' href='/elements.${lang}/pratilipi-social.html'>
		<link rel='import' href='/elements.${lang}/pratilipi-carousel.html'>
		<link rel='import' href='/elements.${lang}/pratilipi-card-mini.html'>
		<link rel='import' href='/elements.${lang}/pratilipi-review.html'>

		<#-- Custom Stylesheets -->
		<link type="text/css" rel="stylesheet" href="/stylesheets/main.css">
		<link type="text/css" rel="stylesheet" href="/stylesheets/palettes.css">
		<link type="text/css" rel="stylesheet" href="/stylesheets/pratilipi.css">
	</head>
	<body>

		<pratilipi-header></pratilipi-header>
		
		<div class="container" style="margin-top:10px">
			<pratilipi-navigation
					class='pull-left hidden-xs hidden-sm'
					></pratilipi-navigation>
			<div style="overflow:hidden">
				<pratilipi-pratilipi pratilipi='${ pratilipiJson }'></pratilipi-pratilipi>
				<pratilipi-carousel pratilipi-list='${ recommendedJsonList }'></pratilipi-carousel>
				<pratilipi-review></pratilipi-review>
			</div>
		</div>
		
	</body>
</html>
