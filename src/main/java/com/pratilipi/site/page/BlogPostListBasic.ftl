<!DOCTYPE html>
<html lang="${lang}">

	<head>
		<#-- Page Description -->
		<meta name="description" content="A platform to discover, read and share your favorite stories, poems and books in a language, device and format of your choice.">
		
		<#include "meta/HeadBasic.ftl">
		
		<style>
			img {
				max-width: 100%!important;
			}
		</style>
		
	</head>

	<body>
		<#include "../element/pratilipi-header.ftl">
			<div class="parent-container">
				<div class="container">
					<#list blogPostList as blogPost>
						<div class="secondary-500 pratilipi-shadow box" style="padding: 16px 12px;">
							<a class="pratilipi-blue pull-right" href="${ blogPost.getPageUrl() }">
								${ _strings.view_more }...
							</a>
							<a href="${ blogPost.getPageUrl() }">
								<h4 style="display: inline-block;" class="pratilipi-red">${  blogPost.getTitle() }</h4>
							</a>
							<div class="text-muted" style="max-height: 160px; overflow: hidden;">${ blogPost.getContent() }</div>
						</div>
					</#list>
				</div>
			</div>		
			<#include "../element/pratilipi-footer.ftl">
	</body>
	
</html>