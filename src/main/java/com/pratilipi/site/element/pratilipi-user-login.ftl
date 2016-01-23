<script>
	function validateEmail( email ) {
		var re = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
		return re.test(email);
	}
	function login() {

		var email = $( '#inputLoginEmail' ).val();
		var password = $( '#inputLoginPassword' ).val();
		
		if( email == null || email.trim() == "" ) {
			// Throw message - Please Enter Email
			return;
		}
		
		if( password == null || password.trim() == "" ) {
			// Throw message - Please Enter Password
			return;
		}
		
		if( ! validateEmail( email ) ) {
			// Throw message - Email is not valid
			return;
		}
		
		// Make Ajax call
		console.log( email );
		console.log( password );
		
	}
</script>

<div class="modal" id="pratilipiUserLogin" role="dialog">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				<h5 class="modal-title">${ _strings.user_sign_in_to_pratilipi }</h5>
			</div>
			<div class="modal-body">
				<form id="userLoginForm" class="form-horizontal" action="javascript:void(0);">
	                <div class="form-group">
	                    <label for="inputLoginEmail" class="col-sm-2 control-label">Email</label>
	                    <div class="col-sm-10">
	                        <input name="email" type="email" class="form-control" id="inputLoginEmail" placeholder="${ _strings.user_email }">
	                    </div>
	                </div>
	                <div class="form-group">
	                    <label for="inputLoginPassword" class="col-sm-2 control-label">Password</label>
	                    <div class="col-sm-10">
	                        <input name="password" type="password" class="form-control" id="inputLoginPassword" placeholder="${ _strings.user_password }">
	                    </div>
	                </div>
	            </form>
			</div>
			<div class="modal-footer">
				<button class="btn btn-default" onclick="login()">${ _strings.user_sign_in }</button>
			</div>
		</div>
	</div>
</div>