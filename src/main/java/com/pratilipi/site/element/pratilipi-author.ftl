<div class="box">
	<table>
		<tr>
			<td style="height:100px;width:100px;padding-top:20px">
				<img src="${ author.getImageUrl( 100 ) }" alt="${ author.fullName }" title="${ author.fullNameEn }" />
			</td>
			<td>
				<h1>${ author.name }</h1>
				<#if author.penName?? >
					<h2>"${ author.penName }"</h2>
				</#if>
				<div style="margin:5px 10px 5px 10px">
					<h3>${ _strings.author_since }</h3>
					<span>${ author.registrationDate?date }</span>
				</div>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<hr/>
				<p class="three-cols" style="text-align:center">
					<small>${ _strings.author_count_works }</small> <br/> <span class="red" style="font-size:20px;font-weight:bold">${ author.contentPublished }</span> <br/>
					<small>${ _strings.author_count_reads }</small> <br/> <span class="red" style="font-size:20px;font-weight:bold">${ author.totalReadCount }</span> <br/>
					<small>${ _strings.author_count_likes }</small> <br/> <span class="red" style="font-size:20px;font-weight:bold">${ author.totalLikeCount! 99 }</span> <br/>
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