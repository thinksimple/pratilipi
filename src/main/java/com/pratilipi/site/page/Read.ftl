<#compress>
<!DOCTYPE html>
<html lang="${lang}">

	<head>
		<#assign mainPage="pratilipi-reader-page">
		<#include "meta/Head.ftl">
		<script>
			$(document).keyup( function(e) {
				if( e.which == 37 || e.which == 39 )
					document.querySelector( 'pratilipi-reader-page' ).keyupHandler( e.which );
			});
			function setCookie( name, value, days, path ) {
				var date = new Date();
				date.setTime( date.getTime() + ( days * 24 * 60 * 60 * 1000 ) );
				var expires = "; expires=" + date.toGMTString();
				document.cookie = name + "=" + value + expires + "; path=" + path;
			}
			function getCookie( cname ) {
				var name = cname + "=";
				var ca = document.cookie.split( ';' );
				for( var i = 0; i <ca.length; i++ ) {
					var c = ca[i];
					while( c.charAt(0) == ' ' ) c = c.substring( 1 );
					if( c.indexOf( name ) == 0 ) return c.substring(name.length,c.length);
				}
				return "";
			}
		</script>
		<style type="text/css" media="print"> * { visibility: hidden; display: none; } </style>
		<style>
			body {
				-webkit-transition: all 0.5s ease;
				-moz-transition: all 0.5s ease;
				-o-transition: all 0.5s ease;
				transition: all 0.5s ease;
			}
		</style>
		<script language=JavaScript>
			$( document ).ready( function() { $( document ).on( "contextmenu",function() { return false; }); $( document ).mousedown( function(e) { if( e.button == 2 ) return false; else return true; }); }); $(document).keyup(function(e){ if(e.keyCode == 44) return false; }); document.onkeydown = function(e) { var isCtrl = false; if(e.which == 17) isCtrl = true; if(( ( e.which == 67 ) || ( e.which == 80 ) ) && isCtrl == true) return false; }
			function clickIE4(){ if (event.button==2){ return false; } } function clickNS4(e){ if (document.layers||document.getElementById&&!document.all){ if (e.which==2||e.which==3){ return false; } } } if (document.layers){ document.captureEvents(Event.MOUSEDOWN); document.onmousedown=clickNS4; } else if (document.all&&!document.getElementById){ document.onmousedown=clickIE4; } document.oncontextmenu=new Function( "return false" )
		</script>
	</head>
	
	<body ondragstart="return false" onselectstart="return false" onContextMenu="return false" onkeydown="if ((arguments[0] || window.event).ctrlKey) return false">
		<pratilipi-reader-page 
				user-data='${ userJson }'
				pratilipi='${ pratilipiJson }'
				userpratilipi='${ userpratilipiJson }'
				font-size=${ fontSize }
				page-no='${ pageNo }'
				page-count='${ pageCount }'
				content='${ content! }'
				content-type='${ contentType }'
				index='${ indexJson }'
				></pratilipi-reader-page>
	</body>
	<#include "meta/Foot.ftl">
	
</html>
</#compress>