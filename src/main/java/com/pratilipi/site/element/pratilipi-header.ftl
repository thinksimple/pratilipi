<div class="box" style="padding: 10px; margin-top: 0px;" >
	<div class="row">
	
		<#-- For Larger screens -->
		
		<div class="hidden-xs col-sm-3 col-md-3 col-lg-3">
			<a href="/">
				<img style="max-width: 60px;" title="${ _strings.pratilipi }" alt="${ _strings.pratilipi }" src="https://storage.googleapis.com/devo-pratilipi.appspot.com/Logo-for-Site-Header.png" />
			</a>
			<a href="/">
				<img style= "max-width: 90px;" title="${ _strings.pratilipi }" alt="${ _strings.pratilipi }" src="https://storage.googleapis.com/devo-pratilipi.appspot.com/Pratilipi-Text.png" />
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
		<div class="hidden-xs col-sm-2 col-md-2 col-lg-2">
			<button type="button" style="cursor: pointer; margin-top: 10px;" class="pratilipi-white-button pull-right" data-toggle="modal" data-target="#pratilipiUserLogin">${ _strings.user_sign_in }</button>
		</div>
		
		
		<#-- For Mobile screens -->
		
		<div class="col-xs-12 hidden-sm hidden-md hidden-lg">
			<a href="/">
				<img style="max-width: 60px;" title="${ _strings.pratilipi }" alt="${ _strings.pratilipi }" src="https://storage.googleapis.com/devo-pratilipi.appspot.com/Logo-for-Site-Header.png" />
			</a>
			<a href="/">
				<img style= "max-width: 90px;" title="${ _strings.pratilipi }" alt="${ _strings.pratilipi }" src="https://storage.googleapis.com/devo-pratilipi.appspot.com/Pratilipi-Text.png" />
			</a>
			<button type="button" style="cursor: pointer; margin-top: 10px;" class="pratilipi-white-button pull-right" data-toggle="modal" data-target="#pratilipiUserLogin">${ _strings.user_sign_in }</button>
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
