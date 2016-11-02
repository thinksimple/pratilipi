<#macro compress_single_line>
<#local captured><#nested></#local>
${ captured?replace( "^\\s+|\\s+$|\\n|\\r", " ", "rm" ) }
</#macro>
<#if stage=="prod">
<#compress><@compress_single_line><#compress><#include "${ templateName }"></#compress></@compress_single_line></#compress>
<#else>
<#include "${ templateName }">
</#if>