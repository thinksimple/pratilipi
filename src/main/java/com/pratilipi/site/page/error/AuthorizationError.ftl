<!DOCTYPE html>
<html>
	<head>
		<#-- Page Description -->
		<meta name="description" content="A platform to discover, read and share your favorite stories, poems and books in a language, device and format of your choice.">
		<#assign mainPage="pratilipi-authorization-error">
		<#include "../meta/Head.ftl">
	</head>
	<body>
		<dom-module id="pratilipi-authorization-error">
			<template>
				<pratilipi-user user='{{ user }}' user-data='${ userJson }'></pratilipi-user>
				<pratilipi-write pratilipi-types='${ pratilipiTypesJson }'></pratilipi-write>
				<pratilipi-alert></pratilipi-alert>
				<div class="header">
		   			<pratilipi-header language-map='${ languageMap }' user='[[ user ]]'></pratilipi-header>
		   		</div>
		   		<main>
					<div class="parent-container margin-top-bottom">
						<div class="container">
							<pratilipi-navigation
								class='pull-left hidden-xs hidden-sm'
								navigation-list='${ navigationList }'
								></pratilipi-navigation>
							<#-- Navigation & Search bar for extra-small & small screens. -->
							<pratilipi-navigation-drawer with-backdrop navigation-list='${ navigationList }'></pratilipi-navigation-drawer>
							<div style="overflow:hidden; padding: 0 2px;">
								<paper-card style="padding-bottom: 100px; margin-bottom: 10px;">
									<div class="media" style="padding: 20px;">
										<div class="media-left">
											<img src="/stylesheets/Authorization.png" alt="Img">
										</div>
										<div class="media-body" style="padding-left: 35px;">
											<h4><b>Error!</b></h4>
											<h2>Not authorized.</h2>
											<p>Sorry! We can't allow you to view this page.</p>
											<p>You can still search for your favorite content in the search bar on top<br>
												or head over to the home page.</p> <br>
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
					<a id="scrollToTop" on-click="scrollToTop"><img src="http://0.ptlp.co/resource-all/icon/footer/arrow_up_transparent_64.png"/></a>
				</div>
			</template>
			<script>
				HTMLImports.whenReady(function () {
					Polymer({
						is: 'pratilipi-authorization-error',
						
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
		<pratilipi-authorization-error></pratilipi-authorization-error>
	</body>
</html>
