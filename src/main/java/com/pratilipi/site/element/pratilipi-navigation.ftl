
<style>
	.navigation {
		padding-top: 10px;
		padding-bottom: 10px;
	}
</style>

<div class="box" style="padding-top:25px; padding-bottom:25px;">
	<#list navigationList as navigation>
		<h3 style="color: #D0021B; text-align: center;">${ navigation.getTitle() }</h3>
		<div class="row" style="text-align: center;">
			<#list navigation.linkList as Link>
				<div class="col-xs-12 col-sm-6 col-md-3 col-lg-3 navigation">
					<a href="${ Link.getUrl() }">${ Link.getName() }</a>
				</div>
			</#list>
		</div>
	</#list>
</div>