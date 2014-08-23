<!DOCTYPE html>
<html lang="en">
	<head>
	
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
	
		<link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">
		<link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap-theme.min.css">
	
		<script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js" defer></script>
		<script src="//maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js" defer></script>
	
		<script src="//cdn.ckeditor.com/4.4.3/full/ckeditor.js" defer></script>
	
		<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
		<!--[if lt IE 9]>
			<script src="//oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
			<script src="//oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
		<![endif]-->
	
	
		<script type="text/javascript" language="javascript" src="/pagecontent.homepagecontent/pagecontent.homepagecontent.nocache.js" async></script>
	
		<link type="text/css" rel="stylesheet" href="/theme.default/style.css">
		<link type="text/css" rel="stylesheet" href="/theme.pratilipi/style.css">
		<script type="text/javascript" language="javascript" src="/theme.pratilipi/aboutmenu.js"></script>

		<#if domain == "devo.pratilipi.com">
		
			<script async>
			  (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
			  (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
			  m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
			  })(window,document,'script','//www.google-analytics.com/analytics.js','ga');
			
			  ga('create', 'UA-53742841-1', 'auto');
			  ga('send', 'pageview');
			</script>
		
		<#elseif domain == "www.pratilipi.com" >
		
			<script async>
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
		
		<#if websiteWidgetHtmlListMap["FOOTER"] ??>
			<#list websiteWidgetHtmlListMap["FOOTER"] as websiteWidgetHtml>
				${ websiteWidgetHtml }
			</#list>
		</#if>

	</body>
</html>
