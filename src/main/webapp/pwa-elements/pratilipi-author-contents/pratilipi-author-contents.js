function( params ) {
	var self = this;
	var dataAccessor = new DataAccessor();

	this.author = params.author;

	this.publishedPratilipiList = ko.observableArray();
	this.draftedPratilipiList = ko.observableArray();

	this.updatePublishedPratilipiList = function( pratilipiList ) {
		for( var i = 0; i < pratilipiList.length; i++ ) {
			var pratilipi = pratilipiList[i];
			pratilipi[ "author" ] = { "authorId": self.author.authorId(), "name": "" };
			self.publishedPratilipiList.push( ko.mapping.fromJS( pratilipi ) );
		}
	};

	this.updateDraftedPratilipiList = function( pratilipiList ) {
		for( var i = 0; i < pratilipiList.length; i++ ) {
			var pratilipi = pratilipiList[i];
			pratilipi[ "author" ] = { "authorId": self.author.authorId(), "name": "" };
			self.draftedPratilipiList.push( ko.mapping.fromJS( pratilipi ) );
		}
	};


	/* Load Drafted and Published Contents */
	var publishedCursor = null;
	var draftedCursor = null;
	var publishedResultCount = 6;
	var draftedResultCount = 5;

	/* Loading state */
	/*
	 * 4 Possible values
	 * LOADING
	 * LOADED_EMPTY
	 * LOADED
	 * FAILED
	 * 
	 * */
	this.loadingStatePublished = ko.observable();
	this.loadingStateDrafted = ko.observable();

	this.hasMorePublishedContents = ko.observable( true );
	this.hasMoreDraftedContents = ko.observable( true );


	this.loadPublishedContents = function() {
		if( self.loadingStatePublished() == "LOADING" ) return;
		self.loadingStatePublished( "LOADING" );
		dataAccessor.getPratilipiListByAuthor( self.author.authorId(), "PUBLISHED", publishedCursor, null, publishedResultCount,
				function( pratilipiListResponse ) {
					if( pratilipiListResponse == null ) {
						self.loadingStatePublished( "FAILED" );
						return;
					}
					var pratilipiList = pratilipiListResponse.pratilipiList;
					self.updatePublishedPratilipiList( pratilipiList );
					publishedCursor = pratilipiListResponse.cursor;
					self.loadingStatePublished( self.publishedPratilipiList().length > 0 || pratilipiList.length > 0 ? "LOADED" : "LOADED_EMPTY" );
					self.hasMorePublishedContents( pratilipiList.length == publishedResultCount );
		});
	};

	this.loadDraftedContents = function() {
		if( self.loadingStateDrafted() == "LOADING" ) return;
		self.loadingStateDrafted( "LOADING" );
		dataAccessor.getPratilipiListByAuthor( self.author.authorId(), "DRAFTED", draftedCursor, null, draftedResultCount,
				function( pratilipiListResponse ) {
					if( pratilipiListResponse == null ) {
						self.loadingStateDrafted( "FAILED" );
						return;
					}
					var pratilipiList = pratilipiListResponse.pratilipiList;
					self.updateDraftedPratilipiList( pratilipiList );
					draftedCursor = pratilipiListResponse.cursor;
					self.loadingStateDrafted( self.draftedPratilipiList().length > 0 || pratilipiList.length > 0 ? "LOADED" : "LOADED_EMPTY" );
					self.hasMoreDraftedContents( pratilipiList.length == publishedResultCount );
		});
	};


	/* Computed Observables */
	this.authorIdObserver = ko.computed( function() {
		if( self.author.authorId() == null ) return;
		setTimeout( function() {
			if( self.author.hasAccessToUpdate() ) {
				self.loadDraftedContents();
			}
			self.loadPublishedContents();
		}, 0 );
	}, this );

}
