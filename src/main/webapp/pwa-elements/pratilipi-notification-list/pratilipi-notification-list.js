function( params ) {
	var self = this;

	var resultCount = params.resultCount;
	var includeShowMore = params.includeShowMore;
	var dataAccessor = new DataAccessor();

	this.notificationList = ko.observableArray();
	this.hasMoreContents = ko.observable( true );
	this.isLoading = ko.observable( false );

	var cursor = null;

	this.updateNotificationList = function( notificationList ) {
		self.notificationList.push.apply( self.notificationList, notificationList );
	};

	this.initialDataLoaded = ko.observable( false );

	this.fetchNotificationList = function() {
		if( self.isLoading() ) return;
		self.isLoading( true );
		dataAccessor.getNotificationList( cursor, resultCount,
				function( notificationListResponse ) {
					var notificationList = notificationListResponse["notificationList"];
					self.updateNotificationList( notificationList );
					cursor = notificationListResponse.cursor;
					self.initialDataLoaded( true );
					self.isLoading( false );
					self.hasMoreContents( notificationList.length == resultCount && includeShowMore );
		});
	};

	this.fetchNotificationList();

}
