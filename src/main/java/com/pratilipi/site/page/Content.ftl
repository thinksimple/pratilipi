<#compress>
<!DOCTYPE html>
<html lang="${lang}">

	<head>
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