<#if author.hasAccessToUpdate()==true >
	<script>
		function changePratilipiState(bookId, state) {
		var caps_state = state.toUpperCase();
		    $.ajax({type: "POST",
		            url: "/api/pratilipi",
		            data: { pratilipiId: bookId, state: caps_state },
		            success:function(response){
		            	console.log(response);
		            	console.log(typeof response);
		            	
		            	var parsed_data = jQuery.parseJSON( response );
		      			if ( parsed_data.state == caps_state ) {
		      				window.location.reload();
		      			}
		      			else {
		      				
		      			}
		    		},
		            fail:function(response){
						alert("Sorry, we could not process your request.");
		    		}			    		
		    		
		    });		
		}
	</script>
	<div class="pratilipi-shadow secondary-500 box">	
		<div class="pull-left">
			<h5 class="pratilipi-red pratilipi-bold pratilipi-no-margin">
				Drafts
					<#-- <span><button class="pratilipi-grey-button"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span> Add new</button></span> -->
			</h5>
			<p class="works-number"> 12</p>
		</div>
		<div class="pull-right">
			<br>	
			<a class="pull-right pratilipi-red" href="#">View More</a>
		</div>                   
		<div class="clearfix"></div>
		<hr style="margin-top:0px;margin-bottom:0px;">	
		<#if draftedPratilipiList?has_content>
			<#list draftedPratilipiList as pratilipi>
				<#include "pratilipi-draft-card-mini.ftl">
			</#list>
		</#if>			
	</div>
</#if>
