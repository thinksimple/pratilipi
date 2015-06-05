<#setting time_zone="${ timeZone }">
<#import "../../../../com/pratilipi/commons/client/PratilipiView.ftl" as pratilipiView>

<!-- PageContent :: Publisher :: Start -->

<div style="padding-top: 50px; margin-bottom:20px; background-image : url( '${ publisherData.getPublisherBannerUrl() }'); text-align : center;">
	<img src="/resource.publisher-banner/original/SHA-logo.png" style="height:68px;"/>
	<h2 style="color: #8B4513 !important; margin-top: 0px;">HARI UVACH</h2>
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

<div class="container" style="text-align: left; color: red;">
	<h3 style="color: red !important;">Reading Instruction</h3>
	<p>1. Click on (+) button to enlarge font size and (-) to reduce font size</p>
	<img src="/resource.publisher-banner/original/BasicReaderChangeSizeIcons.jpg " style="margin-bottom: 10px;" />
	<p>or click on side line to get text option</p>
	<img src="/resource.publisher-banner/original/ReaderMenuIcon.jpg" style="margin-bottom: 10px; width:96px; height:68px;"/>
	<p>2. Move slider for go to any page number, push button ">" for next and "<" for previous page</p>
	<img src="/resource.publisher-banner/original/Slider.jpg" style="margin-bottom: 10px;height:68px" />
	<img src="/resource.publisher-banner/original/ReaderChangePageIcons.jpg" style="margin-left: 10px; margin-bottom: 10px;height:68px;" />
	<p>or Click to type page number and press set or Enter/return</p>
	<img src="/resource.publisher-banner/original/BasicReaderPageNumber.jpg " style="margin-bottom: 10px;height:68px;" />
	
</div>

</div>

<!-- PageContent :: Publisher :: End -->
