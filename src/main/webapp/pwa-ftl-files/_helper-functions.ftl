<#macro compress_single_line>
<#local captured><#compress><#nested></#compress></#local>
${ captured?replace( "^\\s+|\\s+$|\\n|\\r", " ", "rm" ) }
</#macro>

<#macro add_backslashes>
<#local captured><#compress><#nested></#compress></#local>
${ "`" + captured?replace("\\n", "", 'r') + "`" }
</#macro>
