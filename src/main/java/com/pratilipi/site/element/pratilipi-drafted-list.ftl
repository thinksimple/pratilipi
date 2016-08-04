<div class="pull-left">
	<a href="${ draftsUrl }"><h5 class="pratilipi-no-margin pratilipi-red">
		${ _strings.author_drafts }
			<#-- <span><button class="pratilipi-grey-button"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span> Add new</button></span> -->
	</h5></a>
	<a href="${ draftsUrl }"><p class="works-number"> ${ author.getContentDrafted() } ${ _strings.author_drafts }</p></a>
</div>	                   
<div class="clearfix"></div>
<hr class="pratilipi-margin-top-2">	

<#list pratilipiList as pratilipi>
	<#include "pratilipi-draft-card-mini.ftl">
</#list>
<#-- Add page navigation -->
	<#assign currentPage = pratilipiListPageCurr>
	<#assign maxPage = pratilipiListPageMax>
	<#include "../element/pratilipi-page-navigation.ftl">