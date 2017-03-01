function( params ) { 
	var self = this;
	this.title = ko.observable( '' );
	this.titleEn = ko.observable( '' );
	this.requestOnFlight = ko.observable( false );

	this.submit = function() {

		if( self.requestOnFlight() ) return;

		var type = document.querySelector( '#pratilipi_edit_pratilipi #pratilipi_edit_pratilipi_type' ).getAttribute( "data-val" );

		if( self.title().trim() == "" ) {
			ToastUtil.toast( "${ _strings.writer_error_title_required }" );
			return;
		}
		if( type == null || type.trim() == "" ) {
			ToastUtil.toast( "${ _strings.writer_error_category_required }" );
			return;
		}

		var successCallBack = function( pratilipi ) {
			ToastUtil.toastUp( "${ _strings.content_created_success }" );
			/* TODO: Update PratilipiInfo */
			$( '#pratilipi_edit_pratilipi' ).modal( 'hide' );
			self.requestOnFlight( false );
		};

		var errorCallBack = function( error, status ) {
			ToastUtil.toast( error.message != null ? error.message : "${ _strings.server_error_message }" );
			self.requestOnFlight( false );
		};

		ToastUtil.toastUp( "${ _strings.working }" );
		self.requestOnFlight( true );
		var pratilipi = { 
				"pratilipiId": "", /* TODO: pratilipiId */
				"title": self.title(),
				"titleEn": self.titleEn(),
				"type": type
		};

		var dataAccessor = new DataAccessor();
		dataAccessor.createOrUpdatePratilipi( pratilipi, successCallBack, errorCallBack );

	};

}