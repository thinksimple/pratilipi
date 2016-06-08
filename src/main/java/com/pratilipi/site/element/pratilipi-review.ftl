<#if review.getReview()?? && review.getReview() != "" >
	 
	<#assign hasReview = true >
	 
	<#if review.getReviewDateMillis()?? >
		<script>
			$( document ).ready( function() {
				$( '#reviewDate-${ review.getId() }' ).html( convertDate( ${ review.getReviewDateMillis()?c } ) );
			});
		</script>
	</#if>
	<div class="secondary-500 pratilipi-shadow box" style="padding: 10px 20px;">
		<div style="padding: 20px;">
			<a href="${ review.getUserProfilePageUrl() }">
				<img style="margin: 12px 0;" class="img-circle pratilipi-shadow pull-left" src="${ review.getUserImageUrl() }" alt="${ review.getUserName() }" title="${ review.getUserName() }"/>
			</a>
			<div style="display: inline-block; margin-left: 16px;">
				<a href="${ review.getUserProfilePageUrl() }">${ review.getUserName() }</a>  
                <#if review.rating?? >
					<div>
						<#assign rating=review.rating>
						<#include "pratilipi-rating.ftl" >
					</div>
				</#if>
				<#if review.getReviewDateMillis()?? >
					<span style="display: block;" id="reviewDate-${ review.getId() }"></span>
				</#if>
			</div>
		</div>
		<div style="text-align: justify; font-size: 15px; margin-bottom: 8px;">${ review.getReview() }</div>
		<div class="reply-section">
			<a class="reply-text pratilipi-red">${ _strings.comment_reply_comment }</a>
			<br/>
			<a class="expand-comments" id="view-replies-${ review.getId() }" onClick="loadComments( '${ review.getId() }' )">${ _strings.comment_see_all_comments }</a>
		</div>
	</div>
	<div id="comments-${ review.getId() }"></div>
</#if>