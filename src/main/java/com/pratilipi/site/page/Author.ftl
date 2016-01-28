<!DOCTYPE html>
<html lang="${lang}">

	<head>
		<#include "meta/Head.ftl">
		<link rel='import' href='/elements.${lang}/pratilipi-author-page.html?20160128'>
	</head>

	<body>
		<pratilipi-author-page 
			user-data='${ userJson }'
			author-data='${ authorJson }'
			pratilipi-list='${ publishedPratilipiListJson }'
			filter='${ publishedPratilipiListFilterJson! }'
			cursor='${ publishedPratilipiListCursor! }'></pratilipi-author-page>
	</body>
	
</html>