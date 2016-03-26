<!DOCTYPE html>
<html lang="${lang}">

	<head>
		<#include "meta/HeadBasic.ftl">
	</head>

	<body>
		<div class="container">
			<#include "../element/pratilipi-header.ftl">
			
			<#if pratilipiListTitle??>
				<div class="box" style="padding: 12px 10px;">
					<h2 style="color: #D0021B;">${ pratilipiListTitle }</h2>
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
			
			<#include "../element/pratilipi-navigation.ftl">
			<#include "../element/pratilipi-footer.ftl">
		</div>
	</body>
	
</html>