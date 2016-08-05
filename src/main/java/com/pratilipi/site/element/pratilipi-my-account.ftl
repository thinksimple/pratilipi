<script>
	function goBack() {
		if( getUrlParameter( "ret" ) != null )
			window.location.href =  getUrlParameter( "ret" );
		else
			window.location.href = "/";
	}	
	function logout() {
		$.ajax({
			type: 'get',
			url: '/api/user/logout',
			success: function( response ) {
				if( getUrlParameter( "ret" ) != null )
					window.location.href =  getUrlParameter( "ret" );
				else
					window.location.href = "/";
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
	<div class="pull-right">
	  	<a style="cursor: pointer;" onclick="goBack()">
			<img style="width: 20px;height: 20px;" src="http://0.ptlp.co/resource-all/icon/svg/cross.svg">
	  	</a>
	</div>                  
	<div class="clearfix"></div>
	<hr class="pratilipi-margin-top-2">	
	<a class="pratilipi-red-button" href="/updatepassword">${ _strings.user_reset_password}</a>
	<div style="margin-top: 5px;"><a class="pratilipi-red-button" onclick="logout()">Logout</a></div>			
</div>