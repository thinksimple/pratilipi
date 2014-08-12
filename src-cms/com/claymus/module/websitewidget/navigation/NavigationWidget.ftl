<div class="websitewidget-navigation">
	<#list getLinks() as link>
		<a href="${ link[1] }">${ link[0] }</a>
		&nbsp;
	</#list>
</div>