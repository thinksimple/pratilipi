<!DOCTYPE html>
<html lang="${lang}">

	<head>
		<#assign mainPage="pratilipi-static-page">
		<#include "meta/Head.ftl">
	</head>
	
	<body>
		<dom-module id="pratilipi-static-page">
			<template>
				<header class="nav-down">
					<pratilipi-header user='[[ user ]]'></pratilipi-header>
				</header>
				<main>
					<pratilipi-user user='{{ user }}' user-data='${ userJson }'></pratilipi-user>
					<pratilipi-edit-account user='[[ user ]]'></pratilipi-edit-account>
					<pratilipi-write pratilipi-types='${ pratilipiTypesJson }'></pratilipi-write>
					<div class="parent-container margin-top-bottom">
						<div class="container">
							<pratilipi-navigation
								class='pull-left hidden-xs hidden-sm'
								navigation-list='${ navigationList }'
								></pratilipi-navigation>
							<#-- Navigation & Search bar for extra-small & small screens. -->
							<pratilipi-navigation-drawer with-backdrop navigation-list='${ navigationList }'></pratilipi-navigation-drawer>
							<div class='secondary-500' style='padding:20px; overflow:hidden'>
								<h3>${ title }</h3>
								<div style="text-align: justify;">${ content }</div>
							</div>
						</div>
					</div>
				</main>
				<footer>
    				<pratilipi-footer></pratilipi-footer>
				</footer>
				<div class="scroll-top-button">
					<a id="scrollToTop" on-click="scrollToTop"><img src="http://0.ptlp.co/resource-all/icon/footer/arrow_up_transparent_64.png"/></a>
				</div>
			</template>
			<script>
				HTMLImports.whenReady(function () {
					Polymer({
						is: 'pratilipi-static-page',
						
						properties: {
							lastScrollTop: { type: Number, value: 0 }
						},
						
						scrollToTop: function() {
							$( 'html, body' ).animate( { scrollTop : 0 },800 );
						},
						
						ready: function() {
							jQuery( '#scrollToTop' ).css( "display", "none" );
						},
						
						scrollHandler: function( st ) {
							if( st > this.lastScrollTop || st < 100 )
								jQuery( '#scrollToTop' ).fadeOut();
							else
								jQuery( '#scrollToTop' ).fadeIn();
							this.lastScrollTop = st;
						}
					});
				});
			</script>
		</dom-module>
		<pratilipi-static-page></pratilipi-static-page>
	</body>
</html>
