<#-- For Google Analytics tracking -->

<script language="javascript">

	(function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
	(i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
	m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
	})(window,document,'script','//www.google-analytics.com/analytics.js','ga');

	ga('create', 'UA-53742841-2', 'pratilipi.com');
	ga('require', 'displayfeatures');
	ga('set', 'dimension2', '${ ga_website }');
	ga('set', 'dimension3', '${ ga_websiteMode }');
	ga('set', 'dimension4', '${ ga_websiteVersion }');

	ga('send', 'pageview');

	function triggerGlobalGAEvent(  eventCategory, eventAction, eventLabel, eventValue ) {
		ga('send', 'event', eventCategory, eventAction, eventLabel, eventValue);
	}	

</script>