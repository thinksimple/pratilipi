<div class="websitewidget-header">

	<table style="width: 100%; border-collapse: collapse;">
		<tr>
			<td style="padding:0px; text-align:left;">
				<#list getLeftLinks() as link>
					<a href="${ link[1] }">${ link[0] }</a>
				</#list>
			</td>
			<td style="padding:0px; text-align:center;">
				<a href="/" class="title">${ getTitle() }</a>
			</td>
			<td style="padding:0px; text-align:right;">
				<#list getRightLinks() as link>
					<a href="${ link[1] }">${ link[0] }</a>
				</#list>
			</td>
		</tr>
	</table>

</div>