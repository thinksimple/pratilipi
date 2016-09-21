<!DOCTYPE html>
<html lang="${lang}">
	<#if ( action == "start_writing" )>
		<#include "../element/writer-panel/writer-start-screen/index.html">
	<#elseif ( action == "write" )>
		<#include "../element/writer-panel/writer-main-screen/index.html">
	<#elseif ( action == "summarize" )>
		<#include "../element/writer-panel/writer-final-screen/index.html">	
	</#if>
</html>