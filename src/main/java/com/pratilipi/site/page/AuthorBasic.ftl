<!DOCTYPE html>
<html lang="${lang}">

	<head>
		<#include "meta/HeadBasic.ftl">
	</head>

	<body>
		<#include "../element/pratilipi-header.ftl">
		<div class="parent-container">
			<div class="container">
				
				<#include "../element/pratilipi-author.ftl">
				
				<div class="secondary-500 pratilipi-shadow box" style="padding: 15px 10px;">
					<h2 class="pratilipi-red" style="margin: 0;">${ _strings.author_published_works }</h2>
				</div>
				<#if publishedPratilipiList?has_content>
					<#list publishedPratilipiList as pratilipi>
						<#include "../element/pratilipi-pratilipi-card.ftl">
					</#list>
				<#else>
					<div style="padding: 50px 10px;" class="secondary-500 pratilipi-shadow box">
						<img style="width: 48px; height: 48px; margin: 0px auto 20px auto; display: block;" 
								src="https://storage.googleapis.com/devo-pratilipi.appspot.com/icomoon_24_icons/SVG/info.svg" alt="${ _strings.author_no_contents_published }" />
						<div class="text-center">${ _strings.author_no_contents_published }</div>
					</div>
				</#if>
				
				
				<#if publishedPratilipiListSearchQuery?? >
					<div style="text-align: center; margin: 20px auto;">
						<a style="width: 100%; display: block;" class="pratilipi-new-blue-button text-center" href="/search?${ publishedPratilipiListSearchQuery }">
							${ _strings.load_more_contents }
						</a>
					</div>
				</#if>
			</div>
		</div>
		<#include "../element/pratilipi-footer.ftl">
	</body>
	
</html>