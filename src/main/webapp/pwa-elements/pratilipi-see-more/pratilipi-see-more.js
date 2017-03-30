function( params ) { 
	var self = this;

	/* Pass originalText as ko.observable() */
	this.originalText = params.originalText;

	/* Booleans */
	this.isViewMoreVisible = ko.observable( false );
	this.isViewMoreShown = ko.observable( true );
	this.maxHeightPx = ko.observable( "initial" );

	this.toggleViewMore = function() {
		self.isViewMoreShown( ! self.isViewMoreShown() );
	};

}