<script>
	function updatePasswordEmail() {

		var newPassword = $( '#inputPasswordUpdateEmailNewPassword' ).val();
		var newPassword2 = $( '#inputPasswordUpdateEmailNewPassword2' ).val();
		
		if( newPassword == null || newPassword.trim() == "" ) {
			// Throw message - Please Enter new Password
			return;
		}
		
		if( newPassword2 == null || newPassword2.trim() == "" ) {
			// Throw message - Please Enter new password again
			return;
		}
		
		// Make Ajax call with newPassword only
		console.log( newPassword );
		
	}
</script>

<div class="modal" id="pratilipiUserPasswordUpdateEmail" role="dialog">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				<h5 class="modal-title">${ _strings.user_reset_password }</h5>
			</div>
			<div class="modal-body">
				<form id="userPasswordUpdateEmailForm" class="form-horizontal" action="javascript:void(0);">
	                <div class="form-group">
	                    <label for="inputPasswordUpdateEmailNewPassword" class="col-sm-2 control-label">New Password</label>
	                    <div class="col-sm-10">
	                        <input name="newPassword" type="password" class="form-control" id="inputPasswordUpdateEmailNewPassword" placeholder="${ _strings.user_new_password }">
	                    </div>
	                </div>
	                <div class="form-group">
	                    <label for="inputPasswordUpdateEmailNewPassword2" class="col-sm-2 control-label">Confirm Password</label>
	                    <div class="col-sm-10">
	                        <input name="newPassword2" type="password" class="form-control" id="inputPasswordUpdateEmailNewPassword2" placeholder="${ _strings.user_confirm_password }">
	                    </div>
	                </div>
	            </form>
			</div>
			<div class="modal-footer">
				<button class="btn btn-default" onclick="updatePasswordEmail()">${ _strings.update }</button>
			</div>
		</div>
	</div>
</div>