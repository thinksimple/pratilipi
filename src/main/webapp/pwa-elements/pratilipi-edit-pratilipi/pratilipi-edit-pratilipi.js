function( params ) { 
	var self = this;
	this.pratilipi = params.pratilipi;
	this.updatePratilipi = params.updatePratilipi;

	this.pratilipiId = ko.observable( null );
	this.title = ko.observable();
	this.titleEn = ko.observable();
	this.type = ko.observable();
	this.summary = ko.observable();

	this.pratilipiObserver = ko.computed( function() {
		self.pratilipiId( self.pratilipi.pratilipiId() );
		self.title( self.pratilipi.title() );
		self.titleEn( self.pratilipi.titleEn() );
		self.type( self.pratilipi.type() );
		self.summary( self.pratilipi.summary() );
		$( "#pratilipi_edit_pratilipi #pratilipi_edit_pratilipi_type" ).attr( 'data-val', self.pratilipi.type() );
		$( "#pratilipi_edit_pratilipi #pratilipi_edit_pratilipi_type" ).attr( 'value', getPratilipiTypeVernacular( self.pratilipi.type() ) );
	}, this );


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

		var successCallBack = function( pratilipi ) {
			ToastUtil.toast( "${ _strings.content_created_success }" );
			self.updatePratilipi( pratilipi );
			$( '#pratilipi_edit_pratilipi' ).modal( 'hide' );
			self.requestOnFlight( false );
		};

		var errorCallBack = function( error, status ) {
			ToastUtil.toast( error.message != null ? error.message : "${ _strings.server_error_message }", 3000 );
			self.requestOnFlight( false );
		};

		ToastUtil.toastUp( "${ _strings.working }" );
		var pratilipi = { 
				"pratilipiId": self.pratilipiId(),
				"title": self.title(),
				"titleEn": self.titleEn(),
				"type": self.type(),
				/* "summary": self.summary() */
		};

		self.requestOnFlight( true );
		var dataAccessor = new DataAccessor();
		dataAccessor.createOrUpdatePratilipi( pratilipi, successCallBack, errorCallBack );

	};

	this.updateType = function() {
		self.type( document.querySelector( '#pratilipi_edit_pratilipi #pratilipi_edit_pratilipi_type' ).getAttribute( "data-val" ) );
	};

	this.canEditPratilipi = ko.computed( function() {
		return self.title() != null && self.title() != "" && self.type() != null && ! self.requestOnFlight();
	}, this );

}