<script>
	function FollowAuthorPostRequest(authorId, follow){
		$.ajax({type: "POST",
		        url: "/api/userauthor/follow",
		        data: { authorId: authorId, following: follow },
		        success:function(response){
		        	console.log(response);
		        	console.log(typeof response);
		        	
		        	var parsed_data = jQuery.parseJSON( response );
		  			if ( parsed_data.following == follow ) {
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
<#macro follow_author_card isGuest can_follow retUrl authorId followCount following name pageUrl imageUrl >
	<div class="media">
		<a class="media-left" href="${ pageUrl }">
		    <img class="media-object img-circle pratilipi-without-margin" style="width:90px;height:90px;" src="${ imageUrl }">
		</a>
		<div class="media-body pratilipi-padding-15">
		    <#if isGuest == "true" >
		    	<a class="pratilipi-light-blue-button pull-right" href="/login?ret=${ retUrl }"><span class="glyphicon glyphicon-user" aria-hidden="true"></span> ${ _strings.author_follow } &nbsp</a>
		    <#else>
		    	<#if can_follow == "true">
			    	<#if following == true >
					    <button class="pratilipi-grey-button pull-right" onclick="FollowAuthorPostRequest(${ authorId?c }, false)">
							<span class="glyphicon glyphicon-minus" aria-hidden="true"></span>
							${ _strings.author_unfollow } &nbsp
						</button>
					<#else>
						<button class="pratilipi-light-blue-button pull-right" onclick="FollowAuthorPostRequest(${ authorId?c }, true)">
							<span class="glyphicon glyphicon-user" aria-hidden="true"></span>
							${ _strings.author_follow } &nbsp
						</button>			
					</#if>		    		
		    	<#else>
		    		<p>${ _strings.author_cannot_follow_yourself }</p>
		    	</#if>
		    </#if>	
		    <a href="${ pageUrl }"><h4 class="media-heading">${ name }</h4></a>
		    <span>${ followCount } ${ _strings.author_followers }</span>  			
		</div>
		<br>
	</div>
</#macro>