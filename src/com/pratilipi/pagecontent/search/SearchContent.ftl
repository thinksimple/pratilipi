<#import "../../../../com/pratilipi/commons/client/PratilipiView.ftl" as pratilipiView>
<!-- PageContent :: Pratilipis :: Start -->

<script>
	function onClick( object ){
		document.getElementById( "inputgroupbutton" ).innerHTML = object.innerHTML + " " + "<span class=\"caret\"></span>";
	}
</script>
<div class="container">
	<div class="col-md-12" id="PageContent-Search-SearchBox" style="margin-top: 20px; margin-bottom: 20px;"></div>
	<div class="row" id="PageContent-Search-Preloaded">
		<#list pratilipiDataList as pratilipiData >
			<@pratilipiView.thumbnail pratilipiData=pratilipiData />
		</#list>
		<div class="col-xs-12">
			<#if endOfSearchReached && isSearchQueryPresent >
				${ serverMsg }
			</#if>
		</div>
	</div>
	<div id="PageContent-Search-SearchResult" style="margin-top: 10px;"></div>
</div>

<script type="text/javascript" language="javascript" src="/pagecontent.search/pagecontent.search.nocache.js" async></script>

<!-- PageContent :: Pratilipis :: End -->