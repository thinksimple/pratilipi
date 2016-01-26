<!DOCTYPE html>
<html lang="${lang}">

	<head>
		<#include "meta/Head.ftl">

		<#-- Polymer 1.0 Custom Elements -->
		<link rel='import' href='/elements.${lang}/pratilipi-home-page.html'>
		<link rel='import' href='/elements.${lang}/pratilipi-user.html'>
		<link rel='import' href='/elements.${lang}/pratilipi-header.html'>
		<link rel='import' href='/elements.${lang}/pratilipi-edit-account.html'>
		<link rel='import' href='/elements.${lang}/pratilipi-write.html'>
		<link rel='import' href='/elements.${lang}/pratilipi-navigation.html'>
	</head>

	<body>
		<pratilipi-home-page 
			user-data='${ userJson }'
			sections-list='${ sectionsJson }'></pratilipi-home-page>
	</body>

</html>
