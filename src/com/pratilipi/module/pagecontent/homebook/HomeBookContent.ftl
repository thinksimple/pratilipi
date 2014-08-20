<div class="container-fluid">
	
	<div class="row">
		<div class="col-sm-2">
			<a href="${ bookHomeUrl }${ book.getId() }">
				<img class="img-responsive img-thumbnail" src="${ bookCoverUrl }${ book.getId()?string("#") }">
			</a>
		</div>
		<div class="col-sm-10">
			<h3>
				<a href="${ bookHomeUrl }${ book.getId()?string("#") }">${ book.getTitle() }</a>
			</h3>
			<h4>
				<a href="${ authorHomeUrl }${ author.getId()?string("#") }">${ author.getFirstName() } ${ author.getLastName() }</a>
			</h4>
			<div>
				<#if book.getSummary()?? >
					<div id="PageContent-HomeBook-Summary">
						${ book.getSummary() }
					</div>
				</#if>
				<#if showEditOptions>
					<div>
						<a href="#" onClick="editSummmary( 'PageContent-HomeBook-Summary' )">
							<#if book.getSummary()?? >
								Edit Summary
							<#else>	
								Add Summary
							</#if>
						</a>
					</div>
				</#if>
			</div>
		</div>
	</div>

	<script type="text/javascript" language="javascript" src="/pagecontent.homebook/pagecontent.homebook.nocache.js" async></script>
	<#if showEditOptions>
		<script type="text/javascript" language="javascript" src="/pagecontent.homebook.editoptions/pagecontent.homebook.editoptions.nocache.js" async></script>
	</#if>
	
</div>
