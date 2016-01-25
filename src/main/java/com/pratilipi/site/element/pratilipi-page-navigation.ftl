
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
	
<div style="padding-top:25px; padding-bottom:25px;">
	<div class="row" style=" text-align: center;">
	    
	    <div class="col-xs-2 col-xs-offset-1"><a href="?page=${ rating1 }">${ rating1 }</a></div>
	    
    	<#if rating2?? >
			<div class="col-xs-2"><a href="?page=${ rating2 }">${ rating2 }</a></div>
		</#if>
	    
	    <#if rating3?? >
		    <div class="col-xs-2"><a href="?page=${ rating3 }">${ rating3 }</a></div>
	    </#if>
	    
	    <#if rating4?? >
	    	<div class="col-xs-2"><a href="?page=${ rating4 }">${ rating4 }</a></div>
	    </#if>
	    
	    <#if rating5?? >
	    	<div class="col-xs-2"><a href="?page=${ rating5 }">${ rating5 }</a></div>
	    </#if>
	    
	</div>
</div>
