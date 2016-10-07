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
<script src='http://0.ptlp.co/resource-all/jquery.bootstrap.polymer.firebase.compressed.js'></script>
<link rel='stylesheet' href='http://1.ptlp.co/third-party/bootstrap-3.3.4/css/bootstrap.min.css'>

<#-- Pratilipi dependencies -->
<link rel='import' href='/elements.${lang}/pratilipi-custom-elements.html?79'>

<#-- Google Analytics -->
<#include "GoogleAnalytics.ftl">

<#-- Firebase Script -->
<#include "Firebase.ftl">

<#-- Mobile device -->
<script>function isMobile(){return /Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent);}</script>