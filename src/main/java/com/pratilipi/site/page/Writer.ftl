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
	
	<#if !user.isGuest() >
		<script>
			$( document ).ready(function() {
			    window.location.href = "/?action=login&login_success=redirect_writer";
			});
		</script>
	<#else>
		<#if action?? >
			<#if action!="start_writing" && action!="write" && action!="publish">
				<#assign action="start_writing">
			</#if>
		<#else>
			<#if pratilipiId??>
				<#assign action="write">
			<#else>
				<#assign action="start_writing">
			</#if>	
		</#if>
		
		<#if action == "start_writing">
			<#include "../element/standard/writer-panel/writer-start-screen/index.html">
		<#elseif ( action == "write" )>	
			<#include "../element/standard/writer-panel/writer-main-screen/index.html">
		<#elseif ( action == "publish" )>
			<#include "../element/standard/writer-panel/writer-final-screen/index.html">
		</#if>		
	</#if>
	
</html>