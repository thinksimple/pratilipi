function() {
    this.reviewList = ko.observableArray( [] );
    this.pratilipiId = getQueryVariable( "id" );
//    this.userIsGuest = ko.observable();
    this.pushToReviewList = function( revList ) {
        for( var i=0; i< revList.length; i++ ) {
            this.reviewList.push( revList[i] );
        }
    }
    var self = this;
    
//    this.getUser = function() {
//      $.ajax({
//        type: 'get',
//        url: '<#if stage == "alpha">${ prefix }</#if>/api/user',
//        success: function( response ) {
//          <#if stage == "alpha" || stage == "gamma">
//              var res = response;
//          <#else>
//              var res = jQuery.parseJSON( response );
//          </#if>        
//          self.userIsGuest( res["isGuest"] );
//        },
//        error: function( response ) {
//            console.log( response );
//            console.log( typeof( response ) );
//        }
//      });      
//    };
    
    this.getReviewList = function() {
        $.ajax({
            type: 'get',
            url: '<#if stage == "alpha">${ prefix }</#if>/api/userpratilipi/review/list?pratilipiId=' + self.pratilipiId + "&resultCount=3",
            success: function( response ) {
    //          var res = jQuery.parseJSON( response );
                var res = response;          
                self.pushToReviewList( res[ "reviewList" ] );
            },
            error: function( response ) {
                console.log( response );
                console.log( typeof( response ) );
            }
        });
    };
//    this.getUser();
    this.getReviewList();
    
    var dialog = document.querySelector( 'dialog' );
//    var showDialogButton = document.querySelector( '.show' );
    if ( !dialog.showModal ) {
        dialogPolyfill.registerDialog( dialog );
    }
    
    this.openReviewModal = function() {
        componentHandler.upgradeDom();
        dialog.showModal();
    };
   
    this.hideReviewModal = function() {
        dialog.close();
    };    
//    showDialogButton.addEventListener('click', function() {
//        componentHandler.upgradeDom();
//        dialog.showModal();
//    });
//    dialog.querySelector('.close').addEventListener('click', function() {
//        dialog.close();
//    });    
}