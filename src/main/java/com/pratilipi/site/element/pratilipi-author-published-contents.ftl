<script>
	function roundOffRating(n) {
	    return (Math.round(n*2)/2).toFixed(1);
	};
</script>

<#if ( ( user.isGuest == false ) && ( user.getId() == author.getUser().getId() ) && ( !publishedPratilipiList?has_content ) ) >
<#else>
	<div class="pratilipi-shadow secondary-500 box">	
		<div class="pull-left">
			<h5 class="pratilipi-bold pratilipi-no-margin">
				${ _strings.author_published_works }
			</h5>
			<p class="works-number"> ${author.getContentPublished()} ${ _strings.author_count_works }</p>
		</div>
		<div class="pull-right">	
			<a class="pull-right pratilipi-red pratilipi-view-more-link" href="/search?authorId=${ author.getId()?c }&state=PUBLISHED">${ _strings.view_more }</a>
		</div>
		<div class="clearfix"></div>
		<hr>	
		
		<#if publishedPratilipiList?has_content>
			<#include "pratilipi-card-list.ftl">
		<#else>
			<div style="padding: 50px 10px;" class="secondary-500 pratilipi-shadow box">
				<img style="width: 48px; height: 48px; margin: 0px auto 20px auto; display: block;" 
						src="https://storage.googleapis.com/devo-pratilipi.appspot.com/icomoon_24_icons/SVG/info.svg" alt="${ _strings.author_no_contents_published }" />
				<div class="text-center">${ _strings.author_no_contents_published }</div>
			</div>
		</#if>
	</div>
</#if>
