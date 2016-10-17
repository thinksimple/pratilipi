<!DOCTYPE html>
<html lang="${lang}">

	<head>
		<#assign mainPage="pratilipi-blog-page">
		<#include "meta/Head.ftl">
	</head>

	<body>
		<#include "meta/PolymerDependencies.ftl">
		<pratilipi-blog-page 
			user-data='${ userJson }'
			blogpost='${ blogPostJson }'
			pratilipi-types='${ pratilipiTypesJson }'
			navigation-list='${ navigationList }'
			language-map='${ languageMap }'></pratilipi-blog-page>

		<#include "meta/Foot.ftl">
    </body>

</html>