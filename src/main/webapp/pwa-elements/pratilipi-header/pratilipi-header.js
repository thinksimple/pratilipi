function( params ) {
	var self = this;

	var openWriteDialog = function() {
		$( "#pratilipiWrite" ).modal();
	};

	var closeWriteDialog = function() {
		$( "#pratilipiWrite" ).modal( 'hide' );
	};

	this.search = function( formElement ) {
		if( appViewModel.searchQuery() && appViewModel.searchQuery().trim().length && window.location.pathname != "/search" )
			window.location.href = "/search?q=" + appViewModel.searchQuery();
	};

	this.openMenuNavigationDrawer = function() {
		if( !( typeof( componentHandler ) == 'undefined' ) ) {
			componentHandler.upgradeAllRegistered();
		}
	  document.querySelector( '.mdl-layout' ).MaterialLayout.toggleDrawer();
	};

	this.write = function() {
		if( isMobile() ) {
			ToastUtil.toast( "${ _strings.write_on_desktop_only }", 5000 );
			return;
		}
		if( appViewModel.user.isGuest() ) {
			goToLoginPage( { "action": "write" }, { "message": "WRITE" } );
		} else {
			openWriteDialog();
		}
	};

	/* Loading Notifications */
	var notificationContainer = $( "header.pratilipi-header #notificationContainer" );
	var notificationLink = $( "header.pratilipi-header #notificationLink" );
	notificationLink.click( function() {
		resetFbNotificationCount();
		var windowsize = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth;
		if( windowsize < 768 ) {
			window.location.href = "/notifications";
		} else {
			notificationContainer.fadeToggle(50);
		}
		return false;
	});

	$( document ).click( function() { notificationContainer.hide(); } );
	notificationContainer.click( function(e) { e.stopPropagation(); } );

	this.userObserver = ko.computed( function() {
		if( ! appViewModel.user.isGuest() && getUrlParameter( 'action' ) == "write" ) {
			openWriteDialog();
		}
	}, this );

}
