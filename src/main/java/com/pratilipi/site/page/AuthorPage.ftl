<!DOCTYPE html>
<html lang="${lang}">

	<head>
		<#assign mainPage="pratilipi-author-page">
		<#include "meta/Head.ftl">
		<link rel='import' href='/elements.${lang}/pratilipi-author-profile-page.html?20160705'>
	</head>

	<body>
		<pratilipi-author-page 
			user-data='${ userJson }'
			author-data='${ authorJson }'
			user-author-data='${ userAuthorJson }'
			pratilipi-list='${ publishedPratilipiListJson }'
			filter='${ publishedPratilipiListFilterJson! }'
			cursor='${ publishedPratilipiListCursor! }'
			pratilipi-types='${ pratilipiTypesJson }'
			navigation-list='${ navigationList }'></pratilipi-author-page>
	</body>
	
</html>