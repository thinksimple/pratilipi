
<script type="text/javascript">
	function submitReview() {
		var rating = $( '#inputRating' ).val();
		var reviewTitle = $( '#inputReviewTitle' ).val();
		var review = $( '#inputReview' ).val();

		if( review == null || review.trim() == "" ) {
			// Throw message - Please Enter review
			alert( "Please Enter your review!" );
			return;
		}
		
		// Make Ajax call

		$.ajax({
		
			type: 'post',
			url: '/api/userpratilipi',

			data: { 
				'pratilipiId': ${ pratilipi.id }, 
				'rating': rating,
				'reviewTitle': reviewTitle,
				'review' : review
			},
			
			success: function( response ) {
				window.location.href = "/${ pratilipi.pageUrl }"; 
			},
			
			error: function () {
				alert( "Invalid Input!" );
			}
		});
	}
</script>


<div class="box" style="min-height: 370px;">

	<div class="media" style="margin: 20px auto;">
		<div class="media-left">
			<img class="media-object" src="${ pratilipi.getCoverImageUrl( 100 ) }" alt="${ pratilipi.title }" title="${ pratilipi.titleEn }"/>
		</div>
		<div class="media-body">
			<h3 style="margin-left: 0px; color: #D0021B;">${ pratilipi.title }</h3>
			<h4 style="margin-left: 0px;">${ pratilipi.author.name }</h4>
		</div>
	</div>
	
	<form id="reviewInputForm" class="form-horizontal" action="javascript:void(0);">
		<div class="form-group">
			<label for="inputRating" class="col-sm-2 control-label">${ _strings.rating_your_rating }</label>
			<div class="col-sm-10">
				<select class="form-control" id="inputRating">
					<option  
						<#if userpratilipi.rating??>
							<#if userpratilipi.rating == 5>selected="selected"</#if>
						</#if>
						value="5">${ _strings.rating_5_star }</option>
					<option
						<#if userpratilipi.rating??>
							<#if userpratilipi.rating == 4>selected="selected"</#if>
						</#if> 
						value="4">${ _strings.rating_4_star }</option>
					<option
						<#if userpratilipi.rating??>
							<#if userpratilipi.rating == 3>selected="selected"</#if>
						</#if> 
						value="3">${ _strings.rating_3_star }</option>
					<option
						<#if userpratilipi.rating??>
							<#if userpratilipi.rating == 2>selected="selected"</#if>
						</#if> 
						value="2">${ _strings.rating_2_star }</option>
					<option
						<#if userpratilipi.rating??>
							<#if userpratilipi.rating == 1>selected="selected"</#if>
						</#if> 
						value="1">${ _strings.rating_1_star }</option>
				</select>
			</div>
		</div>
		
	    <div class="form-group">
	        <label for="inputReviewTitle" class="col-sm-2 control-label">${ _strings.review_title }</label>
	        <div class="col-sm-10">
	            <input 	name="reviewTitle" 
	            		type="text" 
	            		class="form-control" 
	            		id="inputReviewTitle"
	            		<#if userpratilipi.reviewTitle??>value="${ userpratilipi.reviewTitle }"</#if> 
	            		placeholder="Review Title" >
	        </div>
	    </div>
	    <div class="form-group">
	        <label for="inputReview" class="col-sm-2 control-label">${ _strings.review_content }</label>
	        <div class="col-sm-10">
	            <textarea 	name="inputReview" 
	            			type="text" 
	            			class="form-control" 
	            			id="inputReview" rows="10" 
	            			<#if userpratilipi.review??>value="${ userpratilipi.review }"</#if>
	            			placeholder="Review"></textarea>
	        </div>
	    </div>
	    <div class="form-group" style="margin: 25px auto; text-align: center;">
	    	<button class="btn btn-default" onclick="submitReview()">${ _strings.review_submit_review }</button>
	    </div>
	</form>
</div>