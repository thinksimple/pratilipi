<!DOCTYPE html>
<html lang="${lang}">

	<head>
		<#-- Page Description -->
		<meta name="description" content="A platform to discover, read and share your favorite stories, poems and books in a language, device and format of your choice.">
		
		<#assign mainPage="pratilipi-blog-page">
		<#include "meta/Head.ftl">
		<link rel='import' href='/elements.${lang}/pratilipi-blog-page.html?20160806'>
	</head>

	<body>
		<pratilipi-blog-page 
			user-data='${ userJson }'
			blogpost='${ blogPostJson }'
			pratilipi-types='${ pratilipiTypesJson }'
			navigation-list='${ navigationList }'
			language-map='${ languageMap }'></pratilipi-blog-page>
		<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.6/angular.min.js"></script>	
    </body>
</html>
