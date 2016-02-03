<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">

<#-- Page Title & Favicon -->
<title>${ title }</title>
<link rel="shortcut icon" type="image/png" href="/favicon.png">

<#-- Third-Party Library -->
<#list resourceList as resource>
	${ resource }
</#list>

<#-- Polymer 1.0 Custom Elements -->
<link rel='import' href='/elements.${lang}/pratilipi-user.html?20160203'>
<link rel='import' href='/elements.${lang}/pratilipi-header.html?20160203'>
<link rel='import' href='/elements.${lang}/pratilipi-edit-account.html?20160203'>
<link rel='import' href='/elements.${lang}/pratilipi-write.html?20160203'>
<link rel='import' href='/elements.${lang}/pratilipi-navigation.html?20160203'>
<link rel='import' href='/elements.${lang}/pratilipi-footer.html?20160203'>

<#-- Custom Stylesheet -->
<link type="text/css" rel="stylesheet" href="/resources/style.css?20160203">

<#include "GoogleAnalytics.ftl">
