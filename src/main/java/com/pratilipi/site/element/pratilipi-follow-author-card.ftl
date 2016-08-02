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
		    	<a class="pratilipi-light-blue-button pull-right" href="/login?ret=${ retUrl }"><span class="glyphicon glyphicon-user" aria-hidden="true"></span> ${ _strings.author_follow } </a>
		    <#else>
		    	<#if can_follow == "true">
			    	<#if following == true >
					    <button class="pratilipi-grey-button pull-right" onclick="FollowAuthorPostRequest(${ authorId?c }, false)">
							<span class="glyphicon glyphicon-minus" aria-hidden="true"></span>
							${ _strings.author_unfollow }
						</button>
					<#else>
						<button class="pratilipi-light-blue-button pull-right" onclick="FollowAuthorPostRequest(${ authorId?c }, true)">
							<span class="glyphicon glyphicon-user" aria-hidden="true"></span>
							${ _strings.author_follow }
						</button>			
					</#if>		    		
		    	</#if>
		    </#if>	
		    <a href="${ pageUrl }"><h4 style="display:inline;" class="media-heading clip-content-2-lines bigger-line-height">${ name }</h4></a>
		    <div>${ followCount } ${ _strings.author_followers }</div>  			
		</div>
		<br>
	</div>
</#macro>