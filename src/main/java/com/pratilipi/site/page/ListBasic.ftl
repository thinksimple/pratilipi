<!DOCTYPE html>
<html lang="${lang}">

	<head>
		<#include "meta/HeadBasic.ftl">
	</head>

	<body>
		<div class="container">
			<#include "../element/pratilipi-header.ftl">
			
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