<!DOCTYPE html>
<html lang="${lang}">

	<head>
		<#include "meta/Head.ftl">
		<link rel='import' href='/elements.${lang}/pratilipi-edit-blog.html?2'>
	</head>

	<body>
		<#include "meta/PolymerDependencies.ftl">
		<pratilipi-edit-blog 
			blog='<#if blogPostJson??>${ blogPostJson }<#else>{}</#if>'
			blog-id="${ blogId?c }"></pratilipi-edit-blog>
		<#include "meta/Font.ftl">
    </body>

</html>