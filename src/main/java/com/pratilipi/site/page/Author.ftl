<!DOCTYPE html>
<html lang="${lang}">

	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">

		<#-- Page Title & Favicon -->
		<title>${ author.name } / ${ author.nameEn } &#0171; ${ _strings.pratilipi }</title>
		<link rel="shortcut icon" type="image/png" href="/favicon.png">

		<#-- Third-Party Library -->
		<#list resourceList as resource>
			${ resource }
		</#list>

		<#-- Polymer 1.0 Custom Elements -->
		<link rel='import' href='/elements.${lang}/pratilipi-author-page.html'>
		<link rel='import' href='/elements.${lang}/pratilipi-user.html'>
		<link rel='import' href='/elements.${lang}/pratilipi-header.html'>
		<link rel='import' href='/elements.${lang}/pratilipi-navigation.html'>
		<link rel='import' href='/elements.${lang}/pratilipi-author.html'>
		<link rel='import' href='/elements.${lang}/pratilipi-card-grid.html'>
		<link rel='import' href='/elements.${lang}/pratilipi-footer.html'>

		<#-- Custom Stylesheet -->
		<link type="text/css" rel="stylesheet" href="/resources/style.css">
	</head>

	<body class="fullbleed layout vertical">
		<template is="dom-bind">
			<pratilipi-author-page user-data='${ userJson }'
				author-data='${ authorJson }'
				pratilipi-list='${ publishedPratilipiListJson }'
				filter='${ publishedPratilipiListFilterJson! }'
				cursor='${ publishedPratilipiListCursor! }'></pratilipi-author-page>
		</template>
	</body>
	
</html>