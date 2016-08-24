<!DOCTYPE html>
<html lang="${lang}">

	<head>
		<#assign mainPage="pratilipi-list-page">
		<#include "meta/Head.ftl">
	</head>
	
	<body>
		<pratilipi-list-page 
			user-data='${ userJson }'
			heading='${ pratilipiListTitle }'
			pratilipi-list='${ pratilipiListJson! }'
			filter='${ pratilipiListFilterJson! }'
			cursor='${ pratilipiListCursor! }'
			pratilipi-types='${ pratilipiTypesJson }'
			navigation-list='${ navigationList }'
			language-map='${ languageMap }'></pratilipi-list-page>
	</body>

</html>
