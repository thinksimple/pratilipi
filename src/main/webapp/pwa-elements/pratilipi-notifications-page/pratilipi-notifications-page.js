function( params ) {
	var self = this;
	var cursor = null;
	var resultCount = 20;
	var dataAccessor = new DataAccessor();

	this.notificationList = ko.observableArray();
	this.hasMoreContents = ko.observable( true );
	this.isLoading = ko.observable( false );


	this.updateNotificationList = function( notificationList ) {
		self.notificationList( notificationList );
	};

	this.initialDataLoaded = ko.observable( false );

	this.fetchNotificationList = function() {
		self.isLoading( true );
		dataAccessor.getNotificationList( resultCount,
				function( notificationListResponse ) {
					var notificationList = notificationListResponse.notificationList;
					self.updateNotificationList( notificationList );
					cursor = notificationListResponse.cursor;
					self.initialDataLoaded( true );
					self.isLoading( false );
					self.hasMoreContents( notificationList.length == resultCount );
		});
	};

	this.fetchNotificationList();

}
