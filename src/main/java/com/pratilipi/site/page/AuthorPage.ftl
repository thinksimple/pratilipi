<!DOCTYPE html>
<html lang="${lang}">

	<head>
		<#assign mainPage="pratilipi-author-page">
		<#include "meta/Head.ftl">
		<link rel='import' href='/elements.${lang}/pratilipi-author-profile-page.html?2016071503'>
	</head>

	<body>
		<pratilipi-author-page 
			user-data='${ userJson }'
			author='${ authorJson }'
			user-author='${ userAuthorJson }'
			published-pratilipi-list='${ publishedPratilipiListObjectJson }'
			following-list='${ followingListJson }'
			followers-list='${ followersListJson }'
			pratilipi-types='${ pratilipiTypesJson }'
			navigation-list='${ navigationList }'></pratilipi-author-page>
	</body>
	
</html>