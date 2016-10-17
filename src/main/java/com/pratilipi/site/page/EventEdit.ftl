<!DOCTYPE html>
<html lang="${lang}">

	<head>
		<#include "meta/Head.ftl">
		<link rel='import' href='/elements.${lang}/pratilipi-edit-event.html'>
	</head>

	<body>
		<#include "meta/PolymerDependencies.ftl">
		<pratilipi-edit-event event='<#if eventJson??>${ eventJson }<#else>{}</#if>'></pratilipi-edit-event>
		<#include "meta/Font.ftl">
    </body>

</html>