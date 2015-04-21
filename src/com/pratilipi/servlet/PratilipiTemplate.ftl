<!DOCTYPE html>
<html lang="en">
	<head>
	
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
		
		<#if request.getRequestURI() == "/">
			<meta name="description" content="A platform to discover, read and share your favorite stories, poems and books in a language, device and format of your choice.">
		</#if>

		<link rel="shortcut icon" type="image/png" href="/theme.pratilipi/favicon.png">

		<#if basicMode>
			<script src="//j.pratilipi.info/third-party/jquery-1.11.1/jquery-1.11.1.min.js"></script>
			<script src="//b.pratilipi.info/third-party/bootstrap-3.2.0/js/bootstrap.min.js" defer></script>
			<link rel="stylesheet" href="//b.pratilipi.info/third-party/bootstrap-3.2.0/css/bootstrap.min.css">
			<script src="//j.pratilipi.info/third-party/jquery-file-upload-9.7.0/js/vendor/jquery.ui.widget.js" defer></script>
			<script src="//j.pratilipi.info/third-party/jquery-file-upload-9.7.0/js/jquery.iframe-transport.js" defer></script>
			<script src="//j.pratilipi.info/third-party/jquery-file-upload-9.7.0/js/jquery.fileupload.js" defer></script>
			<script src="//c.pratilipi.info/third-party/ckeditor-4.4.5-full/ckeditor.js" charset="utf-8" defer></script>
			<script language="javascript" defer>
				window.onload = function() {
					CKEDITOR.config.toolbar = [
							['Source','Format','Bold','Italic','Underline','Strike','-','Subscript','Superscript','-','RemoveFormat'],
							['JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock','-','Outdent','Indent'],
							['NumberedList','BulletedList'],
							['Blockquote','Smiley','HorizontalRule','PageBreak'],
							['Link','Unlink'],
							['Cut','Copy','Paste','PasteText','PasteFromWord','-','Undo','Redo'],
							['ShowBlocks','Maximize']];
					CKEDITOR.config.toolbar_BASIC = [
							['Bold','Italic','Underline','Strike','-','Subscript','Superscript','-','RemoveFormat'],
							['NumberedList','BulletedList'],
							['Blockquote','Smiley','HorizontalRule'],
							['Link','Unlink']];	
					
				}				
			</script>
		</#if>


		<#list resourceTagList as resourceTag>
			${ resourceTag }
		</#list>
		
		<meta property="og:image" content="http://www.pratilipi.com/theme.pratilipi/logo-200x200.png">

		<#if basicMode>
			<link type="text/css" rel="stylesheet" href="/theme.pratilipi/style.basicmode.min.css">
			<script type="text/javascript" language="javascript" src="/theme.pratilipi/script.basicmode.min.js" defer></script>
		<#else>
			<link type="text/css" rel="stylesheet" href="/theme.pratilipi/style.min.css">
			<script type="text/javascript" language="javascript" src="/theme.pratilipi/script.min.js" defer></script>
		</#if>

		<#if page.getType()! != "READ" && page.getType()! != "WRITE">
			<script type="text/javascript" language="javascript" src="/pagecontent.userforms/pagecontent.userforms.nocache.js?20150421" async></script>
		</#if>

		<title>${ (page.getTitle() + " &#0187 Pratilipi") ! "Pratilipi" }</title>		
		
		<!-- For Google Analytics tracking -->
		<script language="javascript">
			(function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
			(i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
			m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
			})(window,document,'script','//www.google-analytics.com/analytics.js','ga');
			
			<#if userId == 0>
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
		
		<!-- Facebook Conversion-Tracking Pixel Code -->
		<script>
			(function() {
				var _fbq = window._fbq || (window._fbq = []);
				if (!_fbq.loaded) {
					var fbds = document.createElement('script');
					fbds.async = true;
					fbds.src = '//connect.facebook.net/en_US/fbds.js';
					var s = document.getElementsByTagName('script')[0];
					s.parentNode.insertBefore(fbds, s);
					_fbq.loaded = true;
				}
			})();
			window._fbq = window._fbq || [];
			window._fbq.push(['track', '6017683737248', {'value':'0.01','currency':'INR'}]);
		</script>
		<noscript>
			<img height="1" width="1" alt="" style="display:none" src="https://www.facebook.com/tr?ev=6017683737248&amp;cd[value]=0.01&amp;cd[currency]=INR&amp;noscript=1" />
		</noscript>
		
		<!-- Facebook Custom Audience Pixel -->
		<script>
			(function() {
				var _fbq = window._fbq || (window._fbq = []);
				if (!_fbq.loaded) {
					var fbds = document.createElement('script');
					fbds.async = true;
					fbds.src = '//connect.facebook.net/en_US/fbds.js';
					var s = document.getElementsByTagName('script')[0];
					s.parentNode.insertBefore(fbds, s);
					_fbq.loaded = true;
				}
				_fbq.push(['addPixelId', '1569748966613739']);
			})();
			window._fbq = window._fbq || [];
			window._fbq.push(['track', 'PixelInitialized', {}]);
		</script>
		<noscript>
			<img height="1" width="1" alt="" style="display:none" src="https://www.facebook.com/tr?id=1569748966613739&amp;ev=PixelInitialized" />
		</noscript>

	</head>
	<#if basicMode>
		<body>
			<span id="Claymus-Loading">Loading...</span>
			<#if websiteWidgetHtmlListMap["HEADER"] ??>
				<#list websiteWidgetHtmlListMap["HEADER"] as websiteWidgetHtml>
					${ websiteWidgetHtml }
				</#list>
			</#if>
	<#else>
		<body fullbleed layout vertical>
			<span id="Claymus-Loading">Loading...</span>
			<template unresolved is="auto-binding" id="Polymer">
				<#if websiteWidgetHtmlListMap["HEADER"] ??>
					<core-scroll-header-panel flex id="Polymer-Window">
						<core-toolbar>
							<div flex>
								Header
							</div>
						</core-toolbar>
				</#if>
	</#if>
		

		<#list pageContentHtmlList as pageContentHtml>
			${ pageContentHtml }
		</#list>
	
		<#if websiteWidgetHtmlListMap["FOOTER"] ??>
			<#list websiteWidgetHtmlListMap["FOOTER"] as websiteWidgetHtml>
				${ websiteWidgetHtml }
			</#list>
		</#if>
			
		<#if !basicMode>
				<#if websiteWidgetHtmlListMap["HEADER"] ??>
					</core-scroll-header-panel>
				</#if>
			</template>
		</#if>

	</body>
</html>
