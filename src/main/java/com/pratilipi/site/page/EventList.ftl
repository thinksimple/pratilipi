<!DOCTYPE html>
<html lang="${lang}">

	<head>
		<#-- Page Description -->
		<meta name="description" content="A platform to discover, read and share your favorite stories, poems and books in a language, device and format of your choice.">
		
		<#assign mainPage="pratilipi-event-list-page">
		<#include "meta/Head.ftl">
		<link rel='import' href='/elements.${lang}/pratilipi-event-list-page.html?20160722'>
	</head>

	<body>
		<pratilipi-event-list-page 
			user-data='${ userJson }'
			event-list='${ eventListJson }'
			has-access-to-add=${ hasAccessToAdd?c }
			pratilipi-types='${ pratilipiTypesJson }'
			navigation-list='${ navigationList }'></pratilipi-event-list-page>
    </body>

</html>
