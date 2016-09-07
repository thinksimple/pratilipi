<#compress>
<!DOCTYPE html>
<html lang="${lang}">

	<head>
		<#-- Page Description -->
		<meta name="description" content="A platform to discover, read and share your favorite stories, poems and books in a language, device and format of your choice.">
		<#assign mainPage="pratilipi-content-page">
		<#include "meta/Head.ftl">
	</head>

	<body>
		<pratilipi-content-page 
			user-data='${ userJson }'
			pratilipi-types='${ pratilipiTypesJson }'
			navigation-list='${ navigationList }'
			language-map='${ languageMap }'></pratilipi-content-page>
			
		<#include "meta/Foot.ftl">
	</body>

</html>
</#compress>