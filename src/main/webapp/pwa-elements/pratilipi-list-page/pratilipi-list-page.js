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

	this.fetchPratilipiList = function() {
		if( self.isLoading() || ! self.hasMoreContents() ) return;
		self.isLoading( true );
		dataAccessor.getPratilipiListByListName( window.location.pathname.substring(1), cursor, null, resultCount,
				function( pratilipiListResponse ) {
					if( pratilipiListResponse == null ) {
						self.isLoading( false );
						return;
					}
					var pratilipiList = pratilipiListResponse.pratilipiList;
					self.updatePratilipiList( pratilipiList );
					cursor = pratilipiListResponse.cursor;
					self.isLoading( false );
					self.hasMoreContents( pratilipiList.length == resultCount );
		});
	};

	this.pageScrollObserver = ko.computed( function() {
		if( ( appViewModel.scrollTop() / $( ".js-pratilipi-list-grid" ).height() ) > 0.6 ) {
			setTimeout( function() {
				self.fetchPratilipiList();
			}, 0 );
		}
	}, this ); 

	this.fetchPratilipiList();

}
