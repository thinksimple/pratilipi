<div class="pratilipi-block cover-image pratilipi-shadow secondary-500 box" style="background-image: url('http://trendymods.com/wp-content/uploads/2015/10/facebook-cover-photos-ideas-5.jpg')">
	<div class="">
		<div class="my-account pull-right">
		    <a href="?action=account&ret=${ author.getPageUrl() }" class="icon">
		    	<span class="glyph-white-background badge badge-circle"><img src="http://0.ptlp.co/resource-all/icon/svg/user.svg"></span>
			</a>			
		</div>	
		<div class="edit-profile pull-right">
		    <a href="?action=edit_profile&ret=${ author.getPageUrl() }" class="icon">
		    	<span class="glyph-white-background badge badge-circle"><img src="http://0.ptlp.co/resource-all/icon/svg/cog.svg"></span>
			</a>			
		</div>		
		<div class="share-author pull-right">
			<a onclick="gotoShare( '${ author.getPageUrl() }', 'author_page' )" class="icon">
				<span class="glyph-white-background badge badge-circle"><img src="http://0.ptlp.co/resource-all/icon/svg/share2.svg"></span>
			</a>
		</div>
		<div class="clearfix"></div>
	</div>
	<div class="author-info text-center">
		<div>
			<img class="img-responsive img-thumbnail img-circle profile-picture" src="${ author.getImageUrl(140) }">
		</div>
		<div class="author-name">${ author.getName()!author.getNameEn() }</div>
	</div>
	<div class="clearfix"></div>
	<div class="follow-author">
		<#if userAuthor.isFollowing()??>
			<#if ( ( !author.getUser().getId()?? ) || ( user.getId() != author.getUser().getId() ) ) >
				<#if userAuthor.isFollowing() == true>
					<button class="pratilipi-red-button" onclick="FollowUnfollowPostRequest(false)">
						<img class="width-16" src="http://0.ptlp.co/resource-all/icon/svg/user-check-red.svg"></img>
						${ _strings.author_unfollow } | ${ author.getFollowCount()?c }
					</button>		
				<#else>
					<button class="pratilipi-red-button" onclick="FollowUnfollowPostRequest(true)">
						<img class="width-16" src="http://0.ptlp.co/resource-all/icon/svg/user-plus-red.svg"></img>
						${ _strings.author_follow } | ${ author.getFollowCount()?c }
					</button>
				</#if>
			<#elseif ( ( author.getUser().getId()?? ) && ( user.getId() == author.getUser().getId() ) ) >
				<p> ${ author.getFollowCount() } &nbsp; ${_strings.author_followers } </p>
			</#if>
		<#else>
			<a class="pratilipi-red-button" href="/login?ret=${ author.getPageUrl() }">
						<img class="width-16" src="http://0.ptlp.co/resource-all/icon/svg/user-plus-red.svg"></img>
						${ _strings.author_follow } | ${ author.getFollowCount()?c }
			</a>					
		</#if>
	</div>
</div>

<div class="pratilipi-shadow pratilipi-block secondary-500 box text-center">
	<div class="row">
		<div class="col-xs-4 pratilipi-author-stat">
			<span>${ _strings.author_count_works }</span>
			<div class="numbers">${ author.getContentPublished() }</div>
		</div>
		<div class="col-xs-4 pratilipi-author-stat">
			<span> ${ _strings.author_count_reads }</span>
			<div class="numbers"> ${ author.getTotalReadCount() } </div>
		</div>
		<div class="col-xs-4 pratilipi-author-stat">
			<span> ${ _strings.author_count_likes } </span>
			<div class="numbers"> ${ author.getTotalFbLikeShareCount() }</div>
		</div>
	</div>
</div>