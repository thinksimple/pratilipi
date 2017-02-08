<#macro add_backslashes>
<#local captured><#nested></#local>
${ "'" + captured?replace("\\n", "", 'r') + "'" }
</#macro>


ko.components.register('pratilipi-header', {
	viewModel: <#include "pratilipi-header.js">,
    template: <@add_backslashes><#include "pratilipi-header.html"></@add_backslashes>
});

ko.components.register('pratilipi-footer', {
    template: <@add_backslashes><#include "pratilipi-footer.html"></@add_backslashes>
});

ko.components.register('pratilipi-navigation', {
	viewModel: <#include "pratilipi-navigation.js">,
    template: <@add_backslashes><#include "pratilipi-navigation.html"></@add_backslashes>
});

ko.components.register('pratilipi-navigation-aside', {
	viewModel: <#include "pratilipi-navigation.js">,
    template: <@add_backslashes><#include "pratilipi-navigation-aside.html"></@add_backslashes>
});

ko.components.register('pratilipi-info', {
	viewModel: <#include "pratilipi-info.js">,
    template: <@add_backslashes><#include "pratilipi-info.html"></@add_backslashes>
});

ko.components.register('pratilipi-review-list', {
	viewModel: <#include "pratilipi-review-list.js">,
    template: <@add_backslashes><#include "pratilipi-review-list.html"></@add_backslashes>
});