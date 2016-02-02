<!DOCTYPE html>
<html lang="${lang}">

	<head>
		<#include "meta/HeadBasic.ftl">
	</head>
	
	<body>
		<div class="container">
			<#include "../element/pratilipi-header.ftl">
			
			
			<#-- review param is null -->
			<#if !reviewParam??>
				<#assign reviewParam = "pratilipi">
			</#if>
			
			<#-- Edge cases -->
			<#-- 1. User Logged out from review input screen -->
			<#if reviewParam == "write" && ! userpratilipi?? >
				<#assign reviewParam = "pratilipi">
			</#if>
			
			<#-- 2. Author reviewing his own book -->
			<#if reviewParam == "write" && userpratilipi?? && userpratilipi.hasAccessToReview == false >
				<div class="box" style="padding: 20px auto; text-align: center;">
					Sorry! You are not Authorized to write review for this book! You can review this book from logging in from a different account!
				</div>
				<#assign reviewParam = "hide">
			</#if>

			
			<#-- Show review-input if reviewParam == write. Else if reviewParam == list, show complete review-list with page numbers
					If reviewParam = pratilipi, show pratilipi page and few reviews. -->
			<#if reviewParam == "write">
				<#include "../element/pratilipi-review-input.ftl">
				
			<#elseif reviewParam == "list">
				<#include "../element/pratilipi-review-list.ftl">
				<#-- Add page navigation -->
				<#assign currentPage = reviewListPageCurr>
				<#assign maxPage = reviewListPageMax>
				<#assign prefix = "?review=list&" >
				<#include "../element/pratilipi-page-navigation.ftl">
			<#elseif reviewParam == "pratilipi">
				<#include "../element/pratilipi-pratilipi.ftl">
				<#include "../element/pratilipi-review-list.ftl">
				<#if pratilipi.reviewCount?? >
					<#if pratilipi.reviewCount gt 10>
						<a style="display: block; width: 100%;" class="btn btn-default red" href="?review=list">See all reviews</a>
					</#if>
				</#if>
			</#if>

			<#include "../element/pratilipi-navigation.ftl">
			<#include "../element/pratilipi-footer.ftl">
		</div>
	</body>
	
</html>
