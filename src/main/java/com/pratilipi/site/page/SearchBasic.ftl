<!DOCTYPE html>
<html lang="${lang}">

	<head>
		<#include "meta/HeadBasic.ftl">
	</head>

	<body>
		<div class="container">
			<#include "../element/pratilipi-header.ftl">
			
			<#list pratilipiList as pratilipi>
				<#include "../element/pratilipi-pratilipi-card.ftl">
			</#list>
			
			<#assign currentPage = pratilipiListPageCurr>
			<#assign maxPage = pratilipiListPageMax >
			<#include "../element/pratilipi-page-navigation.ftl">
			
			<#include "../element/pratilipi-navigation.ftl">
			<#include "../element/pratilipi-footer.ftl">
		</div>
	</body>
	
</html>