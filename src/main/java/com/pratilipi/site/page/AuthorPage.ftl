<!DOCTYPE html>
<html lang="${lang}">

	<head>
		<#assign mainPage="pratilipi-author-page">
		<#include "meta/Head.ftl">
		<link rel='import' href='/elements.${lang}/pratilipi-author-profile-page.html?201606'>
	</head>

	<body>
		<pratilipi-author-page 
			user-data='${ userJson }'
			author-data='${ authorJson }'
			is-following=${ isFollowing?c }
			pratilipi-list='${ publishedPratilipiListJson }'
			filter='${ publishedPratilipiListFilterJson! }'
			cursor='${ publishedPratilipiListCursor! }'
			pratilipi-types='${ pratilipiTypesJson }'
			navigation-list='${ navigationList }'></pratilipi-author-page>
	</body>
	
</html>