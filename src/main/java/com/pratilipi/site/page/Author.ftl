<!DOCTYPE html>
<html lang="${lang}">

	<head>
		<#assign mainPage="pratilipi-author-page">
		<#include "meta/Head.ftl">
	</head>

	<body>
		<#include "meta/PolymerDependencies.ftl">
		<pratilipi-author-page 
			user-data='${ userJson }'
			author='${ authorJson }'
			user-author='${ userAuthorJson }'
			pratilipi-types='${ pratilipiTypesJson }'
			navigation-list='${ navigationList }'
			language-map='${ languageMap }'></pratilipi-author-page>

		<#include "meta/Foot.ftl">
	</body>
	
</html>