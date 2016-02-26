<!DOCTYPE html>
<html lang="${lang}">

	<head>
		<#assign mainPage="pratilipi-author-page">
		<#include "meta/Head.ftl">
		<link rel='import' href='/elements.${lang}/${ mainPage }.html?2016022602'>
	</head>

	<body>
		<pratilipi-author-page 
			user-data='${ userJson }'
			author-data='${ authorJson }'
			pratilipi-list='${ publishedPratilipiListJson }'
			filter='${ publishedPratilipiListFilterJson! }'
			cursor='${ publishedPratilipiListCursor! }'
			pratilipi-types='${ pratilipiTypesJson }'></pratilipi-author-page>
	</body>
	
</html>