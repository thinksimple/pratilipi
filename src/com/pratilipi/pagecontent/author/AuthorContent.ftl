<#setting time_zone="${ timeZone }">
<#import "../../../../com/claymus/commons/client/ui/Social.ftl" as social>

<!-- PageContent :: Author :: Start -->

<div id="PageContent-Author">
	<!-- Refactor below code to use one div for all 4 pratilipi types -->
	<div id="PageContent-Author-AddPratilipi"></div>
	<div id="PageContent-Author-Detail" class="row margin0">
		<div class="container">
			<div class="col-md-3">
				<div id="profile-pic-div">
					<img style="width:75%" src="${ authorImage }"></img><br/>
					<#if showEditOption>
						<span id="upload-link">Upload your photo</span>
						<!-- Add Author Image -->
						<div id="PageContent-Author-Image-EditOptions"></div>
					</#if>
				</div>
				<#if authorUrl??>
					<div style="margin-top:10px; margin-bottom:10px;"><@social.toolbar shareUrl=authorUrl/></div>
				</#if>
			</div>
			<div class="col-md-9">
				<div class="row margin0">
					<div class="col-sm-7" style="border-right: 1px solid #DDD;">



						<h1 id="PageContent-Author-Name" style="text-align:center;">${ authorData.getName() }</h1>
						<h3 id="PageContent-Author-NameEn" style="text-align:center; margin-top:10px;">${ authorData.getNameEn() }</h3>

						<div id="PageContent-Author-Summary" style="margin-top:20px; margin-bottom:10px;">
							${ author.getSummary()! }
						</div>
						<#if showEditOption>
							<div id="PageContent-Author-Summary-EditOptions" style="text-align:right;"></div>
						</#if>
						
						
						
					</div>
					<div class="col-sm-4 published-works" style="margin-left: 4px;">
						<h3 style="text-align: center;">Works</h3>
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
								<#if showEditOption><td id="addBook"></td></#if>
							</tr>
							<tr>
								<td>Stories</td>
								<td>${ _storyCount }</td>
								<td>0</td>
								<#if showEditOption><td id="addStory"></td></#if>
							</tr>
							<tr>
								<td>Poems</td>
								<td>${ _poemCount }</td>
								<td>0</td>
								<#if showEditOption><td id="addPoem"></td></#if>
							</tr>
							<tr>
								<td>Article</td>
								<td>${ _articleCount }</td>
								<td>0</td>
								<#if showEditOption><td id="addArticle"></td></#if>
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

<#if showEditOption>
	<div id="PageContent-Author-EncodedData" style="display:none;">${ authorDataEncodedStr }</div>
	<script type="text/javascript" language="javascript" src="/pagecontent.author.witheditoptions/pagecontent.author.witheditoptions.nocache.js" defer></script>

	<!-- Add Pratilipi javascript -->
	<script type="text/javascript" language="javascript" src="/pagecontent.pratilipis/pagecontent.pratilipis.nocache.js" async></script>
<#else>
	<script type="text/javascript" language="javascript" src="/pagecontent.author/pagecontent.author.nocache.js" defer></script>
</#if>

<!-- PageContent :: Author :: End -->