<div class="box" style="padding:5px 15px 0px 15px">
	<table>
		<tr>
			<td rowspan="2" style="height:120px;width:80px;padding-top:15px">
				<a href="${ pratilipi.pageUrlAlias!pageUrl }">
					<img src="${ pratilipi.getCoverImageUrl( 80 ) }" alt="${ pratilipi.title!pratilipi.titleEn }" title="${ pratilipi.titleEn!pratilipi.title }" />
				</a>
			</td>
			<td>
				<h3><a href="${ pratilipi.pageUrlAlias!pratilipi.pageUrl }">${ pratilipi.title!pratilipi.titleEn }</a></h3>
				<#if pratilipi.author?? >
					<h4><a href="${ pratilipi.author.pageUrlAlias!pratilipi.author.pageUrl }">
						"${ pratilipi.author.name!pratilipi.author.nameEn }"
					</a></h4>
				</#if>
				<div style="margin:10px">
					<#assign rating=pratilipi.averageRating >
					<#include "pratilipi-rating.ftl" > (${rating})
				</div>
			</td>
		</tr>
		<tr>
			<td style="vertical-align:bottom">
				<div style="margin:3px 10px;text-transform:uppercase">
					<button type="button">${ _strings.pratilipi_read }</button>
				</div>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<hr/>
				<p class="three-cols" style="text-align:center">
					<span>${ pratilipi.readCount } ${ _strings.pratilipi_count_reads }</span> <br/>
					<span>${ pratilipi.fbLikeShareCount } ${ _strings.pratilipi_count_likes }</span> <br/>
					<span>${ pratilipi.reviewCount } ${ _strings.pratilipi_count_reviews }</span> <br/>
				</p>
			</td>
		</tr>
	</table>
</div>