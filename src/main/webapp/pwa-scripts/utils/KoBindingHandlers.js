ko.bindingHandlers.seeMore = {
	update: function( element, valueAccessor, allBindings, viewModel, bindingContext ) {
		var originalText = ( viewModel.originalText() );
		var $target_p = $(element).find( "p" );
		$target_p.text( originalText );
		if( $target_p.height() / ( parseFloat( $target_p.css( "line-height" ) ) ) > 3 ) {
			viewModel.isSeeMoreRequired( true );
			viewModel.isMoreShown( false );
		} else {
			viewModel.isSeeMoreRequired( false );
			viewModel.isMoreShown( false );
		}
		ko.utils.unwrapObservable( valueAccessor() );
		var $target_p = $(element).find( "p" );
		if( viewModel.isMoreShown() ) {
			$target_p.removeClass( "see-more-text" );
		} else {
			$target_p.addClass( "see-more-text" );
		}
	}
};

ko.bindingHandlers.slideIn = {
	init: function( element, valueAccessor ) {
		var value = ko.utils.unwrapObservable( valueAccessor() );
		$(element).toggle( value );
	},
	update: function( element, valueAccessor ) {
		var value = ko.utils.unwrapObservable( valueAccessor() );
		value ? $(element).slideDown() : $(element).slideUp();
	}
};

ko.bindingHandlers.transliterate = {
	init: function( element, valueAccessor ) {
		var $content_object = $(element);
		var content_transliteration_object = new transliterationApp( $content_object, "${ lang }" );
		content_transliteration_object.init();
	},
};

/* http://stackoverflow.com/questions/32957407/material-design-lite-how-to-programatically-reset-a-floating-label-input-text#32958279 */
ko.bindingHandlers.mdlFloatingInput = {
	init: function( element, valueAccessor, allBindingsAccessor, data, context ) {
		var $el = $(element),
			$enclosingDiv = $( '<div>' ).insertAfter( $el ),
			$label = $( '<label>' ),
			params = valueAccessor();
		$el.attr( 'id', params.id );
		$label.attr( 'for', params.id ).text( params.label );
		$el.addClass( 'mdl-textfield__input' );
		$label.addClass( 'mdl-textfield__label' );
		$enclosingDiv.addClass( "mdl-textfield mdl-js-textfield mdl-textfield--floating-label" ).append( $el ).append( $label );
		ko.bindingHandlers.value.init( element, function () { return params.value; }, allBindingsAccessor, data, context );
	},
	update: function( element, valueAccessor, allBindingsAccessor, data, context ) {
		var params = valueAccessor(),
			value = params.value();
		ko.bindingHandlers.value.update( element, function () { return params.value; }, allBindingsAccessor, data, context );
		$(element).parent()[ value ? 'addClass' : 'removeClass' ]( 'is-dirty' );
	}
};