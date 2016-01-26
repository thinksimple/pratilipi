<!DOCTYPE html>
<html lang="${lang}">

	<head>
		<#include "meta/Head.ftl">

		<#-- Polymer 1.0 Custom Elements -->
		<link rel='import' href='/elements.${lang}/pratilipi-search-page.html'>
		<link rel='import' href='/elements.${lang}/pratilipi-user.html'>
		<link rel='import' href='/elements.${lang}/pratilipi-header.html'>
		<link rel='import' href='/elements.${lang}/pratilipi-edit-account.html'>
		<link rel='import' href='/elements.${lang}/pratilipi-write.html'>
		<link rel='import' href='/elements.${lang}/pratilipi-navigation.html'>
	</head>

	<body>
		<pratilipi-search-page 
			user-data='${ userJson }'
			pratilipi-list='${ pratilipiListJson }'
			search-query='${ pratilipiListSearchQuery! }'
			filter='${ pratilipiListFilterJson }'
			cursor='${ pratilipiListCursor! }'></pratilipi-search-page>
	</body>
</html>
