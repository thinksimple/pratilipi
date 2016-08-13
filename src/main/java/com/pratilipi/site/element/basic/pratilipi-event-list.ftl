<div class="pratilipi-shadow secondary-500 box">
	<div class="align-text-center">
		<h2 class="pratilipi-red pratilipi-no-margin">
			${ _strings.event_entries }
		</h2>
	</div>
</div>

<#list pratilipiList as pratilipi>
	<#include "pratilipi-pratilipi-card.ftl">
</#list>

<#-- Add page navigation -->
<#assign currentPage = pratilipiListPageCurr>
<#assign maxPage = pratilipiListPageMax>
<#include "pratilipi-page-navigation.ftl">