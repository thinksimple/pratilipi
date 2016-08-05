<!DOCTYPE html>
<html lang="${lang}">

	<head>
		<#-- Page Description -->
		<meta name="description" content="A platform to discover, read and share your favorite stories, poems and books in a language, device and format of your choice.">
		
		<#include "meta/HeadBasic.ftl">
	</head>

	<body>
		<#include "../element/pratilipi-new-header-basic.ftl">
		<div class="parent-container" style="padding-top: 80px;">
			<div class="container">
				<#if ( ( action == "edit_profile") && author.hasAccessToUpdate() )>
					<#include "../element/pratilipi-author-settings.ftl">
				<#elseif action == "list_contents">
				<div class="pratilipi-shadow secondary-500 box">
					<#if state == "PUBLISHED">
						<#include "../element/pratilipi-published-list.ftl">
					<#elseif state == "DRAFTED">
						<#include "../element/pratilipi-drafted-list.ftl">						
					</#if>
				</div>
				<#elseif action=="account">
					<#include "../element/pratilipi-my-account.ftl">	
				<#else>
					<#include "../element/pratilipi-author-details.ftl">
					<#include "../element/pratilipi-author-tabs.ftl">
				</#if>	
			</div>
		</div>
		<#include "../element/pratilipi-footer.ftl">
		<script>
			function getUrlParameter( key ) {
			   if( key = ( new RegExp( '[?&]' +encodeURIComponent( key ) + '=([^&]*)' ) ).exec( location.search ) )
			      return decodeURIComponent( key[1] );
			   else
				   return null;
			}
			function roundOffRating(n) {
			    return (Math.round(n*2)/2).toFixed(1);
			};
			
			function gotoShare( pageUrl, utmSource, pos ) {
			
				var language = "${ language }".toLowerCase();
				var url = "http://" + language + ".pratilipi.com" + pageUrl + "?utm_source=" + utmSource;
				if( pos !== undefined ) {
					url += ( "&pos=" + pos )
				}
				
				window.location.href = ( "/share?url=" + encodeURIComponent(url) );
			}		
			
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
			  function FollowUnfollowPostRequest(follow){
			    $.ajax({type: "POST",
			            url: "/api/userauthor/follow",
			            data: { authorId: "${ author.getId()?c }", following: follow },
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
	</body>
	
</html>