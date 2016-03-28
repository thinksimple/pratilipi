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
					<div class="secondary-500 pratilipi-shadow box" style="padding: 12px 10px;">
						<h2 class="pratilipi-red">${ event.name }</h2>
					</div>
					
					<div style="padding: 0px;" class="pratilipi-shadow">
						<img style="width: 100%; height: 100%;" src="${ event.getBannerImageUrl( 600 ) }"/>
					</div>
					
					<#if event.description ??>
						<div class="secondary-500 pratilipi-shadow box" style="padding: 12px 20px;">${ event.description }</div>
					</#if>
					
					<#if pratilipiList ??>
						<div class="box" style="padding: 12px 10px;">
							<h2 class="pratilipi-red">${ _strings.event_entries }</h2>
						</div>
						<#list pratilipiList as pratilipi>
							<#include "../element/pratilipi-pratilipi-card.ftl">
						</#list>
					</#if>
				</div>
			</div>		
			<#include "../element/pratilipi-footer.ftl">
	</body>
	
</html>