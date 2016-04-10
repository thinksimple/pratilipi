<!DOCTYPE html>
<html lang="${lang}">

	<head>
		<#-- Page Description -->
		<meta name="description" content="A platform to discover, read and share your favorite stories, poems and books in a language, device and format of your choice.">
		
		<#include "meta/HeadBasic.ftl">
		
	</head>

	<body>
		<#include "../element/pratilipi-header.ftl">
			<div class="parent-container">
				<div class="container">
					<#list eventList as event>
						<div class="secondary-500 pratilipi-shadow box" style="padding: 16px 5px;">
							<div class="row">
								<div class="col-xs-12 col-sm-12 col-md-4 col-lg-4">
									<a href="${ event.pageUrl }">
										<img style="width: 200px; height: 150px; margin: 5px auto; display: block;" src="${ event.getBannerImageUrl(300) }" alt="${ event.name }" class="pratilipi-shadow" />
									</a>
								</div>
								<div style="padding-left: 30px; padding-right: 20px;" class="col-xs-12 col-sm-12 col-md-8 col-lg-8">
									<a href="${ event.pageUrl }"><h4 class="pratilipi-red">${ event.name }</h4></a>
									<div style="max-height: 120px; overflow: hidden; margin-top: 15px;" class="text-muted">${ event.description }</div>
									<a class="pratilipi-blue" href="${ event.pageUrl }" style="position: absolute; right: 20px; top: 8px;">
										More...
									</a>
								</div>
							</div>
						</div>
					</#list>
				</div>
			</div>		
			<#include "../element/pratilipi-footer.ftl">
	</body>
	
</html>