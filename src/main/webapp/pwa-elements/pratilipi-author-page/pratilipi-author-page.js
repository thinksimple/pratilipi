function() {
	var self = this;

	var defaultAuthor = {
			"authorId": null,
			"user": { "userId": null },
			"firstName": null,
			"lastName": null,
			"name": null,
			"fullName": null,
			"gender": null,
			"dateOfBirth": null,
			"language": null,
			"location": null,
			"summary": null,
			"pageUrl": null,
			"imageUrl": null,
			"hasCoverImage": false,
			"coverImageUrl": null,
			"hasProfileImage": false,
			"profileImageUrl": null,
			"registrationDateMillis": null,
			"followCount": 0,
			"contentDrafted": 0,
			"contentPublished": 0,
			"totalReadCount": 0,
			"totalFbLikeShareCount": 0,
			"following": false,
			"hasAccessToUpdate": false
	};

	var defaultUserAuthor = {
			"authorId": null,
			"following": false
	};

	this.author = ko.mapping.fromJS( defaultAuthor, {}, self.author );
	this.userAuthor = ko.mapping.fromJS( defaultUserAuthor, {}, self.userAuthor );

	this.updateAuthor = function( author ) {
		if( author == null )
			author = defaultAuthor;
		if( author.summary != null && author.summary.trim() == "" )
			author.summary = null;
		ko.mapping.fromJS( author, {}, self.author );
		MetaTagUtil.setMetaTagsForAuthor( ko.mapping.toJS( self.author ) );
	};

	this.updateUserAuthor = function( userAuthor ) {
		if( userAuthor == null )
			userAuthor = defaultUserAuthor;
		ko.mapping.fromJS( userAuthor, {}, self.userAuthor );
	};

	this.initialDataLoaded = ko.observable( false );
	this.fetchAuthorAndUserAuthor = function() {
		var dataAccessor = new DataAccessor();
		dataAccessor.getAuthorByUri( window.location.pathname, true, 
				function( author, userAuthor ) {
			self.updateAuthor( author );
			self.updateUserAuthor( userAuthor );
			if( ! self.initialDataLoaded() ) self.initialDataLoaded( true );
		});
	};

	this.fetchAuthorAndUserAuthor();

}
