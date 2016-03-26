<!DOCTYPE html>
<html lang="${lang}">

	<head>
		<#-- Page Description -->
		<meta name="description" content="A platform to discover, read and share your favorite stories, poems and books in a language, device and format of your choice.">
		
		<#include "meta/HeadBasic.ftl">
	</head>

	<body>
		<div class="container">
			<#include "../element/pratilipi-header.ftl">

			<#list sections as section>
				<div class="box" style="padding: 12px 10px;">
					<h2 style="color: #D0021B; display: inline-block;">${ section["title"] }</h2>
					<#if section["listPageUrl"]??>
						<a href="${ section["listPageUrl"] }" class="link pull-right pratilipi-blue">${ _strings.view_more }</a>
					</#if>
				</div>
				<#list section["pratilipiList"] as pratilipi>
					<#include "../element/pratilipi-pratilipi-card.ftl">
				</#list>
				<br/>
			</#list>
			
			<#include "../element/pratilipi-navigation.ftl">
			<#include "../element/pratilipi-footer.ftl">
		</div>
	</body>
	
</html>