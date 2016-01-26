<!DOCTYPE html>
<html lang="${lang}">

	<head>
		<#include "meta/Head.ftl">

		<#-- Polymer 1.0 Custom Elements -->
		<link rel='import' href='/elements.${lang}/pratilipi-user.html'>
		<link rel='import' href='/elements.${lang}/pratilipi-reader.html'>
	</head>

	<body>
		<template is="dom-bind">

			<pratilipi-user user='{{ user }}' user-data='${ userJson }'></pratilipi-user>

			<pratilipi-reader class="container"></pratilipi-reader>
		
		</template>
	</body>
	
</html>