<#if ( review.getReviewTitle()?? && review.getReviewTitle() != "" ) || 
	 ( review.getReview()?? && review.getReview() != "" ) >
	 
	 <#assign hasReview = true >
	 
	<#if review.getReviewDateMillis()?? >
		<script>
			$( document ).ready( function() {
				var d = new Date( ${ review.getReviewDateMillis()?c } );
				function day(d) { return (d < 10) ? '0' + d : d; }
				function month(m) { var months = ['Jan','Feb','Mar','Apr','May','Jun','Jul','Aug','Sep','Oct','Nov','Dec']; return months[m]; }
				$( '#reviewDate-${ review.getId() }' ).html( [ day(d.getDate()), month(d.getMonth()), d.getFullYear() ].join(' ') );
			});
		</script>
	</#if>
	
	<div class="secondary-500 pratilipi-shadow box" style="padding: 10px 20px;">
		<div class="row" style="padding: 10px;">
			<div class="col-xs-12 col-sm-4 col-md-4 col-lg-4" style="text-align: center; padding-top: 7px;">
				<div style="margin: 5px auto;">
					<#if review.getUserProfilePageUrl() ??><a href="${ review.getUserProfilePageUrl() }"></#if>
						<h5>${ review.getUserName() }</h5>
					<#if review.getUserProfilePageUrl() ??></a></#if>
	   				<#if review.getReviewDateMillis()?? >
	   					<span id="reviewDate-${ review.getId() }"></span>
	   				</#if>
	   			</div>
				<#if review.getUserImageUrl()?? >
					<div style="height: 64px; width: 64px; display: block; margin: 0 auto;">
						<#if review.getUserProfilePageUrl() ??><a href="${ review.getUserProfilePageUrl() }"></#if>
							<img class="img-circle pratilipi-shadow" style="max-width: 64px; max-height: 64px;" src="${ review.getUserImageUrl() }" alt="${ review.getUserName() }" title="${ review.getUserName() }"/>
						<#if review.getUserProfilePageUrl() ??></a></#if>
					</div>
				</#if>
				<#if review.rating?? >
					<div style="margin: 5px auto;">
						<#assign rating=review.rating>
						<#include "pratilipi-rating.ftl" >
					</div>
				</#if>
			</div>
			<div class="col-xs-12 col-sm-8 col-md-8 col-lg-8">
				<#if review.getReviewTitle()?? >
					<h3 style="margin: 10px 0px;" class="pratilipi-red">${ review.getReviewTitle() }</h3>
				</#if>
				<div style="text-align: justify;">${ review.review }</div>
			</div>
		</div>
	</div>
</#if>