<!DOCTYPE html>
<html lang="${lang}">

	<head>
		<#include "meta/HeadBasic.ftl">
		
		<script>
			function getUrlParameters() {
				return JSON.parse('{"' + decodeURI( location.search.substring(1).replace(/&/g, "\",\"").replace(/=/g,"\":\"")) + '"}' );
			}			
		</script>
	</head>

	<body>
		<div class="container">
			<#include "../element/pratilipi-header.ftl">
			
			<#list pratilipiList as pratilipi>
				<#include "../element/pratilipi-pratilipi-card.ftl">
			</#list>
			
			<#include "../element/pratilipi-page-navigation.ftl">
			
			<#include "../element/pratilipi-navigation.ftl">
			<#include "../element/pratilipi-footer.ftl">
		</div>
	</body>
	
</html>