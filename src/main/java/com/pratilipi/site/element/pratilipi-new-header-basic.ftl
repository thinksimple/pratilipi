<div class="secondary-500 pratilipi-shadow" class="pratilipi-new-header" style="display: block; padding: 5px;">
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
				<img style="width: 24px; height: 24px; margin: 10px auto;" src="http://0.ptlp.co/resource-all/icon/svg/pencil.svg" />
			</a>
		</div>
		<div class="col-xs-2 col-sm-2 col-md-2 col-lg-2">
			<a style="display: block;" <#if user.isGuest == true>href="/login?ret=/library"<#else>href="/library"</#if>>
				<img style="	width: 24px; height: 24px; margin: 10px auto;" src="http://0.ptlp.co/resource-all/icon/svg/book-grey.svg" />
			</a>
		</div>
		<div class="col-xs-2 col-sm-2 col-md-2 col-lg-2">
			<a style="display: block;" <#if user.isGuest == true>href="/login?ret=/notifications"<#else>href="/notifications"</#if>>
				<img style="width: 24px; height: 24px; margin: 10px auto;" src="http://0.ptlp.co/resource-all/icon/svg/bell-grey.svg" />
			</a>
		</div>				
		<div class="col-xs-2 col-sm-2 col-md-2 col-lg-2">
			<a style="display: block;" <#if user.isGuest == true>href="/login?ret=${ requestUrl }"<#else>href="/account?ret=${ requestUrl }"</#if>>
				<img style="width: 24px; height: 24px; margin: 10px auto;" 
					<#if user.isGuest == true>src="http://0.ptlp.co/resource-all/icon/svg/user.svg"<#else>src="${ user.getProfileImageUrl() }"</#if> />
			</a>
		</div>
	</div>
	<form id="search_form">
	  <div class="row">
	  	  <div class="col-xs-3">	
	  		<div class="form-group" style="margin-bottom: 5px;">
			  <select class="form-control" id="language" style="padding:0px;border:none;color:red;">
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
		  <div class="col-xs-9">
		    <div class="input-group">
		      <input type="text" class="form-control" placeholder="Search for">
		      <span class="input-group-btn">
		        <button class="btn btn-default" type="button"><img src="http://0.ptlp.co/resource-all/icon/svg/search.svg"></button>
		      </span>
		    </div><!-- /input-group -->
		  </div>
	   </div>	
	</form> 
</div>