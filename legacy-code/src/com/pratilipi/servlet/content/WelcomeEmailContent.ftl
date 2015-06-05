<html>
<body>
<p>

Hi <#if user.getFirstName()??>
		${ user.getFirstName() }
	<#else>
		${ user.getEmail() }
	</#if>, <br/><br/>

Thank you for subscribing at <a href="http://pratilipi.com">Pratilipi</a> !<br/><br/>


Wishing you a great journey ahead.<br/><br/>

 
Thanks and Regards,<br/>
Ranjeet, Co-Founder<br/>
 (Pratilipi.com)<br/><br/><br/>
 
P.S. Please ignore the email, if it was not intended for you.<br/>

</p>
</body>
</html>