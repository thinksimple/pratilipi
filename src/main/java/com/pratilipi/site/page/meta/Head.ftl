<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">

<#-- Page Title & Favicon -->
<title>${ title }</title>
<link rel="shortcut icon" type="image/png" href="/favicon.png">

<#-- DNS Prefetch -->
<#list 0..4 as i>
<link rel="dns-prefetch" href="//${i}.ptlp.co/">
</#list>

<#-- External dependencies -->
<script src='http://0.ptlp.co/resource-all/jquery.bootstrap.polymer.firebase.js'></script>
<script src='http://0.ptlp.co/third-party/ckeditor-4.5.10-full/ckeditor.js'></script>
<link rel='import' href='http://0.ptlp.co/resource-all/pratilipi.polymer.elements.2.html'>
<link rel='stylesheet' href='http://0.ptlp.co/third-party/bootstrap-3.3.4/css/bootstrap.min.css'>

<#-- Pratilipi dependencies -->
<link rel='import' href='/elements.${lang}/pratilipi-custom-elements.html?28'>
<link type="text/css" rel="stylesheet" href="/resources/style.css?68">

<#-- Firebase Script -->
<#include "Firebase.ftl">