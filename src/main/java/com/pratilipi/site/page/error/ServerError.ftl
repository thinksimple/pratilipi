<!DOCTYPE html>
<html>
	<head>
		<#-- Page Description -->
		<meta name="description" content="A platform to discover, read and share your favorite stories, poems and books in a language, device and format of your choice.">
		<#include "../meta/Head.ftl">
	</head>
	<body class="fullbleed layout vertical">
		<template is="dom-bind">
			<paper-header-panel class="flex" mode="waterfall">
				<div class="paper-header">
					<pratilipi-user user='{{ user }}' user-data='${ userJson }'></pratilipi-user>
					<pratilipi-header user='[[ user ]]'></pratilipi-header>
					<pratilipi-edit-account user='[[ user ]]'></pratilipi-edit-account>
					<pratilipi-write pratilipi-types='${ pratilipiTypesJson }'></pratilipi-write>
				</div>
				<div class="fit" style="margin-top: 5px;">
					<div class="parent-container">
						<div class="container">
							<pratilipi-navigation
								class='pull-left hidden-xs hidden-sm'
								></pratilipi-navigation>
							<div style="overflow:hidden">
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
					<pratilipi-footer></pratilipi-footer>
				</div>
			</paper-header-panel>
		</template>
	</body>
</html>
