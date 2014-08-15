<!doctype html>

<html>
	<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<link href='http://fonts.googleapis.com/css?family=Kalam&subset=latin,devanagari' rel='stylesheet' type='text/css'>
		<link type="text/css" rel="stylesheet" href="/theme.default/style.css">
		<link type="text/css" rel="stylesheet" href="/theme.pratilipi/style.css">

		<script type="text/javascript" language="javascript" src="/pagecontent.homepagecontent/pagecontent.homepagecontent.nocache.js"></script>

		<#if domain == "devo.pratilipi.com" >
		
			<script>
			  (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
			  (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
			  m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
			  })(window,document,'script','//www.google-analytics.com/analytics.js','ga');
			
			  ga('create', 'UA-53742841-1', 'auto');
			  ga('send', 'pageview');
			</script>
		
		<#elseif domain == "www.pratilipi.com" >
		
			<script>
			  (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
			  (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
			  m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
			  })(window,document,'script','//www.google-analytics.com/analytics.js','ga');
			
			  ga('create', 'UA-53742841-2', 'auto');
			  ga('send', 'pageview');
			</script>
		
		</#if>

		<title>Pratilipi</title>		
		
	</head>
	<body>
		
		<#if websiteWidgetHtmlListMap["HEADER"] ??>
			<#list websiteWidgetHtmlListMap["HEADER"] as websiteWidgetHtml>
				${ websiteWidgetHtml }
			</#list>
		</#if>
		
		<#list pageContentHtmlList as pageContentHtml>
			${ pageContentHtml }
		</#list>
		
		<#list websiteWidgetHtmlListMap["FOOTER"] as websiteWidgetHtml>
			${ websiteWidgetHtml }
		</#list>

	</body>
</html>
