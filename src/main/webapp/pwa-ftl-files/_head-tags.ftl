<#macro head_tags title include_app_shell>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
	<!-- ko foreach: { data: appViewModel.metaTags() } -->
		<meta data-bind="attr: { 'property': $data.property, 'content': $data.content }" />
	<!--/ko-->
	<title data-bind="text: pageTitle"></title>
	<script src="https://d3cwrmdwk8nw1j.cloudfront.net/resource-all/pwa/js/ko.ko_mapping.js"></script>
	<#include "_google-analytics.ftl">
	<#include "_font-styles.ftl">
	<#if include_app_shell?? && include_app_shell>
		<@register element="pratilipi-header" />
		<@register element="pratilipi-write" />
		<@register element="pratilipi-notification-list" />
		<@register element="pratilipi-navigation-drawer" />
		<@register element="pratilipi-navigation-aside" />
		<@register element="pratilipi-footer" />
	</#if>
</#macro>
