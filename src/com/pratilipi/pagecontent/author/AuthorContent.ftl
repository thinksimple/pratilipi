<!-- PageContent :: Author Profile :: Start -->

<script type="text/javascript">
window.onload = function(){
	     var $book = $('div.book'), //Cache your DOM selector
			 $story = $('div.story'),
			 $poem = $('div.poem'),
			 $pratilipiWidth = 130,
	     	 $bookL = $('div#bookL'),
	     	 $bookR = $('div#bookR'),
			 $storyL = $('div#storyL'),
	     	 $storyR = $('div#storyR'),
			 $poemL = $('div#poemL'),
	     	 $poemR = $('div#poemR'),
	         visible = Math.floor($('div#list-container').outerWidth()/$pratilipiWidth), //Starting index
			 bookIndex = visible,
			 storyIndex = visible,
			 poemIndex = visible;
	    
	    
	    //maxList
		 var $maxList = ( $('div.book').length >= $('div.story').length )?
									   (( $('div.book').length >= $('div.poem').length )? $('div.book').length : $('div.poem').length ):
									   (( $('div.story').length >= $('div.poem').length )? $('div.story').length : $('div.poem').length );
		
	    
	    //Setting width of list div containing all books/stories/poems to stop wrapping of elements
		$('div.list').css( "min-width", $maxList*$pratilipiWidth+'px' );
		
	    $('div#bookR').click(function(){
	    	endIndex = $book.length; 
	        if(bookIndex <= endIndex ){
	          if(bookIndex+visible < endIndex) { 
	            var shift = visible*$pratilipiWidth;     
	          	$book.animate({'left':'-=' + shift + 'px'});
	          	bookIndex = bookIndex+visible;
	          	$bookL.css("cursor", "pointer");
	          }
	          else{
	           var shift = (endIndex - bookIndex)*$pratilipiWidth;
	          	$book.animate({'left':'-=' + shift + 'px'});
	          	bookIndex = endIndex;
	          	$bookR.css("cursor", "default");
	          	$bookL.css("cursor", "pointer");
	          }
	        }
	    });
	    
	    $('div#bookL').click(function(){
	    	endIndex = $book.length; 
	        if(bookIndex > 0){
	          if(bookIndex-visible > visible) { 
	          	var shift = visible*$pratilipiWidth;            
	          	$book.animate({'left':'+='+ shift + 'px'});
	          	bookIndex = bookIndex-visible;
	          	$bookR.css("cursor", "pointer");
	          }
	          else{
	          	var shift = (bookIndex - visible)*$pratilipiWidth;
	          	$book.animate({'left':'+=' + shift + 'px'});
	          	bookIndex = visible;
	          	$bookL.css("cursor", "default");
	          	$bookR.css("cursor", "pointer");
	          }
	       }
		});
		   
		$('div#storyR').click(function(){
		    endIndex = $story.length ; 
	        if(storyIndex <= endIndex ){
	          if(storyIndex+visible < endIndex) { 
	            var shift = visible*$pratilipiWidth;     
	          	$story.animate({'left':'-=' + shift + 'px'});
	          	storyIndex = storyIndex+visible;
	          	$storyL.css("cursor", "pointer");
	          }
	          else{
	           var shift = (endIndex - storyIndex)*$pratilipiWidth;
	          	$story.animate({'left':'-=' + shift + 'px'});
	          	storyIndex = endIndex;
	          	$storyR.css("cursor", "default");
	          	$storyL.css("cursor", "pointer");
	          }
	        }
	    });
	    
	    $('div#storyL').click(function(){
	    	endIndex = $story.length; 
	        if(storyIndex > 0){
	          if(storyIndex-visible > visible) { 
	          	var shift = visible*$pratilipiWidth;            
	          	$story.animate({'left':'+='+ shift + 'px'});
	          	storyIndex = storyIndex-visible;
	          	$storyR.css("cursor", "pointer");
	          }
	          else{
	          	var shift = (storyIndex - visible)*$pratilipiWidth;
	          	$story.animate({'left':'+=' + shift + 'px'});
	          	storyIndex = visible;
	          	$storyL.css("cursor", "default");
	          	$storyR.css("cursor", "pointer");
	          }
	       }
		});
		$('div#poemR').click(function(){
		    endIndex = $poem.length; 
	        if(poemIndex <= endIndex ){
	          if(poemIndex+visible < endIndex) { 
	            var shift = visible*$pratilipiWidth;     
	          	$poem.animate({'left':'-=' + shift + 'px'});
	          	poemIndex = poemIndex+visible;
	          	$poemL.css("cursor", "pointer");
	          }
	          else{
	           var shift = (endIndex - poemIndex)*$pratilipiWidth;
	          	$poem.animate({'left':'-=' + shift + 'px'});
	          	poemIndex = endIndex;
	          	$poemR.css("cursor", "default");
	          	$poemL.css("cursor", "pointer");
	          }
	        }
	    });
	    
	    $('div#poemL').click(function(){
	    	endIndex = $poem.length; 
	        if(poemIndex > 0){
	          if(poemIndex-visible > visible) { 
	          	var shift = visible*$pratilipiWidth;            
	          	$poem.animate({'left':'+='+ shift + 'px'});
	          	poemIndex = poemIndex-visible;
	          	$poemR.css("cursor", "pointer");
	          }
	          else{
	          	var shift = (poemIndex - visible)*$pratilipiWidth;
	          	$poem.animate({'left':'+=' + shift + 'px'});
	          	poemIndex = visible;
	          	$poemL.css("cursor", "default");
	          	$poemR.css("cursor", "pointer");
	          }
	       }
	   
	    }); 
  
	    $("#upload-link").on('click', function(e){
			e.preventDefault();
		    $("#Upload-image:hidden").trigger('click');
		});
	}
