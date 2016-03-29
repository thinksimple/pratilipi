<!DOCTYPE html>
<html lang="${lang}">

	<head>
		<#include "meta/HeadBasic.ftl">
	</head>

	<body>
		<#include "../element/pratilipi-header.ftl">
		<div class="parent-container">
			<div class="container">
				<div class="secondary-500 pratilipi-shadow box" style="padding: 12px 10px;">
					<h2 class="pratilipi-red">${ _strings.my_library }</h2>
				</div>
	
				<#if pratilipiList?has_content>
					<#list pratilipiList as pratilipi>
						<#include "../element/pratilipi-pratilipi-card.ftl">
					</#list>
				<#else>
					<div style="padding: 50px 10px;" class="secondary-500 pratilipi-shadow box">
						<img style="width: 48px; height: 48px; margin: 0px auto 20px auto; display: block;" 
								src="https://storage.googleapis.com/devo-pratilipi.appspot.com/icomoon_24_icons/SVG/info.svg" alt="${ _strings.author_no_contents_published }" />
						<div class="text-center">${ _strings.empty_library }</div>
					</div>
				</#if>
	
				<#-- 
					Add page navigation 
				<#assign currentPage = pratilipiListPageCurr>
				<#assign maxPage = pratilipiListPageMax>
				<#assign prefix = "?" >
				<#include "../element/pratilipi-page-navigation.ftl">
				-->
			</div>
		</div>
		<#include "../element/pratilipi-footer.ftl">
	</body>
	
</html>