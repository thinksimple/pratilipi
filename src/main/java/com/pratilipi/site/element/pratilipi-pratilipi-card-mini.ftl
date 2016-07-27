<script>
	$( document ).ready(function() {
	    $( "#book-rating-${ pratilipi.getId()?c }" ).prepend( roundOffRating( ${ pratilipi.getAverageRating() } ) );
	});
</script>

<div class="media pratilipi-without-margin">
  <a class="media-left" href="${ pratilipi.getPageUrl() }">
    <img class="media-object" src="${ pratilipi.getCoverImageUrl(75) }">	
  </a>
  <div class="media-body">
	  	<div class="col-xs-11">
			<a href="${ pratilipi.getPageUrl() }"><h4 class="media-heading clip-content-2-lines">${ pratilipi.getTitle()!pratilipi.getTitleEn() } &nbsp; </h4></a>
		    
		    <#if ( pratilipi.getAverageRating() >= 1 ) >
		    	<span id="book-rating-${ pratilipi.getId()?c }"> <img src="http://0.ptlp.co/resource-all/icon/svg/star-full.svg"></span>
		    </#if>
		    
		    <#if ( pratilipi.getReadCount() > 0 ) >
		    	<span>${ pratilipi.getReadCount() }<img src="http://0.ptlp.co/resource-all/icon/svg/user.svg"></span>
		    </#if>
		    <div>
		    	<#if user.isGuest == false>
		    		<#if author.hasAccessToUpdate() >
		    			<button type="button" class="pratilipi-light-blue-button pratilipi-padding-7 pratilipi-font-size-14" onclick="confirmAndChangePratilipiState( '${ pratilipi.getId()?c }', 'DRAFTED' )">${ _strings.pratilipi_move_to_drafts }</button>
		    		</#if>
		    		<#if ( user.getId() != author.getUser().getId() ) >
			    		<#if pratilipi.isAddedToLib() == true>
			    			<button type="button" class="pratilipi-grey-button pratilipi-padding-7 pratilipi-font-size-14" onclick="AddToLibrary( ${ pratilipi.getId()?c }, false )">- ${ _strings.my_library } </button>
			    		<#else>
			    			<button type="button" class="pratilipi-light-blue-button pratilipi-padding-7 pratilipi-font-size-14" onclick="AddToLibrary( ${ pratilipi.getId()?c }, true )">+ ${ _strings.my_library } </button>
			    		</#if>
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
