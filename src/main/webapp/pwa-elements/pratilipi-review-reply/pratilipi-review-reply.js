function( params ) {
    var self = this;
    this.parent = params.parent;
    /* populate logged in user's name and profile img etc*/
    this.reply = ko.observable("");
    this.saveInProgress = ko.observable( false );
    this.shouldTransliterate = true;

    this.isSaveDisabled = ko.computed( function() {
        return this.reply().trim().length == 0 ||  this.saveInProgress();
    }, this );    

}