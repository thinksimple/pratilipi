<!DOCTYPE html>
<html lang="${lang}">

	<head>
		<#-- Page Description -->
		<meta name="description" content="A platform to discover, read and share your favorite stories, poems and books in a language, device and format of your choice.">
		
		<#include "../meta/HeadBasic.ftl">
	</head>

	<body>
		<div class="container">
			<#include "../../element/pratilipi-header.ftl">

			<div class="box">
				<div class="media" style="padding: 20px;">
					<div class="media-left">
						<img src="/stylesheets/Server.png" alt="Img">
					</div>
					<div class="media-body" style="padding-left: 20px;">
						<h4><b>Error!</b></h4>
						<h2>Server Error.</h2>
						<p>Sorry! Looks like something is wrong with our server.</p>
						<p>Please try again after a few minutes, or use the search bar to find<br>
						great content, or just head over to the Pratilipi homePage.</p> <br>
						<a class="btn btn-default red" href="/">Home</a>
	    			</div>
				</div>
			</div>
			
			<#include "../../element/pratilipi-navigation.ftl">
			<#include "../../element/pratilipi-footer.ftl">
		</div>
	</body>
	
</html>