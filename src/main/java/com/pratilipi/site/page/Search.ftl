<!DOCTYPE html>
<html lang="${lang}">

	<head>
		<#assign mainPage="pratilipi-search-page">
		<#include "meta/Head.ftl">
		<link rel='import' href='/elements.${lang}/${ mainPage }.html?20160218'>
	</head>

	<body>
		<pratilipi-search-page 
			user-data='${ userJson }'
			pratilipi-list='${ pratilipiListJson }'
			search-query='${ pratilipiListSearchQuery! }'
			filter='${ pratilipiListFilterJson }'
			cursor='${ pratilipiListCursor! }'
			pratilipi-types='${ pratilipiTypesJson }'></pratilipi-search-page>
	</body>
</html>
