<div class="pratilipi-shadow secondary-500 box">	
	<div class="pull-left">
		<h5 class="pratilipi-red pratilipi-bold">
			Biography &nbsp<!-- ${ _strings.author_drafts } -->
			<a href="#">
			 	<span class="glyphicon glyphicon-share-alt" aria-hidden="true"></span> 
			</a>
		</h5>					
	</div>	
	<div class="clearfix"></div>
	<hr>
	<p><span class="pratilipi-bold">Place of Birth</span> &nbsp &nbsp </p>
	<#if author.getDateOfBirth()?? && author.getDateOfBirth() != "" >
		<p><span class="pratilipi-bold">Date of Birth</span> &nbsp &nbsp ${ author.getDateOfBirth() }</p>
	</#if>
	<#if author.getSummary()?? && author.getSummary() != "" >
		<p class="pratilipi-bold">My introduction: </p>
		<p> ${ author.getSummary() }</p>
	</#if>	
</div>