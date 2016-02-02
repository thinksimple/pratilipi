
<div class="box" style="padding: 12px 10px;">
	<h2 style="color: #D0021B; display: inline-block;">${ _strings.review_heading }</h2>
	<#if userpratilipi?? >
		<#if userpratilipi.hasAccessToReview == true>
				<a href="?review=write" style="display: inline-block" class="btn btn-default red">
				<#if userpratilipi.review?? >
					${ _strings.review_edit_review }
				<#else>
					${ _strings.review_write_a_review }
				</#if></a>
		</#if>
	</#if>				
</div>


<#if userpratilipi?? >
	<#if userpratilipi.review??> <#-- User has review -->
		
		<#-- Show user review on top -->
		<#assign review=userpratilipi>
		<#include "../element/pratilipi-review.ftl">
		
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
	<#list reviewList as review>
		<#include "../element/pratilipi-review.ftl">
	</#list>
</#if>