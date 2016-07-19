<script>
	$( document ).ready(function() {
	    $( "#since-date-${ author.getId()?c }" ).append( convertDate( ${ author.getRegistrationDateMillis() } ) );
	    $( "#birth-date-${ author.getId()?c }" ).append( convertDate( ${ author.getDateOfBirth() } ) );
	});
</script>
<div class="pratilipi-shadow secondary-500 box">
	<div class="pull-left">
		<h5 class="pratilipi-red pratilipi-bold">
			Biography &nbsp;
			<a href="#">
			 	<span class="glyphicon glyphicon-share-alt" aria-hidden="true"></span> 
			</a>
		</h5>					
	</div>
	<div class="clearfix"></div>
	<hr>
	<#if author.getRegistrationDateMillis()?? >
		<p id="since-date-${ author.getId()?c }"><span class="pratilipi-bold">With Pratilipi Since: </span> &nbsp; &nbsp; </p>
	</#if>
	<#if author.getDateOfBirth()?? && author.getDateOfBirth() != "" >
		<p id="birth-date-${ author.getId()?c }"><span class="pratilipi-bold">Date of Birth:</span> &nbsp; &nbsp; </p>
	</#if>
	<#if author.getSummary()?? && author.getSummary() != "" >
		<p class="pratilipi-bold">My introduction: </p>
		<p> ${ author.getSummary() }</p>
	</#if>	
</div>