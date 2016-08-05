<#assign publishedPratilipiList = pratilipiList>
<div class="pull-left">
	<h5 class="pratilipi-red pratilipi-no-margin">
		${ author.getName() }
	</h5>
	<p class="works-number"> ${author.getContentPublished()} ${ _strings.author_count_works }</p>
</div>
<div class="pull-right">
  	<a style="cursor: pointer;" href="${ author.getPageUrl() }">
		<img style="width: 20px;height: 20px;" src="http://0.ptlp.co/resource-all/icon/svg/cross.svg">
  	</a>
</div>
<div class="clearfix"></div>
<hr class="pratilipi-margin-top-2">	

<#include "../element/pratilipi-card-list.ftl">

<#-- Add page navigation -->
<#assign currentPage = pratilipiListPageCurr>
<#assign maxPage = pratilipiListPageMax>
<#include "../element/pratilipi-page-navigation.ftl">