<!DOCTYPE html>
<html lang="${lang}">

	<head>
		<#include "meta/HeadBasic.ftl">
		<script>
			function convertDate( date ) {
				var d = new Date( date );
				function day(d) { return (d < 10) ? '0' + d : d; }
				function month(m) { var months = ['${ _strings.month_jan }','${ _strings.month_feb }','${ _strings.month_mar }',
												'${ _strings.month_apr }','${ _strings.month_may }','${ _strings.month_jun }',
												'${ _strings.month_jul }','${ _strings.month_aug }','${ _strings.month_sep }',
												'${ _strings.month_oct }','${ _strings.month_nov }','${ _strings.month_dec }']; 
												return months[m]; }
				return [ day(d.getDate()), month(d.getMonth()), d.getFullYear() ].join(' ');
			}
		</script>
	</head>

	<body>
		<#include "../element/pratilipi-header.ftl">
		<div class="parent-container">
			<div class="container">
				
				<#include "../element/pratilipi-author.ftl">
				
				<div class="secondary-500 pratilipi-shadow box" style="padding: 15px 10px;">
					<h2 class="pratilipi-red" style="margin: 0;">${ _strings.author_published_works }</h2>
				</div>
				<#if publishedPratilipiList?has_content>
					<#list publishedPratilipiList as pratilipi>
						<#include "../element/pratilipi-pratilipi-card.ftl">
					</#list>
				<#else>
					<div style="padding: 50px 10px;" class="secondary-500 pratilipi-shadow box">
						<img style="width: 48px; height: 48px; margin: 0px auto 20px auto; display: block;" 
								src="https://storage.googleapis.com/devo-pratilipi.appspot.com/icomoon_24_icons/SVG/info.svg" alt="${ _strings.author_no_contents_published }" />
						<div class="text-center">${ _strings.author_no_contents_published }</div>
					</div>
				</#if>
				
				
				<#if publishedPratilipiListSearchQuery?? >
					<div style="text-align: center; margin: 20px auto;">
						<a style="width: 100%; display: block;" class="pratilipi-new-blue-button text-center" href="/search?${ publishedPratilipiListSearchQuery }">
							${ _strings.load_more_contents }
						</a>
					</div>
				</#if>
			</div>
		</div>
		<#include "../element/pratilipi-footer.ftl">
	</body>
	
</html>