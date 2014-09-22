<#macro thumbnail pratilipiData>
	<div class="col-lg-2 col-md-3 col-sm-3 col-xs-6" style="margin-bottom:30px;" >
		<div style="width:150px; height:240px;">
			<div class="bg-gray" style="width:150px; height:240px; overflow:hidden;">
				<a href="${ pratilipiData.getPageUrl() }">
					<img src="${ pratilipiData.getCoverImageUrl() }" title="${ pratilipiData.getTitle() }" alt="${ pratilipiData.getTitle() }" class="img-responsive" />
				</a>
			</div>
			<div class="bg-gray bg-translucent" style="position:absolute; bottom:0px; width:150px; padding:5px 10px 5px 10px;">
				<a href="${ pratilipiData.getPageUrl() }" style="display:block">
					<strong style="color:black !important;">${ pratilipiData.getTitle() }</strong>
				</a>
				<a href="${ pratilipiData.getAuthorPageUrl() }" style="display:block">
					<i><small style="color:black !important;">-${ pratilipiData.getAuthorName() }</small></i>
				</a>
			</div>
		</div>
	</div>
</#macro>