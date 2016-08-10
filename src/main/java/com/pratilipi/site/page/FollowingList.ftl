<!DOCTYPE html>
<html lang="${lang}">

	<head>
		<#assign mainPage="pratilipi-following-list-page">
		<#include "meta/Head.ftl">
		<link rel='import' href='/elements.${lang}/pratilipi-following-list-page.html?20160810'>
	</head>

	<body>
		<pratilipi-following-list-page 
			user-data='${ userJson }'
			author='${ authorJson! }'
			following-object='${ followingObjectJson! }'
			pratilipi-types='${ pratilipiTypesJson }'
			navigation-list='${ navigationList }'
			language-map='${ languageMap }'></pratilipi-following-list-page>
	</body>
	
</html>