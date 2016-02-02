
<div class="box" style="padding: 12px 10px;">
	<h2 style="color: #D0021B; display: inline-block;">${ _strings.review_heading }</h2>
	<#if userpratilipi?? && userpratilipi.hasAccessToReview == true>
			<a href="?review=write" style="display: inline-block" class="btn btn-default red">
			<#if userpratilipi.review?? >
				${ _strings.review_edit_review }
			<#else>
				${ _strings.review_write_a_review }
			</#if></a>
	<#elseif user.isGuest == true>
		<a href="/login?ret=${ requestUrl }?review=write" style="display: inline-block" class="btn btn-default red">${ _strings.review_write_a_review }</a>
	</#if>				
</div>


<#if userpratilipi?? >
	<#if userpratilipi.review??> <#-- User has review -->
		
		<#-- Show user review on top iff userpratilipi.reviewState != "DELETED" && userpratilipi.reviewState != "BLOCKED" -->
		<#if userpratilipi.reviewState != "DELETED" && userpratilipi.reviewState != "BLOCKED">
			<#assign review=userpratilipi>
			<#include "../element/pratilipi-review.ftl">
		</#if>
		
		<#-- Check and run a loop to remove his review from review List -->
		<#list reviewList as review>
			<#if review.getId() != userpratilipi.getId()>
				<#include "../element/pratilipi-review.ftl">
			</#if>
		</#list>
		
	<#else> <#-- User Doesn't have review -->
		<#list reviewList as review>
			<#include "../element/pratilipi-review.ftl">
		</#list>
	</#if>

<#else> <#-- Userpratilipi is null i.e. guest user -->
	<#if reviewList??>
		<#list reviewList as review>
			<#include "../element/pratilipi-review.ftl">
		</#list>
	<#else>
		<a style="display: block; width: 100%;" class="btn btn-default red" href="?review=list">See all reviews</a>
	</#if>
	
</#if>