<!-- PageContent :: Author Profile :: Start -->

<#setting time_zone="${ timeZone }">
<script type="text/javascript">
$(function(){
  
    var $bl    = $(".thumbnails95"),
        $th    = $(".prod-info"),
        blW    = $bl.outerWidth(),
        blSW   = $bl[0].scrollWidth,
        wDiff  = (blSW/blW)-1,  // widths difference ratio
        mPadd  = 60,  // Mousemove Padding
        damp   = 20,  // Mousemove response softness
        mX     = 0,   // Real mouse position
        mX2    = 0,   // Modified mouse position
        posX   = 0,
        mmAA   = blW-(mPadd*2), // The mousemove available area
        mmAAr  = (blW/mmAA);    // get available mousemove fidderence ratio

	$bl.mousemove(function(e) {
        mX = e.pageX - this.offsetLeft;
        mX2 = Math.min( Math.max(0, mX-mPadd), mmAA ) * mmAAr;
	});

	setInterval(function(){
		posX += (mX2 - posX) / damp; // zeno's paradox equation "catching delay"	
		$th.css({marginLeft: -posX*wDiff });
	}, 10);

});
</script>
<div id="PageContent-Author">
	<div id="PageContent-Author-Detail" class="row margin0">
		<div class="container margin-top25 clearfix">
			<div class="col-md-3">
				<img style="width:75%" src="${ authorImage }"></img>
				<#if showAddOption>
					<!-- Add Author Image -->
					<div id="PageContent-Author-Image-EditOptions"></div>
				</#if>
			</div>
			<div class="col-md-9">
				<div class="row margin0 published-works">
					<div class="col-sm-7">
						<#assign _authorNameEn = author.getFirstNameEn()+ " " + author.getLastNameEn()>
						<#assign _authorName = author.getFirstName()+ " " + author.getLastName()>
						<h3>${ _authorName }</h3>
						<h4>${ _authorNameEn }</h4>
						<div>
							<div id="PageContent-Author-Summary">
								<#if author.getSummary()??>
									${ author.getSummary() }
								</#if>
							</div>
							<#if showAddOption>
								<div id="PageContent-Author-Summary-EditOptions"></div>
							</#if>
						</div>
					</div>
					<div class="col-sm-3">
						<h3>Published Works</h3>
						<#assign _bookCount = bookDataList?size>
						<#assign _poemCount = poemDataList?size>
						<#assign _storyCount = storyDataList?size>
						<table>
							<tr><td>Books</td><td>${ _bookCount }</td></tr>
							<tr><td>Stories</td><td>${ _storyCount }</td></tr>
							<tr><td>Poems</td><td>${ _poemCount }</td></tr>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="row margin0">
		<div class="container">
			<div class="col-md-12">
				<hr></hr>
				<h4>PUBLISHED BOOKS</h4>
				<#list bookDataList as book >
					<div class="row new-arrivals thumbnails95">
						<div class="col-xs-4 col-sm-2 prod-info">
							<a href="${ bookUrlMap[ book.getId()?string("#") ] }">
								<img src="${ bookCoverMap[ book.getId()?string("#") ] }"></img>
								<p>${ book.getTitle() }</p>
							</a>
							<span>${ languageMap[ book.getLanguageId()?string("#") ] }</span>
						</div>
					</div>
				</#list>
				<hr></hr>
				<h4>PUBLISHED STORIES</h4>
				<#list storyDataList as story >
					<div class="row new-arrivals thumbnails95">
						<div class="col-xs-4 col-sm-2 prod-info">
							<a href="${ storyUrlMap[ story.getId()?string("#") ] }">
								<img src="${ storyCoverMap[ story.getId()?string("#") ] }"></img>
								<p>${ story.getTitle() }</p>
							</a>
							<span>${ languageMap[ story.getLanguageId()?string("#") ] }</span>
						</div>
					</div>
				</#list>
				<hr></hr>
				<h4>PUBLISHED POEMS</h4>
				<#list poemDataList as poem >
					<div class="row new-arrivals thumbnails95">
						<div class="col-xs-4 col-sm-2 prod-info">
							<a href="${ poemUrlMap[ poem.getId()?string("#") ] }">
								<img src="${ poemCoverMap[ poem.getId()?string("#") ] }"></img>
								<p>${ poem.getTitle() }</p>
							</a>
							<span>${ languageMap[ poem.getLanguageId()?string("#") ] }</span>
						</div>
					</div>
				</#list>
			</div>
		</div>
	</div>
</div>
<#if showAddOption>
	<!-- Add Author Image Javascript -->
	<script type="text/javascript" language="javascript" src="/pagecontent.author/pagecontent.author.nocache.js" defer></script>
</#if>

<!-- PageContent :: Author Profile :: End -->