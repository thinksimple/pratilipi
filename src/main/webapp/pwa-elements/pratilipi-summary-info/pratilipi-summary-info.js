function( params ) {
	var self = this;

	this.pratilipi = params.pratilipi;
	this.userPratilipi = params.userPratilipi;
	this.updatePratilipi = params.updatePratilipi;
	this.updateUserPratilipi = params.updateUserPratilipi();

	this.fetchAuthorAndUserAuthor = function() {
		var dataAccessor = new DataAccessor();
		dataAccessor.getAuthorById( self.pratilipi.author.authorId() , true, 
			function( author, userAuthor ) {
	//			self.pushToAuthorViewModel( author, userAuthor );
				console.log( author, userAuthor );
		});
	};

	this.openShareModal = function() {
		console.log( "openShareModal" );
	};

	this.addToLibrary = function() {
		console.log( "addToLibrary" );
	};

	console.log( this.pratilipi );

}
