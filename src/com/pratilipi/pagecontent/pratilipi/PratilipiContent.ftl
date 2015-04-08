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
			<#if pratilipiData.getTitleEn()??>
				<img id="PageContent-Pratilipi-CoverImage" class="img-responsive" src="${ pratilipiData.getCoverImageUrl() }" title="${ pratilipiData.getTitleEn() }" alt="${ pratilipiData.getTitle() }" itemprop="image">
			<#else>
				<img id="PageContent-Pratilipi-CoverImage" class="img-responsive" src="${ pratilipiData.getCoverImageUrl() }" title="${ pratilipiData.getTitle() }" alt="${ pratilipiData.getTitle() }" itemprop="image">
			</#if>
			<#if showEditOptions>
				<div id="PageContent-Pratilipi-CoverImage-EditOptions"></div>
			</#if>
			<div style="margin-top:10px; margin-bottom:10px;">
				<@social.vToolbar shareUrl=shareUrl/>
				<#if pratilipiData.getStarCount() gt 0 && pratilipiData.getRatingCount() gt 0>
					<label style="font-size: 14px;">Rating: ${ pratilipiData.getStarCount()/ pratilipiData.getRatingCount() }/5</label>
				<#elseif pratilipiData.getStarCount() gt 0 && pratilipiData.getRatingCount() == 0>
					<label style="font-size: 14px;">Rating: ${ pratilipiData.getStarCount()/ 1 }/5</label>
				<#else>
					<label style="font-size: 14px;">Rating: -/5</label>
				</#if>
			</div>
		</div>
		
		<#-- Title, Author Name, Genre List, Summary and Buttons -->
		<div class="col-lg-10 col-md-10 col-sm-9 col-xs-8" style="padding-bottom:15px;">
			<h1 id="PageContent-Pratilipi-Title" itemprop="name">${ pratilipiData.getTitle() }</h1>
			<#if pratilipiData.getAuthorData()??>
				<h4><a href="${ pratilipiData.getAuthorData().getPageUrlAlias() ! pratilipiData.getAuthorData().getPageUrl() }" id="PageContent-Pratilipi-AuthorName">${ pratilipiData.getAuthorData().getFullName() ! pratilipiData.getAuthorData().getFullNameEn() }</a></h4>
			</#if>
			
			<h5 id="PageContent-Pratilipi-GenreList">
				<#list pratilipiData.getGenreNameList() as genreName>
					${ genreName }<#if genreName_has_next>,</#if>
				</#list>
			</h5>
			
			<h6>
				<span>Listed On : ${ pratilipiData.getListingDate()?date } / </span>
				<span>Last Updated On : ${ pratilipiData.getLastUpdated()?date }</span>
			</h6>
			
			<#if !userData.getEmail()??>
				<div id="PageContent-Pratilipi-RatingReadOnly" title="Click to Rate" data-toggle='modal' data-target="#loginModal" onclick="window.location.href='#Rate'" style="width:120px;">
					<img class="star" title="1" onmouseover="onMouseOver( this );" onmouseout="onMouseOut();" src="/theme.pratilipi/images/unselected.png" style="cursor: pointer;">
					<img class="star" title="2" onmouseover="onMouseOver( this );" onmouseout="onMouseOut();" src="/theme.pratilipi/images/unselected.png" style="cursor: pointer;">
					<img class="star" title="3" onmouseover="onMouseOver( this );" onmouseout="onMouseOut();" src="/theme.pratilipi/images/unselected.png" style="cursor: pointer;">
					<img class="star" title="4" onmouseover="onMouseOver( this );" onmouseout="onMouseOut();" src="/theme.pratilipi/images/unselected.png" style="cursor: pointer;">
					<img class="star" title="5" onmouseover="onMouseOver( this );" onmouseout="onMouseOut();" src="/theme.pratilipi/images/unselected.png" style="cursor: pointer;">
					<span id="PageContent-Pratilipi-RatingReadOnly-Label" class="gwt-InlineLabel" style="font-size: 12px; width: 100px; display: block;">Your Rating: -/5</span>
				</div>
			</#if>
			<#if showRatingOption>
				<div id="PageContent-Pratilipi-Rating"></div>
			</#if>
			<div id="PageContent-Pratilipi-Summary" style="margin-top:20px; margin-bottom:10px;" itemprop="description">
				${ pratilipiData.getSummary()! }
			</div>
			<#if showEditOptions>
				<div id="PageContent-Pratilipi-Summary-EditOptions" style="text-align:right;"></div>
			</#if>
				
			<button type="button" class="btn btn-success" onclick="window.location.href='${ pratilipiData.getReaderPageUrl() }'">Read For Free</button>
			<#if showWriterOption>
				<button type="button" class="btn btn-primary" onclick="window.location.href='${ pratilipiData.getWriterPageUrl() }'">Edit This ${ pratilipiData.getType().getName() }</button>
			</#if>
			<#if showReviewedMessage>
				<button type="button" class="btn btn-primary" onclick="window.location.href='#Reviews'">
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
		<#if !userData.getEmail()??>
			<div title="Click to Write" data-toggle='modal' data-target="#loginModal" onclick="window.location.href='#Review'">
				<textarea rows="4" cols="50" placeholder="Write Review" style="width:100%; padding: 5px;"></textarea>
			</div>
		</#if>
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



	<div id="PageContent-Pratilipi-EncodedData" style="display:none;">${ pratilipiDataEncodedStr }</div>
<#if showEditOptions>
	<script type="text/javascript" language="javascript" src="/pagecontent.pratilipi.witheditoptions/pagecontent.pratilipi.witheditoptions.nocache.js" defer></script>
<#else>
	<script type="text/javascript" language="javascript" src="/pagecontent.pratilipi/pagecontent.pratilipi.nocache.js" defer></script>
</#if>



<#if !userData.getEmail()??>
	<script language='javascript'>
		
		var maxRating = 5;
		
		function setRatingImage( hoverIndex ){
			var ratingDiv = document.getElementById( "PageContent-Pratilipi-RatingReadOnly" );
			var stars = ratingDiv.getElementsByTagName( "IMG" );
			for( var i=0; i < stars.length; ++i){
				stars[i].src=getImageSrc( i, hoverIndex );
			}
		}
		
		function getImageSrc( index, hoverIndex ){
			var path = "";
	        if (index >= hoverIndex ) {
	            path =  "/theme.pratilipi/images/unselected.png";
	        }
	        else {
	            path = "/theme.pratilipi/images/hover_blue.png";
	        }   
	    
	        return path;
		}
		
		function onMouseOver( object ){
			var ratingLabel = document.getElementById( "PageContent-Pratilipi-RatingReadOnly-Label" );
			var title = object.title;
			ratingLabel.innerHTML = "Your Rating: " + title + "/5";
			setRatingImage( parseInt( title ));
		}
		
		function onMouseOut(){
			var ratingLabel = document.getElementById( "PageContent-Pratilipi-RatingReadOnly-Label" );
			ratingLabel.innerHTML = "Your Rating: -/5";
			setRatingImage( 0 );
		}
		
	</script>
</#if>

<!-- PageContent :: Pratilipi :: End -->