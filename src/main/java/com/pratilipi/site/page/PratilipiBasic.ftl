<!DOCTYPE html>
<html lang="${lang}">

	<head>
		<#include "meta/HeadBasic.ftl">
	</head>
	
	<body>
		<div class="container">
			<#include "../element/pratilipi-header.ftl">
			<#include "../element/pratilipi-pratilipi.ftl">

			<#-- Show review-input iff writeReview == true. Else, show review-list -->
			<#if writeReview?? >
				<#if writeReview == true>
					<#include "../element/pratilipi-review-input.ftl">
				<#else>
					<#include "../element/pratilipi-review-list.ftl">
				</#if>
			<#else>
				<#include "../element/pratilipi-review-list.ftl">
			</#if>

			<#include "../element/pratilipi-navigation.ftl">
			<#include "../element/pratilipi-footer.ftl">
		</div>
	</body>
	
</html>
