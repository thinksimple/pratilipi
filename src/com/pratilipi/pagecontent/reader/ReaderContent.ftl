<div class="container-fluid">
	<div class="row">
		<div class="col-sm-10 col-sm-offset-1 col-md-8 col-md-offset-2 col-lg-6 col-lg-offset-3" style="border:1px solid #EEE; font-size:18px" id="PageContent-Pratilipi-Content">
			${ pageContent }
		</div>
		<div class="col-sm-10 col-sm-offset-1 col-md-8 col-md-offset-2 col-lg-6 col-lg-offset-3" style="border:1px solid #EEE; font-size:18px" id="PageContent-Pratilipi-Content-EditOptions">
			<#if previousPageUrl??><a href="${ previousPageUrl }">Previous Page</a></#if>
			<#if nextPageUrl??><a href="${ nextPageUrl }">Next Page</a></#if>
		</div>
	</div>
</div>

<script type="text/javascript" language="javascript" src="/pagecontent.reader/pagecontent.reader.nocache.js" defer></script>
