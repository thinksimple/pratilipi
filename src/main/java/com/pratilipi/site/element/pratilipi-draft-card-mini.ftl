<div class="media">
  <a class="media-left" href="#">
    <img class="media-object" src="${ pratilipi.getCoverImageUrl(75) }">	
  </a>
  <div class="media-body">
  	<div class="pull-left">	
	    <h4 class="media-heading">${ pratilipi.getTitle()!pratilipi.getTitleEn() } &nbsp; <a href="${ pratilipi.getPageUrl() }"><span class="glyphicon glyphicon-share" aria-hidden="true"></span></a></h4>
	    <div>
	    	<button type="button" class="pratilipi-light-blue-button" onclick="changePratilipiState( '${ pratilipi.getId()?c }', 'PUBLISHED' )">Publish</button>
	    	<button type="button" class="pratilipi-light-blue-button" onclick="changePratilipiState( '${ pratilipi.getId()?c }', 'DELETED' )">Delete</button>
	    </div> 
	 </div>
  </div>
  <hr>
</div>
