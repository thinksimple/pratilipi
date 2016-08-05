<script>	
	function logout() {
		$.ajax({
			type: 'get',
			url: '/api/user/logout',
			success: function( response ) {
				location.reload();
			},
			error: function () {
				alert( "Logout Failed!" );
			}
		});
	}
</script>

<div class="pratilipi-shadow secondary-500 box">	
	<div class="pull-left">
		<h5 class="pratilipi-no-margin pratilipi-red">
			${ _strings.user_my_account	 }
		</h5>
	</div>                   
	<div class="clearfix"></div>
	<hr class="pratilipi-margin-top-2">	
	<a class="pratilipi-red-button" href="/updatepassword">${ _strings.user_reset_password}</a>
	<a class="pratilipi-red-button" onclick="logout()">Logout</a>			
</div>