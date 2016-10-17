<!DOCTYPE html>
<html lang="${lang}">

	<head>
		<#assign mainPage="pratilipi-blog-list-page">
		<#include "meta/Head.ftl">
	</head>

	<body>
		<#include "meta/PolymerDependencies.ftl">
		<pratilipi-blog-list-page 
			user-data='${ userJson }'
			blog-id='${ blogId?c }'
			has-access-to-add=${ hasAccessToAdd?c }
			blogpost-list='${ blogPostListJson }'
			filter='${ blogPostFilterJson }'
			cursor='${ blogPostListCursor }'
			pratilipi-types='${ pratilipiTypesJson }'
			navigation-list='${ navigationList }'
			language-map='${ languageMap }'></pratilipi-blog-list-page>

		<#include "meta/Foot.ftl">
    </body>

</html>