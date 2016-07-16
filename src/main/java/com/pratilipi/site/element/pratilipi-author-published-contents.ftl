<div class=" pratilipi-shadow secondary-500 box">	
	<div class="pull-left">
		<h5 class="pratilipi-red pratilipi-bold pratilipi-no-margin">
			<#-- ${ _strings.author_drafts } -->Published
				<!-- <span><button class="pratilipi-grey-button"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span> Add new</button></span> -->
		</h5>
		<p class="works-number"> ${author.getContentPublished()} ${ _strings.author_count_works }</p>
	</div>
	<div class="pull-right">
		<br>	
		<a class="pull-right pratilipi-red" href="#">View More</a>
	</div>
	<div class="clearfix"></div>
	<hr style="margin-top:0px;margin-bottom:0px;">	

	<#if publishedPratilipiList?has_content>
		<#list publishedPratilipiList as pratilipi>
			<#include "pratilipi-pratilipi-card-mini.ftl">
		</#list>
	<#else>
		<div style="padding: 50px 10px;" class="secondary-500 pratilipi-shadow box">
			<img style="width: 48px; height: 48px; margin: 0px auto 20px auto; display: block;" 
					src="https://storage.googleapis.com/devo-pratilipi.appspot.com/icomoon_24_icons/SVG/info.svg" alt="${ _strings.author_no_contents_published }" />
			<div class="text-center">${ _strings.author_no_contents_published }</div>
		</div>
	</#if>
</div>