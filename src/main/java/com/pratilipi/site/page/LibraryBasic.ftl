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
				<div class="secondary-500 pratilipi-shadow box" style="padding: 12px 10px;">
					<h2 class="pratilipi-red">${ _strings.my_library }</h2>
				</div>
				<#if user.isGuest() >
					<div style="padding: 25px 10px;" class="secondary-500 pratilipi-shadow box">
						<div class="sprites-icon size-24-icon info-icon"></div>
						<div class="text-center"><a class="login-link" href="/login?ret=/library">${ _strings.user_login_to_view_library }</a></div>
					</div>				
				<#else>
					<#if pratilipiList?has_content>
						<#include "../element/basic/pratilipi-pratilipi-card.ftl">
						<#list pratilipiList as local_pratilipi>
							<@pratilipi_card from="library" pratilipi=local_pratilipi />
						</#list>
					<#else>
						<div style="padding: 25px 10px;" class="secondary-500 pratilipi-shadow box">
							<div class="sprites-icon size-24-icon info-icon"></div>
							<div class="text-center">${ _strings.empty_library }</div>
						</div>
					</#if>
		
					<#-- 
						Add page navigation 
					<#assign currentPage = pratilipiListPageCurr>
					<#assign maxPage = pratilipiListPageMax>
					<#assign prefix = "?" >
					<#include "../element/basic/pratilipi-page-navigation.ftl">
					-->				
				</#if> 

			</div>
		</div>
		<#include "../element/basic/pratilipi-footer.ftl">
	</body>
	
</html>