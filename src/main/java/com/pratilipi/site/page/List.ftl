<!DOCTYPE html>
<html lang="${lang}">

	<head>
		<#assign mainPage="pratilipi-list-page">
		<#include "meta/Head.ftl">
		<link rel='import' href='/elements.${lang}/${ mainPage }.html?20160218'>
	</head>
	
	<body>
		<pratilipi-list-page 
			user-data='${ userJson }'
			heading='${ pratilipiListTitle }'
			pratilipi-list='${ pratilipiListJson }'
			filter='${ pratilipiListFilterJson! }'
			cursor='${ pratilipiListCursor! }'
			pratilipi-types='${ pratilipiTypesJson }'></pratilipi-list-page>
	</body>

</html>
