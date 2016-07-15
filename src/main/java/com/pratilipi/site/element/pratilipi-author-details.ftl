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
			<img class="img-responsive img-thumbnail img-circle profile-picture" src="https://hindizenblog.files.wordpress.com/2009/10/munshi-premchand.jpg">
		</div>
		<div class="author-name">${ author.getName()!author.getNameEn() }</div>
	</div>
	<div class="clearfix"></div>
	<div class="follow-author">
		<#if userAuthor.isFollowing() == true>
			<button class="pratilipi-light-blue-button" onclick="FollowUnfollowPostRequest(true)">
				<span class="glyphicon glyphicon-user" aria-hidden="true"></span>
				Follow|1.2k
			</button>
		<#else>
			<button class="pratilipi-grey-button" onclick="FollowUnfollowPostRequest(false)>
				<span class="glyphicon glyphicon-minus" aria-hidden="true"></span>
				Unfollow &nbsp; 1.2k
			</button>
		</#if>    			
	</div>
</div>

<div class="pratilipi-shadow pratilipi-block secondary-500 box text-center">
	<div class="row">
		<div class="col-xs-4 pratilipi-author-stat">
			<span><#-- ${ _strings.author_count_works } -->Reads</span>
			<div class="numbers"><#-- {{ contentPublished }} --> 56</div>
		</div>
		<div class="col-xs-4 pratilipi-author-stat">
			<span><#-- ${ _strings.author_count_reads } -->Views</span>
			<div class="numbers"><#-- {{ totalReadCount }} --> 109</div>
		</div>
		<div class="col-xs-4 pratilipi-author-stat">
			<span><#-- ${ _strings.author_count_likes } --> Likes</span>
			<div class="numbers"><#-- {{ totalFbLikeShareCount }} --> 30</div>
		</div>
	</div>
</div>