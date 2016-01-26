<!DOCTYPE html>
<html lang="${lang}">

	<head>
		<#include "meta/HeadBasic.ftl">
	</head>

	<body>
		<div class="container">
			<#include "../element/pratilipi-header.ftl">
			<#include "../element/pratilipi-author.ftl">
			
			<div class="box" style="padding: 12px 10px;">
				<h2 style="color: #D0021B;">${ _strings.author_published_works }</h2>
			</div>
			<#list publishedPratilipiList as pratilipi>
				<#include "../element/pratilipi-pratilipi-card.ftl">
			</#list>
			
			<#if publishedPratilipiListSearchQuery?? >
				<div style="text-align: center;">
					<a class="btn btn-primary red" href="/search?${ publishedPratilipiListSearchQuery }">
						All Books by Author
					</a>
				</div>
			</#if>
			
			<#include "../element/pratilipi-navigation.ftl">
			<#include "../element/pratilipi-footer.ftl">
		</div>
	</body>
	
</html>