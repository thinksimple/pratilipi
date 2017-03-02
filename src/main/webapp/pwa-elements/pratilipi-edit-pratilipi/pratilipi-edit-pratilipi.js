function( params ) { 
	var self = this;
	this.title = ko.observable( '' );
	this.titleEn = ko.observable( '' );
	this.pratilipiId = ko.observable( null );
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
			window.location.reload();
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
				"pratilipiId": self.pratilipiId(),
				"title": self.title(),
				"titleEn": self.titleEn(),
				"type": type
		};

		var dataAccessor = new DataAccessor();
		dataAccessor.createOrUpdatePratilipi( pratilipi, successCallBack, errorCallBack );

	};

	// TODO: Remove Hack asap
	var dataAccessor = new DataAccessor();
	dataAccessor.getPratilipiByUri( window.location.pathname, false, 
			function( pratilipi ) {
		self.pratilipiId( pratilipi.pratilipiId );
		self.title( pratilipi.title );
		self.titleEn( pratilipi.titleEn );
		var pratilipiTypes = {
				<#list pratilipiTypes as pratilipiType>
					"${ pratilipiType.value }": "${ pratilipiType.name }",
				</#list>
		};
		$( "#pratilipi_edit_pratilipi #pratilipi_edit_pratilipi_type" ).attr( 'data-val', pratilipi.type );
		$( "#pratilipi_edit_pratilipi #pratilipi_edit_pratilipi_type" ).attr( 'value', pratilipiTypes[ pratilipi.type ] );
		
	});

}