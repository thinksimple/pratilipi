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
	    <span>${ pratilipi.getReadCount() }<span class="glyphicon glyphicon-user" aria-hidden="true"></span></span>
	    <div>
	    	<#if user.isGuest == false>
	    		<#if author.hasAccessToUpdate() >
	    			<button type="button" class="pratilipi-light-blue-button" onclick="confirmAndChangePratilipiState( '${ pratilipi.getId()?c }', 'DRAFTED' )">Move to Drafts</button>
	    		</#if>
	    		<#if pratilipi.isAddedToLib() == true>
	    			<button type="button" class="pratilipi-grey-button" onclick="AddToLibrary( ${ pratilipi.getId()?c }, false )">- Library</button>
	    		<#else>
	    			<button type="button" class="pratilipi-light-blue-button" onclick="AddToLibrary( ${ pratilipi.getId()?c }, true )">+ Library</button>
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
