<!DOCTYPE html>
<html lang="${lang}">

	<head>
		<#include "meta/HeadBasic.ftl">
	</head>

	<body>
		<div class="container">
			<#include "../element/pratilipi-header.ftl">
			<#include "../element/pratilipi-author.ftl">
			
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