ko.bindingHandlers.seeMore = {
	update: function( element, valueAccessor, allBindings, viewModel, bindingContext ) {
		var p = $( element ).find( "p" );
		p.text( viewModel.originalText() );
		setTimeout( function() {
			var pHeight = p.height();
			var pAllowedLineHeight = parseFloat( p.css( "line-height" ) ) * 3;
			var isViewMoreVisible = pHeight > pAllowedLineHeight;
			viewModel.isViewMoreVisible( isViewMoreVisible );
			viewModel.maxHeightPx( isViewMoreVisible ? pAllowedLineHeight + 'px' : 'initial' );
		}, 0 );
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