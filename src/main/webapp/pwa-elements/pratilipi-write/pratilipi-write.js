function( params ) { 
	var self = this;
	this.title = ko.observable( '' );
	this.titleEn = ko.observable( '' );
	this.type = ko.observable( null );
	this.agreedTerms = ko.observable( false );
	this.requestOnFlight = ko.observable( false );

	this.submit = function() {

		if( self.requestOnFlight() ) return;

		if( self.title().trim() == "" ) {
			ToastUtil.toast( "${ _strings.writer_error_title_required }" );
			return;
		}

		if( self.type() == null || self.type().trim() == "" ) {
			ToastUtil.toast( "${ _strings.writer_error_category_required }" );
			return;
		}

		if( ! self.agreedTerms() ) {
			ToastUtil.toast( "${ _strings.writer_error_copyright_required }" );
			return;
		}

		var successCallBack = function( pratilipi ) {
			ToastUtil.toastUp( "${ _strings.content_created_success }" );
			window.location.href = pratilipi.writePageUrl;
		};

		var errorCallBack = function( error, status ) {
			ToastUtil.toast( error.message != null ? error.message : "${ _strings.server_error_message }" );
			self.requestOnFlight( false );
		};

		ToastUtil.toastUp( "${ _strings.working }" );
		self.requestOnFlight( true );
		var pratilipi = { 
				"title": self.title(),
				"titleEn": self.titleEn(),
				"language":  "${ language }",
				"type": self.type(),
				"state": "DRAFTED"
		};

		var dataAccessor = new DataAccessor();
		dataAccessor.createOrUpdatePratilipi( pratilipi, successCallBack, errorCallBack );

	};

	this.updateType = function() {
		self.type( document.querySelector( '#pratilipiWrite #pratilipi_write_type' ).getAttribute( "data-val" ) );
	};

	this.canCreatePratilipi = ko.computed( function() {
		return self.title() != null && self.title() != "" && self.agreedTerms() && self.type() != null && ! self.requestOnFlight();
	}, this );

}