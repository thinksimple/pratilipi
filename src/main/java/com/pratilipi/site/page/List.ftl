<!DOCTYPE html>
<html lang="${lang}">

	<head>
		<#include "meta/Head.ftl">
		<link rel='import' href='/elements.${lang}/pratilipi-list-page.html?20160213'>
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
