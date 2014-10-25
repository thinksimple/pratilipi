<#import "../../../../com/pratilipi/commons/client/PratilipiView.ftl" as pratilipiView>

<!-- PageContent :: Pratilipis :: Start -->

<div class="container">
	<h1 class="hr-below">${ pratilipisType }</h1>
	<div class="row" id="PageContent-Pratilipis-Preloaded">
		<#list pratilipiDataList as pratilipiData >
			<@pratilipiView.thumbnail pratilipiData=pratilipiData />
		</#list>
	</div>
	<div id="PageContent-Pratilipis" ></div>
</div>

<div id="PageContent-Pratilipis-EncodedData" style="display:none;">${ pratilipiFilterEncodedStr }</div>
<script type="text/javascript" language="javascript" src="/pagecontent.pratilipis/pagecontent.pratilipis.nocache.js" async></script>

<!-- PageContent :: Pratilipis :: End -->