<div class="container">
	
	<div class="row">

		<#-- Cover Image -->
		<div class="col-lg-2 col-md-2 col-sm-3 col-xs-4" style="padding-top:25px;padding-bottom:15px;">
			<a href="${ pratilipiHomeUrl }">
				<img class="img-responsive" src="${ pratilipiCoverUrl }">
			</a>
			<#if showEditOptions>
				<div id="PageContent-Pratilipi-CoverImage-EditOptions"></div>
				<div id="PageContent-Pratilipi-HtmlContent-EditOptions"></div>
				<div id="PageContent-Pratilipi-WordContent-EditOptions"></div>
			</#if>
		</div>
		
		<#-- Title, Author Name and Buttons -->
		<div class="col-lg-10 col-md-10 col-sm-9 col-xs-8" style="padding-bottom:15px;">
			<h2>
				<a href="${ pratilipiHomeUrl }">${ pratilipi.getTitle() }</a>
			</h2>
			<h4>
				<a href="${ authorHomeUrl }">${ author.getFirstName() }<#if author.getLastName()??> ${ author.getLastName() }</#if></a>
			</h4>
			
			<button type="button" class="btn btn-success visible-xs-inline-block" onclick="window.location.href='${ pratilipiReaderUrl }'">Read This ${ pratilipi.getType().getName() }</button>
			<#if showReviewedMessage>
				<button type="button" class="btn btn-info visible-xs-inline-block" onclick="window.location.href='#Reviews'">
					<span class="glyphicon glyphicon-ok"></span> Reviewed
				</button>
			</#if>
			<#if showReviewOption>
				<button type="button" class="btn btn-primary visible-xs-inline-block" onclick="window.location.href='#Review'">Review This ${ pratilipi.getType().getName() }</button>
			</#if>
		</div>

		<#-- Summary and Buttons -->
		<div class="col-lg-10 col-md-10 col-sm-9 col-xs-12">
			<div>
				<div id="PageContent-Pratilipi-Summary">
					<#if pratilipi.getSummary()?? >
						${ pratilipi.getSummary() }
					</#if>
				</div>
				<#if showEditOptions>
					<div id="PageContent-Pratilipi-Summary-EditOptions"></div>
				</#if>

				<button type="button" class="btn btn-success hidden-xs" onclick="window.location.href='${ pratilipiReaderUrl }'">Read This ${ pratilipi.getType().getName() }</button>
				<#if showReviewedMessage>
					<button type="button" class="btn btn-info hidden-xs" onclick="window.location.href='#Reviews'">
						<span class="glyphicon glyphicon-ok"></span> Reviewed
					</button>
				</#if>
				<#if showReviewOption>
					<button type="button" class="btn btn-primary hidden-xs" onclick="window.location.href='#Review'">Review This ${ pratilipi.getType().getName() }</button>
				</#if>
			</div>
		</div>
		

	</div> <#-- END of row -->

	<div id="Reviews" class="well">
		<#list reviewList as review >
			<div class="hr-below">
				<h4 style="display:inline-block">${ userIdNameMap[ review.getUserId()?string("#") ] } Says,</h4>
				<span class="pull-right"> ${ review.getReviewDate()?date }</span>
				<p>
					${ review.getReview() }
				</p>
			</div>
		</#list>
		<#if showReviewOption>
			<div id="Review">
				<h4 style="display:inline-block">${ userName } Says,</h4>
				<div id="PageContent-Pratilipi-Review"></div>
				<div id="PageContent-Pratilipi-Review-AddOptions" style="padding-top:15px"></div>
			</div>
		</#if>
	</div> <#-- END of well -->
	
</div> <#-- END of container -->

<script type="text/javascript" language="javascript" src="/pagecontent.pratilipi/pagecontent.pratilipi.nocache.js" defer></script>
<#if showEditOptions>
	<script type="text/javascript" language="javascript" src="/pagecontent.pratilipi.edit/pagecontent.pratilipi.edit.nocache.js" defer></script>
</#if>
