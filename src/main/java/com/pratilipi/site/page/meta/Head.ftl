<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">

<#-- Page Title & Favicon -->
<title>${ title }</title>
<link rel="shortcut icon" type="image/png" href="/favicon.png">

<#-- Third-Party Library and Facebook OG Tags-->
<#list resourceList as resource>${ resource }</#list>

<#-- DNS Prefetch -->
<#list 0..4 as i>
<link rel="dns-prefetch" href="//${i}.ptlp.co/">
</#list>

<#-- External dependencies -->
<script src='http://0.ptlp.co/resource-all/jquery.bootstrap.polymer.firebase.compressed.js'></script>
<link rel='stylesheet' href='http://1.ptlp.co/third-party/bootstrap-3.3.4/css/bootstrap.min.css'>
<script src='http://1.ptlp.co/third-party/ckeditor-4.5.10-full/ckeditor.js'></script>

<#-- Pratilipi dependencies -->
<link rel='import' href='/elements.${lang}/pratilipi-custom-elements.html?45'>

<#-- Google Analytics -->
<#include "GoogleAnalytics.ftl">

<#-- Firebase Script -->
<#include "Firebase.ftl">

<#-- CustomerLabs Tag -->
<script>!function(t,e,r,c,a,n,s){t.ClAnalyticsObject=a,t[a]=t[a]||[],t[a].methods=["trackSubmit","trackClick","pageview","identify","track"],t[a].factory=function(e){return function(){var r=Array.prototype.slice.call(arguments);return r.unshift(e),t[a].push(r),t[a]}};for(var i=0;i<t[a].methods.length;i++){var o=t[a].methods[i];t[a][o]=t[a].factory(o)};n=e.createElement(r),s=e.getElementsByTagName(r)[0],n.async=1,n.crossOrigin="anonymous",n.src=c,s.parentNode.insertBefore(n,s)}(window,document,"script","//cdn.js.customerlabs.co/cl63u8jemht8.js","_cl");_cl.SNIPPET_VERSION="1.0.0"</script>