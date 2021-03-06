<!DOCTYPE html>
<html>
	<head>
		<#-- Page Description -->
		<meta name="description" content="A platform to discover, read and share your favorite stories, poems and books in a language, device and format of your choice.">
		<#assign mainPage="pratilipi-server-error">
		<#include "../meta/Head.ftl">
		<style>
			main {
				padding-top: 54px;
			}
		</style>
	</head>
	<body>
		<#include "../meta/PolymerDependencies.ftl">
		<dom-module id="pratilipi-server-error">
			<template>
				<pratilipi-user user='{{ user }}' user-data='${ userJson }'></pratilipi-user>
				<pratilipi-write pratilipi-types='${ pratilipiTypesJson }'></pratilipi-write>
				<pratilipi-alert></pratilipi-alert>
				<div class="header-pos">
		   			<pratilipi-header language-map='${ languageMap }' user='[[ user ]]'></pratilipi-header>
		   		</div>
		   		<main>
					<div class="parent-container margin-top-bottom">
						<div class="container">
							<pratilipi-navigation
								class='pull-left hidden-xs hidden-sm'
								navigation-list='${ navigationListJson }'
								></pratilipi-navigation>
							<#-- Navigation & Search bar for extra-small & small screens. -->
							<pratilipi-navigation-drawer with-backdrop navigation-list='${ navigationListJson }'></pratilipi-navigation-drawer>
							<div style="overflow:hidden; padding: 0 2px;">
								<paper-card style="padding-bottom: 100px; margin-bottom: 10px;">
									<div class="media" style="padding: 20px;">
										<div class="media-left">
											<img src="/stylesheets/Server.png" alt="Img">
										</div>
										<div class="media-body" style="padding-left: 35px;">
											<h4><b>Error!</b></h4>
											<h2>Server Error.</h2>
											<p>Sorry! Looks like something is wrong with our server.</p>
											<p>Please try again after a few minutes, or use the search bar to find<br>
											great content, or just head over to the Pratilipi homePage.</p> <br>
											<a class="pratilipi-light-blue-button" href="/">Home</a>
						    			</div>
									</div>
								</paper-card>
							</div>
						</div>
					</div>
				</main>
				<footer>
    				<pratilipi-footer></pratilipi-footer>
				</footer>
				<div class="scroll-top-button">
					<a id="scrollToTop" on-click="scrollToTop"><paper-fab mini noink style="background: #c0c0c0;" icon="icons:arrow-upward"></paper-fab></a>
				</div>
			</template>
			<script>
				HTMLImports.whenReady(function () {
					Polymer({
						is: 'pratilipi-server-error',
						
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
		<pratilipi-server-error></pratilipi-server-error>
		<#include "../meta/Foot.ftl">
	</body>
</html>