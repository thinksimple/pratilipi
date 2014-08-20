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
					${ book.getSummary() }
				</#if>
			</div>
		</div>
	</div>
	
</div>
