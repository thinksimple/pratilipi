<div class="box">
	<table>
		<tr>
			<td style="height:100px;width:100px;padding-top:20px">
				<img src="${ author.getImageUrl( 100 ) }" alt="${ author.fullName!author.fullNameEn }" title="${ author.fullNameEn!author.fullName }" />
			</td>
			<td>
				<h1>${ author.name!author.nameEn }</h1>
				<#if author.penName?? || author.penNameEn?? >
					<h2>"${ author.penName!author.penNameEn }"</h2>
				</#if>
				<div style="margin:5px 0px 5px 5px">
					<h4>${ _strings.author_since }</h4>
					<span>${ author.registrationDate?date }</span>
				</div>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<hr/>
				<p class="three-cols" style="text-align:center;text-transform:uppercase">
					<small>${ _strings.author_count_works }</small> <br/> <span class="red" style="font-size:20px;font-weight:bold">${ author.contentPublished }</span> <br/>
					<small>${ _strings.author_count_reads }</small> <br/> <span class="red" style="font-size:20px;font-weight:bold">${ author.totalReadCount }</span> <br/>
					<small>${ _strings.author_count_likes }</small> <br/> <span class="red" style="font-size:20px;font-weight:bold">${ author.totalFbLikeShareCount }</span> <br/>
				</p>
			</td>
		</tr>
	</table>
</div>

<#if author.summary?? >
	<div class="box">
		<h2>${ _strings.author_about }</h2>
		<p>${ author.summary }</p>
	</div>
</#if>