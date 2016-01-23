<script>
	function updatePassword() {

		var password = $( '#inputPasswordUpdatePassword' ).val();
		var newPassword = $( '#inputPasswordUpdateNewPassword' ).val();
		var newPassword2 = $( '#inputPasswordUpdateNewPassword2' ).val();
		
		if( password == null || password.trim() == "" ) {
			// Throw message - Please Enter Current Password
			return;
		}
		
		if( newPassword == null || newPassword.trim() == "" ) {
			// Throw message - Please Enter new Password
			return;
		}
		
		if( newPassword2 == null || newPassword2.trim() == "" ) {
			// Throw message - Please Enter new password again
			return;
		}
		
		// Make Ajax call with password and newPassword only
		console.log( password );
		console.log( newPassword );
		
	}
</script>

<div class="modal" id="pratilipiUserPasswordUpdate" role="dialog">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				<h5 class="modal-title">${ _strings.user_reset_password }</h5>
			</div>
			<div class="modal-body">
				<form id="userPasswordUpdateForm" class="form-horizontal" action="javascript:void(0);">
	                <div class="form-group">
	                    <label for="inputPasswordUpdatePassword" class="col-sm-2 control-label">Current Password</label>
	                    <div class="col-sm-10">
	                        <input name="password" type="password" class="form-control" id="inputPasswordUpdatePassword" placeholder="${ _strings.user_current_password }">
	                    </div>
	                </div>
	                <div class="form-group">
	                    <label for="inputPasswordUpdateNewPassword" class="col-sm-2 control-label">New Password</label>
	                    <div class="col-sm-10">
	                        <input name="newPassword" type="password" class="form-control" id="inputPasswordUpdateNewPassword" placeholder="${ _strings.user_new_password }">
	                    </div>
	                </div>
	                <div class="form-group">
	                    <label for="inputPasswordUpdateNewPassword2" class="col-sm-2 control-label">Confirm Password</label>
	                    <div class="col-sm-10">
	                        <input name="newPassword2" type="password" class="form-control" id="inputPasswordUpdateNewPassword2" placeholder="${ _strings.user_confirm_password }">
	                    </div>
	                </div>
	            </form>
			</div>
			<div class="modal-footer">
				<button class="btn btn-default" onclick="updatePassword()">${ _strings.update }</button>
			</div>
		</div>
	</div>
</div>