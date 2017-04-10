function() {
	var self = this;
	var dataAccessor = new DataAccessor();

	this.bannerList = ko.observableArray();
	this.sectionList = ko.observableArray();

	dataAccessor.getHomePageBanners( function( response ) {
		self.bannerList( response.bannerList );
		$( '#carousel' ).carousel({
			interval: 6000,
			pause: "hover"
		});
	});

	dataAccessor.getHomePageSections( function( response ) {
		var newPratilipiList = function( pList ) {
			var pratilipiList = ko.observableArray();
			for( var i = 0; i < pList.length; i++ )
				pratilipiList.push( ko.mapping.fromJS( pList[i] ) );
			return pratilipiList;
		};
		var sectionList = response.sections;
		for( var i = 0; i < sectionList.length; i++ ) {
			var section = sectionList[i];
			section.pratilipiList = newPratilipiList( section.pratilipiList );
			self.sectionList.push( section );
		}
	});

	this.dataLoaded = ko.computed( function() {
		return self.bannerList().length > 0 && self.sectionList().length > 0;
	}, this );

}
