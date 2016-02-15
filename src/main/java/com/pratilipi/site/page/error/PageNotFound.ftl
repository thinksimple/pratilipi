<!DOCTYPE html>
<html>
	<head>
		<#-- Page Description -->
		<meta name="description" content="A platform to discover, read and share your favorite stories, poems and books in a language, device and format of your choice.">
		<#include "../meta/Head.ftl">
		<script>
			var didScroll;
			$( window ).scroll( function( event ) {
				didScroll = true;
			});
			
			setInterval( function() {
				if( didScroll ) {
					document.querySelector( 'pratilipi-page-not-found-error' ).scrollHandler( $(this).scrollTop() );
					didScroll = false;
				}
			}, 30);
		</script>
	</head>
	
	<body>
		<dom-module id="pratilipi-page-not-found-error">
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
								></pratilipi-navigation>
							<div style="overflow:hidden">
								<paper-card style="padding-bottom: 100px; margin-bottom: 10px;">
									<div class="media" style="padding: 20px;">
										<div class="media-left">
											<img src="/stylesheets/PageNotFound.png" alt="Img">
										</div>
							
										<div class="media-body" style="padding-left: 35px;">
											<h4><b>Error 404</b></h4>
											<h2>Page not found.</h2>
											<p>The page you are looking for isn't here.</p>
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
						is: 'pratilipi-page-not-found-error',
						
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
		<pratilipi-page-not-found-error></pratilipi-page-not-found-error>
	</body>
</html>
