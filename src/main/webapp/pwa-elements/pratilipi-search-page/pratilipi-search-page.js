function() {
	var self = this;
	var cursor = null;
	var resultCount = 20;
	var dataAccessor = new DataAccessor();

	this.pratilipiList = ko.observableArray();
	this.hasMoreContents = ko.observable( false );
	
	/* Loading state */
	/*
	 * 4 Possible values for 'loadingState'
	 * LOADING
	 * LOADED_EMPTY
	 * LOADED
	 * FAILED
	 * 
	 * */
	this.loadingState = ko.observable();

	this.updatePratilipiList = function( pratilipiList ) {
		for( var i = 0; i < pratilipiList.length; i++ )
			self.pratilipiList.push( ko.mapping.fromJS( pratilipiList[i] ) );
	};

	this.fetchPratilipiList = function() {
		if( self.loadingState() == "LOADING" ) return;
		self.loadingState( "LOADING" );
		dataAccessor.getPratilipiListBySearchQuery( appViewModel.searchQuery(), cursor, null, resultCount,
				function( pratilipiListResponse ) {
					if( pratilipiListResponse == null ) {
						self.loadingState( "FAILED" );
						return;
					}
					var pratilipiList = pratilipiListResponse.pratilipiList;
					self.updatePratilipiList( pratilipiList );
					cursor = pratilipiListResponse.cursor;
					self.loadingState( self.pratilipiList().length > 0 || pratilipiList.length > 0 ? "LOADED" : "LOADED_EMPTY" );
					self.hasMoreContents( pratilipiList.length == resultCount );
		});
	};

	this.searchQueryUpdated = function() {
		self.pratilipiList( [] );
		cursor = null;
		self.loadingState( null );
		self.fetchPratilipiList();
	};

	this.searchQueryObserver = ko.computed( function() {
		appViewModel.searchQuery()
		setTimeout( function() {
			self.searchQueryUpdated( appViewModel.searchQuery() );
		}, 0 );
	}, this );

}
