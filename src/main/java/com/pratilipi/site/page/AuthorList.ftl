<!DOCTYPE html>
<html lang="${lang}">

	<head>
		<#assign mainPage="pratilipi-author-list-page">
		<#include "meta/Head.ftl">
		<link rel='import' href='/elements.${lang}/${ mainPage }.html?2016031603'>
	</head>

	<body>
		<pratilipi-author-list-page 
			user-data='${ userJson }'
			author-list='${ authorListJson }'
			filter='${ authorListFilterJson! }'
			cursor='${ authorListCursor! }'
			pratilipi-types='${ pratilipiTypesJson }'></pratilipi-author-list-page>
	</body>
	
</html>