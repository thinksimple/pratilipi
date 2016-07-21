<#macro follow-author-card userId followCount following name pageUrl imageUrl >
	<div class="media">
		<a class="media-left" href="${ pageUrl }">
		    <img class="media-object img-circle pratilipi-without-margin" style="width:90px;height:90px;" src="${ imageUrl }">
		</a>
		<div class="media-body">
		    <a href="${ pageUrl }"><h4 class="media-heading">${ name }</h4></a>
		    <span>${ followCount } Followers</span>
		    <#if userId == user.getId()?c >
		    	<p>Yourself</p>
		    <#else>
		    	<#if following == true >
				    <button class="pratilipi-grey-button">
						<span class="glyphicon glyphicon-minus" aria-hidden="true"></span>
						Unfollow &nbsp
					</button>
				<#else>
					<button class="pratilipi-light-blue-button">
						<span class="glyphicon glyphicon-user" aria-hidden="true"></span>
						Follow &nbsp
					</button>			
				</#if>
		    </#if>	  			
		</div>
		<br>
	</div>
</#macro>