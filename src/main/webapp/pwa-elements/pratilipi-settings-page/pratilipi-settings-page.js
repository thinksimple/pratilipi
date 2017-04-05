function( params ) {
	var self = this;
	var dataAccessor = new DataAccessor();

	/* PROFILE SETTINGS */
	this.firstName = ko.observable();
	this.lastName = ko.observable();
	this.firstNameEn = ko.observable();
	this.lastNameEn = ko.observable();
	this.penName = ko.observable();
	this.language = ko.observable();
	this.summary = ko.observable();

	this.email = ko.observable();
	this.phone = ko.observable();
	this.gender = ko.observable();
	this.dateOfBirth = ko.observable();
	this.isLoading = ko.observable();

	this.updateLanguage = function() {
		self.language( document.querySelector( '#pratilipi-settings-language' ).getAttribute( "data-val" ) );
	};

	this.updateGender = function() {
		self.gender( document.querySelector( '#pratilipi-settings-gender' ).getAttribute( "data-val" ) );
	};

	this.loadUserAndAuthor = function() {
		if( self.isLoading() ) return;
		self.isLoading( true );
		dataAccessor.getAuthorById( appViewModel.user.author.authorId(), false,
			function( author ) {
				self.firstName( author.firstName );
				self.lastName( author.lastName );
				self.firstNameEn( author.firstNameEn );
				self.lastNameEn( author.lastNameEn );
				self.penName( author.penName );
				self.language( author.language );
				$( "#pratilipi-settings-language" ).attr( 'data-val', author.language );
				$( "#pratilipi-settings-language" ).attr( 'value', getLanguageVernacular( author.language ) );
				self.summary( author.summary );
				var getGenderValue = function( gender ) {
					if( gender == null ) return null;
					if( gender == "MALE" ) return "${ _strings.gender_male }";
					if( gender == "FEMALE" ) return "${ _strings.gender_female }";
					if( gender == "OTHER" ) return "${ _strings.gender_other }";
				};
				self.gender( author.gender );
				$( "#pratilipi-settings-gender" ).attr( 'data-val', author.gender );
				$( "#pratilipi-settings-gender" ).attr( 'value', getGenderValue( author.gender ) );
				var getDateOfBirthValue = function( date ) {
					if( date == null ) return null;
					var d = new Date( date.replace( /(\d{2})-(\d{2})-(\d{4})/, "$2/$1/$3") ),
						month = '' + ( d.getMonth() + 1 ),
						day = '' + d.getDate(),
						year = d.getFullYear();
						if (month.length < 2) month = '0' + month;
						if (day.length < 2) day = '0' + day;
						return [ year, month, day ].join( '-' );
				};
				self.dateOfBirth( getDateOfBirthValue( author.dateOfBirth ) );
				self.isLoading( false );
		});
	};

	this.updateProfileSettings = function() {
		var formatDateOfBirth = function( inputDate ) {
			if( inputDate == null ) return null;
			var d = new Date( inputDate ),
				month = '' + ( d.getMonth() + 1 ),
				day = '' + d.getDate(),
				year = d.getFullYear();
			if (month.length < 2) month = '0' + month;
			if (day.length < 2) day = '0' + day;
			return [ day, month, year ].join( '-' );
		};
		var author = { "authorId": appViewModel.user.author.authorId(), "firstName": self.firstName(), "language": self.language() };
		if( self.lastName() ) author[ "lastName" ] = self.lastName();
		if( self.firstNameEn() ) author[ "firstNameEn" ] = self.firstNameEn();
		if( self.lastNameEn() ) author[ "lastNameEn" ] = self.lastNameEn();
		if( self.penName() ) author[ "penName" ] = self.penName();
		if( self.summary() ) author[ "summary" ] = self.summary();
		if( self.gender() ) author[ "gender" ] = self.gender();
		if( self.dateOfBirth() ) author[ "dateOfBirth" ] = formatDateOfBirth( self.dateOfBirth() );

		/* 4 states: INITIAL, LOADING, SUCCESS, FAIL */
		self.userApiCallState = ko.observable( "INITIAL" );
		self.authorApiCallState = ko.observable( "INITIAL" );
		self.callbackObserver = ko.computed( function() {
			if( self.userApiCallState() == "SUCCESS" && self.authorApiCallState() == "SUCCESS" ) {
				ToastUtil.toast( "${ _strings.success_generic_message }" );
				self.isLoading( false );
				self.userApiCallState( "INITIAL" );
				self.authorApiCallState( "INITIAL" );
			} else if( self.userApiCallState() == "LOADING" || self.authorApiCallState() == "LOADING" ) {
				return;
			} else if( self.userApiCallState() == "FAILED" || self.authorApiCallState() == "FAILED" ) {
				ToastUtil.toast( "${ _strings.server_error_message }" );
				self.isLoading( false );
			}
		}, self );

		self.isLoading( true );
		ToastUtil.toastUp( "${ _strings.working }" );

		self.userApiCallState( "LOADING" );
		dataAccessor.createOrUpdateUser( appViewModel.user.userId(), self.email(), self.phone(),
			function( user ) {
				self.userApiCallState( "SUCCESS" );
				appViewModel.user.email( user.email );
				appViewModel.user.phone( user.phone );
			}, function( error ) {
				self.userApiCallState( "FAILED" );
		});

		self.authorApiCallState( "LOADING" );
		dataAccessor.createOrUpdateAuthor( author,
			function( aAuthor ) {
				self.authorApiCallState( "SUCCESS" );
			}, function( error ) {
				self.authorApiCallState( "FAILED" );
		});
	};

	this.canSaveProfileSettings = ko.computed( function() {
		return self.firstName() != null && self.firstName().trim() != "" &&
				self.language() != null && self.language().trim() != "" &&
				! self.isLoading();
	}, this );

	this.userObserver = ko.computed( function() {
		if( appViewModel.user.userId() == null ) return;
		if( appViewModel.user.isGuest() && appViewModel.user.userId() == 0 )
			goToLoginPage();

		self.email( appViewModel.user.email() );
		self.phone( appViewModel.user.phone() );

		setTimeout( function() {
			self.loadUserAndAuthor();
		}, 0 );

	}, this );


	/* NOTIFICATION SETTINGS */
	var getEmailFrequencyVernacular = function( emailFrequency ) {
		switch( emailFrequency ) {
			case "IMMEDIATELY":
				return "${ _strings.email_frequency_immediate }";
			case "DAILY":
				return "${ _strings.email_frequency_daily }";
			case "WEEKLY":
				return "${ _strings.email_frequency_weekly }";
			case "MONTHLY":
				return "${ _strings.email_frequency_monthly }";
			case "NEVER":
				return "${ _strings.email_frequency_never }";
		}
	};

	this.updateNotificationPreferences = function() {
		var userPreferences = {};
		userPreferences[ "emailFrequency" ] = document.querySelector( "#pratilipi-settings-email-frequency" ).getAttribute( "data-val" );
		userPreferences[ "notificationSubscriptions" ] = {};
		$( '#notification-settings .mdl-js-checkbox' ).each( function( index, element ) {
			userPreferences[ "notificationSubscriptions" ][ element.firstChild.value ] = element.firstChild.checked;
		});
		setUserPreferences( userPreferences );
		ToastUtil.toast( "${ _strings.success_generic_message }" );
	};

	this.firebaseCallback = ko.computed( function() {
		var userPreferences = appViewModel.userPreferences();
		var emailFrequency = userPreferences[ "emailFrequency" ] != null ? userPreferences[ "emailFrequency" ] : "IMMEDIATELY";
		var notificationSubscriptions = userPreferences[ "notificationSubscriptions" ] != null ?
				userPreferences[ "notificationSubscriptions" ] : {};

		$( "#pratilipi-settings-email-frequency" ).attr( 'data-val', emailFrequency );
		$( "#pratilipi-settings-email-frequency" ).attr( 'value', getEmailFrequencyVernacular( emailFrequency ) );
		$( '#notification-settings .mdl-js-checkbox' ).each( function( index, element ) {
			if( element.MaterialCheckbox == null ) return;
			var val = element.firstChild.value;
			if( notificationSubscriptions[ val ] != null && ! notificationSubscriptions[ val ] )
				element.MaterialCheckbox.uncheck();
			else
				element.MaterialCheckbox.check();
		});

	}, this );


	/* PASSWORD SETTINGS */
	this.currentPassword = ko.observable();
	this.newPassword = ko.observable();
	this.confirmPassword = ko.observable();

	this.updatePassword = function() {
		if( self.newPassword() != self.confirmPassword() ) {
			ToastUtil.toast( "Passwords doesn't match!" );
			return;
		}
		if( self.isLoading() ) return;
		self.isLoading( true );
		ToastUtil.toastUp( "${ _strings.working }" );
		dataAccessor.updateUserPassword( self.currentPassword(), self.newPassword(),
			function( user ) {
				self.currentPassword( null );
				self.newPassword( null );
				self.confirmPassword( null );
				self.isLoading( false );
				ToastUtil.toast( "${ _strings.success_generic_message }" );
			}, function( error ) {
				self.isLoading( false );
				var message = "${ _strings.server_error_message }";
				if( error[ "password" ] != null ) message = error[ "password" ];
				if( error[ "newPassword" ] != null ) message = error[ "newPassword" ];
				if( error[ "message" ] != null ) message = error[ "message" ];
				ToastUtil.toast( message );
		});
	};

	this.canUpdatePassword = ko.computed( function() {
		return self.currentPassword() != null && self.currentPassword() != "" &&
				self.newPassword() != null && self.newPassword() != "" &&
				self.confirmPassword() != null && self.confirmPassword() != "" &&
				! self.isLoading();
	}, this );


	/* LOGOUT */
	this.logoutUser = function() {
		if( self.isLoading() ) return;
		self.isLoading( true );
		ToastUtil.toastUp( "${ _strings.working }" );
		dataAccessor.logoutUser(
			function( user ) {
				ToastUtil.toastUp( "${ _strings.success_generic_message }" );
				window.location.href = "/";
			}, function( error ) {
				self.isLoading( false );
				ToastUtil.toast( error[ "message" ] != null ? error[ "message" ] : "${ _strings.server_error_message }" );
		});
	};

}