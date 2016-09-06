<#compress>
<!DOCTYPE html>
<html lang="${lang}">

	<head>
		<#-- Page Description -->
		<meta name="description" content="A platform to discover, read and share your favorite stories, poems and books in a language, device and format of your choice.">
		<#assign mainPage="pratilipi-home-page">
		<#include "meta/Head.ftl">
	</head>

	<body>
		<pratilipi-home-page 
			user-data='${ userJson }'
			sections-list='${ sectionsJson }'
			pratilipi-types='${ pratilipiTypesJson }'
			navigation-list='${ navigationList }'
			language-map='${ languageMap }'></pratilipi-home-page>
			
		<#include "meta/Foot.ftl">
	</body>

</html>
</#compress>