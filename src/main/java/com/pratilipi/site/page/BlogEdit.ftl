<#compress>
<!DOCTYPE html>
<html lang="${lang}">

	<head>
		<#include "meta/Head.ftl">
		<link rel='import' href='/elements.${lang}/pratilipi-edit-blog.html'>
	</head>

	<body>
		<pratilipi-edit-blog blog='<#if blogPostJson??>${ blogPostJson }<#else>{}</#if>'></pratilipi-edit-blog>
		<#include "meta/Font.ftl">
    </body>

</html>
</#compress>