<!DOCTYPE html>
<html lang="${lang}">

	<head>
		<#include "meta/HeadBasic.ftl">
		
		<script>
			function openLoginForm() {
				jQuery( "#pratilipiUserLogin" ).modal();
			}
		</script>
	</head>

	<body>
		<div class="container">
			<#include "../element/pratilipi-header.ftl">
			<#include "../element/pratilipi-user-login.ftl">
			<#include "../element/pratilipi-author.ftl">
			<#list pratilipiList as pratilipi>
				<#include "../element/pratilipi-pratilipi-card.ftl">
			</#list>
			<#include "../element/pratilipi-navigation.ftl">
			<#include "../element/pratilipi-footer.ftl">
		</div>
	</body>
	
</html>