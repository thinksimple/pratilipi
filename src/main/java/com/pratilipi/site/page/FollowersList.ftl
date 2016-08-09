<!DOCTYPE html>
<html lang="${lang}">

	<head>
		<#assign mainPage="pratilipi-followers-list-page">
		<#include "meta/Head.ftl">
		<link rel='import' href='/elements.${lang}/pratilipi-followers-list-page.html?20160809'>
	</head>

	<body>
		<pratilipi-followers-list-page 
			user-data='${ userJson }'
			author='${ authorJson! }'
			followers-object='${ followersObjectJson! }'
			pratilipi-types='${ pratilipiTypesJson }'
			navigation-list='${ navigationList }'
			language-map='${ languageMap }'></pratilipi-followers-list-page>
	</body>
	
</html>