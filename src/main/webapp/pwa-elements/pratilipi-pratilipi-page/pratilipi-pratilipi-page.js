function() {
	var self = this;

	var defaultPratilipi = { 
			"pratilipiId": null, 
			"title": null, 
			"titleEn": null, 
			"language": null, 
			"author": { "authorId": null,"name": null, "pageUrl": null }, 
			"summary": null, 
			"pageUrl": null, 
			"coverImageUrl": null,
			"readPageUrl": null,
			"writePageUrl": null,
			"type": null,
			"state": null,
			"listingDateMillis": null,
			"wordCount": null,
			"reviewCount": null,
			"ratingCount": null,
			"averageRating": null, 
			"readCount": null, 
			"fbLikeShareCount": null,
			"hasAccessToUpdate": false 
	};

	var defaultUserPratilipi = {
			"userPratilipiId": null,
			"userId": null,
			"pratilipiId": null,
			"userName": null,
			"userImageUrl": null,
			"userProfilePageUrl": null,
			"rating": 0,
			"review": "",
			"reviewState": null,
			"addedToLib": false, 
			"hasAccessToReview": false, 
			"isLiked": false,
	};

	this.pratilipi = ko.mapping.fromJS( defaultPratilipi, {}, self.pratilipi );
	this.userPratilipi = ko.mapping.fromJS( defaultUserPratilipi, {}, self.userPratilipi );

	this.updatePratilipi = function( pratilipi ) {
		ko.mapping.fromJS( pratilipi, {}, self.pratilipi );
		MetaTagUtil.setMetaTagsForPratilipi( self.pratilipi );
	};

	this.updateUserPratilipi = function( userPratilipi ) {
		ko.mapping.fromJS( userPratilipi, {}, self.userPratilipi );
	};

	this.initialDataLoaded = ko.observable( false );
	this.fetchPratilipiAndUserPratilipi = function() {
		var dataAccessor = new DataAccessor();
		dataAccessor.getPratilipiByUri( window.location.pathname, true, 
				function( pratilipi, userPratilipi ) {
			self.updatePratilipi( pratilipi );
			self.updateUserPratilipi( userPratilipi );
			if( ! self.initialDataLoaded() ) self.initialDataLoaded( true );
		});
	};

	this.fetchPratilipiAndUserPratilipi();

}
