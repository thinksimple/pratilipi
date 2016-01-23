<script>
	function validateEmail( email ) {
		var re = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
		return re.test(email);
	}
	function register() {
		
		var name = $( '#inputRegisterName' ).val();
		var email = $( '#inputRegisterEmail' ).val();
		var password = $( '#inputRegisterPassword' ).val();
		
		if( name == null || name.trim() == "" ) {
			// Throw message - Please Enter Name
			return;
		}
		
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
		console.log( name );
		console.log( email );
		console.log( password );
		
	}
</script>

<div class="modal" id="pratilipiUserRegister" role="dialog">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				<h5 class="modal-title">${ _strings.user_sign_up_for_pratilipi }</h5>
			</div>
			<div class="modal-body">
				<form id="userRegisterForm" class="form-horizontal" action="javascript:void(0);">
					<div class="form-group">
	                    <label for="inputRegisterName" class="col-sm-2 control-label">Name</label>
	                    <div class="col-sm-10">
	                        <input name="name" type="text" class="form-control" id="inputRegisterName" placeholder="${ _strings.user_full_name }">
	                    </div>
	                </div>
	                <div class="form-group">
	                    <label for="inputRegisterEmail" class="col-sm-2 control-label">Email</label>
	                    <div class="col-sm-10">
	                        <input name="email" type="email" class="form-control" id="inputRegisterEmail" placeholder="${ _strings.user_email }">
	                    </div>
	                </div>
	                <div class="form-group">
	                    <label for="inputRegisterPassword" class="col-sm-2 control-label">Password</label>
	                    <div class="col-sm-10">
	                        <input name="password" type="password" class="form-control" id="inputRegisterPassword" placeholder="${ _strings.user_password }">
	                    </div>
	                </div>
	            </form>
			</div>
			<div class="modal-footer">
				<button class="btn btn-default" onclick="register()">${ _strings.user_sign_up }</button>
			</div>
		</div>
	</div>
</div>