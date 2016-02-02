<!DOCTYPE html>
<html lang="${lang}">

	<head>
		<#include "meta/HeadBasic.ftl">
	</head>
	
	<body>
		<div class="container">
			<#include "../element/pratilipi-header.ftl">

			<#-- Show review-input if reviewParam == write. Else if reviewParam == list, show complete review-list with page numbers
					If reviewParam = null, show pratilipi page and few reviews. -->

			<#if reviewParam?? >
				<#if reviewParam == "write">
					<#include "../element/pratilipi-review-input.ftl">
				<#elseif reviewParam == "list">
					<#include "../element/pratilipi-review-list.ftl">
					
					<#-- Add page navigation -->
					<#assign currentPage = reviewListPageCurr>
					<#assign maxPage = reviewListPageMax>
					<#assign prefix = "?review=list&" >
					<#include "../element/pratilipi-page-navigation.ftl">
				</#if>
			<#else>
				<#include "../element/pratilipi-pratilipi.ftl">
				<#include "../element/pratilipi-review-list.ftl">
				<#if pratilipi.reviewCount?? >
					<#if pratilipi.reviewCount gt 10>
						<a class="btn btn-default red" href="?review=list">See all reviews</a>
					</#if>
				</#if>
			</#if>

			<#include "../element/pratilipi-navigation.ftl">
			<#include "../element/pratilipi-footer.ftl">
		</div>
	</body>
	
</html>
