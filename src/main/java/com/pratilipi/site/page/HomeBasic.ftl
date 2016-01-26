<!DOCTYPE html>
<html lang="${lang}">

	<head>
		<#include "meta/HeadBasic.ftl">
	</head>

	<body>
		<div class="container">
			<#include "../element/pratilipi-header.ftl">
			<#include "../element/pratilipi-user-login.ftl">
			
			<#include "../element/pratilipi-user-register.ftl">
			<#include "../element/pratilipi-user-password-reset.ftl">
			<#include "../element/pratilipi-user-password-update.ftl">
			<#include "../element/pratilipi-user-password-update-email.ftl">
	<#--	<#include "../element/pratilipi-user-verification.ftl">		-->
			
			<#list sections as section>
				<#list pratilipiList as pratilipi>
					<#include "../element/pratilipi-pratilipi-card.ftl">
				</#list>
			</#list>
			
			<#include "../element/pratilipi-navigation.ftl">
			<#include "../element/pratilipi-footer.ftl">
		</div>
	</body>
	
</html>