</script>

<#setting time_zone="${ timeZone }">

<div id="PageContent-Author">
	<!-- Refactor below code to use one div for all 4 pratilipi types -->
	<div id="PageContent-Author-AddPratilipi"></div>
	<div id="PageContent-Author-Detail" class="row margin0">
		<div class="container margin-top25 clearfix">
			<div id="profile-pic-div" class="col-md-3">
				<img style="width:75%" src="${ authorImage }"></img><br/>
				<#if showUpdateOption>
					<span id="upload-link">Upload your photo</span>
					<!-- Add Author Image -->
					<div id="PageContent-Author-Image-EditOptions"></div>
				</#if>
			</div>
			<div class="col-md-9">
				<div class="row margin0">
					<div class="col-sm-7" style="border: 1px solid #DDD;">
						<h3 style="text-align: center;">${ author.getFirstName() }<#if author.getLastName()??> ${ author.getLastName() }</#if></h3>
						<h4 style="text-align: center;">${ author.getFirstNameEn() }<#if author.getLastNameEn()??> ${ author.getLastNameEn() }</#if></h4>
						<div>
							<div id="PageContent-Author-Summary">
								<#if author.getSummary()??>
									${ author.getSummary() }
								</#if>
							</div>
							<#if showUpdateOption>
								<div id="PageContent-Author-Summary-EditOptions"></div>
							</#if>
						</div>
					</div>
					<div class="col-sm-4 published-works">
						<h3>Works</h3>
						<#assign _bookCount = bookDataList?size>
						<#assign _poemCount = poemDataList?size>
						<#assign _storyCount = storyDataList?size>
						<#assign _articleCount = articleDataList?size>
						<table>
							<tr><th></th><th>Published</th><th>Drafted</th></tr>
							<tr>
								<td>Books</td>
								<td>${ _bookCount }</td>
								<td>0</td>
								<#if showUpdateOption><td><a href="" id="addBook" data-toggle='modal' data-target="#PageContent-Author-AddBook">Add</a></td></#if>
							</tr>
							<tr>
								<td>Stories</td>
								<td>${ _storyCount }</td>
								<td>0</td>
								<#if showUpdateOption><td><a href="" id="addStory" data-toggle='modal' data-target="#PageContent-Author-AddStory">Add</a></td></#if>
							</tr>
							<tr>
								<td>Poems</td>
								<td>${ _poemCount }</td>
								<td>0</td>
								<#if showUpdateOption><td><a href="" id="addPoem" data-toggle='modal' data-target="#PageContent-Author-AddPoem">Add</a></td></#if>
							</tr>
							<tr>
								<td>Article</td>
								<td>${ _articleCount }</td>
								<td>0</td>
								<#if showUpdateOption><td><a href="" id="addArticle" data-toggle='modal' data-target="#PageContent-Author-AddArticle">Add</a></td></#if>
							</tr>
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
				<#if articleDataList?has_content>
					<hr></hr>
					<h4 class="published">PUBLISHED ARTICLES</h4>
					<div id="container" class="row new-arrivals">
						<div id="list-container">
							<div id="articleL"></div>
							<div id="articleR"></div>
							<div class="list">
								<#list articleDataList as article >
									<div class="prod-info article">
										<div style="width:100px; padding-top: 10px; padding-bottom:10px;">
											<div style="width:100px; height:160px; overflow: hidden;">
												<a href="${ articleUrlMap[ article.getId()?string("#") ] }">
													<img class="img-responsive" src="${ articleCoverMap[ article.getId()?string("#") ] }"></img>
												</a>
											</div>
											<div style="width:100px; height:55px; overflow: hidden; white-space: normal; font-size: 12px;">
												<a href="${ articleUrlMap[ article.getId()?string("#") ] }">${ article.getTitle() }</a>
												<p>${ languageMap[ article.getLanguageId()?string("#") ] }</p>
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
<#if showUpdateOption>
	<!-- Add Author Image Javascript -->
	<script type="text/javascript" language="javascript" src="/pagecontent.author/pagecontent.author.nocache.js" defer></script>
	<!-- Add Pratilipi javascript -->
	<script type="text/javascript" language="javascript" src="/pagecontent.pratilipis/pagecontent.pratilipis.nocache.js" async></script>
</#if>

<!-- PageContent :: Author Profile :: End -->