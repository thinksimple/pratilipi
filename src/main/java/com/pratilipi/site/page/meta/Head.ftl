<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<meta name="description" content="A platform to discover, read and share your favorite stories, poems and books in a language, device and format of your choice.">

<#-- Page Title & Favicon -->
<title>${ title }</title>
<link rel="shortcut icon" type="image/png" href="/favicon.png">

<#-- Third-Party Library and Facebook OG Tags-->
<#list resourceList as resource>${ resource }</#list>

<#-- DNS Prefetch -->
<#list 0..4 as i><link rel="dns-prefetch" href="//${i}.ptlp.co/"></#list>

<#-- External dependencies -->
<script src='http://0.ptlp.co/resource-all/jquery.bootstrap.polymer.firebase.jsapi.compressed.js'></script>
<link rel='stylesheet' href='http://1.ptlp.co/third-party/bootstrap-3.3.4/css/bootstrap.min.css'>

<#-- Google Analytics -->
<#include "GoogleAnalytics.ftl">

<#-- Firebase Script -->
<#include "Firebase.ftl">

<#-- Basic functions -->
<#include "Scripts.ftl">

<#-- Google Login -->
<script src="https://apis.google.com/js/api:client.js"></script>

<#if lang=="hi" && stage=="prod">
<#-- Facebook Pixel Code only on hindi pratilipi -->
<script>
!function(f,b,e,v,n,t,s){if(f.fbq)return;n=f.fbq=function(){n.callMethod?
n.callMethod.apply(n,arguments):n.queue.push(arguments)};if(!f._fbq)f._fbq=n;
n.push=n;n.loaded=!0;n.version='2.0';n.queue=[];t=b.createElement(e);t.async=!0;
t.src=v;s=b.getElementsByTagName(e)[0];s.parentNode.insertBefore(t,s)}(window,
document,'script','https://connect.facebook.net/en_US/fbevents.js');

fbq('init', '1569748966613739');
fbq('track', "PageView");</script>
<noscript><img height="1" width="1" style="display:none"
src="https://www.facebook.com/tr?id=1569748966613739&ev=PageView&noscript=1"
/></noscript>
</#if>
<script>function trackPixelEvents(eventName){if(typeof(fbq)!='undefined')fbq('trackCustom',eventName);}</script>