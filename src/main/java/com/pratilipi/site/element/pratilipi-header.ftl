<style type="text/css">
	.box{
		text-overflow: hidden;
		overflow: hidden;
	}
	.text-center {
		text-align: center;
	}
	.pull-right {
		text-align: right;
	}
	.search-button {
		background:none!important;
		border:none; 
		padding:0!important;
		font: inherit;
		cursor: pointer;
		color: white;
	}
	.pratilipi-white-button {
		background: #FFFFFF;
		border: 1px solid #979797;
		font-size: 13px;
		color: #000000;
		margin: 0px;
		max-width: 100px;
		padding: 20px auto;
		height: 40px;
		text-align: center;
		line-height: 0px;
		outline: none;
	}
</style>

<div class="box" style="padding: 10px; margin-top: 0px;" >
	<div class="row">
		<div class="col-xs-6 col-sm-3 col-md-3 col-lg-3">
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
		
		<div class="col-xs-6 col-sm-2 col-md-2 col-lg-2 pull-right" style="margin-top: 10px;">
			<button type="button" style="cursor: pointer;" class="pratilipi-white-button" onclick="openLoginForm()">${ _strings.user_sign_in }</button>
		</div>
	</div>
	
	
	<div class="row">
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
