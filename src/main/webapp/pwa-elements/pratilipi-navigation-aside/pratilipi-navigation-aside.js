function() {
    var self = this;
    componentHandler.upgradeDom();
    this.navigationList = ko.observableArray([]);
    this.pushToNavigationList = function( navList ) {
      for( var i=0; i< navList.length; i++ ) {
        this.navigationList.push( navList[i] );
      }
    }

    this.getNavigationListCallback = function( response ) {
        if( response ) {
            self.pushToNavigationList( response["navigationList"] );
        }
    };

    this.getNavigationList = function() {
      var dataAccessor = new DataAccessor();
      dataAccessor.getNavigationList( this.getNavigationListCallback );
    };
    this.getNavigationList();
}