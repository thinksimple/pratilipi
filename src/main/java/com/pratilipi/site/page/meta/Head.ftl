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
<link rel='import' href='http://0.ptlp.co/resource-all/polymer.pratilipi.html'>
<link rel='import' href='/elements.${lang}/pratilipi-custom-elements.html?31'>

<#-- Custom Stylesheet -->
<link type="text/css" rel="stylesheet" href="/resources/style.css?201607">

<#include "GoogleAnalytics.ftl">

<#if language == "HINDI">
	<script type="text/javascript">
	  (function(e,t){var n=e.amplitude||{_q:[],_iq:{}};var r=t.createElement("script");r.type="text/javascript";
	  r.async=true;r.src="https://d24n15hnbwhuhn.cloudfront.net/libs/amplitude-3.0.1-min.gz.js";
	  r.onload=function(){e.amplitude.runQueuedFunctions()};var i=t.getElementsByTagName("script")[0];
	  i.parentNode.insertBefore(r,i);function s(e,t){e.prototype[t]=function(){this._q.push([t].concat(Array.prototype.slice.call(arguments,0)));
	  return this}}var o=function(){this._q=[];return this};var a=["add","append","clearAll","prepend","set","setOnce","unset"];
	  for(var u=0;u<a.length;u++){s(o,a[u])}n.Identify=o;var c=function(){this._q=[];return this;
	  };var p=["setProductId","setQuantity","setPrice","setRevenueType","setEventProperties"];
	  for(var l=0;l<p.length;l++){s(c,p[l])}n.Revenue=c;var d=["init","logEvent","logRevenue","setUserId","setUserProperties","setOptOut","setVersionName","setDomain","setDeviceId","setGlobalUserProperties","identify","clearUserProperties","setGroup","logRevenueV2","regenerateDeviceId"];
	  function v(e){function t(t){e[t]=function(){e._q.push([t].concat(Array.prototype.slice.call(arguments,0)));
	  }}for(var n=0;n<d.length;n++){t(d[n])}}v(n);n.getInstance=function(e){e=(!e||e.length===0?"$default_instance":e).toLowerCase();
	  if(!n._iq.hasOwnProperty(e)){n._iq[e]={_q:[]};v(n._iq[e])}return n._iq[e]};e.amplitude=n;
	  })(window,document);
	
	  amplitude.getInstance().init("db959c1578a66b007dbf8b995b57da14");
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