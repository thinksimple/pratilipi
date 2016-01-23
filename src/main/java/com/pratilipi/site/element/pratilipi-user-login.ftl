<div class="modal" id="pratilipiUserLogin" role="dialog">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				<h5 class="modal-title">${ _strings.user_sign_in_to_pratilipi }</h5>
			</div>
			<div class="modal-body" on-keyup="onSubmit">
				<form id="userLoginForm" class="form-horizontal" action="javascript:void(0);">
	                <div class="form-group">
	                    <label for="inputEmail" class="col-sm-2 control-label">Email</label>
	                    <div class="col-sm-10">
	                        <input name="email" type="email" class="form-control" id="inputEmail" placeholder="Email">
	                    </div>
	                </div>
	                <div class="form-group">
	                    <label for="inputPassword" class="col-sm-2 control-label">Password</label>
	                    <div class="col-sm-10">
	                        <input name="password" type="password" class="form-control" id="inputPassword" placeholder="Password">
	                    </div>
	                </div>
	                <div class="form-group">
	                    <div class="col-sm-offset-2 col-sm-10">
	                        
	                    </div>
	                </div>
	            </form>
			</div>
			<div class="modal-footer">
				<button class="btn btn-default" onclick="login()">Sign in</button>
			</div>
		</div>
	</div>
</div>