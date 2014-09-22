<#import "../../../../com/pratilipi/commons/client/PratilipiView.ftl" as pratilipiView>

<!-- PageContent :: ${ pratilipiType } List :: Start -->

<#if showAddOption>
	<div id="PageContent-${ pratilipiType }-DataInput"></div>
</#if>

<div class="container">
	<h2 class="hr-below">${ pratilipisType }</h2>
	<div class="row" id="PageContent-${ pratilipiType }-List" pratilipi-filters="${ pratilipiFilters }" >
		<#list pratilipiDataList as pratilipiData >
			<@pratilipiView.thumbnail pratilipiData=pratilipiData />
		</#list>
	</div>
</div>

<script type="text/javascript" language="javascript" src="/pagecontent.pratilipis/pagecontent.pratilipis.nocache.js" async></script>

<!-- PageContent :: ${ pratilipiType } List :: End -->