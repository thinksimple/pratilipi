<#import "../../../../com/claymus/commons/client/ui/Social.ftl" as social>


<!-- PageContent :: AuthorDashboard :: Start -->


<@pratilipiTable title="Books"    pratilipiDataList=bookDataList/>
<@pratilipiTable title="Stories"  pratilipiDataList=storyDataList/>
<@pratilipiTable title="Poems"    pratilipiDataList=poemDataList/>
<@pratilipiTable title="Articles" pratilipiDataList=articleDataList/>


<#macro pratilipiTable title pratilipiDataList>
	<#if pratilipiDataList?has_content>
		<div class="container">
			<div class="table-responsive">
				<table class="table table-hover">
					<thead>
						<tr>
							<th><h3>${ title }</h3></th>
							<th>Read Count</th>
							<th>Facebook</th>
						</tr>
					</thead>
					<#list pratilipiDataList as pratilipiData>
						<tr>
							<td><a href="${ pratilipiData.getPageUrlAlias() ! pratilipiData.getPageUrl() }">${ pratilipiData.getTitle() }</a></td>
							<td>${ pratilipiData.getReadCount() }</td>
							<td>
								<@social.facebook shareUrl="http://${ domain }${ pratilipiData.getPageUrl() }" />
							</td>
						</tr>
					</#list>
				</table>
			</div>
		</div>
	</#if>
</#macro>


<!-- PageContent :: AuthorDashboard :: End -->
