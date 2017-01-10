<div class="secondary-500 pratilipi-shadow box" style="padding:10px">
	<div class="media">
		<div class="media-left">
			<div style="width: 100px; height: 150px; margin-left: 5px;" class="pratilipi-shadow" onclick="triggerCleverTapContentClickEvent( ${ pratilipi.getId()?c }, "${ pratilipi.title }", ${ pratilipi.author.getId()?c }, "${ pratilipi.author.name }" )">
				<a href="${ pratilipi.pageUrl }">
					<img src="${ pratilipi.getCoverImageUrl( 100 ) }" alt="${ pratilipi.title }" title="${ pratilipi.title }" />
				</a>
			</div>
		</div>
		<div class="media-body">
			<a href="${ pratilipi.pageUrl }"><h4 class="pratilipi-red" style="margin-top: 2px;">${ pratilipi.title }</h4></a>
			<#if pratilipi.author?? >
				<a href="${ pratilipi.author.pageUrl }"><h6 style="margin-top:15px;">${ pratilipi.author.name }</h6></a>
			</#if>
			<#if pratilipi.ratingCount?? && pratilipi.ratingCount gt 0 >
				<div style="margin: 10px 0px;">
					<#assign rating=pratilipi.averageRating >
					<#include "pratilipi-rating.ftl" ><small>(${ pratilipi.ratingCount })</small>
				</div>
			<#else>
				<div style="min-height: 25px;"></div>
			</#if>
			<#if !pratilipi.author?? ><div style="min-height: 25px;"></div></#if>
			<div style="margin:25px 0px 10px 0px;">
				<a class="pratilipi-light-blue-button" href="${ pratilipi.readPageUrl }&ret=${ requestUrl }">${ _strings.read }</a>
			</div>
		</div>
	</div>
</div>