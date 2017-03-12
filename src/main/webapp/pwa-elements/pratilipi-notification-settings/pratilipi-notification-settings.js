function( params ) {
	var self = this;
	
	this.canSaveChanges = ko.observable( false );
	<#--
	properties: {
		selectedEmailFrequency: { type: Number },
		message: { type: String },
		: { type: Boolean }
	},

	stopUserInteraction: function() {
		// Enable spinner and disable button
		this.set( 'canSaveChanges', false );
		this.set( 'spinnerActive', true );
	},

	resumeUserInteration: function() {
		// Enable button and disable spinner
		this.set( 'canSaveChanges', true );
		this.set( 'spinnerActive', false );
	},

	open: function() {
		jQuery( "#pratilipiNotificationSettings" ).modal();
		this.stopUserInteraction();
		getNotificationPreferences( firebaseCallback );
	},

	close: function() {
		jQuery( "#pratilipiNotificationSettings" ).modal( 'hide' );
	},

	firebaseCallback: function( preferences ) {

		var emailFrequency = preferences[ "emailFrequency" ] != null ? preferences[ "emailFrequency" ] : "IMMEDIATELY";
		var i = 0;
		$( "pratilipi-notification-settings #selectEmailFrequency paper-menu paper-item" ).each( function() {
			if( $( this ).attr( "value" ) === emailFrequency ) {
				return false;
			} i++;
		});
		this.set( 'selectedEmailFrequency', i );

		var notificationSubscriptions = preferences[ "notificationSubscriptions" ] != null ? 
				preferences[ "notificationSubscriptions" ] : {};
		$( "pratilipi-notification-settings #pratilipiNotificationSettings paper-checkbox" ).each( function() {
			$( this ).prop( 'checked', notificationSubscriptions[ $( this ).val() ] != null ?
					notificationSubscriptions[ $( this ).val() ] : true );
		});

		this.resumeUserInteration();

	},

	onSubmit: function() {

		var preferences = {};
		preferences[ "emailFrequency" ] = this.$.selectEmailFrequency.selectedItem.getAttribute( "value" );
		preferences[ "notificationSubscriptions" ] = {};

		$( "pratilipi-notification-settings #pratilipiNotificationSettings paper-checkbox" ).each( function() {
			preferences[ "notificationSubscriptions" ][ $( this ).val() ] = this.checked;
		});

		this.stopUserInteraction();
		setNotificationPreferences( preferences );

		// Feedback here
		this.set( "message", "${ _strings.success_generic_message }" );
		this.async( function() {
			this.resumeUserInteration();
			this.set( "message", null );
			this.close();
		}, 1000 );
	}
	-->
}
