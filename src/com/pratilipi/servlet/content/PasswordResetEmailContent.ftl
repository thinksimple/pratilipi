<html>
<body>
<p>

Hi<#if user.getFirstName() ??> ${ user.getFirstName() }</#if>,<br/><br/>

Please click on link below to reset your password.</br></br>

<a href="http://devo-pratilipi.appspot.com/#changepassword-${ user.getEmail() }-${ user.getPassword() }">
	http://devo-pratilipi.appspot.com/#changepassword-${ user.getEmail() }-${ user.getPassword() }</a></br></br>


Thanks,<br/>
Pratilipi Team<br/>
</p>
</body>
</html>