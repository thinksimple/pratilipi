<table align="left" table-layout="fixed;" style="width: 100%; height: 100%;">
	<tr>
		<td>
			<hr style="border: 1px solid #eeeeee;">
			<p style="margin: 15px 0 10px 0;">Your story <b>${ pratilipi_title }</b> has been published.</p>
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
				<tr>
					<td colspan="3">
						<span style="font-size: 14px; line-height: 19px; color: #646464;">
							${ pratilipi_summary }
						</span>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>