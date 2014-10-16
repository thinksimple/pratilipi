<#import "../../../../com/pratilipi/commons/client/PratilipiView.ftl" as pratilipiView>

<!-- PageContent :: Pratilipi List :: Start -->

<div class="container">
	<h1 class="hr-below">${ pratilipisType }</h1>
	<div class="row" id="PageContent-Pratilipi-List-Preloaded" pratilipi-filters="${ pratilipiFilters }" >
		<#list pratilipiDataList as pratilipiData >
			<@pratilipiView.thumbnail pratilipiData=pratilipiData />
		</#list>
	</div>
	<div id="PageContent-Pratilipi-List" ></div>
</div>

<script type="text/javascript" language="javascript" src="/pagecontent.pratilipis/pagecontent.pratilipis.nocache.js" async></script>

<!-- PageContent :: Pratilipi List :: End -->