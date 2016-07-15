<!DOCTYPE html>
<html lang="${lang}">

	<head>
		<#-- Page Description -->
		<meta name="description" content="A platform to discover, read and share your favorite stories, poems and books in a language, device and format of your choice.">
		
		<#include "meta/HeadBasic.ftl">
	</head>

	<body>
		<#include "../element/pratilipi-header.ftl">
		<div class="parent-container">
			<div class="container">
				<#include "../element/pratilipi-author-details.ftl">
				<#include "../element/pratilipi-author-tabs.ftl">
			</div>
		</div>
		<#include "../element/pratilipi-footer.ftl">
		<script>
		    $(document).ready(function(){
			  function FollowUnfollowPostRequest(follow){
			    $.ajax({type: "POST",
			            url: "/api/userauthor/follow",
			            data: { authorId: "${ author.getId()?c }", following: follow },
			            success:function(response){
			      			if (response.following == follow) {
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
			});
		</script>
	</body>
	
</html>

<#--
import element pratilpi-author-details
import element pratilipi-authoRr-activity
	pratilipi-author-published-contents
	pratilipi-author-library-contents
		pratilipi-pratilipi-card-mini
		
import element pratilipi-author-about
	import element pratilipi-author-biography
	import element pratilipi-author-following
		pratilipi-follow-author-card
	import element pratilipi-author-followers
		pratilipi-follow-author-card
		
		
-->