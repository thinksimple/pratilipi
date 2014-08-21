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
				<div id="PageContent-HomeBook-Summary">
					<#if book.getSummary()?? >
						${ book.getSummary() }
					</#if>
				</div>
				<#if showEditOptions>
					<div id="PageContent-HomeBook-Summary-EditOptions"></div>
				</#if>
			</div>
			<div>
				<div id="PageContent-HomeBook-Review">
					<#list reviewList as review >
						<a href="#">${ review.getUserName() }</a>?string(": ")${ review.getReview() }
						<#if review_has_next >,</#if>
					</#list>
				</div>
				<!-- TODO : ADD CHECK TO MAKE SURE THIS SECTION IS NOT VISIBLE TO BOOK AUTHOR -->
				<!-- TODO : GET CURRENT USERID FROM SESSION.  -->
				<#if showAddReviewOption>
					<#if !userBook.getReview()??>
						<div id="PageContent-HomeBook-Review"></div>
					</#if>
				</#if>
			</div>
		</div>
	</div>

	<script type="text/javascript" language="javascript" src="/pagecontent.homebook/pagecontent.homebook.nocache.js" defer></script>
	<#if showEditOptions>
		<script type="text/javascript" language="javascript" src="/pagecontent.homebook.editoptions/pagecontent.homebook.editoptions.nocache.js" defer></script>
	</#if>
	
</div>
