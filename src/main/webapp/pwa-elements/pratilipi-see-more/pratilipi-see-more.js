function( params ) { 
	var self = this;
	this.originalText = params.originalText;
	this.isSeeMoreRequired = ko.observable( false );
	this.isMoreShown = ko.observable( true );
	this.toggleSeeMore = function() {
		self.isMoreShown( ! self.isMoreShown() );
	};
}