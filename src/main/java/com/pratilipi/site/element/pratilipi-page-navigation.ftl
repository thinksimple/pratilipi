
<#if pratilipiListPageMax lte 5 >
	<#if pratilipiListPageMax gte 1 >
		<#assign rating1=1 >
	</#if>
	<#if pratilipiListPageMax gte 2 >
		<#assign rating2=2 >
	</#if>
	<#if pratilipiListPageMax gte 3 >
		<#assign rating3=3 >
	</#if>
	<#if pratilipiListPageMax gte 4 >
		<#assign rating4=4 >
	</#if>
	<#if pratilipiListPageMax == 5 >
		<#assign rating5=5 >
	</#if>
<#else>
	<#if pratilipiListPageCurr lte 2 >
		<#assign rating1=1 >
		<#assign rating2=2 >
		<#assign rating3=3 >
		<#assign rating4=4 >
		<#assign rating5=5 >
	<#elseif pratilipiListPageCurr gte pratilipiListPageMax-1 >
		<#assign rating1=pratilipiListPageMax-4 >
		<#assign rating2=pratilipiListPageMax-3 >
		<#assign rating3=pratilipiListPageMax-2 >
		<#assign rating4=pratilipiListPageMax-1 >
		<#assign rating5=pratilipiListPageMax >
	<#else>
		<#assign rating1=pratilipiListPageCurr-2 >
		<#assign rating2=pratilipiListPageCurr-1 >
		<#assign rating3=pratilipiListPageCurr >
		<#assign rating4=pratilipiListPageCurr+1 >
		<#assign rating5=pratilipiListPageCurr+2 >
	</#if>
</#if>
	
<div style="padding-top:25px; padding-bottom:25px; text-align: center;">
	<#if rating1?? >
		${ rating1 }
	</#if>
	<#if rating2?? >
		${ rating2 }
	</#if>
	<#if rating3?? >
		${ rating3 }
	</#if>
	<#if rating4?? >
		${ rating4 }
	</#if>
	<#if rating5?? >
		${ rating5 }
	</#if>
</div>
