<!DOCTYPE html>
<html lang="${lang}">

	<head>
		<#include "meta/HeadBasic.ftl">
	</head>
	
	<body>
		<div class="container">
			<#include "../element/pratilipi-header.ftl">
			<#include "../element/pratilipi-pratilipi.ftl">
			<#list reviewList as review>
				<#include "../element/pratilipi-review.ftl">
			</#list>
			<#include "../element/pratilipi-navigation.ftl">
			<#include "../element/pratilipi-footer.ftl">
		</div>
	</body>
	
</html>
