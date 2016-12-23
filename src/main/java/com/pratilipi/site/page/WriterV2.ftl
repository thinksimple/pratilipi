<!DOCTYPE html>
<html lang="${ lang }">
	<head>
		<#include "meta/Head.ftl"> 
	</head>
	<body>

		<#assign hasAccess = true>

		<#if user.isGuest() >
			<#assign hasAccess = false>
		<#elseif !pratilipiId?? >
			<#assign hasAccess = false>
		<#elseif !pratilipi.hasAccessToUpdate() >
			<#assign hasAccess = false>
		</#if>


		<#if hasAccess>
			<#include "../element/standard/writer-panel-tinymce/index.html">
		<#else>
			<script>
				$( document ).ready(function() {
				    window.location.href = "/?action=start_writing";
				});
			</script>
		</#if>
		<#include "meta/Font.ftl">
	</body>
</html>
