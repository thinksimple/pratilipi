<script>
	$( document ).ready(function() {
	    $( "#book-rating-${ pratilipi.getId()?c }" ).prepend( roundOffRating( ${ pratilipi.getAverageRating() } ) );
	});
</script>

<div class="media pratilipi-without-margin">
  <a class="media-left" href="#">
    <img class="media-object" src="${ pratilipi.getCoverImageUrl(75) }">	
  </a>
  <div class="media-body">
	  	<div class="col-xs-11">
	  		<div class="row">
		    	<div class="col-xs-10"><h4 class="media-heading clip-content-2-lines">${ pratilipi.getTitle()!pratilipi.getTitleEn() } &nbsp; </h4></div>
		    	<div class="col-xs-2 pratilipi-no-padding"><a href="${ pratilipi.getPageUrl() }"><img style="height:16px;width:16px;" src="http://0.ptlp.co/resource-all/icon/svg/share.svg"></img></a></div>
		    </div>
		    
		    <span id="book-rating-${ pratilipi.getId()?c }"> <span class="glyphicon glyphicon-star" aria-hidden="true"></span></span>
		    <span>${ pratilipi.getReadCount() }<span class="glyphicon glyphicon-user" aria-hidden="true"></span></span>
		    <div>
		    	<#if user.isGuest == false>
		    		<#if author.hasAccessToUpdate() >
		    			<button type="button" class="pratilipi-light-blue-button pratilipi-padding-7 pratilipi-font-size-14" onclick="confirmAndChangePratilipiState( '${ pratilipi.getId()?c }', 'DRAFTED' )">${ _strings.pratilipi_move_to_drafts }</button>
		    		</#if>
		    		<#if pratilipi.isAddedToLib() == true>
		    			<button type="button" class="pratilipi-grey-button pratilipi-padding-7 pratilipi-font-size-14" onclick="AddToLibrary( ${ pratilipi.getId()?c }, false )">- ${ _strings.my_library } </button>
		    		<#else>
		    			<button type="button" class="pratilipi-light-blue-button pratilipi-padding-7 pratilipi-font-size-14" onclick="AddToLibrary( ${ pratilipi.getId()?c }, true )">+ ${ _strings.my_library } </button>
		    		</#if>
		    	</#if>
		    </div> 
		 </div>
		 <div class="col-xs-1 pratilipi-no-padding" >
		 	<a href="#">
		 		<img src="http://0.ptlp.co/resource-all/icon/svg/share2.svg"></img> 
		 	</a> 
		 </div> 
  </div>
  <hr>
</div>
