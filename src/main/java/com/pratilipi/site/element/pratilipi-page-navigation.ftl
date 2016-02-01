
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

<#if pratilipiListSearchQuery?? >
	<#if nav1?? >
		<#assign redirect1= "?${ pratilipiListSearchQuery }&page=${ nav1 }" >
	</#if>
	<#if nav2?? >
		<#assign redirect2= "?${ pratilipiListSearchQuery }&page=${ nav2 }" >
	</#if>
	<#if nav3?? >
		<#assign redirect3= "?${ pratilipiListSearchQuery }&page=${ nav3 }" >
	</#if>
	<#if nav4?? >
		<#assign redirect4= "?${ pratilipiListSearchQuery }&page=${ nav4 }" >
	</#if>
	<#if nav5?? >
		<#assign redirect5= "?${ pratilipiListSearchQuery }&page=${ nav5 }" >
	</#if>
<#else>
	<#if nav1?? >
		<#assign redirect1= "?page=${ nav1 }" >
	</#if>
	<#if nav2?? >
		<#assign redirect2= "?page=${ nav2 }" >
	</#if>
	<#if nav3?? >
		<#assign redirect3= "?page=${ nav3 }" >
	</#if>
	<#if nav4?? >
		<#assign redirect4= "?page=${ nav4 }" >
	</#if>
	<#if nav5?? >
		<#assign redirect5= "?page=${ nav5 }" >
	</#if>
</#if>
	
<div style="padding-top:25px; padding-bottom:25px;">
	<div class="row" style=" text-align: center;">
	    
	    <#if nav1?? >
		    <#if nav1 == currentPage>
		    	<div class="col-xs-2 col-xs-offset-1"><a style="color: #D0021B; font-size: 18px;" href="${ redirect1 }">${ nav1 }</a></div>
			<#else>
				<div class="col-xs-2 col-xs-offset-1"><a href="${ redirect1 }">${ nav1 }</a></div>
			</#if>
	    </#if>
	    
    	<#if nav2?? >
    		<#if nav2 == currentPage>
    			<div class="col-xs-2"><a style="color: #D0021B; font-size: 18px;" href="${ redirect2 }">${ nav2 }</a></div>
    		<#else>
    			<div class="col-xs-2"><a href="${ redirect2 }">${ nav2 }</a></div>
    		</#if>
		</#if>
	    
	    <#if nav3?? >
	    	<#if nav3 == currentPage>
    			<div class="col-xs-2"><a style="color: #D0021B; font-size: 18px;" href="${ redirect3 }">${ nav3 }</a></div>
    		<#else>
    			<div class="col-xs-2"><a href="${ redirect3 }">${ nav3 }</a></div>
    		</#if>
	    </#if>
	    
	    <#if nav4?? >
	    	<#if nav4 == currentPage>
    			<div class="col-xs-2"><a style="color: #D0021B; font-size: 18px;" href="${ redirect4 }">${ nav4 }</a></div>
    		<#else>
    			<div class="col-xs-2"><a href="${ redirect4 }">${ nav4 }</a></div>
    		</#if>
	    </#if>
	    
	    <#if nav5?? >
	    	<#if nav5 == currentPage>
    			<div class="col-xs-2"><a style="color: #D0021B; font-size: 18px;" href="${ redirect5 }">${ nav5 }</a></div>
    		<#else>
    			<div class="col-xs-2"><a href="${ redirect5 }">${ nav5 }</a></div>
    		</#if>
	    </#if>
	    
	</div>
</div>
