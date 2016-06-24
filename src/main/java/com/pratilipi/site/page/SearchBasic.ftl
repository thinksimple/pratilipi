<!DOCTYPE html>
<html lang="${lang}">

	<head>
		<#include "meta/HeadBasic.ftl">
	</head>

	<body>
		<#include "../element/pratilipi-header.ftl">
		<div class="parent-container">
			<div class="container">
				<form style="margin-top: 15px;" method="get" action="/search">
					<div class="form-group">
						<div class="input-group">
							<input type="text" class="form-control" name="q" placeholder="${ _strings.search }" style="display: table-cell; width:100%;" maxlength="120" <#if pratilipiListSearchQuery?? >value="${ pratilipiListSearchQuery }"</#if> />
							<div style="background: #f5f5f5" class="input-group-addon"><button class="search-button" type="submit">
								<img style="width: 16px; height: 16px;" src="http://0.ptlp.co/resource-all/icon/svg/search.svg" />
							</button></div>
						</div>
					</div>
				</form>
				
				<#if pratilipiList?has_content>
					<#list pratilipiList as pratilipi>
						<#include "../element/pratilipi-pratilipi-card.ftl">
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
				
				<#include "../element/pratilipi-page-navigation.ftl">
	
			</div>
		</div>
		<#include "../element/pratilipi-footer.ftl">
	</body>
	
</html>