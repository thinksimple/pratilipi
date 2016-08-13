<!DOCTYPE html>
<html lang="${lang}">

	<head>
		<#-- Page Description -->
		<meta name="description" content="A platform to discover, read and share your favorite stories, poems and books in a language, device and format of your choice.">
		
		<#assign mainPage="pratilipi-event-page">
		<#include "meta/Head.ftl">
		<link rel='import' href='/elements.${lang}/pratilipi-event-page.html?20160813'>
	</head>

	<body>
		<pratilipi-event-page 
			user-data='${ userJson }'
			event='${ eventJson }'
			pratilipi-list-object='${ pratilipiListObjectJson }'
			pratilipi-types='${ pratilipiTypesJson }'
			navigation-list='${ navigationList }'
			language-map='${ languageMap }'></pratilipi-event-page>
	
    </body>


</html>
