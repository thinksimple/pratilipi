<#macro add_backslashes>
<#local captured><#nested></#local>
${ "'" + captured?replace("\\n", "", 'r') + "'" }
</#macro>


ko.components.register('pratilipi-header', {
    template: <@add_backslashes><#include "pratilipi-header.html"></@add_backslashes>
});

ko.components.register('pratilipi-footer', {
    template: <@add_backslashes><#include "pratilipi-footer.html"></@add_backslashes>
});

ko.components.register('pratilipi-navigation', {
	viewModel: <#include "pratilipi-navigation.js">,
    template: <@add_backslashes><#include "pratilipi-navigation.html"></@add_backslashes>
});