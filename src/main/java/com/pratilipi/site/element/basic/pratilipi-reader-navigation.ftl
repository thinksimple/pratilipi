<style>
	.navigation {
		padding-top: 10px;
		padding-bottom: 10px;
		display: block;
		text-align: center;
	}
	a.heading , a.heading:hover, a.heading:focus {
		font-weight: 600;
	}
	a.grey , a.grey:hover, a.grey:focus {
		color: #707070;
	}
	a.black , a.black:hover, a.black:focus {
		color: #333;
	}
	a.blue , a.blue:hover, a.blue:focus {
		color: #107FE5;
	}
</style>

<div class="secondary-500 pratilipi-shadow box">
	<#list indexList as index>
		<#assign level=index.level?number>
		<#if index.pageNo??>
			<#assign page=index.pageNo?number>
		<#else>
			<#assign page=index.chapterNo?number>
		</#if>
		<a class="navigation<#if level == 0> heading <#if page==pageNo> blue <#else> black </#if></#if>    <#if level == 1><#if page==pageNo> blue <#else> grey </#if></#if>" 
			style="cursor: pointer;"
			onCLick="gotoPage( ${ page } )">${ index.title }</a>
	</#list>
</div>