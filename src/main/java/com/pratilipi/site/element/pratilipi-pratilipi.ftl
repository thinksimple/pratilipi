<div class="box">
	<table>
		<tr>
			<td style="height:150px;width:100px">
				<a href="${ pratilipi.pageUrlAlias!pratilipi.pageUrl }">
					<img src="${ pratilipi.getCoverImageUrl( 100 ) }" alt="${ pratilipi.title!pratilipi.titleEn }" title="${ pratilipi.titleEn!pratilipi.title }" />
				</a>
			</td>
			<td>
				<h1><a href="${ pratilipi.pageUrlAlias!pratilipi.pageUrl }">${ pratilipi.title!pratilipi.titleEn }</a></h1>
				<#if pratilipi.author?? >
					<h2><a href="${ pratilipi.author.pageUrlAlias!pratilipi.author.pageUrl }">
						${ pratilipi.author.fullName!pratilipi.author.fullNameEn }
					</a></h2>
				</#if>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<hr/>
				<p class="two-cols" style="text-align:center;text-transform:uppercase;">
					<small>${ _strings.pratilipi_count_reads }</small> <br/> <span class="red" style="font-size:20px;font-weight:bold">${ pratilipi.readCount }</span> <br/>
					<small>${ _strings.pratilipi_count_likes }</small> <br/> <span class="red" style="font-size:20px;font-weight:bold">${ pratilipi.fbLikeShareCount }</span> <br/>
				</p>
			</td>
		</tr>
	</table>
</div>

<#if pratilipi.summary?? >
	<div class="box">
		<h2>${ _strings.pratilipi_summary }</h2>
		<p>${ pratilipi.summary }</p>
	</div>
</#if>