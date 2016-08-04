<#assign publishedPratilipiList = pratilipiList>
<div class="pull-left">
	<h5 class="pratilipi-red pratilipi-no-margin">
		${ _strings.author_published_works }
	</h5>
	<p class="works-number"> ${author.getContentPublished()} ${ _strings.author_count_works }</p>
</div>
<div class="clearfix"></div>
<hr class="pratilipi-margin-top-2">	

<#include "../element/pratilipi-card-list.ftl">

<#-- Add page navigation -->
<#assign currentPage = pratilipiListPageCurr>
<#assign maxPage = pratilipiListPageMax>
<#include "../element/pratilipi-page-navigation.ftl">