<html>
<body>
<p>

Hi <#if recipient.getFirstName()??>
		${ recipient.getFirstName() }
	</#if>, <br/><br/>
<#if notificationType == "REVIEW_ADD">
	<#if user.getFirstName()??> ${ user.getFirstName() } </#if> has reviewed your book ${ pratilipiData.getTitle() }. <br/>
	Click below link to reply to the review.<br/>
<#elseif notificationType == "REVIEW_UPDATE">
	<#if user.getFirstName()??> ${ user.getFirstName() } </#if> has updated his review on your book ${ pratilipiData.getTitle() }. <br/>
	Click below link to reply to the review.<br/>
<#elseif notificationType == 'COMMENT_ADD'>
	<#if user.getFirstName()??> ${ user.getFirstName() } </#if> has replied on your review of book ${ pratilipiData.getTitle() }. <br/>
	Click below link to reply to the review.<br/>
</#if>

<a href='${ pratilipiData.getPageUrl() }'>${ pratilipiData.getPageUrl() }</a><br/>

 
Thanks and Regards,<br/>
Team Pratilipi<br/>
(Pratilipi.com)<br/><br/><br/>

</p>
</body>
</html>