
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

<#if pratilipiListSearchQuery?? >
	<#assign redirect1="?${pratilipiListSearchQuery}&page=" + rating1 >
	<#assign redirect2="?${pratilipiListSearchQuery}&page=" + rating2 >
	<#assign redirect3="?${pratilipiListSearchQuery}&page=" + rating3 >
	<#assign redirect4="?${pratilipiListSearchQuery}&page=" + rating4 >
	<#assign redirect5="?${pratilipiListSearchQuery}&page=" + rating5 >
<#else>
	<#assign redirect1="?page=" + rating1 >
	<#assign redirect2="?page=" + rating2 >
	<#assign redirect3="?page=" + rating3 >
	<#assign redirect4="?page=" + rating4 >
	<#assign redirect5="?page=" + rating5 >
</#if>
	
<div style="padding-top:25px; padding-bottom:25px;">
	<div class="row" style=" text-align: center;">
	    
	    <#if rating1 == pratilipiListPageCurr>
	    	<div class="col-xs-2 col-xs-offset-1"><a style="color: #D0021B; font-size: 18px;" href="${ redirect1 }">${ rating1 }</a></div>
		<#else>
			<div class="col-xs-2 col-xs-offset-1"><a href="${ redirect1 }">${ rating1 }</a></div>
		</#if>
	    
	    
    	<#if rating2?? >
    		<#if rating2 == pratilipiListPageCurr>
    			<div class="col-xs-2"><a style="color: #D0021B; font-size: 18px;" href="${ redirect2 }">${ rating2 }</a></div>
    		<#else>
    			<div class="col-xs-2"><a href="${ redirect2 }">${ rating2 }</a></div>
    		</#if>
		</#if>
	    
	    <#if rating3?? >
	    	<#if rating3 == pratilipiListPageCurr>
    			<div class="col-xs-2"><a style="color: #D0021B; font-size: 18px;" href="${ redirect3 }">${ rating3 }</a></div>
    		<#else>
    			<div class="col-xs-2"><a href="${ redirect3 }">${ rating3 }</a></div>
    		</#if>
	    </#if>
	    
	    <#if rating4?? >
	    	<#if rating4 == pratilipiListPageCurr>
    			<div class="col-xs-2"><a style="color: #D0021B; font-size: 18px;" href="${ redirect4 }">${ rating4 }</a></div>
    		<#else>
    			<div class="col-xs-2"><a href="${ redirect4 }">${ rating4 }</a></div>
    		</#if>
	    </#if>
	    
	    <#if rating5?? >
	    	<#if rating5 == pratilipiListPageCurr>
    			<div class="col-xs-2"><a style="color: #D0021B; font-size: 18px;" href="${ redirect5 }">${ rating5 }</a></div>
    		<#else>
    			<div class="col-xs-2"><a href="${ redirect5 }">${ rating5 }</a></div>
    		</#if>
	    </#if>
	    
	</div>
</div>
