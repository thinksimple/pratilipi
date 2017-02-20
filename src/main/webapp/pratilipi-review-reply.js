function( params ) {
    var self = this;
    this.parent = params.parent;
    /* populate logged in user's name and profile img etc*/
    this.reply = ko.observable("");
    this.saveInProgress = ko.observable( false );
    
    this.isSaveDisabled = ko.computed( function() {
        return this.reply().trim().length == 0 ||  this.saveInProgress();
    }, this );

    this.generateLikeAjaxRequest = function() {
        $.ajax({
            type: 'post',
            url: '<#if stage == "alpha">${ prefix }</#if>/api/vote',
            data: {
                parentType: "COMMENT",
                parentId: self.reviewCommentObject.commentId,
                type: self.isLiked ? "LIKE" : "NONE"
            },
            success: function( response ) {
        //      var res = jQuery.parseJSON( response );
                var res = response;          
                console.log(res);
            },
            error: function( response ) {
                /* revert changes */
                self.updateLikeCount();
            }
        });      
    };
    
    
    
    var textarea = document.querySelector('textarea.js-autoresize-textarea');

    textarea.addEventListener('keydown', autosize);
                 
    function autosize(){
      var el = this;
      setTimeout(function(){
        el.style.cssText = 'height:auto; padding:0';
        // for box-sizing other than "content-box" use:
        // el.style.cssText = '-moz-box-sizing:content-box';
        el.style.cssText = 'height:' + el.scrollHeight + 'px';
      },0);
    }       
}