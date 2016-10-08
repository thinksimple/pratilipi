<#compress>
<!DOCTYPE html>
<html lang="${lang}">

	<head>
		<link rel='import' href='/elements.${lang}/pratilipi-author-list-page.html?201608'>
		<#assign mainPage="pratilipi-author-list-page">
		<#include "meta/Head.ftl">
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
</#compress>