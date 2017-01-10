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
				<#include "../element/basic/pratilipi-pratilipi-card.ftl">
				<#if pratilipiListTitle??>
					<div class="secondary-500 pratilipi-shadow box" style="padding: 12px 10px;">
						<h2 class="pratilipi-red">${ pratilipiListTitle }</h2>
					</div>
				</#if>
	
				<#list pratilipiList as local_pratilipi>
					<@pratilipi_card from="list" pratilipi=local_pratilipi />
				</#list>
	
				<#-- Add page navigation -->
				<#assign currentPage = pratilipiListPageCurr>
				<#assign maxPage = pratilipiListPageMax>
				<#include "../element/basic/pratilipi-page-navigation.ftl">
			</div>
		</div>
		<#include "../element/basic/pratilipi-footer.ftl">
	</body>
	
</html>