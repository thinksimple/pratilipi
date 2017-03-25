function( params ) {
	var self = this;

	var resultCount = params.resultCount;
	var notificationsPageBehaviour = params.notificationsPageBehaviour != null ? params.notificationsPageBehaviour : false;
	var listenToFirebase = params.listenToFirebase != null ? params.listenToFirebase : false;
	var dataAccessor = new DataAccessor();
	var cursor = null;

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
	this.notificationList = ko.observableArray();
	this.hasMoreContents = ko.observable( false );

	this.setNotificationList = function( notificationList ) {
		self.notificationList( notificationList );
	};

	this.updateNotificationList = function( notificationList ) {
		self.notificationList.push.apply( self.notificationList, notificationList );
	};

	this.fetchNotificationList = function() {
		if( self.loadingState() == "LOADING" ) return;
		self.loadingState( "LOADING" );
		dataAccessor.getNotificationList( cursor, resultCount,
				function( notificationListResponse ) {
					if( notificationListResponse == null ) {
						self.loadingState( "FAILED" );
						return;
					}
					var notificationList = notificationListResponse[ "notificationList" ];
					self.loadingState( self.notificationList().length > 0 || notificationList.length > 0 ? "LOADED" : "LOADED_EMPTY" );
					if( ! notificationsPageBehaviour ) {
						self.setNotificationList( notificationList );
						return;
					}
					cursor = notificationListResponse.cursor;
					self.updateNotificationList( notificationList );
					self.hasMoreContents( notificationList.length == resultCount && notificationsPageBehaviour );
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
