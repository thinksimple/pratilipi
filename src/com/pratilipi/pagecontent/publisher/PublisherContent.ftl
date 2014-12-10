<#setting time_zone="${ timeZone }">
<#import "../../../../com/pratilipi/commons/client/PratilipiView.ftl" as pratilipiView>

<!-- PageContent :: Publisher :: Start -->

<#if pratilipiDataList?has_content>
	<div class="container">
		<div class="row">
			<#list pratilipiDataList as pratilipiData>
				<@pratilipiView.thumbnail pratilipiData=pratilipiData />
			</#list>
		</div>
	</div>
</#if>


<!-- PageContent :: Publisher :: End -->
