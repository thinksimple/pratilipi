<!-- SENDER_NAME: Team Pratilipi -->
<!-- SENDER_EMAIL: contact@pratilipi.com -->
<!-- SUBJECT: Meet your new follower: ${ follower_name } -->

<hr style="border: 1px solid #eeeeee;">
<table align="left" table-layout="fixed;" style="width: 100%; height: 100%;">
	<tr>
		<td>
			<p style="margin: 15px 0 10px 0; color: #000; font-size: 16px;"><b>${ follower_name }</b> has started following you.</p>
		</td>
	</tr>
</table>
<table style="width: 100%; margin-left: auto; margin-right: auto; border: solid 1px #eeeeee; border-radius: 2px; padding: 10px;">
	<tr>
		<td>
			<table align="left" style="border-collapse: collapse;" cellspacing="0" cellpadding="0">
				<tr>
					<td>
						<a href="${ follower_page_url }" target="_blank">
							<img src="${ follower_profile_image_url }" style="border:0; width: 50px; height: 50px; border-radius: 50%;" />
						</a>
					</td>
					<td width="10">
						&nbsp;&nbsp;&nbsp;
					</td>
					<td width="100%">
						<table style="border-collapse: collapse;">
							<tr>
								<td>
									<a href="${ follower_page_url }" style="color: #000; text-decoration: none; font-size: 14px; line-height: 19px; font-weight: bold;" target="_blank">
										${ follower_name }
									</a>
								</td>
							</tr>
							<#if follower_followers_count??>
								<tr>
									<td>
										<span style="font-size: 14px; line-height: 19px; color: #c7c7c7;">${ follower_followers_count } Followers</span>
									</td>
								</tr>
							</#if>
						</table>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
<table align="center" cellspacing="0" cellpadding="0" table-layout="fixed" style="margin: 0 auto; width:100%;"> 
	<tr>
		<td height="10">
			&nbsp;
		</td>
	</tr>
	<tr>
		<td align="center" height="40" bgcolor="#D0021B" style="-webkit-border-radius: 5px; -moz-border-radius: 5px; border-radius: 5px; color: #ffffff; display: block; width: 100%; margin: auto;">
			<a href="${ follower_page_url }" target="_blank" style="font-size: 18px; text-decoration: none; line-height: 40px; width: 100%; display: inline-block;">
				<span style="color: #ffffff; padding-left: 15px; padding-right: 15px;">
					View ${ follower_name } on Pratilipi
				</span>
			</a>
		</td>
	</tr>
	<tr>
		<td height="32">
			&nbsp;
		</td>
	</tr>
</table>