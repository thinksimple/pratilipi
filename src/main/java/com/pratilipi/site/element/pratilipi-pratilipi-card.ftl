<div class="box">
	<table>
		<tr>
			<td style="height:150px;width:100px">
				<a href="${ pratilipi.pageUrlAlias!pageUrl }">
					<img src="${ pratilipi.getCoverImageUrl( 100 ) }" alt="${ pratilipi.title!pratilipi.titleEn }" title="${ pratilipi.titleEn!pratilipi.title }" />
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
					<#include "pratilipi-rating.ftl" >
				</div>
				<div style="margin:10px">
					${ _strings.pratilipi_free }
				</div>
			</td>
		</tr>
	</table>
</div>