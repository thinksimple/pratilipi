<!DOCTYPE html>
<html lang="${lang}">

	<head>
		<#include "meta/HeadBasic.ftl">
	</head>

	<body>
		<#include "../element/basic/pratilipi-header.ftl">
		<div class="parent-container">
			<div class="container">
				<#include "../element/basic/pratilipi-android-launch.ftl">
				<#if pratilipiList?has_content>
					<#include "../element/basic/pratilipi-pratilipi-card.ftl">
					<#list pratilipiList as local_pratilipi>
						<@pratilipi_card from="search" pratilipi=local_pratilipi />
					</#list>
				<#else>
					<div style="padding: 25px 10px;" class="secondary-500 pratilipi-shadow box">
					<div class="sprites-icon size-24-icon search-block-icon"></div>
						<div class="text-center">${ _strings.search_no_results_found }</div>
					</div>
				</#if>
	
				<#-- Add page navigation -->
				<#assign currentPage = pratilipiListPageCurr>
				<#assign maxPage = pratilipiListPageMax>
				
				<#include "../element/basic/pratilipi-page-navigation.ftl">
	
			</div>
		</div>
		<#include "../element/basic/pratilipi-footer.ftl">
	</body>
	
</html>