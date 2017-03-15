var metaTag = function( property, content ) {
	return {
		"property": property,
		"content": content
	};
};

var MetaTagUtil = (function() {
	return {
		setMetaTagsForPratilipi: function( pratilipi ) {
			var metaTagArray = [];
			metaTagArray.push( new metaTag( "og:title", pratilipi.title ) );
			metaTagArray.push( new metaTag( "books:isbn", pratilipi.pratilipiId ) );
			metaTagArray.push( new metaTag( "og:locale", "${ lang }_IN" ) );
			metaTagArray.push( new metaTag( "og:url", "http://www.pratilipi.com" + pratilipi.pageUrl ) );
			metaTagArray.push( new metaTag( "fb:app_id", "${ fbAppId }" ) );
			metaTagArray.push( new metaTag( "og:image", pratilipi.coverImageUrl ) );
			metaTagArray.push( new metaTag( "books:author", "http://www.pratilipi.com" + pratilipi.author.pageUrl ) );
			appViewModel.metaTags( metaTagArray );
		}
	};
})();