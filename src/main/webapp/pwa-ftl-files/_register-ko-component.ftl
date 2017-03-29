<#macro register element>
	<script>
		ko.components.register( '${ element }', {
			<#attempt>viewModel: <@compress_single_line><#include "../pwa-elements/${ element }/${ element }.js"></@compress_single_line>,<#recover></#attempt>
		    <#attempt>template: <@add_backslashes><#include "../pwa-elements/${ element }/${ element }.html"></@add_backslashes><#recover></#attempt>
		});
	</script>
</#macro>
