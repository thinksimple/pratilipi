function( params ) {
	var self = this;

	this.userObserver = ko.computed( function() {
		if( appViewModel.user.isGuest() && appViewModel.user.userId() == 0 ) {
			ToastUtil.toastUp( "${ _strings.user_login_to_view_notifications }" );
		}
	}, this );

}
