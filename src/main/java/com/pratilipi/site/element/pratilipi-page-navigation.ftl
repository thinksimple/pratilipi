
<#if maxPage lte 5 >
	<#if maxPage gte 1 >
		<#assign nav1=1 >
	</#if>
	<#if maxPage gte 2 >
		<#assign nav2=2 >
	</#if>
	<#if maxPage gte 3 >
		<#assign nav3=3 >
	</#if>
	<#if maxPage gte 4 >
		<#assign nav4=4 >
	</#if>
	<#if maxPage == 5 >
		<#assign nav5=5 >
	</#if>
<#else>
	<#if currentPage lte 2 >
		<#assign nav1=1 >
		<#assign nav2=2 >
		<#assign nav3=3 >
		<#assign nav4=4 >
		<#assign nav5=5 >
	<#elseif currentPage gte maxPage-1 >
		<#assign nav1=maxPage-4 >
		<#assign nav2=maxPage-3 >
		<#assign nav3=maxPage-2 >
		<#assign nav4=maxPage-1 >
		<#assign nav5=maxPage >
	<#else>
		<#assign nav1=currentPage-2 >
		<#assign nav2=currentPage-1 >
		<#assign nav3=currentPage >
		<#assign nav4=currentPage+1 >
		<#assign nav5=currentPage+2 >
	</#if>
</#if>

<#if nav1?? >
	<#assign redirect1= "${ prefix }page=${ nav1 }" >
</#if>
<#if nav2?? >
	<#assign redirect2= "${ prefix }page=${ nav2 }" >
</#if>
<#if nav3?? >
	<#assign redirect3= "${ prefix }page=${ nav3 }" >
</#if>
<#if nav4?? >
	<#assign redirect4= "${ prefix }page=${ nav4 }" >
</#if>
<#if nav5?? >
	<#assign redirect5= "${ prefix }page=${ nav5 }" >
</#if>
	
<#-- Don't display page navigation if maxPage == 1  -->
<#if maxPage gt 1 >
	<div class="secondary-500 pratilipi-shadow box" style="padding-top:25px; padding-bottom:25px;">
		<div class="row" style=" text-align: center;">
		    
		    <#if nav1?? >
		    	<div class="col-xs-2 col-xs-offset-1">
		    		<a <#if nav1 == currentPage>style="font-size: 18px;" class="pratilipi-blue"</#if>  
		    			href="${ redirect1 }">${ nav1 }</a>
		    	</div>
		    </#if>
		    
	    	<#if nav2?? >
    			<div class="col-xs-2">
    				<a <#if nav2 == currentPage>style="font-size: 18px;" class="pratilipi-blue"</#if> 
    				 href="${ redirect2 }">${ nav2 }</a>
    			</div>
			</#if>
		    
		    <#if nav3?? >
		    	<div class="col-xs-2">
    				<a <#if nav3 == currentPage>style="font-size: 18px;" class="pratilipi-blue"</#if> 
    				 href="${ redirect3 }">${ nav3 }</a>
    			</div>
		    </#if>
		    
		    <#if nav4?? >
		    	<div class="col-xs-2">
    				<a <#if nav4 == currentPage>style="font-size: 18px;" class="pratilipi-blue"</#if> 
    				 href="${ redirect4 }">${ nav4 }</a>
    			</div>
		    </#if>
		    
		    <#if nav5?? >
		    	<div class="col-xs-2">
    				<a <#if nav5 == currentPage>style="font-size: 18px;" class="pratilipi-blue"</#if> 
    				 href="${ redirect5 }">${ nav5 }</a>
    			</div>
		    </#if>
		    
		</div>
	</div>
</#if>
