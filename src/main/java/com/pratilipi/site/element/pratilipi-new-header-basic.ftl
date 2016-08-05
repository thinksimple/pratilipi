<div class="secondary-500 pratilipi-shadow pratilipi-new-header" style="display: block; padding: 0px 5px 0px 5px;">
	<div class="row" style="text-align: center; margin: 0 auto;">
		<div class="col-xs-2 col-sm-2 col-md-2 col-lg-2">
			<a href="/">
				<img class="pratilipi-icon" title="${ _strings.pratilipi }" alt="${ _strings.pratilipi }" src="http://0.ptlp.co/resource-${ lang }/logo/pratilipi_logo.png" />
			</a>
		</div>
		<div class="col-xs-2 col-sm-2 col-md-2 col-lg-2">
			<a style="display: block;" href="/navigation">
				<img style="width: 24px; height: 24px; margin: 10px auto;" src="http://0.ptlp.co/resource-all/icon/svg/menu-grey.svg" />
			</a>
		</div>
		<div class="col-xs-2 col-sm-2 col-md-2 col-lg-2">
			<a style="display: block;" href="/">
				<img style="width: 24px; height: 24px; margin: 10px auto;" src="http://0.ptlp.co/resource-all/icon/svg/pencil-header-grey.svg" />
			</a>
		</div>
		<div class="col-xs-2 col-sm-2 col-md-2 col-lg-2">
			<a style="display: block;" <#if user.isGuest == true>href="/login?ret=/library"<#else>href="/library"</#if>>
				<img style="	width: 24px; height: 24px; margin: 10px auto;" src="http://0.ptlp.co/resource-all/icon/svg/book-grey.svg" />
			</a>
		</div>
		<div class="col-xs-2 col-sm-2 col-md-2 col-lg-2">
			<a style="display: block;" <#if user.isGuest == true>href="/login?ret=/notification"<#else>href="/notification"</#if>>
				<img style="width: 24px; height: 24px; margin: 10px auto;" src="http://0.ptlp.co/resource-all/icon/svg/bell-grey.svg" />
			</a>
		</div>				
		<div class="col-xs-2 col-sm-2 col-md-2 col-lg-2">
			<a style="display: block;" <#if user.isGuest == true>href="/login?ret=${ requestUrl }"<#else>href="${ user.getProfilePageUrl() }"</#if>>
				<img style="width: 24px; height: 24px; margin: 10px auto;" 
					<#if user.isGuest == true>src="http://0.ptlp.co/resource-all/icon/svg/user-header-grey.svg"<#else>src="${ user.getProfileImageUrl() }"</#if> />
			</a>
		</div>
	</div>
		<div class="row">
	  	  <div class="col-xs-3">	
	  		<div class="form-group" style="margin-bottom: 5px;">
			  <select class="form-control" id="language" style="box-shadow:none;padding:0px;border:none;color:red;height:30px;">
			    <option>HINDI</option>
			    <option>TAMIL</option>
			    <option>KANNADA</option>
			    <option>BENGALI</option>
			    <option>TELUGU</option>
			    <option>GUJARATI</option>
			    <option>MARATHI</option>
			    <option>MALAYALAM</option>
			  </select>
			</div>
		  </div>
		  <div class="col-xs-9" style="padding-left: 2px;">
			<form style="" method="get" action="/search">
				<div class="form-group" style="margin-bottom:0px;">
					<div class="input-group">
						<input type="text" class="form-control" name="q" placeholder="खोजिए" style="display: table-cell; width:100%;" maxlength="120">
						<div style="background: #f5f5f5" class="input-group-addon"><button class="search-button" type="submit">
							<img style="width: 16px; height: 16px;" src="http://0.ptlp.co/resource-all/icon/svg/search.svg">
						</button></div>
					</div>
				</div>
			</form>
	   </div>		
	</div>
</div>