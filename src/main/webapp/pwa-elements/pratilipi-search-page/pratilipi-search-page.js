function() {
	var self = this;
	var cursor = null;
	var resultCount = 20;
	var dataAccessor = new DataAccessor();

	this.searchTitle = ko.observable();
	this.pratilipiList = ko.observableArray();
	this.hasMoreContents = ko.observable( true );
	
	/* Loading state */
	/*
	 * 4 Possible values for 'loadingState'
	 * LOADING
	 * LOADED_EMPTY
	 * LOADED
	 * FAILED
	 * 
	 * */
	this.loadingState = ko.observable();

	this.updatePratilipiList = function( pratilipiList ) {
		for( var i = 0; i < pratilipiList.length; i++ )
			self.pratilipiList.push( ko.mapping.fromJS( pratilipiList[i] ) );
	};

	this.fetchPratilipiList = function() {
		if( self.loadingState() == "LOADING" || ! self.hasMoreContents() ) return;
		self.loadingState( "LOADING" );
		dataAccessor.getPratilipiListBySearchQuery( appViewModel.searchQuery(), cursor, null, resultCount,
				function( pratilipiListResponse ) {
					if( pratilipiListResponse == null ) {
						self.loadingState( "FAILED" );
						return;
					}
					var loadMore = self.pratilipiList().length != 0;
					var pratilipiList = pratilipiListResponse.pratilipiList;
					self.updatePratilipiList( pratilipiList );
					cursor = pratilipiListResponse.cursor;
					self.loadingState( self.pratilipiList().length > 0 || pratilipiList.length > 0 ? "LOADED" : "LOADED_EMPTY" );
					self.hasMoreContents( pratilipiList.length == resultCount && cursor != null );
					if( loadMore ) ga_CA( 'Pratilipi', 'LoadMore' );
		});
	};

	this.searchQueryUpdated = function() {
		self.searchTitle( getSearchTitle() );
		self.pratilipiList( [] );
		cursor = null;
		self.loadingState( null );
		self.hasMoreContents( true );
		self.fetchPratilipiList();
	};

	this.searchQueryObserver = ko.computed( function() {
		appViewModel.searchQuery();
		setTimeout( function() {
			self.searchQueryUpdated( appViewModel.searchQuery() );
		}, 0 );
	}, this );

	this.pageScrollObserver = ko.computed( function() {
		if( ( appViewModel.scrollTop() / $( ".js-pratilipi-list-grid" ).height() ) > 0.6 ) {
			setTimeout( function() {
				self.fetchPratilipiList();
			}, 0 );
		}
	}, this );


	/* Setting searchTitle for search pages coming from navigation */
	var getSearchTitle = function() {
		var listsearchTitleMap = {
			<#list navigationList as navigation>
				<#list navigation.linkList as link>
				"${ link.url }": "${ link.name }",
				</#list>
			</#list>
		};
		return listsearchTitleMap[ "/search?q=" + appViewModel.searchQuery() ] != null ?
			listsearchTitleMap[ "/search?q=" + appViewModel.searchQuery() ] : "${ _strings.search_results }";
	};

}
