<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">

<#-- Page Title & Favicon -->
<title>${ title }</title>
<link rel="shortcut icon" type="image/png" href="/favicon.png">

<#-- Third-Party Library -->
<#list resourceList as resource>
	${ resource }
</#list>

<#-- Polymer 1.0 Custom Elements -->
<link rel='import' href='/elements.${lang}/pratilipi-user.html?20160312'>
<link rel='import' href='/elements.${lang}/pratilipi-header.html?201603'>
<link rel='import' href='/elements.${lang}/pratilipi-edit-account.html?201603'>
<link rel='import' href='/elements.${lang}/pratilipi-write.html?20160312'>
<link rel='import' href='/elements.${lang}/pratilipi-navigation-drawer.html?03'>
<link rel='import' href='/elements.${lang}/pratilipi-navigation.html?20160227'>
<link rel='import' href='/elements.${lang}/pratilipi-card-grid.html?20160227'>
<link rel='import' href='/elements.${lang}/pratilipi-footer.html?2016022026'>
<link rel='import' href='/elements.${lang}/pratilipi-input.html'>


<#-- Custom Stylesheet -->
<link type="text/css" rel="stylesheet" href="/resources/style.css?2016030312">

<#include "GoogleAnalytics.ftl">

<script>
	var didScroll;
	var lastScrollTop = 0;
	var delta = 10;
	var navbarHeight = 75;
	
	window.onscroll = function() {
		var st = $(this).scrollTop();
		document.querySelector( '${ mainPage }' ).scrollHandler( st );
		if( Math.abs( lastScrollTop - st ) <= delta )
				return;
		if( st > lastScrollTop && st > navbarHeight )
			$( 'header' ).removeClass( 'nav-down' ).addClass( 'nav-up' );
		else if( st + $(window).height() < $(document).height() || st < navbarHeight )
			$( 'header' ).removeClass( 'nav-up' ).addClass( 'nav-down' );
		lastScrollTop = st; 
	};
</script>
