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
<link rel='import' href='/elements.${lang}/pratilipi-user.html?20160414'>
<link rel='import' href='/elements.${lang}/pratilipi-header.html?20160412'>
<link rel='import' href='/elements.${lang}/pratilipi-edit-account.html?2016041103'>
<link rel='import' href='/elements.${lang}/pratilipi-write.html?20160412'>
<link rel='import' href='/elements.${lang}/pratilipi-navigation-drawer.html?03'>
<link rel='import' href='/elements.${lang}/pratilipi-navigation.html?20160227'>
<link rel='import' href='/elements.${lang}/pratilipi-card-grid.html?2016041103'>
<link rel='import' href='/elements.${lang}/pratilipi-footer.html?201603'>
<link rel='import' href='/elements.${lang}/pratilipi-input.html?20160411'>


<#-- Custom Stylesheet -->
<link type="text/css" rel="stylesheet" href="/resources/style.css?20160325">

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
<script>
	function processContentTinyMCE( content ) {
		content = content.replace( /&nbsp;/g, " " );
		<#--
		content = content.replace( /style=\"text-align: left;\"/g, "TEXT-LEFT" );
		content = content.replace( /style=\"text-align: right;\"/g, "TEXT-RIGHT" );
		content = content.replace( /style=\"text-align: center;\"/g, "TEXT-CENTER" );
		content = content.replace( /style=\"text-align: justify;\"/g, "TEXT-JUSTIFY" );
		content = content.replace( /(style=("|\Z)(.*?)("|\Z))|(class=("|\Z)(.*?)("|\Z))|<style>.*?<\/style>|<span.*?>|<\/span>/g, "" );
		content = content.replace( /TEXT-LEFT/g,"style=\"text-align: left;\"" );
		content = content.replace( /TEXT-RIGHT/g,"style=\"text-align: right;\"" );
		content = content.replace( /TEXT-CENTER/g,"style=\"text-align: center;\"" );
		content = content.replace( /TEXT-JUSTIFY/g,"style=\"text-align: justify;\"" );
		-->
		content = JSON.stringify( content );
 		content = content.substring( 1, content.length - 1 );
		return content;
	}
</script>
<#if lang=="gu">
	<script type="text/javascript" id="inspectletjs">
	window.__insp = window.__insp || [];
	__insp.push(['wid', 497579642]);
	(function() {
	function ldinsp(){if(typeof window.__inspld != "undefined") return; window.__inspld = 1; var insp = document.createElement('script'); insp.type = 'text/javascript'; insp.async = true; insp.id = "inspsync"; insp.src = ('https:' == document.location.protocol ? 'https' : 'http') + '://cdn.inspectlet.com/inspectlet.js'; var x = document.getElementsByTagName('script')[0]; x.parentNode.insertBefore(insp, x); };
	setTimeout(ldinsp, 500); document.readyState != "complete" ? (window.attachEvent ? window.attachEvent('onload', ldinsp) : window.addEventListener('load', ldinsp, false)) : ldinsp();
	})();
	</script>
</#if>
