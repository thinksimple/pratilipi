<table align="left" table-layout="fixed;" style="width: 100%; height: 100%;">
	<tr>
		<td>
			<hr style="border: 1px solid #eeeeee;">
			<#if notification_to_author ??>
				<p style="margin: 15px 0 10px 0;">Your story <b>${ pratilipi_title }</b> has been published.</p>
			<#elseif notification_to_follower ??>
				<p style="margin: 15px 0 10px 0;">${ author_name } has published a new story <b>${ pratilipi_title }</b>.</p>
			</#if>
		</td>
	</tr>
</table>
<table style="width: 100%; margin-left: auto; margin-right: auto;">
	<tr>
		<td style="border: solid 1px #eeeeee; border-radius: 2px; padding: 10px; display: block;">
			<table style="border-collapse: collapse;" cellspacing="0" cellpadding="0">
				<tr>
					<td>
						<a href="${ pratilipi_page_url }" target="_blank">
							<img src="${ pratilipi_cover_image_url }" style="border:0; width: 50px; height: auto;" />
						</a>
					</td>
					<td style="display: block; width: 10px;">
						&nbsp;&nbsp;&nbsp;
					</td>
					<td width="100%">
						<table style="border-collapse: collapse;">
							<tr>
								<td>
									<a href="${ pratilipi_page_url }" style="text-decoration: none; font-size: 16px; line-height: 21px; font-weight: bold; color: #333;"  target="_blank">
										${ pratilipi_title }
									</a>
								</td>
							</tr>
							<tr>
								<td style="font-size: 14px; line-height: 19px; color: #c7c7c7;">
									<a href="${ author_page_url }" style="color: #141823; text-decoration: none; font-size: 14px; line-height: 19px; font-weight: bold;" target="_blank">
										${ author_name }
									</a>
								</td>
							</tr>
							<tr>
								<td style="font-size: 14px; line-height: 19px; color: #646464;">
									${ pratilipi_listing_date }
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td style="line-height: 10px" height="10">
						&nbsp;
					</td>
				</tr>
				<#if pratilipi_summary??>
					<tr>
						<td colspan="3">
							<span style="font-size: 14px; line-height: 19px; color: #646464;">
								${ pratilipi_summary }
							</span>
						</td>
					</tr>
				</#if>
			</table>
		</td>
	</tr>
</table>
<table align="center" cellspacing="0" cellpadding="0" table-layout="fixed" style="margin: 0 auto; width:100%;"> 
	<tr>
		<td style="line-height: 10px" height="10">
			&nbsp;
		</td>
	</tr>
	<tr>
		<td align="center" height="40" bgcolor="#D0021B" style="-webkit-border-radius: 5px; -moz-border-radius: 5px; border-radius: 5px; color: #ffffff; display: block; width: 90%; margin: auto;">   
			<a href="${ pratilipi_page_url }" target="_blank" style="font-size: 18px; text-decoration: none; line-height: 40px; width: 100%; display: inline-block;">
				<span style="color: #ffffff; padding-left: 15px; padding-right: 15px;">
					View on Pratilipi
				</span>
			</a>
		</td>
	</tr>
</table>