function() {
	var self = this;
	var cursor = null;
	var resultCount = 20;

	this.pratilipiList = ko.mapping.fromJS( [], {}, self.pratilipi );
	this.hasMoreContents = ko.observable( true );


	this.updatePratilipiList = function( pratilipiList ) {
		ko.mapping.fromJS( pratilipiList, {}, self.pratilipiList );
	};

	this.initialDataLoaded = ko.observable( false );

	this.fetchPratilipiList = function() {
		var dataAccessor = new DataAccessor();
		dataAccessor.getPratilipiListByListName( window.location.pathname.substring(1), cursor, null, resultCount,
				function( pratilipiListResponse ) {
					var pratilipiList = pratilipiListResponse.pratilipiList;
					self.updatePratilipiList( pratilipiList );
					cursor = pratilipiListResponse.cursor;
					self.initialDataLoaded( true );
					self.hasMoreContents( pratilipiList.length == resultCount );
		});
	};

	this.fetchPratilipiList();

}
