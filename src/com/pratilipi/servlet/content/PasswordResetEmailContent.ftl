<html>
<body>
<p>

Hi<#if user.getFirstName() ??> ${ user.getFirstName() }</#if>,<br/><br/>

Your new password is <b>${ newPassword }</b> 


Thanks,<br/>
Pratilipi Team<br/>
</p>
</body>
</html>