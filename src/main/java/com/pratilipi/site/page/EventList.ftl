<#compress>
<!DOCTYPE html>
<html lang="${lang}">

	<head>
		<#-- Page Description -->
		<meta name="description" content="A platform to discover, read and share your favorite stories, poems and books in a language, device and format of your choice.">
		
		<#assign mainPage="pratilipi-event-list-page">
		<#include "meta/Head.ftl">
	</head>

	<body>
		<pratilipi-event-list-page 
			user-data='${ userJson }'
			event-list='${ eventListJson }'
			has-access-to-add=${ hasAccessToAdd?c }
			pratilipi-types='${ pratilipiTypesJson }'
			navigation-list='${ navigationList }'
			language-map='${ languageMap }'></pratilipi-event-list-page>

		<#include "meta/Foot.ftl">

    </body>

</html>
</#compress>