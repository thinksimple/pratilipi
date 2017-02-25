function( params ) { 
	var self = this;
	this.title = ko.observable( '' );
	this.titleEn = ko.observable( '' );
	this.submit = function(e, f) {
		var language = document.querySelector( '#pratilipiWrite #pratilipi_write_language' ).getAttribute( "data-val" );
		console.log( language );
	};
}