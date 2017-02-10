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
        url: '<#if stage == "alpha">${ prefix }</#if>/api/navigation/list?language=HINDI',
        success: function( response ) {
          <#if stage == "alpha">
              var res = response;
          <#else>
              var res = jQuery.parseJSON( response );
          </#if> 
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