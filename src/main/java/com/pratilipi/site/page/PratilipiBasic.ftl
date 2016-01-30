<!DOCTYPE html>
<html lang="${lang}">

	<head>
		<#include "meta/HeadBasic.ftl">
	</head>
	
	<body>
		<div class="container">
			<#include "../element/pratilipi-header.ftl">
			<#include "../element/pratilipi-pratilipi.ftl">
			<div class="box" style="padding: 12px 10px;">
				<h2 style="color: #D0021B;">${ _strings.review_heading }</h2>
			</div>
			<#include "../element/pratilipi-review-input.ftl">
			<#list reviewList as review>
				<#include "../element/pratilipi-review.ftl">
			</#list>
			<#include "../element/pratilipi-navigation.ftl">
			<#include "../element/pratilipi-footer.ftl">
		</div>
	</body>
	
</html>
