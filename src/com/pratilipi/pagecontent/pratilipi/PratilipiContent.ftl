<!-- PageContent :: Pratilipi :: Start -->

<div class="container">
	
	<div class="row">

		<#-- Cover Image -->
		<div class="col-lg-2 col-md-2 col-sm-3 col-xs-4" style="padding-top:25px;padding-bottom:15px;">
			<a href="${ pratilipiHomeUrl }">
				<img class="img-responsive" src="${ pratilipiCoverUrl }">
			</a>
			<#if showEditOptions>
				<div id="PageContent-Pratilipi-CoverImage-EditOptions" upload-url="${ pratilipiCoverUploadUrl }" ></div>
				<div id="PageContent-Pratilipi-HtmlContent-EditOptions" upload-url="${ pratilipiContentHtmlUrl }" ></div>
				<div id="PageContent-Pratilipi-WordContent-EditOptions" upload-url="${ pratilipiContentWordUrl }" ></div>
			</#if>
		</div>
		
		<#-- Title, Author Name, Genre List and Buttons -->
		<div class="col-lg-10 col-md-10 col-sm-9 col-xs-8" style="padding-bottom:15px;">
			<h1 id="PageContent-Pratilipi-Title">
				${ pratilipiData.getTitle() }
			</h1>
			<h4>
				<a href="${ authorHomeUrl }">${ author.getFirstName() }<#if author.getLastName()??> ${ author.getLastName() }</#if></a>
			</h4>
			<h5 id="PageContent-Pratilipi-GenreList">
				<#list pratilipiData.getGenreNameList() as genreName>
					${ genreName }<#if genreName_has_next>,</#if>
				</#list>
			</h5>
			<div style="line-height: 15px;">
				<div id="fb-like" class="fb-like" data-layout="button_count" data-action="like" data-show-faces="true" data-share="true">
				</div>
				<a class="twitter-share-button"
					  href="https://twitter.com/share"
					  data-size="small">
				</a>
			</div>
			<button type="button" class="btn btn-success visible-xs-inline-block" onclick="window.location.href='${ pratilipiReaderUrl }'">Read For Free</button>
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
				
				<#if showEditOptions>
					<!-- Current is not author of this book -->
					<button type="button" class="btn btn-success hidden-xs" onclick="window.location.href='${ pratilipiReaderUrl }'">Read This ${ pratilipi.getType().getName() }</button>
					<!-- TODO: EDIT THIS BOOK URL -->
					<button type="button" class="btn btn-primary hidden-xs" onclick="">Edit ${ pratilipi.getType().getName() } Content</button>
					<div id="PageContent-Pratilipi-Info-EditOption" style="display: inline;"></div>
				<#else>
					<!-- Current is author of this book -->
					<button type="button" class="btn btn-success hidden-xs" onclick="window.location.href='${ pratilipiReaderUrl }'">Read For Free</button>
				</#if>
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

</div> <#-- END of container -->



<div class="container">

	<div id="Reviews" class="well" style="margin-top:25px;">
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



<#if showEditOptions>
	<div id="PageContent-Pratilipi-EncodedData" style="display:none;">${ pratilipiDataEncodedStr }</div>
	<script type="text/javascript" language="javascript" src="/pagecontent.pratilipi.witheditoptions/pagecontent.pratilipi.witheditoptions.nocache.js" defer></script>
<#else>
	<script type="text/javascript" language="javascript" src="/pagecontent.pratilipi/pagecontent.pratilipi.nocache.js" defer></script>
</#if>

<!-- PageContent :: Pratilipi :: End -->