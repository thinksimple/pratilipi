function() {
	var self = this;
	var cursor = null;
	var resultCount = 20;
	var dataAccessor = new DataAccessor();

	this.pratilipiList = ko.observableArray();
	this.hasMoreContents = ko.observable( true );
	this.isLoading = ko.observable( false );


	this.updatePratilipiList = function( pratilipiList ) {
		for( var i = 0; i < pratilipiList.length; i++ )
			self.pratilipiList.push( ko.mapping.fromJS( pratilipiList[i] ) );
	};

	this.initialDataLoaded = ko.observable( false );

	this.fetchUserLibraryList = function() {
		self.isLoading( true );
		dataAccessor.getUserLibraryList( cursor, resultCount,
				function( pratilipiListResponse ) {
					var pratilipiList = pratilipiListResponse.pratilipiList;
					self.updatePratilipiList( pratilipiList );
					cursor = pratilipiListResponse.cursor;
					self.initialDataLoaded( true );
					self.isLoading( false );
					self.hasMoreContents( pratilipiList.length == resultCount );
		});
	};

	this.userObserver = ko.computed( function() {
		if( appViewModel.user.isGuest() && appViewModel.user.userId() == 0 ) {
			goToLoginPage( null, { "message": "LIBRARY" } );
		}
	}, this );

	this.fetchUserLibraryList();

}
