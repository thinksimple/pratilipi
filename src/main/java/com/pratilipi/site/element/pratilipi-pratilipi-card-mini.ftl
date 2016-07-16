<div class="media">
  <a class="media-left" href="#">
    <img class="media-object" style="width:100px;height:125px;" src="${ pratilipi.getCoverImageUrl() }">
  </a>
  <div class="media-body">
  	<div class="pull-left">	
	    <h4 class="media-heading">${ pratilipi.getTitle()!pratilipi.getTitleEn() }<a href="${ pratilipi.getPageUrl() }"><span class="glyphicon glyphicon-share" aria-hidden="true"></span></a></h4>
	    <span>${ pratilipi.getAverageRating() } <span class="glyphicon glyphicon-star" aria-hidden="true"></span></span>
	    <span>${ pratilipi.getReadCount()?c } <span class="glyphicon glyphicon-user" aria-hidden="true"></span></span>
	    <div>
	    	<a class="btn btn-primary btn-sm" href="${ pratilipi.getReadPageUrl() }">Read</a>
	    	<button type="button" class="btn btn-primary btn-sm"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span>Add to Library</button>
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