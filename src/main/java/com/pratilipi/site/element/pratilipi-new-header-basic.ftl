<div class="secondary-500 pratilipi-shadow" style="display: block; padding: 5px;">
	<div class="row" style="text-align: center; margin: 0 auto;">
		<div class="col-xs-2 col-sm-2 col-md-2 col-lg-2">
			<a href="/">
				<img class="pratilipi-icon" title="${ _strings.pratilipi }" alt="${ _strings.pratilipi }" src="http://0.ptlp.co/resource-${ lang }/logo/pratilipi_logo.png" />
			</a>
		</div>
		<div class="col-xs-2 col-sm-2 col-md-2 col-lg-2">
			<a style="display: block;" href="/navigation">
				<img style="width: 24px; height: 24px; margin: 20px auto;" src="http://0.ptlp.co/resource-all/icon/svg/menu-grey.svg" />
			</a>
		</div>
		<div class="col-xs-2 col-sm-2 col-md-2 col-lg-2">
			<a style="display: block;" href="/">
				<img style="width: 24px; height: 24px; margin: 20px auto;" src="http://0.ptlp.co/resource-all/icon/svg/pencil.svg" />
			</a>
		</div>
		<div class="col-xs-2 col-sm-2 col-md-2 col-lg-2">
			<a style="display: block;" <#if user.isGuest == true>href="/login?ret=/library"<#else>href="/library"</#if>>
				<img style="	width: 24px; height: 24px; margin: 20px auto;" src="http://0.ptlp.co/resource-all/icon/svg/book-grey.svg" />
			</a>
		</div>
		<div class="col-xs-2 col-sm-2 col-md-2 col-lg-2">
			<a style="display: block;" <#if user.isGuest == true>href="/login?ret=/notifications"<#else>href="/notifications"</#if>>
				<img style="width: 24px; height: 24px; margin: 20px auto;" src="http://0.ptlp.co/resource-all/icon/svg/bell-grey.svg" />
			</a>
		</div>				
		<div class="col-xs-2 col-sm-2 col-md-2 col-lg-2">
			<a style="display: block;" <#if user.isGuest == true>href="/login?ret=${ requestUrl }"<#else>href="/account?ret=${ requestUrl }"</#if>>
				<img style="width: 24px; height: 24px; margin: 20px auto;" 
					<#if user.isGuest == true>src="http://0.ptlp.co/resource-all/icon/svg/user.svg"<#else>src="${ user.getProfileImageUrl() }"</#if> />
			</a>
		</div>
	</div>
</div>