<#macro compress_single_line>
<#local captured><#nested></#local>
${ captured?replace( "^\\s+|\\s+$|\\n|\\r", " ", "rm" ) }
</#macro>
<@compress_single_line>
<!DOCTYPE html>
<html lang="${lang}">

	<head>
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
</@compress_single_line>