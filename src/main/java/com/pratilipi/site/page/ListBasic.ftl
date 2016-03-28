<!DOCTYPE html>
<html lang="${lang}">

	<head>
		<#include "meta/HeadBasic.ftl">
	</head>

	<body>
		<#include "../element/pratilipi-header.ftl">
		<div class="parent-container">
			<div class="container">
				<#if pratilipiListTitle??>
					<div class="secondary-500 pratilipi-shadow box" style="padding: 12px 10px;">
						<h2 class="pratilipi-red">${ pratilipiListTitle }</h2>
					</div>
				</#if>
	
				<#list pratilipiList as pratilipi>
					<#include "../element/pratilipi-pratilipi-card.ftl">
				</#list>
	
				<#-- Add page navigation -->
				<#assign currentPage = pratilipiListPageCurr>
				<#assign maxPage = pratilipiListPageMax>
				<#assign prefix = "?" >
				<#include "../element/pratilipi-page-navigation.ftl">
			</div>
		</div>
		<#include "../element/pratilipi-footer.ftl">
	</body>
	
</html>