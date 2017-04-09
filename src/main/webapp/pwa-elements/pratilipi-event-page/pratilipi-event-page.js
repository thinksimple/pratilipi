function() {
	var self = this;

	var defaultEvent = {
			"eventId": null,
			"name": null,
			"nameEn": null,
			"language": null,
			"description": null,
			"pageUrl": null,
			"bannerImageUrl": null,
			"hasAccessToUpdate": false
	};

	this.event = ko.mapping.fromJS( defaultEvent, {}, self.event );

	this.updateEvent = function( event ) {
		if( event == null )
			event = defaultEvent;
		ko.mapping.fromJS( event, {}, self.event );
		MetaTagUtil.setMetaTagsForEvent( ko.mapping.toJS( self.event ) );
	};

	this.initialDataLoaded = ko.observable( false );

	this.fetchEvent = function() {
		var dataAccessor = new DataAccessor();
		dataAccessor.getEventByUri( window.location.pathname,
				function( event ) {
			self.updateEvent( event );
			if( ! self.initialDataLoaded() ) self.initialDataLoaded( true );
		});
	};

	this.fetchEvent();

}
