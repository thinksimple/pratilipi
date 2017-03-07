function() {
	var self = this;
	var cursor = null;

	this.pratilipi = ko.mapping.fromJS( {}, {}, self.pratilipi );

	this.updatePratilipi = function( pratilipi ) {
		ko.mapping.fromJS( pratilipi, {}, self.pratilipi );
	};

	this.initialDataLoaded = ko.observable( false );
	this.fetchPratilipiList = function() {
		var dataAccessor = new DataAccessor();
		
		dataAccessor.getPratilipiListByListName( window.location.pathname.substring(1), cursor, null, null ,
				function( pratilipiListResponse ) {
					console.log( pratilipiListResponse.pratilipiList );
					console.log( pratilipiListResponse.cursor );
		});
	};

	this.fetchPratilipiList();

}
