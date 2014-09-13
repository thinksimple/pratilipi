<!-- PageContent :: Author Profile :: Start -->

<script type="text/javascript">
window.onload = function(){
	    var $bl    = $(".books-block"),
	    	$sl    = $(".stories-block"),
	    	$pl    = $(".poems-block"),
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
	
		var $th;
		
		$bl.mousemove(function(e) {
			$th = $(".books");
	        mX = e.pageX - this.offsetLeft;
	        mX2 = Math.min( Math.max(0, mX-mPadd), mmAA ) * mmAAr;
		});
		
		$sl.mousemove(function(e) {
			$th = $(".stories");
	        mX = e.pageX - this.offsetLeft;
	        mX2 = Math.min( Math.max(0, mX-mPadd), mmAA ) * mmAAr;
		});
		
		$pl.mousemove(function(e) {
			$th = $(".poems");
	        mX = e.pageX - this.offsetLeft;
	        mX2 = Math.min( Math.max(0, mX-mPadd), mmAA ) * mmAAr;
		});
	
		setInterval(function(){
			posX += (mX2 - posX) / damp; // zeno's paradox equation "catching delay"	
			$th.css({marginLeft: -posX*wDiff });
		}, 10);
		
	}
</script>

<#setting time_zone="${ timeZone }">

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
				<#if bookDataList?has_content>
					<hr></hr>
					<h4 class="published">PUBLISHED BOOKS</h4>
					<div class="row new-arrivals books-block">
						<div class="books col-lg-12 col-md-12 col-sm-12 col-xs-12">
							<#list bookDataList as book >
								<div class="prod-info thumb">
									<div style="width:100px; padding-top: 10px; padding-bottom:10px;">
										<div style="width:100px; height:160px; overflow: hidden;">
											<a href="${ bookUrlMap[ book.getId()?string("#") ] }">
												<img class="img-responsive" src="${ bookCoverMap[ book.getId()?string("#") ] }"></img>
											</a>
										</div>
										<div style="width:100px; height:55px; overflow: hidden; white-space: normal;">
											<a href="${ bookUrlMap[ book.getId()?string("#") ] }">${ book.getTitle() }</a>
											<span>${ languageMap[ book.getLanguageId()?string("#") ] }</span>
										</div>
									</div>
								</div>
							</#list>
						</div>
					</div>
				</#if>
				<#if storyDataList?has_content> 	
					<hr></hr>
					<h4>PUBLISHED STORIES</h4>
					<div class="row new-arrivals stories-block">
						<div class="stories col-lg-12 col-md-12 col-sm-12 col-xs-12">
							<#list storyDataList as story >
								<div class="prod-info thumb">
									<div style="width:100px; padding-top: 10px; padding-bottom:10px;">
										<div style="width:100px; height:160px; overflow: hidden;">
											<a href="${ storyUrlMap[ story.getId()?string("#") ] }">
												<img class="img-responsive" src="${ storyCoverMap[ story.getId()?string("#") ] }"></img>
											</a>
										</div>
										<div style="width:100px; height:55px; overflow: hidden; white-space: normal;">
											<a href="${ storyUrlMap[ story.getId()?string("#") ] }">${ story.getTitle() }</a>
											<span>${ languageMap[ story.getLanguageId()?string("#") ] }</span>
										</div>
									</div>
								</div>
							</#list>
						</div>
					</div>
				</#if>
				<#if poemDataList?has_content>
					<hr></hr>
					<h4>PUBLISHED POEMS</h4>
					<div class="row new-arrivals poems-block">
						<div class="poems col-lg-12 col-md-12 col-sm-12 col-xs-12">	
							<#list poemDataList as poem >
								<div class="prod-info thumb">
									<div style="width:100px; padding-top: 10px; padding-bottom:10px;">
										<div style="width:100px; height:160px; overflow: hidden;">
											<a href="${ poemUrlMap[ poem.getId()?string("#") ] }">
												<img class="img-responsive" src="${ poemCoverMap[ poem.getId()?string("#") ] }"></img>
											</a>
										</div>
										<div style="width:100px; height:55px; overflow: hidden; white-space: normal;">
											<a href="${ poemUrlMap[ poem.getId()?string("#") ] }">${ poem.getTitle() }</a>
											<span>${ languageMap[ poem.getLanguageId()?string("#") ] }</span>
										</div>
									</div>
								</div>
							</#list>
						</div>
					</div>
				</#if>
			</div>
		</div>
	</div>
</div>
<#if showAddOption>
	<!-- Add Author Image Javascript -->
	<script type="text/javascript" language="javascript" src="/pagecontent.author/pagecontent.author.nocache.js" defer></script>
</#if>

<!-- PageContent :: Author Profile :: End -->