		<div class="container">
			<div class="col-md-12">
				<#if bookDataList?has_content>
					<h4 class="published hr-below">BOOKS</h4>
					<div id="container" class="row new-arrivals">
						<div id="list-container">
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
					<h4 class="published hr-below">STORIES</h4>
					<div id="container" class="row new-arrivals">
						<div id="list-container">
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
					<h4 class="published hr-below">POEMS</h4>
					<div id="container" class="row new-arrivals">
						<div id="list-container">
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
