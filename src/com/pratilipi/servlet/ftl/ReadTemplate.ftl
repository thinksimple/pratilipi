<!DOCTYPE html>
<html lang="en">
	<head>
	
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">

		<link rel="shortcut icon" type="image/png" href="/theme.pratilipi/favicon.png">

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
	
		<link type="text/css" rel="stylesheet" href="/theme.default/style.css">
		<link type="text/css" rel="stylesheet" href="/theme.pratilipi/style.css">

		<script type="text/javascript" language="javascript" src="/theme.default/script.js" defer></script>
		<script type="text/javascript" language="javascript" src="/theme.pratilipi/script.js" defer></script>
	
		<title>${ (page.getTitle() + " | Pratilipi") ! "Pratilipi" }</title>		
		
	</head>
	<body fullbleed layout vertical>
		<#list pageContentHtmlList as pageContentHtml>
			${ pageContentHtml }
		</#list>
	</body>
</html>
