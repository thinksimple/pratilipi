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

		<#-- Custom Stylesheet -->
		<link type="text/css" rel="stylesheet" href="/resources/style-basic.css">
	</head>

	<body>

		<div class="container">
			<#include "../element/pratilipi-header.ftl">
			<#include "../element/pratilipi-author.ftl">
			<#list pratilipiList as pratilipi>
				<#include "../element/pratilipi-pratilipi-card.ftl">
			</#list>
			<#include "../element/pratilipi-navigation.ftl">
			<#include "../element/pratilipi-footer.ftl">
		</div>
		
	</body>
	
</html>