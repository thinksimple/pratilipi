<!DOCTYPE html>
<html>
	<head>
		<#-- Page Description -->
		<meta name="description" content="A platform to discover, read and share your favorite stories, poems and books in a language, device and format of your choice.">
		<#include "meta/Head.ftl">
	</head>
	<body class="fullbleed layout vertical">
		<template is="dom-bind">
			<paper-header-panel class="flex" mode="waterfall">
				<div class="paper-header">
					<pratilipi-user user='{{ user }}' user-data='${ userJson }'></pratilipi-user>
					<pratilipi-header user='[[ user ]]'></pratilipi-header>
					<pratilipi-edit-account user='[[ user ]]'></pratilipi-edit-account>
					<pratilipi-write></pratilipi-write>
				</div>
				<div class="fit" style="margin-top: 5px;">
					<div class="parent-container">
						<div class="container">
							<paper-card>
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
										<a class="pratilipi-light-blue-button" href="http://tamil.pratilipi.com">Home</a>
					    			</div>
								</div>
							</paper-card>
						</div>
					</div>
					<div style="position: absolute; bottom: 0px; width: 100%;">
						<pratilipi-footer></pratilipi-footer>
					</div>
				</div>
			</paper-header-panel>
		</template>
	</body>
</html>
