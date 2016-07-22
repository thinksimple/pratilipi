<!DOCTYPE html>
<html lang="${lang}">

	<head>
		<#-- Page Description -->
		<meta name="description" content="A platform to discover, read and share your favorite stories, poems and books in a language, device and format of your choice.">
		
		<#include "meta/HeadBasic.ftl">
		
		<style>
			.title-holder {
				margin: 4px 0px 8px 0px;
			}
			.title-holder .blog-title {
				font-size: 18px;
			}
			.title-holder .view-more {
				font-size: 14px;
				float: right;
				color: #094c89;
			}
			.content-summary {
				max-height: 120px; 
				overflow: hidden;
				padding-right: 4px;
				text-align: justify;
				font-size: 14px;
				line-height: 20px;
			}
		</style>
		
	</head>

	<body>
		<#include "../element/pratilipi-header.ftl">
			<div class="parent-container">
				<div class="container">
					<#list blogPostList as blogPost>
						<div class="secondary-500 pratilipi-shadow box" style="padding: 12px 16px;">
							<div class="title-holder">
								<a class="pratilipi-red blog-title" href="${ blogPost.getPageUrl() }">${  blogPost.getTitle() }</a>
								<a class="view-more" href="${ blogPost.getPageUrl() }">${ _strings.view_more }...</a>
							</div>
							<div class="text-muted content-summary">${ blogPost.getContentText() }</div>
						</div>
					</#list>
				</div>
			</div>		
			<#include "../element/pratilipi-footer.ftl">
	</body>
	
</html>