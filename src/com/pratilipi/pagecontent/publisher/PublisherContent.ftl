<#setting time_zone="${ timeZone }">
<#import "../../../../com/pratilipi/commons/client/PratilipiView.ftl" as pratilipiView>

<!-- PageContent :: Publisher :: Start -->

<div style="padding-top: 10px; margin-bottom:20px; background-image : url( '${ publisherData.getPublisherBannerUrl() }'); text-align : center;">
	<h2 style="color: #8B4513 !important;">HARI UVACH</h2>
	<h4><a href="http://shaharidham.org/en/epublication/individual-publication/my-orders">Back to My Order Page</a><h4>
<#if pratilipiDataList?has_content>
	<div class="container">
		<div class="row">
			<#list pratilipiDataList as pratilipiData>
				<@pratilipiView.libThumbnail pratilipiData=pratilipiData readerRetUrl=readerRetUrl />
			</#list>
		</div>
	</div>
</#if>

<div class="container" style="text-align: left;">
	<h3>Reading Instruction</h3>
	<img src="/resource.publisher-banner/original/reading-instruction1.jpg" style="margin-bottom: 10px;" />
	<img src="/resource.publisher-banner/original/reading-instruction2.jpg" style="margin-bottom: 10px;" />
</div>

</div>

<!-- PageContent :: Publisher :: End -->
