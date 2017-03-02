<#macro compress_single_line>
<#local captured><#compress><#nested></#compress></#local>
${ captured?replace( "^\\s+|\\s+$|\\n|\\r", " ", "rm" ) }
</#macro>

<#macro add_backslashes>
<#local captured><#compress><#nested></#compress></#local>
${ "`" + captured?replace("\\n", "", 'r') + "`" }
</#macro>

<#macro register element>
<script>
	ko.components.register( '${ element }', {
		<#attempt>viewModel: <#include "../pwa-elements/${ element }/${ element }.js">,<#recover></#attempt>
	    <#attempt>template: <@add_backslashes><#include "../pwa-elements/${ element }/${ element }.html"></@add_backslashes><#recover></#attempt>
	});
</script>
</#macro>
