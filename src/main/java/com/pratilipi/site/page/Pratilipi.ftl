<!DOCTYPE html>
<html lang="${lang}">

	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">

		<#-- Page Title & Favicon -->
		<title>${ pratilipi.title } <#if pratilipi.titleEn??>/ ${ pratilipi.titleEn }</#if> &#0171; <#if pratilipi.author.name??>${ pratilipi.author.name } / </#if>${ pratilipi.author.nameEn } &#0171; ${ _strings.pratilipi }</title>		
		<link rel="shortcut icon" type="image/png" href="/favicon.png">

		<#-- Third-Party Library -->
		<#list resourceList as resource>
			${ resource }
		</#list>

		<#-- Polymer 1.0 Custom Elements -->
		<link rel='import' href='/elements.${lang}/pratilipi-pratilipi-page.html'>
		<link rel='import' href='/elements.${lang}/pratilipi-user.html'>
		<link rel='import' href='/elements.${lang}/pratilipi-header.html'>
		<link rel='import' href='/elements.${lang}/pratilipi-userpratilipi.html'>
		<link rel='import' href='/elements.${lang}/pratilipi-navigation.html'>
		<link rel='import' href='/elements.${lang}/pratilipi-pratilipi.html'>
		<link rel='import' href='/elements.${lang}/pratilipi-review-list.html'>
		<link rel='import' href='/elements.${lang}/pratilipi-footer.html'>
		

		<#-- Custom Stylesheet -->
		<link type="text/css" rel="stylesheet" href="/resources/style.css?20151218">
	</head>
	
	<body class="fullbleed layout vertical">
		<template is="dom-bind">
			<pratilipi-pratilipi-page user-data='${ userJson }'
					pratilipi-id='${ pratilipi.getId()?c }'
					userpratilipi-data='${ userpratilipiJson! }'
					pratilipi-data='${ pratilipiJson }'
					
					review-list='${ reviewListJson }'
					cursor='${ reviewListCursor! }'></pratilipi-pratilipi-page>
		</template>
	</body>
	
</html>
