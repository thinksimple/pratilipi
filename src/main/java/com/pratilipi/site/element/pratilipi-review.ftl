<div class="box" style="padding: 10px 20px; margin-bottom: 10px;">
	<div class="row" style="padding: 10px;">
		<div class="col-xs-12 col-sm-3 col-md-3 col-lg-3" style="text-align: center; padding-top: 7px;">
			<#assign rating=review.rating >
			<#include "pratilipi-rating.ftl" > (${rating})
			<img class="img-circle" style="max-width: 64px; max-height: 64px; display: block; margin: 0 auto;" src="${ review.userImageUrl }" alt="${ review.userName }" title="${ review.userName }"/>
   			<span>${ review.userName }</span>
		</div>
		<div class="col-xs-12 col-sm-9 col-md-9 col-lg-9">
			<#if review.reviewTitle?? >
				<h4 style="color: #D0021B;">${ review.reviewTitle }</h4>
			</#if>
			<div style="text-align: justify;">${ review.review }</div>
		</div>
	</div>
</div>
	
