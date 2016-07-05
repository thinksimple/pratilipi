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
<link rel='import' href='http://0.ptlp.co/resource-all/elements.vulcanized.html'>
<link rel='import' href='/elements.${lang}/pratilipi-custom-elements.html?12'>

<#-- Custom Stylesheet -->
<link type="text/css" rel="stylesheet" href="/resources/style.css?201607">

<#include "GoogleAnalytics.ftl">

<#if language == "HINDI">
	<#-- Hotjar Tracking Code for http://www.pratilipi.com -->
	<script>
		(function(h,o,t,j,a,r){
			h.hj=h.hj||function(){(h.hj.q=h.hj.q||[]).push(arguments)};
			h._hjSettings={hjid:243839,hjsv:5};
			a=o.getElementsByTagName('head')[0];
			r=o.createElement('script');r.async=1;
			r.src=t+h._hjSettings.hjid+j+h._hjSettings.hjsv;
			a.appendChild(r);
		})(window,document,'//static.hotjar.com/c/hotjar-','.js?sv=');
	</script>
</#if>


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
<script defer>
	function processContentTinyMCE( content ) {
		content = content.replace( /&nbsp;/g, " " );
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
