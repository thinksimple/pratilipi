<!DOCTYPE html>
<html lang="${lang}">

	<head>
		<#include "meta/HeadBasic.ftl">
	</head>
	
	<body>
		<div class="container">
			<#include "../element/pratilipi-header.ftl">
			<#include "../element/pratilipi-pratilipi.ftl">

			<#-- Show review-input if review == write. Else if review == list, show complete review-list with page numbers
					If review = null, show pratilipi page and few reviews. -->
			<#if review?? >
				<#if review == "write">
					<#include "../element/pratilipi-review-input.ftl">
				<#elseif review == "list">
					<#include "../element/pratilipi-review-list.ftl">
					
					<#-- Add page navigation -->
					<#-- 
					<#assign currentPage = reviewListPageCurr>
					<#assign maxPage = reviewListPageMax>
					<#assign prefix = "?review=list&" >
					<#include "../element/pratilipi-page-navigation.ftl">
					-->
				</#if>
			<#else>
				<#include "../element/pratilipi-review-list.ftl">
			</#if>

			<#include "../element/pratilipi-navigation.ftl">
			<#include "../element/pratilipi-footer.ftl">
		</div>
	</body>
	
</html>
