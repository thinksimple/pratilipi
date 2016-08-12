
<style>
	.reader-icon {
		width: 24px;
		height: 24px;
		margin-top: 14px;
		margin-left: 10px;
		margin-right: 10px;
	}
</style>

<div class="secondary-500 pratilipi-shadow" style="display: block; padding: 5px; height: 64px;">
	<table style="min-width: 100%;">
		<tr>
			<td align="left">
				<#-- Back Button -->
				<a onClick="exitReader()" style="cursor: pointer;"><img class="reader-icon" src="http://0.ptlp.co/resource-all/icon/svg/first.svg"/></a>
				<#-- Index Button if content has Index -->
				<#if indexList?? && indexList?has_content>
					<a onClick="gotoNavigation()" style="cursor: pointer;"><img class="reader-icon" style="width: 20px; height: 20px;" src="http://0.ptlp.co/resource-all/icon/svg/list.svg"/></a>
				</#if>
			</td>
			<td align="right">
				<#-- Write Review -->
				<a <#if user.isGuest == true>href="/login?ret=${ pratilipi.getPageUrl() }?review=write%26ret=/${ pageUrl }?id=${ pratilipi.getId()?c }%26pageNo=${ pageNo }"<#else>href="${ pratilipi.getPageUrl() }?review=write&ret=/${ pageUrl }?id=${ pratilipi.getId()?c }%26pageNo=${ pageNo }"</#if> >
					<img class="reader-icon" src="http://0.ptlp.co/resource-all/icon/svg/pencil.svg"/>
				</a>
				<#-- Share buttons -->
				<a onClick="gotoShare()" style="cursor: pointer;">
					<img class="reader-icon" src="http://0.ptlp.co/resource-all/icon/svg/share2.svg"/>
				</a>
				<#-- Menu button -->
				<a onClick="gotoSetting()" style="cursor: pointer;">
					<img class="reader-icon" src="http://0.ptlp.co/resource-all/icon/svg/cog.svg"/>
				</a>
			</td>
		</tr>
	</table>
</div>