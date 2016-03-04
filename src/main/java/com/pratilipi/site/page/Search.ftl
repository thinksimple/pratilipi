<!DOCTYPE html>
<html lang="${lang}">

	<head>
		<#assign mainPage="pratilipi-search-page">
		<#include "meta/Head.ftl">
		<link rel='import' href='/elements.${lang}/${ mainPage }.html?201603'>
	</head>

	<body>
		<pratilipi-search-page 
			user-data='${ userJson }'
			pratilipi-list='${ pratilipiListJson }'
			search-query='${ pratilipiListSearchQuery! }'
			filter='${ pratilipiListFilterJson }'
			cursor='${ pratilipiListCursor! }'
			pratilipi-types='${ pratilipiTypesJson }'
			navigation-list='${ navigationList }'></pratilipi-search-page>
	</body>
</html>
