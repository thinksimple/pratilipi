<!DOCTYPE html>
<html lang="${lang}">

	<head>
		<#-- Page Description -->
		<meta name="description" content="A platform to discover, read and share your favorite stories, poems and books in a language, device and format of your choice.">
		
		<#include "meta/HeadBasic.ftl">
		
		$( document ).ready( function() {
			var jSummary = jQuery( "#pratilipi-event-description" );
			jSummary.html( this.event.description );
		});
	</head>

	<body>
		<div class="container">
			<#include "../element/pratilipi-header.ftl">

			<div class="box" style="padding: 12px 10px;">
				<h2 style="color: #D0021B;">${ event.name }</h2>
			</div>
			
			<img src="${ event.getBannerImageUrl( 300 ) }"/>
			
			<#if event.description ??>
				<div id="pratilipi-event-description" class="box" style="padding: 12px 20px;"></div>
			</#if>
			
			
			<#list pratilipiList as pratilipi>
				<#include "../element/pratilipi-pratilipi-card.ftl">
			</#list>
						
			<#include "../element/pratilipi-navigation.ftl">
			<#include "../element/pratilipi-footer.ftl">
		</div>
	</body>
	
</html>