<#macro thumbnail pratilipiData>
	<div class="col-lg-2 col-md-3 col-sm-3 col-xs-6" style="margin-bottom:30px;" >
		<div style="width:150px; height:240px;">

			<div class="bg-gray" style="width:150px; height:240px; overflow:hidden;">
				<a href="${ pratilipiData.getPageUrlAlias() }">
					<img src="${ pratilipiData.getCoverImageUrl() }" title="${ pratilipiData.getTitle() }" alt="${ pratilipiData.getTitle() }" class="img-responsive" />
				</a>
			</div>

			<div class="bg-gray bg-translucent" style="position:absolute; bottom:0px; width:150px; padding:5px 10px 5px 10px;">
				<a href="${ pratilipiData.getPageUrlAlias() }" style="display:block">
					<strong style="color:black !important;">${ pratilipiData.getTitle() }</strong>
				</a>
				<#if pratilipiData.getAuthorData()??>
					<#assign authorData=pratilipiData.getAuthorData()>
					<a href="${ authorData.getPageUrlAlias() }" style="display:block">
						<i><small style="color:black !important;">-${ authorData.getFullName() }</small></i>
					</a>
				</#if>
			</div>

		</div>
	</div>
</#macro>


<#macro thumbnail pratilipiData readerRetUrl>
	<div class="col-lg-2 col-md-3 col-sm-3 col-xs-6" style="margin-bottom:30px;" >
		<div style="width:150px;">
			<a href="${ pratilipiData.getReaderPageUrl() }&ret=${ readerRetUrl }">
				<img src="${ pratilipiData.getCoverImageUrl() }" title="${ pratilipiData.getTitle() }" alt="${ pratilipiData.getTitle() }" class="img-responsive" />
			</a>
		</div>
	</div>
</#macro>