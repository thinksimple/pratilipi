<#setting time_zone="${ timeZone }">
<#import "../../../../com/pratilipi/commons/client/PratilipiView.ftl" as pratilipiView>

<!-- PageContent :: Publisher :: Start -->

<div style="margin-bottom:20px;">
	<img src="${ publisherData.getPublisherBannerUrl() }" style="width:100%">
</div>
	
<#if pratilipiDataList?has_content>
	<div class="container">
		<div class="row">
			<#list pratilipiDataList as pratilipiData>
				<@pratilipiView.thumbnail pratilipiData=pratilipiData readerRetUrl=readerRetUrl />
			</#list>
		</div>
	</div>
</#if>


<!-- PageContent :: Publisher :: End -->
