<!DOCTYPE html>
<html lang="${lang}">

	<head>
		<#assign mainPage="pratilipi-author-page">
		<#include "meta/Head.ftl">
		<link rel='import' href='/elements.${lang}/pratilipi-author-profile-page.html?20160707'>
	</head>

	<body>
		<pratilipi-author-page 
			user-data='${ userJson }'
			author-data='${ authorJson }'
			user-author-data='${ userAuthorJson }'
			published-pratilipi-list='${ publishedPratilipiListObjectJson }'
			pratilipi-types='${ pratilipiTypesJson }'
			navigation-list='${ navigationList }'></pratilipi-author-page>
	</body>
	
</html>