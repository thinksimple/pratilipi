function( params, componentInfo ) { 
    var self = this;
    this.originalText = params.originalText();
    console.log( componentInfo );
    this.isSeeMoreRequired = ko.observable( false );
    this.isMoreShown = ko.observable( true );

    this.buttonText = ko.computed(function() {
        return this.isMoreShown() ? "${ _strings.show_less }" : "${ _strings.show_more }";
    }, this);
    
    this.toggleSeeMore = function() {
        self.isMoreShown( !self.isMoreShown() );
        if( self.isMoreShown() ) {
            self.$seeMoreElement.removeClass( "see-more-text" );
        } else {
            self.$seeMoreElement.addClass( "see-more-text" );
        }      
    };

    this.checkAndAddSeeMore = function() {
        this.$seeMoreElement = $( ".js-see-more" );
        if( this.originalText.split(" ").length > 10 ) {
            this.isSeeMoreRequired( true );
            this.toggleSeeMore();
        }
    }
    
    this.init = function() {
        this.checkAndAddSeeMore();
    }

    this.init();
}