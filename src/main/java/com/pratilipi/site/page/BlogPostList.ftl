<!DOCTYPE html>
<html lang="${lang}">

	<head>
		<#-- Page Description -->
		<meta name="description" content="A platform to discover, read and share your favorite stories, poems and books in a language, device and format of your choice.">
		
		<#assign mainPage="pratilipi-blog-list-page">
		<#include "meta/Head.ftl">
		<link rel='import' href='/elements.${lang}/${ mainPage }.html?20160430'>
	</head>

	<body>
		<pratilipi-blog-list-page 
			user-data='${ userJson }'
			blogpost-list='${ blogPostListJson }'
			filter='${ blogPostFilterJson }'
			cursor='${ blogPostListCursor }'
			pratilipi-types='${ pratilipiTypesJson }'
			navigation-list='${ navigationList }'></pratilipi-blog-list-page>
    </body>

</html>
