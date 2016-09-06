<div class="pull-left">
  	<a style="cursor: pointer;" href="${ author.getPageUrl() }">
		<img style="width: 20px;height: 20px;" src="http://0.ptlp.co/resource-all/icon/svg/arrow-left.svg">
  	</a>
</div>
<div class="align-text-center">
	<h5 class="pratilipi-no-margin pratilipi-red">
		${ author.getName() ! author.getNameEn() }
	</h5>
	<p class="works-number align-text-center"> ${ author.getContentDrafted() } ${ _strings.author_drafts }</p>
</div>	                   
<div class="clearfix"></div>
<hr class="pratilipi-margin-top-2">	

<#list pratilipiList as pratilipi>
	<#include "pratilipi-draft-card-mini.ftl">
</#list>
<#-- Add page navigation -->
	<#assign currentPage = pratilipiListPageCurr>
	<#assign maxPage = pratilipiListPageMax>
	<#include "pratilipi-page-navigation.ftl">