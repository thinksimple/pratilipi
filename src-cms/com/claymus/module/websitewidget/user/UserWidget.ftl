<div class="websitewidget-user">

	<div>
		<#if ClaymusHelper.getCurrentUser() ??>
			Hello, Guest ! (<a href="${ ClaymusHelper.createLoginURL() }">login</a>)
		<#else>
			Hello, ${ ClaymusHelper.getCurrentUser().getNickname() } ! (<a href="${ ClaymusHelper.createLogoutURL() }">logout</a>)
		</#if>
	</div>
</div>