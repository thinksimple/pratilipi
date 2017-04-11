function() {
	var self = this;
	var dataAccessor = new DataAccessor();

	var defaultBlogPost = {
		blogId: null,
		blogPostId: null,
		content: null,
		creationDateMillis: null,
		pageUrl: null,
		title: null,
		titleEn: null
	};

	this.blogPost = ko.mapping.fromJS( defaultBlogPost, {}, self.blogPost );

	this.updateBlogPost = function( blogPost ) {
		if( blogPost == null )
			blogPost = defaultEvent;
		ko.mapping.fromJS( blogPost, {}, self.blogPost );
	};

	this.initialDataLoaded = ko.observable( false );

	this.fetchBlogPost = function() {
		dataAccessor.getBlogPostByUri( window.location.pathname,
				function( blogPost ) {
			self.updateBlogPost( blogPost );
			if( ! self.initialDataLoaded() ) self.initialDataLoaded( true );
		});
	};

	this.fetchBlogPost();

}