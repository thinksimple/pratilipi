<!DOCTYPE html>
<html lang="${lang}">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">

		<#-- Page Title & Favicon -->
		<title>${ title } &#0171 ${ _strings.pratilipi }</title>
		<link rel="shortcut icon" type="image/png" href="/theme.pratilipi/favicon.png">

		<#-- Third-Party Library -->
		<#list resourceList as resource>
			${ resource }
		</#list>

		<#-- Polymer 1.0 Custom Elements -->
		<link rel='import' href='/elements.${lang}/pratilipi-header.html'>
		<link rel='import' href='/elements.${lang}/pratilipi-search-bar.html'>
		<link rel='import' href='/elements.${lang}/pratilipi-navigation.html'>

		<link rel='import' href='/elements.${lang}/pratilipi-card-grid.html'>
		<link rel='import' href='/elements.${lang}/pratilipi-card.html'>
		<link rel='import' href='/elements.${lang}/pratilipi-rating.html'>

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
			<div class="secondary-500 pull-left" style="width:200px">
				<pratilipi-navigation/>
			</div>
			<div class="secondary-500" style="margin-left:210px">
				<div>
					<pratilipi-card-grid grid-title="${ title }">
						<div>
							<#list pratilipiJsonList as pratilipiJson>
								<pratilipi-card pratilipi='${ pratilipiJson }'></pratilipi-card>
							</#list>
						</div>
					</pratilipi-card-grid>
				</div>
			</div>
		</div>
		
	</body>
</html>
