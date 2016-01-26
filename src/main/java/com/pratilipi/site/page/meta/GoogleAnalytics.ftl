<#-- For Google Analytics tracking -->

<script language="javascript">

	(function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
	(i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
	m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
	})(window,document,'script','//www.google-analytics.com/analytics.js','ga');
	
	<#if user.state == 'GUEST'>
		ga('create', 'UA-53742841-2', 'auto' );
		ga('require', 'displayfeatures');
		ga('send', 'pageview');
	<#else>
		ga('create', 'UA-53742841-2', { 'userId': ${ userId?c } });
		ga('require', 'displayfeatures');
		ga('set', 'dimension1', ${ userId?c });
		ga('send', 'pageview');
	</#if>
	
</script>
