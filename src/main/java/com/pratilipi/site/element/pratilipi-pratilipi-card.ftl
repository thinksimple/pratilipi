<div class="box">
	<table>
		<tr>
			<td style="height:150px;width:100px">
				<a href="${ pratilipi.pageUrlAlias!pageUrl }">
					<img src="${ pratilipi.getCoverImageUrl( 100 ) }" alt="${ pratilipi.title }" title="${ pratilipi.titleEn }" />
				</a>
			</td>
			<td>
				<h3><a href="${ pratilipi.pageUrlAlias!pageUrl }">${ pratilipi.title }</a></h3>
				<#if pratilipi.author?? >
					<h4>"${ pratilpi.author.name }"</h4>
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