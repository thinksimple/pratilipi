<div class="pratilipi-block cover-image pratilipi-shadow secondary-500 box" style="background-image: url('http://trendymods.com/wp-content/uploads/2015/10/facebook-cover-photos-ideas-5.jpg')">
	<div class="">
		<div class="edit-profile pull-left">
		    <a href="#" class="icon">
		    	<span class="badge"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span></span>
			</a>
			
		</div>
		<div class="share-author pull-right">
			<a href="#" class="icon">
				<span class="glyphicon glyphicon-share white" aria-hidden="true"></span>
			</a>
		</div>
		<div class="clearfix"></div>
	</div>
	<div class="author-info text-center">
		<div>
			<img class="img-responsive img-thumbnail img-circle profile-picture" src="${ author.getImageUrl() }">
		</div>
		<div class="author-name">${ author.getName()!author.getNameEn() }</div>
	</div>
	<div class="clearfix"></div>
	<div class="follow-author">
		<#if userAuthor.isFollowing()??>
			<#if userAuthor.isFollowing() == true>
				<button class="pratilipi-grey-button" onclick="FollowUnfollowPostRequest(false)">
					<span class="glyphicon glyphicon-minus" aria-hidden="true"></span>
					Unfollow | ${ author.getFollowCount()?c }
				</button>		
			<#else>
				<button style="white-space: nowrap;" class="pratilipi-light-blue-button" onclick="FollowUnfollowPostRequest(true)">
					<span class="glyphicon glyphicon-user" aria-hidden="true"></span>
					Follow | ${ author.getFollowCount()?c }
				</button>
			</#if> 
		<#else>
			<a class="pratilipi-light-blue-button" href="/login?ret=${ author.getPageUrl() }">
						<span class="glyphicon glyphicon-user" aria-hidden="true"></span>
						Follow | ${ author.getFollowCount()?c }
			</a>					
		</#if>
	</div>
</div>

<div class="pratilipi-shadow pratilipi-block secondary-500 box text-center">
	<div class="row">
		<div class="col-xs-4 pratilipi-author-stat">
			<span>${ _strings.author_count_works }</span>
			<div class="numbers">${ author.getContentPublished()?c }</div>
		</div>
		<div class="col-xs-4 pratilipi-author-stat">
			<span> ${ _strings.author_count_reads }</span>
			<div class="numbers"> ${ author.getTotalReadCount()?c } </div>
		</div>
		<div class="col-xs-4 pratilipi-author-stat">
			<span> ${ _strings.author_count_likes } </span>
			<div class="numbers"> ${ author.getTotalFbLikeShareCount()?c }</div>
		</div>
	</div>
</div>