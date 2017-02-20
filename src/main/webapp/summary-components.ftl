<#macro add_backslashes>
<#local captured><#compress><#nested></#compress></#local>
${ "`" + captured?replace("\\n", "", 'r') + "`" }
</#macro>

<#macro register element>
	ko.components.register( '${ element }', {
		<#attempt>viewModel: <#include "${ element }.js">,<#recover></#attempt>
	    <#attempt>template: <@add_backslashes><#include "${ element }.html"></@add_backslashes><#recover></#attempt>
	});
</#macro>

<@register element="pratilipi-header" />
<@register element="pratilipi-footer" />
<@register element="pratilipi-info" />
<@register element="pratilipi-review-list" />
<@register element="pratilipi-review" />
<@register element="pratilipi-review-comment" />
<@register element="pratilipi-review-reply" />

<#-- TODO: Fix this asap -->
ko.components.register( 'pratilipi-navigation-drawer', {
	viewModel: <#include "pratilipi-navigation.js">,
	template: <@add_backslashes><#include "pratilipi-navigation-drawer.html"></@add_backslashes>
});

ko.components.register( 'pratilipi-navigation-aside', {
	viewModel: <#include "pratilipi-navigation.js">,
	template: <@add_backslashes><#include "pratilipi-navigation-aside.html"></@add_backslashes>
});
