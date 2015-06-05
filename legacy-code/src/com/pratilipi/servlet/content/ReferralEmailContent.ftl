<html>
<body>
<p>

Hi<#if user.getFirstName() ??> ${ user.getFirstName() }</#if>,<br/><br/>

Your friend <#if referer.getFirstName() ??>${ referer.getFirstName() }<#else>${ referer.getEmail() }</#if> has invited you to join him at <href="http://pratilipi.com?ref=${ user.getReferer() }">Pratilipi</a>.<br/><br/>

<a href="http://pratilipi.com?ref=${ user.getReferer() }">Pratilipi</a> is a platform to discover, read and share your favourite books, stories, poems etc in a language, device and format of your choice.<br/><br/>

'<a href="http://pratilipi.com?ref=${ user.getReferer() }">Join us</a>' today to get some awesome free books and a chance to be one of our Founding Readers.<br/><br/>

On behalf of <#if referer.getFirstName() ??>${ referer.getFirstName() }<#else>${ referer.getEmail() }</#if>,<br/>
Ranjeet, Co-Founder<br/>
  (Pratilipi.Com)<br/>

</p>
</body>
</html>