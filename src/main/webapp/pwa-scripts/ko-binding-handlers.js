ko.bindingHandlers.seeMore = {
    init: function(element, valueAccessor, allBindings, viewModel, bindingContext) {
        var originalText = ( viewModel.originalText() );

        var $target_p = $(element).find("p");
        $target_p.text( originalText );
        if( $target_p.height() / ( parseFloat( $target_p.css("line-height") ) ) > 3 ) {
            viewModel.isSeeMoreRequired( true );
            viewModel.isMoreShown( false );
        }

    },
    update: function(element, valueAccessor, allBindings, viewModel, bindingContext) {
        ko.utils.unwrapObservable(valueAccessor());
        var $target_p = $(element).find("p");
        if( viewModel.isMoreShown() ) {
            $target_p.removeClass( "see-more-text" );
        } else {
            $target_p.addClass( "see-more-text" );
        }
    }
};

ko.bindingHandlers.slideIn = {
    init: function (element, valueAccessor) {
        var value = ko.utils.unwrapObservable(valueAccessor());
        $(element).toggle(value);
    },
    update: function (element, valueAccessor) {
        var value = ko.utils.unwrapObservable(valueAccessor());
        value ? $(element).slideDown() : $(element).slideUp();
    }
};

ko.bindingHandlers.transliterate = {
    init: function (element, valueAccessor) {
        var $content_object = $( element );
        var content_transliteration_object = new transliterationApp( $content_object, "${ lang }" );
        content_transliteration_object.init();
    },
};