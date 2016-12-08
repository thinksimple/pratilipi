<#if rating??>
	<#if rating <= 0.5 >
		<#assign img_name="0_5">
	<#elseif rating <= 1 >
		<#assign img_name="1_0">
	<#elseif rating <= 1.5 >
		<#assign img_name="1_5">	
	<#elseif rating <= 2 >
		<#assign img_name="2_0">	
	<#elseif rating <= 2.5 >
		<#assign img_name="2_5">	
	<#elseif rating <= 3 >
		<#assign img_name="3_0">	
	<#elseif rating <= 3.5 >
		<#assign img_name="3_5">	
	<#elseif rating <= 4 >
		<#assign img_name="4_0">	
	<#elseif rating <= 4.5 >
		<#assign img_name="4_5">
	<#elseif rating <= 5 >
		<#assign img_name="5_0">		
	</#if>
</#if>



<!-- SENDER_NAME: Team Pratilipi -->
<!-- SENDER_EMAIL: contact@pratilipi.com -->
<!-- SUBJECT: New review on your story ${ pratilipi_title } -->
<!DOCTYPE html>
<html>
	<body style="padding: 0; margin: 0;">
		<table align="center" table-layout="fixed;" style="width: 100%; font-family: &quot;Helvetica&quot;, Helvetica, sans-serif; color: #000000;">
			<tr>
				<td>
					<table align="center" width="100%" style="max-width: 600px; margin: auto; padding: 20px; border: 1px solid #c3c3c3; background-color: #ffffff; width: 100%;">
						<tr>
							<td>
								<table style="margin: 0 auto; width: 100%; text-align: left;" width="100%">
									<tr>
										<td width="60">
											<img width="60" height="60" style="width: 60px; height: 60px;" src="http://public.pratilipi.com/pratilipi-logo/png/Logo-2C-RGB-80px.png" />
										</td>
										<td>
											<span style="color: #000; font-size: 20px; font-weight: bold;">pratilipi</span>
										</td>
									</tr>
								</table>
								<hr style="border: 1px solid #eeeeee;">
								<table style="width: 100%;">
									<tr>
										<td width="100%">
											<table align="left" table-layout="fixed;" style="width: 100%; height: 100%;">
												<tr>
													<td>
														<p style="margin: 15px 0 10px 0; color: #000; font-size: 16px;">${ vote_name } likes a review on your content: ${ pratilipi_title }</p>
													</td>
												</tr>
											</table>
											<table style="width: 100%; margin-left: auto; margin-right: auto; border: solid 1px #eeeeee; border-radius: 2px; padding: 10px;">
												<tr>
													<td>
														<table align="left" style="border-collapse: collapse;" cellspacing="0" cellpadding="0">
															<tr>
																<td>
																	<a href="${ user_pratilipi_page_url }" target="_blank">
																		<img src="${ user_pratilipi_image_url }" style="border:0; width: 60px; height: 60px; border-radius: 50%;" />
																	</a>
																</td>
																<td width="10">
																	&nbsp;&nbsp;&nbsp;
																</td>
																<td width="100%">
																	<table style="border-collapse: collapse;">
																		<tr>
																			<td>
																				<a href="${ user_pratilipi_page_url }" style="color: #000; text-decoration: none; font-size: 14px; line-height: 19px; font-weight: bold;" target="_blank">
																					${ user_pratilipi_name }
																				</a>
																			</td>
																		</tr>
																		<#if rating??>
																			<tr>
																				<td>
																					<img src="http://public.pratilipi.com/email/images/stars_${ img_name }.png" style="height: 16px; max-height: 16px;" height="16" />
																				</td>
																			</tr>
																		</#if>
																		<tr>
																			<td>
																				<span style="font-size: 14px; line-height: 19px; color: #c7c7c7;">${ user_pratilipi_creation_date }</span>
																			</td>
																		</tr>
																	</table>
																</td>
															</tr>
														</table>
														<#if user_pratilipi_review ??>
															<table style="width: 100%; padding: 12px 0;">
																<tr>
																	<td>
																		${ user_pratilipi_review }
																	</td>
																</tr>
															</table>
														</#if>
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
														<a href="${ pratilipi_page_url }" target="_blank" style="font-size: 18px; text-decoration: none; line-height: 40px; width: 100%; display: inline-block;">
															<span style="color: #ffffff; padding-left: 15px; padding-right: 15px;">
																View on Pratilipi
															</span>
														</a>
													</td>
												</tr>
											</table>
											<center>
												<small>
													<br/>
													<br/>
														If you face any problems, please reach out to us at 
														<a href="mailto:${ contact_email }" style="color: #4a4a4a; text-decoration: underline;" target="_blank">
															${ contact_email }
														</a>
													<br/>
													<br/>
												</small>
											</center>
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td>
					<center>
						<br/>
							<small style="color: grey;">
								Jai Plaza Elite, #1064, 1st Floor, 7th A Main, 3rd Block Koramangala
								<br/>
								Bengaluru, Karnataka 560095
							</small>
					</center>
				</td>
			</tr>
		</table>
	</body>
</html>