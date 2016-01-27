<div class="box" style="padding:10px">
	<table>
		<tr>
			<td rowspan="2" style="height:120px;width:80px;">
				<a href="${ pratilipi.pageUrl }">
					<img src="${ pratilipi.getCoverImageUrl( 80 ) }" alt="${ pratilipi.title }" title="${ pratilipi.title }" />
				</a>
			</td>
			<td>
				<h3><a style="text-decoration: none; color: #D0021B;" href="${ pratilipi.pageUrl }">${ pratilipi.title }</a></h3>
				<#if pratilipi.author?? >
					<h4 style="margin-left: 10px;"><a style="text-decoration: none;" href="${ pratilipi.author.pageUrl }">
						${ pratilipi.author.name }
					</a></h4>
				</#if>
				<div style="margin:10px">
					<#assign rating=pratilipi.averageRating >
					<#include "pratilipi-rating.ftl" ><small>(${ pratilipi.ratingCount })</small>
				</div>
			</td>
		</tr>
		<tr>
			<td style="vertical-align:bottom">
				<div style="margin:3px 10px;text-transform:uppercase">
					<a class="btn btn-default red" href="http://www.pratilipi.com/read?id=${ pratilipi.id?c }&ret=${ requestUrl }">${ _strings.read }</a>
				</div>
			</td>
		</tr>
	</table>
</div>