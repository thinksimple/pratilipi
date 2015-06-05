<html>
<body>
<p>

Dear <#if user.getFirstName()??>
		${ user.getFirstName() }
	<#else>
		${ user.getEmail() }
	</#if>, <br/><br/>

Greetings from Pratilipi.com<br/><br/>


You have received this mail as you requested for password reset of your account on Pratilipi.com.<br/>

<center>
<table cellspacing="0" cellpadding="0"> 
<tr>
<td align="center" width="300" height="40" bgcolor="#d62828" style="-webkit-border-radius: 5px; -moz-border-radius: 5px; border-radius: 5px; color: #ffffff; display: block;">
<a href="http://www.pratilipi.com/#changepassword-${ user.getEmail() }-${ user.getPassword() }" style="font-size:16px; font-weight: bold; font-family:sans-serif; text-decoration: none; line-height:40px; width:100%; display:inline-block">
<span style="color: #ffffff;">
Reset Your Password
</span>
</a>
</td>
</tr> 
</table> 
</center>
<br/>

Please click (or copy and paste) on the link below to reset your password if above button doesn't work.<br/>

<a href="http://www.pratilipi.com/#changepassword-${ user.getEmail() }-${ user.getPassword() }">
	http://www.pratilipi.com/#changepassword-${ user.getEmail() }-${ user.getPassword() }</a><br/><br/>


Thanks,<br/>
Pratilipi Team<br/>
</p>
</body>
</html>