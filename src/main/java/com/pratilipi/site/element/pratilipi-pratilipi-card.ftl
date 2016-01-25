
<script>
	function getCookie( cname ) {
		var name = cname + "=";
		var ca = document.cookie.split( ';' );
		for( var i = 0; i < ca.length; i++ ) {
			var c = ca[i];
			while ( c.charAt(0)==' ' ) c = c.substring( 1 );
			if( c.indexOf( name ) == 0 ) return c.substring( name.length,c.length );
		}
		return "";
	}
	function redirectToReader() {
		window.location.href = "http://www.pratilipi.com/read?id=" + "${ pratilipi.id }" + "&ret=" + window.location.href + "&accessToken=" + getCookie( "access_token" );
	}
</script>
<div class="box" style="padding:10px">
	<table>
		<tr>
			<td rowspan="2" style="height:120px;width:80px;">
				<a href="${ pratilipi.pageUrl }">
					<img src="http://6.ptlp.co/pratilipi/cover?pratilipiId=5664592977985536&version=1446819509075&width=80" alt="${ pratilipi.title }" title="${ pratilipi.title }" />
				</a>
			</td>
			<td>
				<h3><a style="text-decoration: none;" href="${ pratilipi.pageUrl }">${ pratilipi.title }</a></h3>
				<#if pratilipi.author?? >
					<h4 style="margin-left: 10px;"><a style="text-decoration: none;" href="${ pratilipi.author.pageUrl }">
						${ pratilipi.author.name }
					</a></h4>
				</#if>
				<div style="margin:10px">
					<#assign rating=pratilipi.averageRating >
					<#include "pratilipi-rating.ftl" > (${rating})
				</div>
			</td>
		</tr>
		<tr>
			<td style="vertical-align:bottom">
				<div style="margin:3px 10px;text-transform:uppercase">
					<button class="btn btn-default" style="background: #D0021B; color: #FFF;" type="button" onclick="redirectToReader()">${ _strings.read }</button>
				</div>
			</td>
		</tr>
	</table>
</div>