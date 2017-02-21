<#if action == "one" >
	<#assign prefix = "http://hindi.gamma.pratilipi.com">
	<#include "/summary1.html"> <#-- with unnecessary links with temp drawer -->
<#elseif action == "two">
	<#assign prefix = "http://hindi.gamma.pratilipi.com">
	<#include "/summary2.html"> <#-- w/o unnecessary links -->
<#elseif action == "three">
	<#include "/summary3.html"> <#-- no shadow doms  -->
<#elseif action == "four">
	<#include "/summary4.html"> <#-- no shadow doms & no material js -->
<#elseif action == "five">
	<#include "/summary5.html">	<#-- w/o defer -->
<#elseif action == "six">
	<#include "/summary6.html">	<#-- w/o defer and w/o navigation -->
<#elseif action == "seven">
	<#include "/summary7.html">	<#-- w/o defer and w/o navigation with js-layout-->
<#elseif action == "eight">
	<#include "/summary8.html">
<#elseif action == "nine">
	<#include "/LoginPage.html">	
<#elseif action == "ten">
	<#include "/RegisterPage.html">
<#elseif action == "eleven">
	<#include "/pwa11.html">	
<#elseif action == "twelve">
	<#include "/pwa12.html">
<#elseif action == "thirteen">
	<#include "/pwa13.html">
<#elseif action == "fourteen">
	<#include "/pwa14.html">	
<#elseif action == "fifteen">
	<#include "/pwa15.html">		<#-- changing view model style of defining-->	
<#elseif action == "sixteen">
	<#include "/pwa16.html">
<#elseif action == "seventeen">
	<#include "/mdl-poc.html">		
<#elseif action == "eighteen">
	<#include "/summary1.html">		
<#elseif action == "nineteen">
	<#include "/summary2.html">	
<#elseif action == "twenty">
	<#include "/summary3.html">															
</#if>