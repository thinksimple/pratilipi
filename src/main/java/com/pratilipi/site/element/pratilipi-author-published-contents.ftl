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
	
	<#include "pratilipi-pratilipi-card-mini.ftl">
	<#include "pratilipi-pratilipi-card-mini.ftl">		
</div>