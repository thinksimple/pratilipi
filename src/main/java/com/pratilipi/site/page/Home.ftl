<!DOCTYPE html>
<html lang="${lang}">

	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">

		<#-- Page Title, Favicon & Description -->
		<title>${ _strings.home_page_title } &#0171; ${ _strings.pratilipi }</title>		
		<link rel="shortcut icon" type="image/png" href="/favicon.png">
		<meta name="description" content="A platform to discover, read and share your favorite stories, poems and books in a language, device and format of your choice.">

		<#-- Third-Party Library -->
		<#list resourceList as resource>
			${ resource }
		</#list>

		<#-- Polymer 1.0 Custom Elements -->
		<link rel='import' href='/elements.${lang}/pratilipi-user.html'>
		<link rel='import' href='/elements.${lang}/pratilipi-header.html'>
		<link rel='import' href='/elements.${lang}/pratilipi-navigation.html'>
		<link rel='import' href='/elements.${lang}/pratilipi-tamil-carousel.html'>
		<link rel='import' href='/elements.${lang}/pratilipi-card-grid.html'>
		<link rel='import' href='/elements.${lang}/pratilipi-footer.html'>

		<#-- Custom Stylesheet -->
		<link type="text/css" rel="stylesheet" href="/resources/style.css?20151218">
	</head>

	<body class="fullbleed layout vertical">
		<template is="dom-bind">
			<paper-header-panel class="flex" mode="waterfall">
  				<div class="paper-header">
  					<pratilipi-user user='{{ user }}' user-data='${ userJson }'></pratilipi-user>
					<pratilipi-header user='{{ user }}'></pratilipi-header>
  				</div>
  				<div class="fit" style="margin-top: 5px;">
  					<div class="parent-container">
						<div class="container">
							<pratilipi-navigation
									class='pull-left hidden-xs hidden-sm'
									></pratilipi-navigation>
							<div style="overflow:hidden">
								<pratilipi-tamil-carousel></pratilipi-tamil-carousel>
								<br class="pratilipi-break"/>
								<pratilipi-card-grid
										heading='${ featuredTitle }'
										pratilipi-list='${ featuredListJson }'
										></pratilipi-card-grid>
							</div>
						</div>
					</div>
					<pratilipi-footer></pratilipi-footer>
  				</div>
			</paper-header-panel>
		</template>
	</body>

</html>
