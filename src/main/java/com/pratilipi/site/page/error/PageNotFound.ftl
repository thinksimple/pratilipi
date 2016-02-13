<!DOCTYPE html>
<html>
	<head>
		<#-- Page Description -->
		<meta name="description" content="A platform to discover, read and share your favorite stories, poems and books in a language, device and format of your choice.">
		<#include "../meta/Head.ftl">
	</head>
	
	<body class="fullbleed layout vertical">
		<dom-module id="pratilipi-page-not-found-error">
			<template>
				<paper-scroll-header-panel on-content-scroll="scrollHandler" id="paperScrollHeaderPanel" header-height="75">
					<div class="paper-header">
						<pratilipi-header user='[[ user ]]'></pratilipi-header>
					</div>
					<div class="fit margin-top-bottom">
						<pratilipi-user user='{{ user }}' user-data='${ userJson }'></pratilipi-user>
						<pratilipi-edit-account user='[[ user ]]'></pratilipi-edit-account>
						<pratilipi-write pratilipi-types='${ pratilipiTypesJson }'></pratilipi-write>
						<div class="parent-container">
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
						<pratilipi-footer></pratilipi-footer>
						<template is="dom-if" if="{{ displayScrollTopButton }}">
							<div class="scroll-top-button">
								<a on-click="scrollToTop"><img src="https://storage.googleapis.com/devo-pratilipi.appspot.com/arrow_up_transparent_64.png"/></a>
							</div>
						</template>
					</div>
				</paper-scroll-header-panel>
			</template>
			<script>
				HTMLImports.whenReady(function () {
					Polymer({
						is: 'pratilipi-page-not-found-error',
						
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
		<pratilipi-page-not-found-error></pratilipi-page-not-found-error>
	</body>
</html>
