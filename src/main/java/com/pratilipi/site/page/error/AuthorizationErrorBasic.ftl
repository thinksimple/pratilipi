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
						<img src="/stylesheets/Authorization.png" alt="Img">
					</div>
					<div class="media-body" style="padding-left: 15px;">
						<h4><b>Error!</b></h4>
						<h2>Not authorized.</h2>
						<p>Sorry! We can't allow you to view this page.</p>
						<p>You can still search for your favorite content in the search bar on top<br>
							or head over to the home page.</p> <br>
						<a class="btn btn-default red" href="/">Home</a>
	    			</div>
				</div>
			</div>
			
			<#include "../../element/pratilipi-navigation.ftl">
			<#include "../../element/pratilipi-footer.ftl">
		</div>
	</body>
	
</html>