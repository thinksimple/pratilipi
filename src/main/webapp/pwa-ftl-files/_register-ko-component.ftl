<#macro register element>
	<script>
		ko.components.register( '${ element }', {
			<#attempt>viewModel: <#include "../pwa-elements/${ element }/${ element }.js">,<#recover></#attempt>
		    <#attempt>template: <@add_backslashes><#include "../pwa-elements/${ element }/${ element }.html"></@add_backslashes><#recover></#attempt>
		});
	</script>
</#macro>
