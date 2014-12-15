<!-- PageContent :: Pratilipi :: Start -->


<#setting time_zone="${ timeZone }">
<#import "../../../../com/claymus/commons/client/ui/Social.ftl" as social>

<#assign shareUrl="http://${ domain }${ pratilipiData.getPageUrl() }">
	

<div class="container" itemscope itemtype="http://schema.org/Product">

	<#if showEditOptions>
		<div id="PageContent-Pratilipi-Publish" class="alert alert-danger" role="alert" style="text-align:center; margin-top:20px; margin-bottom:0px; display:none;"></div>
	</#if>

	<div class="row">

		<#-- Cover Image -->
		<div class="col-lg-2 col-md-2 col-sm-3 col-xs-4" style="margin-top:25px; margin-bottom:15px;">
			<img id="PageContent-Pratilipi-CoverImage" class="img-responsive" src="${ pratilipiData.getCoverImageUrl() }" itemprop="image">
			<#if showEditOptions>
				<div id="PageContent-Pratilipi-CoverImage-EditOptions"></div>
			</#if>
			<div style="margin-top:10px; margin-bottom:10px; text-align:center">
				<@social.vToolbar shareUrl=shareUrl/>
			</div>
		</div>
		
		<#-- Title, Author Name, Genre List, Summary and Buttons -->
		<div class="col-lg-10 col-md-10 col-sm-9 col-xs-8" style="padding-bottom:15px;">
			<h1 id="PageContent-Pratilipi-Title" itemprop="name">${ pratilipiData.getTitle() }</h1>
			<#if pratilipiData.getAuthorData()??>
				<h4><a href="${ pratilipiData.getAuthorData().getPageUrlAlias() }" id="PageContent-Pratilipi-AuthorName">${ pratilipiData.getAuthorData().getFullName() }</a></h4>
			</#if>
			
			<h5 id="PageContent-Pratilipi-GenreList">
				<#list pratilipiData.getGenreNameList() as genreName>
					${ genreName }<#if genreName_has_next>,</#if>
				</#list>
			</h5>
			
			<div id="PageContent-Pratilipi-Summary" style="margin-top:20px; margin-bottom:10px;" itemprop="description">
				${ pratilipiData.getSummary()! }
			</div>
			<#if showEditOptions>
				<div id="PageContent-Pratilipi-Summary-EditOptions" style="text-align:right;"></div>
			</#if>
				
			<button type="button" class="btn btn-success" onclick="window.location.href='${ pratilipiData.getReaderPageUrl() }'">Read For Free</button>
			<#if showReviewedMessage>
				<button type="button" class="btn btn-info" onclick="window.location.href='#Reviews'">
					<span class="glyphicon glyphicon-ok"></span> Reviewed
				</button>
			</#if>
			<#if showReviewOption>
				<button type="button" class="btn btn-primary" onclick="window.location.href='#Review'">Review This ${ pratilipiData.getType().getName() }</button>
			</#if>
		</div>

	</div> <#-- END of row -->

</div> <#-- END of container -->



<div class="container">

	<div id="Reviews" class="well" style="margin-top:25px;">
		<#list reviewList as review >
			<#if review.getReview()??>
				<div class="hr-below">
					<h4 style="display:inline-block">${ userIdNameMap[ review.getUserId()?c ] } Says,</h4>
					<span class="pull-right"> ${ review.getReviewDate()?date }</span>
					<p>
						${ review.getReview() }
					</p>
				</div>
			</#if>
		</#list>
		<#if showReviewOption>
			<div id="Review">
				<h4 style="display:inline-block">${ userData.getName() } Says,</h4>
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