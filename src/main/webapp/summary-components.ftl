<#macro add_backslashes>
<#local captured><#compress><#nested></#compress></#local>
${ "'" + captured?replace("\\n", "", 'r') + "'" }
</#macro>


ko.components.register('pratilipi-header', {
	viewModel: <#include "pratilipi-header.js">,
    template: <@add_backslashes><#include "pratilipi-header.html"></@add_backslashes>
});

ko.components.register('pratilipi-footer', {
    template: <@add_backslashes><#include "pratilipi-footer.html"></@add_backslashes>
});

ko.components.register('pratilipi-navigation-drawer', {
	viewModel: <#include "pratilipi-navigation.js">,
    template: <@add_backslashes><#include "pratilipi-navigation-drawer.html"></@add_backslashes>
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

ko.components.register('pratilipi-review', {
	viewModel: <#include "pratilipi-review.js">,
    template: <@add_backslashes><#include "pratilipi-review.html"></@add_backslashes>
});

ko.components.register('pratilipi-review-comment', {
	viewModel: <#include "pratilipi-review-comment.js">,
    template: <@add_backslashes><#include "pratilipi-review-comment.html"></@add_backslashes>
});

ko.components.register('pratilipi-review-reply', {
	viewModel: <#include "pratilipi-review-reply.js">,
    template: <@add_backslashes><#include "pratilipi-review-reply.html"></@add_backslashes>
});
