<#compress>
<!DOCTYPE html>
<html lang="${lang}">

	<head>
		<#include "meta/HeadBasic.ftl">
	</head>

	<body>
		<#include "../element/basic/pratilipi-header.ftl">
		<div class="parent-container">
			<div class="container">
				<#if pratilipiList?has_content>
					<#list pratilipiList as pratilipi>
						<#include "../element/basic/pratilipi-pratilipi-card.ftl">
					</#list>
				<#else>
					<div style="padding: 50px 10px;" class="secondary-500 pratilipi-shadow box">
						<img style="width: 48px; height: 48px; margin: 0px auto 20px auto; display: block;" 
								src="http://0.ptlp.co/resource-all/icon/svg/search.svg" />
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
</#compress>