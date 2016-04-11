<!DOCTYPE html>
<html lang="${lang}">

	<head>
		<#-- Page Description -->
		<meta name="description" content="A platform to discover, read and share your favorite stories, poems and books in a language, device and format of your choice.">
		
		<#assign mainPage="pratilipi-blog-page">
		<#include "meta/Head.ftl">
		<link rel='import' href='/elements.${lang}/${ mainPage }.html?20160405'>
	</head>

	<body>
		<pratilipi-blog-page 
			user-data='${ userJson }'
			blog='${ blogJson }'
			pratilipi-list='${ pratilipiListJson }'
			pratilipi-types='${ pratilipiTypesJson }'
			navigation-list='${ navigationList }'></pratilipi-blog-page>
	
    </body>


</html>
