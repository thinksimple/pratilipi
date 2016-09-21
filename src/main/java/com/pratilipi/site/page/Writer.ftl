<!DOCTYPE html>
<html lang="${lang}">
	<head>
		<meta name="description" content="A platform to discover, read and share your favorite stories, poems and books in a language, device and format of your choice.">
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
		<title>${ title }</title>
		<link rel="shortcut icon" type="image/png" href="/favicon.png">
		<script src='http://0.ptlp.co/resource-all/jquery.bootstrap.polymer.firebase.compressed.js'></script>
		<link rel='stylesheet' href='http://1.ptlp.co/third-party/bootstrap-3.3.4/css/bootstrap.min.css'>
	</head>
	<#if !action??  || action == "start_writing">
		<#include "../element/standard/writer-panel/writer-start-screen/index.html">
	<#elseif ( action == "write" )>
		<#include "../element/standard/writer-panel/writer-main-screen/index.html">
	<#elseif ( action == "summarize" )>
		<#include "../element/standard/writer-panel/writer-final-screen/index.html">
	<#else>
		<#include "../element/standard/writer-panel/writer-start-screen/index.html">			
	</#if>
</html>