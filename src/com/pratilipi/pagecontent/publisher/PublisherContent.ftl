<#setting time_zone="${ timeZone }">
<#import "../../../../com/pratilipi/commons/client/PratilipiView.ftl" as pratilipiView>

<!-- PageContent :: Publisher :: Start -->

<div style="padding-top: 10px; margin-bottom:20px; background-image : url( '${ publisherData.getPublisherBannerUrl() }'); text-align : center;">
	<h3>HARI UVACH</h3>
	<h4><a href="#">Back to My Order Page</a><h4>
<#if pratilipiDataList?has_content>
	<div class="container">
		<div class="row">
			<#list pratilipiDataList as pratilipiData>
				<@pratilipiView.libThumbnail pratilipiData=pratilipiData readerRetUrl=readerRetUrl />
			</#list>
		</div>
	</div>
</#if>
</div>

<!-- PageContent :: Publisher :: End -->
