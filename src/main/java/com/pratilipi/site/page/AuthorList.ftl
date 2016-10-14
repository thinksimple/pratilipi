<!DOCTYPE html>
<html lang="${lang}">

	<head>
		<#assign mainPage="pratilipi-author-list-page">
		<#include "meta/Head.ftl">
		<link rel='import' href='/elements.${lang}/pratilipi-author-list-page.html?1'>
	</head>

	<body>
		<pratilipi-author-list-page 
			user-data='${ userJson }'
			author-list='${ authorListJson }'
			filter='${ authorListFilterJson! }'
			cursor='${ authorListCursor! }'
			pratilipi-types='${ pratilipiTypesJson }'
			language-map='${ languageMap }'></pratilipi-author-list-page>

			<#include "meta/Foot.ftl">
	</body>
	
</html>