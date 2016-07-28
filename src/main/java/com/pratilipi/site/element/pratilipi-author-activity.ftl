<script>
	function confirmAndChangePratilipiState(bookId, state) {
		var confirm_dialog;
		if( state == "DELETED" ) {
			confirm_dialog = "Are you sure you want to delete this draft?";
		}
		else if( state == "DRAFTED" ) {
			confirm_dialog = "Are you sure you want to move this to drafts?";
		}
		
		var confirm_ans = window.confirm( confirm_dialog );
		
		if( confirm_ans ) {
			changePratilipiState(bookId, state);
		}
	}
	
	function changePratilipiState(bookId, state) {
		console.log("Inside change pratilipi state");
		console.log(bookId);
		console.log(state);
		    $.ajax({type: "POST",
		            url: "/api/pratilipi",
		            data: { pratilipiId: bookId, state: state },
		            success:function(response){
		            	console.log("I am here");
		            	console.log(response);
		            	console.log(typeof response);
		            	
		            	var parsed_data = jQuery.parseJSON( response );
		      			if ( parsed_data.state == state ) {
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
<div role="tabpanel" class="tab-pane active" id="author-activity">
    <#include "pratilipi-author-drafted-contents.ftl">
	<#include "pratilipi-author-published-contents.ftl">
</div>