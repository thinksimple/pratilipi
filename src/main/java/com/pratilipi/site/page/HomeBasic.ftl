<!DOCTYPE html>
<html lang="${lang}">

	<head>
		<#-- Page Description -->
		<meta name="description" content="A platform to discover, read and share your favorite stories, poems and books in a language, device and format of your choice.">
		
		<#include "meta/HeadBasic.ftl">
	</head>

	<body>
			<#include "../element/pratilipi-header.ftl">
			<div class="parent-container">
				<div class="container">
					<#list sections as section>
						<div class="secondary-500 pratilipi-shadow box" style="padding: 12px 10px;">
							<h2 class="pratilipi-red" style="display: inline-block;">${ section["title"] }</h2>
							<#if section["listPageUrl"]??>
								<a href="${ section["listPageUrl"] }" class="link pull-right pratilipi-blue">${ _strings.view_more }</a>
							</#if>
						</div>
						<#list section["pratilipiList"] as pratilipi>
							<#include "../element/pratilipi-pratilipi-card.ftl">
						</#list>
						<div style="min-height: 10px;"></div>
					</#list>
				</div>
			</div>
			<#include "../element/pratilipi-footer.ftl">
	</body>
	
</html>