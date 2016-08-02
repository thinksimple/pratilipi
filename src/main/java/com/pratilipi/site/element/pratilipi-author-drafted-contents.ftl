<#if author.hasAccessToUpdate()==true >
	<#assign draftsUrl = "/search?authorId=" + author.getId()?c + "&state=DRAFTED">
	<div class="pratilipi-shadow secondary-500 box">	
		<div class="pull-left">
			<a href="${ draftsUrl }"><h5 class="pratilipi-no-margin">
				${ _strings.author_drafts }
					<#-- <span><button class="pratilipi-grey-button"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span> Add new</button></span> -->
			</h5></a>
			<a href="${ draftsUrl }"><p class="works-number"> ${ author.getContentDrafted() } ${ _strings.author_drafts }</p></a>
		</div>
		<div class="pull-right">
			<a class="pull-right pratilipi-red pratilipi-view-more-link" href="${ draftsUrl }"> ${ _strings.view_more } </a>
		</div>                   
		<div class="clearfix"></div>
		<hr>	
		<#if draftedPratilipiList?has_content>
			<#list draftedPratilipiList as pratilipi>
				<#include "pratilipi-draft-card-mini.ftl">
			</#list>
		</#if>			
	</div>
</#if>
