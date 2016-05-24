<!DOCTYPE html>
<html lang="${lang}">

	<head>
		<#assign mainPage="pratilipi-library-page">
		<#include "meta/Head.ftl">
		<link rel='import' href='/elements.${lang}/pratilipi-library-page.html?20160524'>
	</head>
	
	<body>
		
		<pratilipi-library-page 
			user-data='${ userJson }'
			pratilipi-list='${ pratilipiListJson! }'
			cursor='${ pratilipiListCursor! }'
			pratilipi-types='${ pratilipiTypesJson }'
			navigation-list='${ navigationList }'></pratilipi-library-page>
	</body>

</html>
