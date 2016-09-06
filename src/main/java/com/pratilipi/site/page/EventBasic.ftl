<#compress>
<!DOCTYPE html>
<html lang="${lang}">

	<head>
		<#-- Page Description -->
		<meta name="description" content="A platform to discover, read and share your favorite stories, poems and books in a language, device and format of your choice.">
		
		<#include "meta/HeadBasic.ftl">
		
	</head>

	<body>
		<#include "../element/basic/pratilipi-header.ftl">
			<div class="parent-container">
				<div class="container">
					<#if ( action == "list_contents") >
						<#include "../element/basic/pratilipi-event-list.ftl">
					<#else>					
						<div class="secondary-500 pratilipi-shadow box" style="padding: 12px 10px;">
							<h2 class="pratilipi-red">${ event.name }</h2>
						</div>
						
						<div style="padding: 0px;" class="pratilipi-shadow box">
							<img style="width: 100%; height: 100%;" src="${ event.getBannerImageUrl( 600 ) }"/>
						</div>
						
						<#if event.description ??>
							<div class="secondary-500 pratilipi-shadow box" style="padding: 12px 20px;">${ event.description }</div>
						</#if>
						
						<#if pratilipiList ??>
							<div class="secondary-500 box pratilipi-shadow" style="padding: 12px 10px; margin-top: 12px;">
								<#if ( numberFound > 10 ) >
									<a href="?action=list_contents" class="pull-right"><img style="height:22px;" src="http://0.ptlp.co/resource-all/icon/svg/chevron-right-red.svg"></img></a>
								</#if>
								<div class="align-text-center">
									<h2 class="pratilipi-red">${ _strings.event_entries }</h2>
								</div>	
							</div>
							<#list pratilipiList as pratilipi>
								<#include "../element/basic/pratilipi-pratilipi-card.ftl">
							</#list>	
						</#if>
					</#if>
				</div>
			</div>		
			<#include "../element/basic/pratilipi-footer.ftl">
	</body>
	
</html>
</#compress>