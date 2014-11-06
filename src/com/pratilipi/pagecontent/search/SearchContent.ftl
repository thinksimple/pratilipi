<#import "../../../../com/pratilipi/commons/client/PratilipiView.ftl" as pratilipiView>

<!-- PageContent :: Search :: Start -->

<script language="javascript">
	function validateInput( form ) {
	    var query = form["q"].value;
	    if( query == null || query == "" ) {
	        return false;
	    }
	}
</script>

<div class="container">

	<div class="row" style="margin-top:30px; margin-bottom:30px;">
		<div class="col-lg-8 col-lg-offset-2 col-md-10 col-md-offset-1 col-sm-12 col-xs-12">
			
			<form action="${ pageUrl }" onSubmit="return validateInput( this )" >
				<div class="input-group">
					<div class="input-group-btn">
						<button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
							<#if docType?? == false>
								All <span class="caret"></span>
							<#elseif docType=="Pratilipi-Book">
								Books <span class="caret"></span>
							<#elseif docType=="Pratilipi-Poem">
								Poems <span class="caret"></span>
							<#elseif docType=="Pratilipi-Story">
								Stories <span class="caret"></span>
							<#elseif docType=="Pratilipi-Article">
								Articles <span class="caret"></span>
							<#else>
								All <span class="caret"></span>
							</#if>
						</button>
						<ul class="dropdown-menu" role="menu">
							<li><a href="#" data-value="">All</a></li>
							<li><a href="#" data-value="Pratilipi-Book">Books</a></li>
							<li><a href="#" data-value="Pratilipi-Poem">Poems</a></li>
							<li><a href="#" data-value="Pratilipi-Story">Stories</a></li>
							<li><a href="#" data-value="Pratilipi-Article">Articles</a></li>
						</ul>
						<input type="hidden" name="dt" value="${ docType! }">
					</div>
					<input type="text" class="form-control" name="q" value="${ query! }" />
					<span class="input-group-btn">
						<button class="btn btn-primary" type="submit">Search</button>
					</span>
				</div>
			</form>
			
			<#if resultCount??>
				<h4 style="text-align:center">
					<#if resultCount == 0>
						No result found !
					<#elseif resultCount == 1>
						One result found !
					<#else>
						About ${ resultCount } results found !
					</#if>
				</h4>
			</#if>
			
		</div>
	</div>

	<div class="row" id="PageContent-Search-Result" data-cursor="${ cursor! }">
		<#if dataList??>
			<#list dataList as data >
				<#if data.getClass().getSimpleName() == "PratilipiData">
					<@pratilipiView.thumbnail pratilipiData=data />
				</#if>
			</#list>
		</#if>
	</div>

	<#if cursor??>
		<div class="row ajax-loader" id="PageContent-Search-Loader"></div>
	</#if>

</div>

<script type="text/javascript" language="javascript" src="/pagecontent.search/pagecontent.search.nocache.js" async></script>

<!-- PageContent :: Search :: End -->