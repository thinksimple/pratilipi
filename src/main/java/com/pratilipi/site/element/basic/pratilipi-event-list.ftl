<#include "pratilipi-pratilipi-card.ftl">
<div class="pratilipi-shadow secondary-500 box">
	<div class="align-text-center">
		<h2 class="pratilipi-red pratilipi-no-margin">
			${ _strings.event_entries }
		</h2>
	</div>
</div>

<#list pratilipiList as local_pratilipi>
	<@pratilipi_card from="event_list" pratilipi=local_pratilipi />
</#list>

<#-- Add page navigation -->
<#assign currentPage = pratilipiListPageCurr>
<#assign maxPage = pratilipiListPageMax>
<#include "pratilipi-page-navigation.ftl">