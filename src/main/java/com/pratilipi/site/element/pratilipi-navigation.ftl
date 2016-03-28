
<style>
	.navigation {
		padding-top: 10px;
		padding-bottom: 10px;
	}
</style>

<div class="secondary-500 pratilipi-shadow box" style="padding: 25px 10px;">
	<#list navigationList as navigation>
		<h3 class="pratilipi-red">${ navigation.getTitle() }</h3>
			<#list navigation.linkList as Link>
				<a class="navigation" href="${ Link.getUrl() }">${ Link.getName() }</a>
			</#list>
	</#list>
</div>