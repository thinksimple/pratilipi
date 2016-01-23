<div class="box">
	<div class="row">
		<div class="col-xs-12 col-sm-2 col-md-2 col-lg-2" style="margin: 20px auto; text-align: center;">
			<img style="height: 100px; width: 100px;" src="${ author.getImageUrl( 100 ) }" alt="${ author.fullName!author.fullNameEn }" title="${ author.fullNameEn!author.fullName }" />
		</div>
		<div class="col-xs-12 col-sm-10 col-md-10 col-lg-10" style="text-align: center;">
			<h1>${ author.name!author.nameEn }</h1>
			<#if author.penName?? || author.penNameEn?? >
				<h2>"${ author.penName!author.penNameEn }"</h2>
			</#if>
			<div style="margin:5px 0px 5px 5px">
				<h4>${ _strings.author_since }</h4>
				<span>${ author.registrationDate?date }</span>
			</div>
		</div>
	</div>
	<hr/>
	<div class="row" style="padding: 0px; margin-bottom: 10px;">
		<div style="text-align: center; padding: 10px 0px;" class="col-xs-12 col-sm-4 col-md-4 col-lg-4">
			<h4>${ _strings.author_count_works }</h4>&nbsp;&minus;&nbsp;<span class="red" style="font-size:20px;font-weight:bold">${ author.contentPublished }</span>
		</div>
		<div style="text-align: center; padding: 10px 0px;" class="col-xs-12 col-sm-4 col-md-4 col-lg-4">
			<h4>${ _strings.author_count_reads }</h4>&nbsp;&minus;&nbsp;<span class="red" style="font-size:20px;font-weight:bold">${ author.totalReadCount }</span>
		</div>
		<div style="text-align: center; padding: 10px 0px;" class="col-xs-12 col-sm-4 col-md-4 col-lg-4">
			<h4>${ _strings.author_count_likes }</h4>&nbsp;&minus;&nbsp;<span class="red" style="font-size:20px;font-weight:bold">${ author.totalFbLikeShareCount }</span>
		</div>
	</div>
</div>


<#if author.summary?? >
	<div class="box">
		<h2>${ _strings.author_about }</h2>
		<p>${ author.summary }</p>
	</div>
</#if>