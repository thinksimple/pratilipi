function( params ) { 
	var self = this;
	this.title = ko.observable( '' );
	this.titleEn = ko.observable( '' );
	this.agreedTerms = ko.observable( false );
	this.requestOnFlight = ko.observable( false );

	this.submit = function() {

		if( this.requestOnFlight() ) return;

		var language = document.querySelector( '#pratilipiWrite #pratilipi_write_language' ).getAttribute( "data-val" );
		var type = document.querySelector( '#pratilipiWrite #pratilipi_write_type' ).getAttribute( "data-val" );

		if( this.title().trim() == "" ) {
			ToastUtil.toast( "${ _strings.writer_error_title_required }" );
			return;
		}
		if( type == null || type.trim() == "" ) {
			ToastUtil.toast( "${ _strings.writer_error_category_required }" );
			return;
		}
		if( ! this.agreedTerms() ) {
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
		this.requestOnFlight( true );
		var pratilipi = { 
				"title": this.title(),
				"titleEn": this.titleEn(),
				"language":  language,
				"type": type,
				"state": "DRAFTED"
		};

		var dataAccessor = new DataAccessor();
		dataAccessor.createOrUpdatePratilipi( pratilipi, successCallBack, errorCallBack );

	};

}