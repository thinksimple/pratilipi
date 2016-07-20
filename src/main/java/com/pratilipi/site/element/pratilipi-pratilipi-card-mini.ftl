<script>
	$( document ).ready(function() {
	    $( "#book-rating-${ pratilipi.getId()?c }" ).prepend( roundOffRating( ${ pratilipi.getAverageRating() } ) );
	});
</script>

<div class="media">
  <a class="media-left" href="#">
    <img class="media-object" src="${ pratilipi.getCoverImageUrl(75) }">	
  </a>
  <div class="media-body">
  	<div class="pull-left">	
	    <h4 class="media-heading">${ pratilipi.getTitle()!pratilipi.getTitleEn() } &nbsp; <a href="${ pratilipi.getPageUrl() }"><span class="glyphicon glyphicon-share" aria-hidden="true"></span></a></h4>
	    <span id="book-rating-${ pratilipi.getId()?c }"> <span class="glyphicon glyphicon-star" aria-hidden="true"></span></span>
	    <span>${ pratilipi.getReadCount()?c }<span class="glyphicon glyphicon-user" aria-hidden="true"></span></span>
	    <div>
	    	<a class="pratilipi-light-blue-button" href="${ pratilipi.getReadPageUrl() }">Read</a>
	    	<#if user.isGuest == true>
	    		<a class="pratilipi-light-blue-button" href="/login?ret=${ author.getPageUrl() }"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span>Add to Library</button>
	    	<#else>
	    		<#if pratilipi.isAddedToLib() == true>
	    			<button type="button" class="pratilipi-grey-button" onclick="AddToLibrary( ${ pratilipi.getId()?c }, false )"><span class="glyphicon glyphicon-minus" aria-hidden="true"></span>Library</button>
	    		<#else>
	    			<button type="button" class="pratilipi-light-blue-button" onclick="AddToLibrary( ${ pratilipi.getId()?c }, true )"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span>Add to Library</button>
	    		</#if>
	    	</#if>
	    </div> 
	 </div>
	 <div class="pull-right">
	 	<a href="#">
	 		<span class="glyphicon glyphicon-share-alt" aria-hidden="true"></span> 
	 	</a> 
	 </div> 
  </div>
  <hr>
</div>
