<!DOCTYPE html>
<html lang="${lang}">

	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">

		<#-- Page Title & Favicon -->
		<title>${ pratilipi.title } <#if pratilipi.titleEn??>/ ${ pratilipi.titleEn }</#if> &#0171; <#if pratilipi.author.name??>${ pratilipi.author.name } / </#if>${ pratilipi.author.nameEn } &#0171; ${ _strings.pratilipi }</title>		
		<link rel="shortcut icon" type="image/png" href="/favicon.png">

		<#-- Third-Party Library -->
		<#list resourceList as resource>
			${ resource }
		</#list>

		<#-- Polymer 1.0 Custom Elements -->
		<link rel='import' href='/elements.${lang}/pratilipi-user.html'>
		<link rel='import' href='/elements.${lang}/pratilipi-header.html'>
		<link rel='import' href='/elements.${lang}/pratilipi-userpratilipi.html'>
		<link rel='import' href='/elements.${lang}/pratilipi-navigation.html'>
		<link rel='import' href='/elements.${lang}/pratilipi-pratilipi.html'>
		<link rel='import' href='/elements.${lang}/pratilipi-review-list.html'>
		<link rel='import' href='/elements.${lang}/pratilipi-footer.html'>
		

		<#-- Custom Stylesheet -->
		<link type="text/css" rel="stylesheet" href="/resources/style.css">

		<script defer>
			jQuery( window ).scroll( function() {
				document.querySelector( 'pratilipi-review-list' ).loadMore();
			} );
		</script>
	</head>
	
	<body>
		<template is="dom-bind">

			<pratilipi-user user='{{ user }}' user-data='${ userJson }'></pratilipi-user>
			<pratilipi-header user='{{ user }}'></pratilipi-header>

			<pratilipi-userpratilipi
					user='{{ user }}'
					pratilipi-id='${ pratilipi.getId()?c }'
					userpratilipi='{{ userpratilipi }}'
					userpratilipi-data='${ userpratilipiJson! }'
					></pratilipi-userpratilipi>
		
			<div class="container" style="margin-top:10px; margin-bottom:10px">
				<pratilipi-navigation
						class='pull-left hidden-xs hidden-sm'
						></pratilipi-navigation>
				<div style="overflow:hidden">
					<pratilipi-pratilipi
							user="{{ user }}"
							pratilipi='{{ pratilipi }}'
							pratilipi-data='${ pratilipiJson }'
							userpratilipi='{{ userpratilipi }}'
							></pratilipi-pratilipi>
					<pratilipi-review-list
							id="ReviewSection"
							user="{{ user }}"
							pratilipi='{{ pratilipi }}'
							userpratilipi='{{ userpratilipi }}'
							review-list='${ reviewListJson }'
							pratilipi-id='${ pratilipi.getId()?c }'
							cursor='${ reviewListCursor! }'
							></pratilipi-review-list>
				</div>
			</div>
		
			<pratilipi-footer></pratilipi-footer>

		</template>
	</body>
	
</html>
