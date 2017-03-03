/* Toaster & SnackBar */
var snackbarContainer = document.createElement( 'div' );
snackbarContainer.id = "pratilipi-toast";
snackbarContainer.style.zIndex = "1051";
snackbarContainer.className = "mdl-js-snackbar mdl-snackbar";
snackbarContainer.innerHTML = '<div class="mdl-snackbar__text"></div><button class="mdl-snackbar__action" type="button"></button>';
document.body.appendChild( snackbarContainer );

var ToastUtil = (function() {
	return {
		toast: function( message, timeout, includeCloseButton ) {
			if( message == null ) return;
			var data = { message: message,
						timeout: timeout == null ? 2000 : timeout };
			if( includeCloseButton ) {
				data[ "actionHandler" ] = this.closeToast;
				data[ "actionText" ] = "x";
			}
			setTimeout( function() {
				snackbarContainer.MaterialSnackbar.showSnackbar( data );
			}, 1 );
		},
		toastUp: function( message ) {
			var snackbarText = document.querySelector( '.mdl-snackbar__text' );
			snackbarText.innerHTML = message;
			setTimeout( function() {
				snackbarContainer.classList.add( "mdl-snackbar--active" );
			}, 1 );
		},
		toastDown: function( count ) {
			/* snackbarContainer.MaterialSnackbar.cleanup_(); */
			/* As cleanup_ is not part of the public api and buggy, we are not using it. */
			/* Removing "mdl-snackbar--active" woduln't allow additional toasts to display until that long timeout is completed */
			setTimeout( function() {
				snackbarContainer.classList.remove( "mdl-snackbar--active" );
			}, 1 );
		}
	};
})();