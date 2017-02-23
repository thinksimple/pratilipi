function() {
    componentHandler.upgradeDom();
    this.navigationList = ko.observableArray([]);
    this.pushToNavigationList = function( navList ) {
      for( var i=0; i< navList.length; i++ ) {
        this.navigationList.push( navList[i] );
      }
    }
    var self = this;
    this.getNavigationList = function() {
      $.ajax({
        type: 'get',
        url: '/api/navigation/list?language=HINDI',
        success: function( response ) {
          var res = response;
          self.pushToNavigationList( res["navigationList"] ); 
        },
        error: function( response ) {
            console.log( response );
            console.log( typeof(response) );
        }
      });
    };
    this.getNavigationList();
}