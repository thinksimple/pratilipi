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

<#-- Custom Stylesheet -->
<link type="text/css" rel="stylesheet" href="/resources/style-basic.css?20151208">

<script>
	window.fbAsyncInit = function() {
		FB.init({
			appId      : '293990794105516',
			cookie     : true,
			xfbml      : true,
			version    : 'v2.0' 
		});
	};
	(function(d, s, id) {
		var js, fjs = d.getElementsByTagName(s)[0];
		if (d.getElementById(id)) return;
		js = d.createElement(s); js.id = id;
		js.src = "//connect.facebook.net/en_US/sdk.js";
		fjs.parentNode.insertBefore(js, fjs);
	}(document, 'script', 'facebook-jssdk'));
</script>



<#include "GoogleAnalytics.ftl">
