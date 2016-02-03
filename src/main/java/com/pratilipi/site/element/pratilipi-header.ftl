
<script type="text/javascript">
	
	function logout() {
		// Make Ajax call
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
<div class="box" style="padding: 10px; margin-top: 0px;" >
	<div class="row">
	
		<#-- For Larger screens -->
		
		<div class="hidden-xs col-sm-3 col-md-3 col-lg-3">
			<a href="/">
				<img style="max-width: 60px;" title="${ _strings.pratilipi }" alt="${ _strings.pratilipi }" src="http://0.ptlp.co/resource-${ lang }/logo/pratilipi_logo.png" />
			</a>
			<a href="/">
				<img style= "max-width: 90px;" title="${ _strings.pratilipi }" alt="${ _strings.pratilipi }" src="http://0.ptlp.co/resource-${ lang }/logo/pratilipi_text.png" />
			</a>
		</div>
		<div class="hidden-xs col-sm-7 col-md-7 col-lg-7" style="margin-top: 10px; padding: 5px;">
			<form method="get" action="/search">
				<div class="form-group">
					<div class="input-group">
						<input type="text" class="form-control" name="q" style="display: table-cell; width:100%;" maxlength="120" />
						<div style="background: #D0021B;" class="input-group-addon"><button class="search-button" type="submit">${ _strings.search }</button></div>
					</div>
				</div>
			</form>    
		</div>
		
		<#if user.isGuest >
			<div class="hidden-xs col-sm-2 col-md-2 col-lg-2">
				<a style="margin-top: 15px;" class="btn btn-default red pull-right" href="/login?ret=${ requestUrl }">${ _strings.user_sign_in }</a>
			</div>
		<#else>
			<div class="hidden-xs col-sm-2 col-md-2 col-lg-2">
				<button style="margin-top: 15px;" class="btn btn-default red pull-right" onclick="logout()">${ _strings.user_sign_out }</button>
			</div>
		</#if>
		
		
		
		<#-- For Mobile screens -->
		
		<div class="col-xs-12 hidden-sm hidden-md hidden-lg">
			<a href="/">
				<img style="max-width: 60px;" title="${ _strings.pratilipi }" alt="${ _strings.pratilipi }" src="http://0.ptlp.co/resource-${ lang }/logo/pratilipi_logo.png" />
			</a>
			<a href="/">
				<img style= "max-width: 90px;" title="${ _strings.pratilipi }" alt="${ _strings.pratilipi }" src="http://0.ptlp.co/resource-${ lang }/logo/pratilipi_text.png" />
			</a>
			
			<#if user.isGuest >
				<a style="margin-top: 15px;" class="btn btn-default red pull-right" href="/login?ret=${ requestUrl }">${ _strings.user_sign_in }</a>
			<#else>
				<button style="margin-top: 15px;" class="btn btn-default red pull-right" onclick="logout()">${ _strings.user_sign_out }</button>
			</#if>
			
		</div>
		<div class="col-xs-12 hidden-sm hidden-md hidden-lg" style="margin-top: 5px; padding: 10px;">
			<form method="get" action="/search">
				<div class="form-group">
					<div class="input-group">
						<input type="text" class="form-control" name="q" style="width:100%;" maxlength="120" />
						<div style="background: #D0021B;" class="input-group-addon"><button class="search-button" type="submit">${ _strings.search }</button></div>
					</div>
				</div>
			</form>
		</div>

	</div>
</div>
