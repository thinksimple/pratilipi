<script>
	  function AddToLibrary(bookId, addToLib){
	    $.ajax({type: "POST",
	            url: "/api/userpratilipi/library",
	            data: { pratilipiId: bookId, addedToLib: addToLib },
	            success:function(response){
	            	console.log(response);
	            	console.log(typeof response);
	            	
	            	var parsed_data = jQuery.parseJSON( response );
	      			if ( parsed_data.addedToLib == addToLib ) {
	      				window.location.reload();
	      			}
	      			else {
	      				alert("Sorry, something went wrong with the request.");
	      			}
	    		},
	            fail:function(response){
					alert("Sorry, we could not process your request.");
	    		}			    		
	    		
	    });
	  }
</script>
<#list publishedPratilipiList as pratilipi>
	<#include "pratilipi-pratilipi-card-mini.ftl">
</#list>