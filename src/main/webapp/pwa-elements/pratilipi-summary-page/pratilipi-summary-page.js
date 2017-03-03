function() {
    var self = this;

    this.pratilipiObject = ko.observable( {} );
    this.userPratilipiObject = ko.observable( {} );
    this.pratilipiUri = window.location.pathname;
//    this.pratilipiUri = "/radhika-garg-garg/some-appropriate-title";
   // this.pratilipiUri = "/jitesh-donga/vishwamanav";

    this.pushToViewModel = function( pratilipi, userPratilipi ) {
        ko.mapping.fromJS( pratilipi, {}, this.pratilipiObject );
        ko.mapping.fromJS( userPratilipi, {}, this.userPratilipiObject );
        this.pratilipiObject.valueHasMutated();
        this.userPratilipiObject.valueHasMutated();
        // console.log( this.pratilipiObject(), this.userPratilipiObject() );
    };

    this.ajaxReqCallback = function( pratilipi, userPratilipi ) {
        this.pushToViewModel( pratilipi, userPratilipi )
    };

    this.fetchPratilipiAndUserPratilipi = function() {
        var dataAccessor = new DataAccessor();
        dataAccessor.getPratilipiByUri( this.pratilipiUri, true, this.ajaxReqCallback.bind(this) );
    };

    this.updatePratilipi = function( pratilipi ) {
        ko.mapping.fromJS( pratilipi, {}, this.pratilipiObject );
        this.pratilipiObject.valueHasMutated();
    }

    this.fetchPratilipiAndUserPratilipi();
}