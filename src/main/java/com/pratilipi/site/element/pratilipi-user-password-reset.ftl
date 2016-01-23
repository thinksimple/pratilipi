<script>
	function validateEmail( email ) {
		var re = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
		return re.test(email);
	}
	function forgotPassword() {

		var email = $( '#inputForgotPasswordEmail' ).val();
		
		if( email == null || email.trim() == "" ) {
			// Throw message - Please Enter Email
			return;
		}
		
		if( ! validateEmail( email ) ) {
			// Throw message - Email is not valid
			return;
		}
		
		// Make Ajax call
		console.log( email );
		
	}
</script>

<div class="modal" id="pratilipiUserPasswordReset" role="dialog">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				<h5 class="modal-title">${ _strings.user_forgot_password }</h5>
			</div>
			<div class="modal-body">
				<p class="text-muted">
					${ _strings.user_reset_password_help }
				</p>
				<form id="userPasswordResetForm" class="form-horizontal" action="javascript:void(0);">
	                <div class="form-group">
	                    <label for="inputForgotPasswordEmail" class="col-sm-2 control-label">Email</label>
	                    <div class="col-sm-10">
	                        <input name="email" type="email" class="form-control" id="inputForgotPasswordEmail" placeholder="${ _strings.user_email }">
	                    </div>
	                </div>
	            </form>
			</div>
			<div class="modal-footer">
				<button class="btn btn-default" onclick="forgotPassword()">${ _strings.user_reset_password }</button>
			</div>
		</div>
	</div>
</div>