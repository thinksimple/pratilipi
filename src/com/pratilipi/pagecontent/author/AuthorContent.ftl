<!-- PageContent :: Author Profile :: Start -->

<script type="text/javascript">
window.onload = function(){
	     var $book = $('div.book'), //Cache your DOM selector
			$story = $('div.story'),
			$poem = $('div.poem'),
	     	 $bookL = $('div#bookL'),
	     	 $bookR = $('div#bookR'),
			 $storyL = $('div#storyL'),
	     	 $storyR = $('div#storyR'),
			 $poemL = $('div#poemL'),
	     	 $poemR = $('div#poemR'),
	        index = Math.floor($('div#list-container').outerWidth()/116), //Starting index
	        visible = index;
	    
	    $('div#bookR').click(function(){
	    	endIndex = $book.length; 
	        if(index <= endIndex ){
	          if(index+visible < endIndex) { 
	            var shift = visible*116;     
	          	$book.animate({'left':'-=' + shift + 'px'});
	          	index = index+visible;
	          	$bookL.css("cursor", "pointer");
	          }
	          else{
	           var shift = (endIndex - index)*116;
	          	$book.animate({'left':'-=' + shift + 'px'});
	          	index = endIndex;
	          	$bookR.css("cursor", "default");
	          	$bookL.css("cursor", "pointer");
	          }
	        }
	    });
	    
	    $('div#bookL').click(function(){
	    	endIndex = $book.length; 
	        if(index > 0){
	          if(index-visible > visible) { 
	          	var shift = visible*116;            
	          	$book.animate({'left':'+='+ shift + 'px'});
	          	index = index-visible;
	          	$bookR.css("cursor", "pointer");
	          }
	          else{
	          	var shift = (index - visible)*116;
	          	$book.animate({'left':'+=' + shift + 'px'});
	          	index = visible;
	          	$bookL.css("cursor", "default");
	          	$bookR.css("cursor", "pointer");
	          }
	       }
		});
		   
		$('div#storyR').click(function(){
		    endIndex = $story.length ; 
	        if(index <= endIndex ){
	          if(index+visible < endIndex) { 
	            var shift = visible*116;     
	          	$story.animate({'left':'-=' + shift + 'px'});
	          	index = index+visible;
	          	$storyL.css("cursor", "pointer");
	          }
	          else{
	           var shift = (endIndex - index)*116;
	          	$story.animate({'left':'-=' + shift + 'px'});
	          	index = endIndex;
	          	$storyR.css("cursor", "default");
	          	$storyL.css("cursor", "pointer");
	          }
	        }
	    });
	    
	    $('div#storyL').click(function(){
	    	endIndex = $story.length; 
	        if(index > 0){
	          if(index-visible > visible) { 
	          	var shift = visible*116;            
	          	$story.animate({'left':'+='+ shift + 'px'});
	          	index = index-visible;
	          	$storyR.css("cursor", "pointer");
	          }
	          else{
	          	var shift = (index - visible)*116;
	          	$story.animate({'left':'+=' + shift + 'px'});
	          	index = visible;
	          	$storyL.css("cursor", "default");
	          	$storyR.css("cursor", "pointer");
	          }
	       }
		});
		$('div#poemR').click(function(){
		    endIndex = $poem.length; 
	        if(index <= endIndex ){
	          if(index+visible < endIndex) { 
	            var shift = visible*116;     
	          	$poem.animate({'left':'-=' + shift + 'px'});
	          	index = index+visible;
	          	$poemL.css("cursor", "pointer");
	          }
	          else{
	           var shift = (endIndex - index)*116;
	          	$poem.animate({'left':'-=' + shift + 'px'});
	          	index = endIndex;
	          	$poemR.css("cursor", "default");
	          	$poemL.css("cursor", "pointer");
	          }
	        }
	    });
	    
	    $('div#poemL').click(function(){
	    	endIndex = $poem.length; 
	        if(index > 0){
	          if(index-visible > visible) { 
	          	var shift = visible*116;            
	          	$poem.animate({'left':'+='+ shift + 'px'});
	          	index = index-visible;
	          	$poemR.css("cursor", "pointer");
	          }
	          else{
	          	var shift = (index - visible)*116;
	          	$poem.animate({'left':'+=' + shift + 'px'});
	          	index = visible;
	          	$poemL.css("cursor", "default");
	          	$poemR.css("cursor", "pointer");
	          }
	       }
	    });
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
					<div class="col-sm-7" style="border: 1px solid #DDD;">
						<#assign _authorNameEn = author.getFirstNameEn()+ " " + author.getLastNameEn()>
						<#assign _authorName = author.getFirstName()+ " " + author.getLastName()>
						<h3 style="text-align: center;">${ _authorName }</h3>
						<h4 style="text-align: center;">${ _authorNameEn }</h4>
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
					<div class="col-sm-4">
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
					<div id="container" class="row new-arrivals">
						<div id="list-container">
							<div id="bookL"></div>
							<div id="bookR"></div>
							<div class="list">
								<#list bookDataList as book >
									<div class="prod-info book">
										<div style="width:100px; padding-top: 10px; padding-bottom:10px;">
											<div style="width:100px; height:160px; overflow: hidden;">
												<a href="${ bookUrlMap[ book.getId()?string("#") ] }">
													<img class="img-responsive" src="${ bookCoverMap[ book.getId()?string("#") ] }"></img>
												</a>
											</div>
											<div style="width:100px; height:55px; overflow: hidden; white-space: normal; font-size: 12px;">
												<a href="${ bookUrlMap[ book.getId()?string("#") ] }">${ book.getTitle() }</a>
												<p>${ languageMap[ book.getLanguageId()?string("#") ] }</p>
											</div>
										</div>
									</div>
								</#list>
							</div>
						</div>
					</div>
				</#if>
				<#if storyDataList?has_content>
					<hr></hr>
					<h4 class="published">PUBLISHED STORIES</h4>
					<div id="container" class="row new-arrivals">
						<div id="list-container">
							<div id="storyL"></div>
							<div id="storyR"></div>
							<div class="list">
								<#list storyDataList as story >
									<div class="prod-info story">
										<div style="width:100px; padding-top: 10px; padding-bottom:10px;">
											<div style="width:100px; height:160px; overflow: hidden;">
												<a href="${ storyUrlMap[ story.getId()?string("#") ] }">
													<img class="img-responsive" src="${ storyCoverMap[ story.getId()?string("#") ] }"></img>
												</a>
											</div>
											<div style="width:100px; height:55px; overflow: hidden; white-space: normal; font-size: 12px;">
												<a href="${ storyUrlMap[ story.getId()?string("#") ] }">${ story.getTitle() }</a>
												<p>${ languageMap[ story.getLanguageId()?string("#") ] }</p>
											</div>
										</div>
									</div>
								</#list>
							</div>
						</div>
					</div>
				</#if>
				<#if poemDataList?has_content>
					<hr></hr>
					<h4 class="published">PUBLISHED POEMS</h4>
					<div id="container" class="row new-arrivals">
						<div id="list-container">
							<div id="poemL"></div>
							<div id="poemR"></div>
							<div class="list">
								<#list poemDataList as poem >
									<div class="prod-info poem">
										<div style="width:100px; padding-top: 10px; padding-bottom:10px;">
											<div style="width:100px; height:160px; overflow: hidden;">
												<a href="${ poemUrlMap[ poem.getId()?string("#") ] }">
													<img class="img-responsive" src="${ poemCoverMap[ poem.getId()?string("#") ] }"></img>
												</a>
											</div>
											<div style="width:100px; height:55px; overflow: hidden; white-space: normal; font-size: 12px;">
												<a href="${ poemUrlMap[ poem.getId()?string("#") ] }">${ poem.getTitle() }</a>
												<p>${ languageMap[ poem.getLanguageId()?string("#") ] }</p>
											</div>
										</div>
									</div>
								</#list>
							</div>
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