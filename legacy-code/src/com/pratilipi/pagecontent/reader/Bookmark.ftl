<#macro bookmark pageNo bookmarks>
	 <#if bookmarks?index_of( pageNo?c ) == -1>
	 	<img id="PageContent-Reader-Bookmark" title="Bookmark" src="/theme.pratilipi/images/bookmark_black.png" onclick="onClick( this );" data-bookmark="add" />
	 <#else>
	 	<img id="PageContent-Reader-Bookmark" title="Remove Bookmark" src="/theme.pratilipi/images/bookmark_red.png" onclick="onClick( this );" data-bookmark="remove" />
	 </#if>
	 
	 <style type="text/css">
	 	#PageContent-Reader-Bookmark {
	 		cursor: pointer;
	 		float: right;
	 	}
	 </style>
	 
	 <script language="javascript">
	 	var bookmarkString = ${ bookmarks }
	 	function onClick( object ){
 			var requestType = object.getAttribute( "data-bookmark" );
 			if( requestType.indexOf( "remove" ) != -1 ) {
				object.src = "/theme.pratilipi/images/bookmark_black.png";
 			} else {
 				object.src = "/theme.pratilipi/images/bookmark_red.png";
 			}
 			putBookmark( pageNo );
	 	}
	 	
	 	function setBookmark( pageNumber, bookmarkString ){
	 		var bookmarkImg = document.getElementById( "PageContent-Reader-Bookmark" );
	 		
	 		if( bookmarkString.length > 0 ){
		 		for( var i=0; i < bookmarkString.length; ++i ){
			 		if( bookmarkString[i].pageNo == pageNumber ){
			 			bookmarkImg.src = "/theme.pratilipi/images/bookmark_red.png";
			 			bookmarkImg.title = "Remove Bookmark";
			 			bookmarkImg.setAttribute( "data-bookmark", "remove");
			 			break;
			 		}
			 		else {
			 			bookmarkImg.src = "/theme.pratilipi/images/bookmark_black.png";
			 			bookmarkImg.title = "Bookmark";
			 			bookmarkImg.setAttribute( "data-bookmark", "add");
			 		}
		 		}
	 		} else {
	 			bookmarkImg.src = "/theme.pratilipi/images/bookmark_black.png"
	 			bookmarkImg.title = "Bookmark";
	 			bookmarkImg.setAttribute( "data-bookmark", "add");
	 		}
	 	}
	 	
	 	function putBookmark( pageNumber ){
	 		
	 		var bookmarkImg = document.getElementById( "PageContent-Reader-Bookmark" );
 			var requestType = bookmarkImg.getAttribute( "data-bookmark" );
	 		
	 		jQuery.ajax({
				url: "/api.pratilipi/userpratilipi",
				type: "PUT",
				contentType: "application/json",
				dataType: "json",
				handleAs: "json",
				data: JSON.stringify( { pratilipiId: ${ pratilipiData.getId()?c }, bookmark: pageNumber, requestType: requestType } ),
				beforeSend: function( data, object ){
				},
				success: function( response, status, xhr ) {
					bookmarkString = JSON.parse( response.bookmarks );
					setBookmark( pageNumber, bookmarkString );
					updateBookmarkDiv();
				}, 
				error: function( xhr, status, error ) {
					//Declared in ReaderContent.ftl
					setBookmark( pageNumber, bookmarkString );
				},
				complete: function( event, response ){
				}
			});
	 	}
	 	
	 	function handleAjaxPutResponse( response, pageNumber ){
	 		
	 	}
	 </script>
</#macro>