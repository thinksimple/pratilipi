function( params ) {
	var self = this;

	/* Pass rating in parameter as an ko.observable */
	this.rating = params.rating;

	this.setRating = function( rating ) {
		self.rating( rating );
	}

}
