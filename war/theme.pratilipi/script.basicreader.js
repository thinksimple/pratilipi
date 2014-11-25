/**
 * THIS FILE CONTAINS ALL JS USED IN BASIC MODE READER.
 */

/* SCRIPT TO KEEP READER CENTER ALIGNED IRRESPECTIVE OF THE SCREEN SIZE */
function setMargin(){
	var windowSize = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth;
	var basicReader = document.getElementById("Pratilipi-Reader-Basic");
	var imageContent = document.getElementById( "imageContent" );
	var wordContent = document.getElementById( "PageContent-Pratilipi-Content" );
	var basicReaderWidth = basicReader.offsetWidth;
	var padding;

	if( basicReaderWidth >= 1000 ){
		if( imageContent )
			padding = ( windowSize - imageContent.offsetWidth )/2;
		else
			padding = ( windowSize - wordContent.offsetWidth )/2;
		basicReader.setAttribute("style", "padding-left:" + padding.toString() + "px; background-color: #f5f5f5;");
	}
	else{
		basicReader.setAttribute("style", "padding-left: 10px; padding-right: 10px; background-color: #f5f5f5;");
	}
}

/* SET PAGE CONTENT AS PER COOKIE  */
function setPageContent(){
	var imageHeight = getCookie( "image-height" );
	var fontSize = getCookie( "font-size" );
	var pageContentDiv = document.getElementById( "PageContent-Pratilipi-Content" );
	
	if( pageContentDiv){
		if( document.getElementById( "imageContent" ) && imageHeight ){
			document.getElementById( "imageContent" ).height = imageHeight;
			document.getElementById( "imageContent" ).style.width = 'auto';
			var contentTd = document.getElementById( "Pratilipi-Content-td" );
			contentTd.width = document.getElementById( "imageContent" ).width; 
		}
		
		if( pageContentDiv.getElementsByTagName( "p" ) && fontSize ){
			var tagList = Array.prototype.slice.call( pageContentDiv.getElementsByTagName( "p" ) );
			tagList.forEach( function( value, index, p ) {
				p[index].style.fontSize = fontSize + "px";
			} );
		}
	}
}

/* SAVES PRATILIPI ID AND PAGE NUMBER IN COOKIES AND REDIRECTS PAGE TO NEXT OR PREVIOUS PAGE BASED ON WHICH BUTTON IS CLICKED */
function newPage( url ){
	var urlParameters = window.location.search.substring(1).split( '&' );
	for( i=0; i< urlParameters.length; ++i){
		var param = urlParameters[i].split( '=' );
		if( param && param[0] == "id" ){
			setCookie( "pratilipiId", param[1], 365 );
		}
		
		if( param && param[0] == "page" ){
			setCookie( "pageNo", param[1], 365 );
		} else{
			
		}
	}
	window.location.replace( url );
}


/* Zoom Support */

function increaseSize(){
	var windowSize = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth;
	var imageContent = document.getElementById( "imageContent" );
	var contentTd = document.getElementById( "Pratilipi-Content-td" );
	var basicReader = document.getElementById("Pratilipi-Reader-Basic");
	var pTags = document.getElementById( "PageContent-Pratilipi-Content" ).getElementsByTagName( "p" );
		
	if( imageContent ){
		/* For Image content */
		imageContent.height = imageContent.height + 50;
		imageContent.style.width = 'auto';
		setCookie( "image-height", imageContent.height, 365 );
		contentTd.width = imageContent.offsetWidth;
		var padding = ( windowSize - imageContent.offsetWidth )/2;
		basicReader.setAttribute("style", "padding-left:" + padding.toString() + "px; background-color: #f5f5f5;"); 
	}
	else if( pTags ) {
		/* For word content */
		/* Converting string of tag element list to array */
		var tagList = Array.prototype.slice.call( pTags );
		var fontSizeStr = tagList[0].style.fontSize;
		
		/* By default font size of paragraphs in content is not set. Hence initializing fontSizeStr to default font-size of p tags across website */
		if( !fontSizeStr )
			fontSizeStr = "16px";
			
		var fontSize = parseInt( fontSizeStr.substring( 0, fontSizeStr.indexOf( 'p' )) );
		tagList.forEach( function( value, index, p ) {
			p[index].style.fontSize = ( fontSize + 2 ) + "px";
		} );
		setCookie( "font-size", ( fontSize + 2 ), 365 );
	}

}
	
function decreaseSize(){
	var windowSize = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth;
	var imageContent = document.getElementById( "imageContent" );
	var contentTd = document.getElementById( "Pratilipi-Content-td" );
	var basicReader = document.getElementById("Pratilipi-Reader-Basic");
	var pTags = document.getElementById( "PageContent-Pratilipi-Content" ).getElementsByTagName( "p" );
	
	if( imageContent ){
		/* For Image content */
		imageContent.height = imageContent.height - 50;
		imageContent.style.width = 'auto';
		setCookie( "image-height", imageContent.height, 365 );
		contentTd.width = imageContent.offsetWidth;
		var padding = ( windowSize - imageContent.offsetWidth )/2;
		basicReader.setAttribute("style", "padding-left:" + padding.toString() + "px; background-color: #f5f5f5;");
		
	} 
	else if( pTags ){
		/* For word content */
		var tagList = Array.prototype.slice.call( pTags );
		var fontSizeStr = tagList[0].style.fontSize;
		
		if( !fontSizeStr )
			fontSizeStr = "16px";
			
		var fontSize = parseInt( fontSizeStr.substring( 0, fontSizeStr.indexOf( 'p' )) );
		tagList.forEach( function( value, index, p ) {
			p[index].style.fontSize = ( fontSize - 2 ) + "px";
		} );
		setCookie( "font-size", ( fontSize - 2 ), 365 );
	}
}
