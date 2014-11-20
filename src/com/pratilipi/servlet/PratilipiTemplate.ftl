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

		<#if showBasicVersion>
			<script src="//cdn-asia.pratilipi.com/third-party/jquery-1.11.1/jquery-1.11.1.min.js" defer></script>
			<script src="//cdn-asia.pratilipi.com/third-party/bootstrap-3.2.0/js/bootstrap.min.js" defer></script>
			<link rel="stylesheet" href="//cdn-asia.pratilipi.com/third-party/bootstrap-3.2.0/css/bootstrap.min.css">
			<script src="//cdn-asia.pratilipi.com/third-party/jquery-file-upload-9.7.0/js/vendor/jquery.ui.widget.js" defer></script>
			<script src="//cdn-asia.pratilipi.com/third-party/jquery-file-upload-9.7.0/js/jquery.iframe-transport.js" defer></script>
			<script src="//cdn-asia.pratilipi.com/third-party/jquery-file-upload-9.7.0/js/jquery.fileupload.js" defer></script>
			<script src="//cdn-asia.pratilipi.com/third-party/ckeditor-4.4.5-full/ckeditor.js" charset="utf-8" defer></script>
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
		<#else>
			<script src="//cdn-asia.pratilipi.com/third-party/polymer-0.5.1/webcomponentsjs/webcomponents.js"></script>
			<link rel="import" href="//cdn-asia.pratilipi.com/third-party/polymer-0.5.1/core-collapse/core-collapse.html">
			<link rel="import" href="//cdn-asia.pratilipi.com/third-party/polymer-0.5.1/core-icon-button/core-icon-button.html">
			<link rel="import" href="//cdn-asia.pratilipi.com/third-party/polymer-0.5.1/core-icons/core-icons.html">
			<link rel="import" href="//cdn-asia.pratilipi.com/third-party/polymer-0.5.1/core-item/core-item.html">
			<link rel="import" href="//cdn-asia.pratilipi.com/third-party/polymer-0.5.1/core-menu/core-menu.html">
			<link rel="import" href="//cdn-asia.pratilipi.com/third-party/polymer-0.5.1/core-header-panel/core-header-panel.html">
			<link rel="import" href="//cdn-asia.pratilipi.com/third-party/polymer-0.5.1/core-scroll-header-panel/core-scroll-header-panel.html">
			<link rel="import" href="//cdn-asia.pratilipi.com/third-party/polymer-0.5.1/core-toolbar/core-toolbar.html">
			<link rel="import" href="//cdn-asia.pratilipi.com/third-party/polymer-0.5.1/font-roboto/roboto.html">
			<link rel="import" href="//cdn-asia.pratilipi.com/third-party/polymer-0.5.1/paper-button/paper-button.html">
			<link rel="import" href="//cdn-asia.pratilipi.com/third-party/polymer-0.5.1/paper-dialog/paper-action-dialog.html">
			<link rel="import" href="//cdn-asia.pratilipi.com/third-party/polymer-0.5.1/paper-dialog/paper-dialog.html">
			<link rel="import" href="//cdn-asia.pratilipi.com/third-party/polymer-0.5.1/paper-fab/paper-fab.html">
			<link rel="import" href="//cdn-asia.pratilipi.com/third-party/polymer-0.5.1/paper-dropdown/paper-dropdown.html">
			<link rel="import" href="//cdn-asia.pratilipi.com/third-party/polymer-0.5.1/paper-icon-button/paper-icon-button.html">
			<link rel="import" href="//cdn-asia.pratilipi.com/third-party/polymer-0.5.1/paper-item/paper-item.html">
			<link rel="import" href="//cdn-asia.pratilipi.com/third-party/polymer-0.5.1/paper-menu-button/paper-menu-button.html">
			<link rel="import" href="//cdn-asia.pratilipi.com/third-party/polymer-0.5.1/paper-slider/paper-slider.html">
		</#if>

		<link type="text/css" rel="stylesheet" href="/theme.default/style.css">
		<script type="text/javascript" language="javascript" src="/theme.default/script.js" defer></script>
		<link type="text/css" rel="stylesheet" href="/theme.pratilipi/style.css">
		<script type="text/javascript" language="javascript" src="/theme.pratilipi/script.js" defer></script>
			
		<script type="text/javascript" language="javascript" src="/pagecontent.userforms/pagecontent.userforms.nocache.js" async></script>

		<title>${ (page.getTitle() + " | Pratilipi") ! "Pratilipi" }</title>		
		
	</head>
	<body <#if !showBasicVersion>fullbleed layout vertical</#if>>
		
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
