<div class="secondary-500 pratilipi-shadow box text-center">
	<h2 class="pratilipi-red">${ author.name!author.nameEn }</h2>
	<#if author.penName?? || author.penNameEn?? >
		<h4>"${ author.penName!author.penNameEn }"</h4>
	</#if>
	<div style="width: 200px; height: 200px; margin: 15px auto;" class="pratilipi-shadow">
		<img src="${ author.getImageUrl( 200 ) }" alt="${ author.fullName!author.fullNameEn }" title="${ author.fullNameEn!author.fullName }" />
	</div>
	
	<h5 style="margin: 12px auto;">${ _strings.author_since }&nbsp;&minus;&nbsp;${ author.registrationDate?date }</h5>
	
	<hr/>
	<div class="row" style="padding: 0px; margin-bottom: 10px;">
		<div style="text-align: center; padding: 5px 0px;" class="col-xs-12 col-sm-4 col-md-4 col-lg-4">
			<h5>${ _strings.author_count_works }&nbsp;&minus;&nbsp;${ author.contentPublished }</h5>
		</div>
		<div style="text-align: center; padding: 5px 0px;" class="col-xs-12 col-sm-4 col-md-4 col-lg-4">
			<h5>${ _strings.author_count_reads }&nbsp;&minus;&nbsp;${ author.totalReadCount }</h5>
		</div>
		<div style="text-align: center; padding: 5px 0px;" class="col-xs-12 col-sm-4 col-md-4 col-lg-4">
			<h5>${ _strings.author_count_likes }&nbsp;&minus;&nbsp;${ author.totalFbLikeShareCount }</h5>
		</div>
	</div>
</div>


<#if author.summary?? >
	<div class="secondary-500 pratilipi-shadow box">
		<h2 style="margin-top: 10px; margin-bottom: 15px;" class="pratilipi-red text-center">${ _strings.author_about }</h2>
		<div style="text-align: justify;">${ author.summary }</div>
	</div>
</#if>

<div style="min-height: 7px;"></div>
