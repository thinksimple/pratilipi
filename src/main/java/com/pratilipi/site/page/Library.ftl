<!DOCTYPE html>
<html lang="${lang}">

	<head>
		<#assign mainPage="pratilipi-library-page">
		<#include "meta/Head.ftl">
		<link rel='import' href='/elements.${lang}/${ mainPage }.html?20160315'>
	</head>
	
	<body>
		
		<pratilipi-library-page 
			user-data='${ userJson }'
			heading='My Library'
			pratilipi-list='${ pratilipiListJson! }'
			filter='${ pratilipiListFilterJson! }'
			cursor='${ pratilipiListCursor! }'
			pratilipi-types='${ pratilipiTypesJson }'
			navigation-list='${ navigationList }'></pratilipi-library-page>
	</body>

</html>
