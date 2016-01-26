<div class="box">
	<div class="row">
		<div class="col-xs-12 col-sm-2 col-md-2 col-lg-2" style="margin: 20px auto; text-align: center;">
			<img src="${ pratilipi.getCoverImageUrl( 100 ) }" alt="${ pratilipi.title!pratilipi.titleEn }" title="${ pratilipi.titleEn!pratilipi.title }" />
		</div>
		<div class="col-xs-12 col-sm-10 col-md-10 col-lg-10" style="text-align: center;">
			<h1>${ pratilipi.title!pratilipi.titleEn }</h1>
			<#if pratilipi.author?? >
				<h2><a href="${ pratilipi.author.pageUrlAlias!pratilipi.author.pageUrl }">
					${ pratilipi.author.name }
				</a></h2>
			</#if>
			
			
			<div style="padding-top: 25px;">
				<#assign rating=pratilipi.averageRating >
				<#include "pratilipi-rating.ftl" > (${rating})
				<br/>
				<br/>
				<h4>${ pratilipi.type }</h4>
			</div>
			
			<div style="margin:25px 0px 5px 5px">
				<#if pratilipi.listingDateMillis?? >
					<h4>${ _strings.pratilipi_listing_date }</h4>
					<span>&nbsp;&minus;&nbsp;${ pratilipi.listingDateMillis }</span>
					<br/>
				</#if>
				
				<h4>${ _strings.pratilipi_count_reads }</h4>
				<span>&nbsp;&minus;&nbsp;${ pratilipi.readCount }</span>
			</div>
			
			<div style="padding-top: 20px; padding-bottom: 20px;">
				<a class="btn btn-default red" href="http://www.pratilipi.com/read?id=${ pratilipi.id?c }">${ _strings.read }</a>
			</div>
			
		</div>
	</div>
</div>

<#if pratilipi.summary?? >
	<div class="box">
		<h2>${ _strings.pratilipi_summary }</h2>
		<p>${ pratilipi.summary }</p>
	</div>
</#if>