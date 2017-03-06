function( params ) {
    var self = this;
    console.log( params );
    this.comment = params.comment;
    var dataAccessor = new DataAccessor();
    // this.parent = params.parent;
    // /* populate logged in user's name and profile img etc*/
    this.reply = ko.observable( this.comment.content() );
    this.saveInProgress = ko.observable( false );
    
    this.isSaveDisabled = ko.computed( function() {
        return this.reply().trim().length == 0 ||  this.saveInProgress();
    }, this );  

    this.editSuccessCallback = function( response ) {
        params.editComment( response );
    };

    this.editSelf = function() {
        dataAccessor.createOrUpdateReviewComment(  null, this.comment.commentId() , this.reply(), this.editSuccessCallback.bind(this), null );
    };
    
    // var textarea = document.querySelector('textarea.js-autoresize-textarea');

    // textarea.addEventListener('keydown', autosize);
                 
    // function autosize(){
    //   var el = this;
    //   setTimeout(function(){
    //     el.style.cssText = 'height:auto; padding:0';
    //     // for box-sizing other than "content-box" use:
    //     // el.style.cssText = '-moz-box-sizing:content-box';
    //     el.style.cssText = 'height:' + el.scrollHeight + 'px';
    //   },0);
    // }       
}