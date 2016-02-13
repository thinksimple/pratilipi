<!DOCTYPE html>
<html lang="${lang}">

	<head>
		<#include "meta/Head.ftl">
	</head>
	
	<body>
		<dom-module id="pratilipi-static-page">
			<template>
				<paper-scroll-header-panel on-content-scroll="scrollHandler" id="paperScrollHeaderPanel" header-height="75">
					<div class="paper-header">
						<pratilipi-header user='[[ user ]]'></pratilipi-header>
					</div>
					<div class="margin-top-bottom">
						<pratilipi-user user='{{ user }}' user-data='${ userJson }'></pratilipi-user>
						<pratilipi-edit-account user='[[ user ]]'></pratilipi-edit-account>
						<pratilipi-write pratilipi-types='${ pratilipiTypesJson }'></pratilipi-write>
						<div class="parent-container">
							<div class="container">
								<pratilipi-navigation
									class='pull-left hidden-xs hidden-sm'
									></pratilipi-navigation>
								<div class='secondary-500' style='padding:20px; overflow:hidden'>
									<h3>${ title }</h3>
									<div>${ content }</div>
								</div>
							</div>
						</div>
						<pratilipi-footer></pratilipi-footer>
						<template is="dom-if" if="{{ displayScrollTopButton }}">
							<div class="scroll-top-button">
								<a on-click="scrollToTop"><img src="http://0.ptlp.co/resource-all/icon/footer/arrow_up_transparent_64.png"/></a>
							</div>
						</template>
					</div>
				</paper-scroll-header-panel>
			</template>
			<script>
				HTMLImports.whenReady(function () {
					Polymer({
						is: 'pratilipi-static-page',
						
						properties: {
							lastScrollTop: { type: Number, value: 0 },
							displayScrollTopButton: { type: Boolean, value: false }
						},
						
						scrollToTop: function() {
							this.$.paperScrollHeaderPanel.scrollToTop( true );
						},
						
						scrollHandler: function( event ) {
							var st = event.detail.target.scrollTop;
							if( st > this.lastScrollTop || st == 0 )
								this.displayScrollTopButton = false;
							else
								this.displayScrollTopButton = true;
							this.lastScrollTop = st;
						}
					});
				});
			</script>
		</dom-module>
		<pratilipi-static-page></pratilipi-static-page>
	</body>
</html>
