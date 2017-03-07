function( params ) { 
	var self = this;

	var dataAccessor = new DataAccessor();
	this.pratilipi = params.pratilipi;

	this.test = function( vm, evt ) {
		evt.stopPropagation();
	}
}
