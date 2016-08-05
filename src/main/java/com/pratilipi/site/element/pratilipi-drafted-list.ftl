<div class="pull-left">
	<h5 class="pratilipi-no-margin pratilipi-red">
		${ author.getName() }
	</h5>
	<p class="works-number"> ${ author.getContentDrafted() } ${ _strings.author_drafts }</p>
</div>
<div class="pull-right">
  	<a style="cursor: pointer;" href="${ author.getPageUrl() }">
		<img style="width: 20px;height: 20px;" src="http://0.ptlp.co/resource-all/icon/svg/cross.svg">
  	</a>
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