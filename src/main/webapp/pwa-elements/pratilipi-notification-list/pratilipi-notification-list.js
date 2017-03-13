function( params ) {
	var self = this;

	var resultCount = params.resultCount;
	var includeShowMore = params.includeShowMore;
	var listenToFirebase = params.listenToFirebase != null ? params.listenToFirebase : false;
	var dataAccessor = new DataAccessor();
	var cursor = null;

	/*
	 * 5 Possible values for 'notificationsLoaded'
	 * INITIAL
	 * LOADING
	 * LOADED_EMPTY
	 * LOADED
	 * FAILED
	 * 
	 * */
	this.notificationsLoaded = ko.observable( "INITIAL" );
	this.notificationList = ko.observableArray();
	this.hasMoreContents = ko.observable( true );

	this.setNotificationList = function( notificationList ) {
		self.notificationList( notificationList );
	};

	this.updateNotificationList = function( notificationList ) {
		self.notificationList.push.apply( self.notificationList, notificationList );
	};

	this.fetchNotificationList = function( setNotificationList ) {
		if( self.notificationsLoaded() == "LOADING" ) return;
		self.notificationsLoaded( "LOADING" );
		dataAccessor.getNotificationList( cursor, resultCount,
				function( notificationListResponse ) {
					if( notificationListResponse == null ) {
						self.notificationsLoaded( "FAILED" );
						return;
					}
					var notificationList = notificationListResponse["notificationList"];
					cursor = notificationListResponse.cursor;
					if( setNotificationList )
						self.setNotificationList( notificationList );
					else
						self.updateNotificationList( notificationList );
					self.notificationsLoaded( notificationList.length > 0 ? "LOADED" : "LOADED_EMPTY" );
					self.hasMoreContents( notificationList.length == resultCount && includeShowMore );
		});
	};

	this.resetNotificationList = function() {
		self.fetchNotificationList( true );
	};

	this.userObserver = ko.computed( function() {
		if( ! appViewModel.user.isGuest() ) {
			setTimeout( self.fetchNotificationList, 0 );
		}
	}, this );

	this.notificationCountObserver = ko.computed( function() {
		if( ! appViewModel.user.isGuest() && appViewModel.notificationCount() != 0 && listenToFirebase ) {
			setTimeout( self.resetNotificationList, 0 );
		}
	}, this );

}
