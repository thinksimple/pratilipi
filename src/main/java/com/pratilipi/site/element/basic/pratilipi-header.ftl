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
			<a style="display: block;" onclick="showPopup()" >
				<img style="width: 24px; height: 24px; margin: 10px auto;" src="http://0.ptlp.co/resource-all/icon/svg/pencil-header-grey.svg" />
			</a>
		</div>
		<div class="col-xs-2 col-sm-2 col-md-2 col-lg-2">
			<a style="display: block;" <#if user.isGuest() == true>href="/login?ret=/library"<#else>href="/library"</#if>>
				<img style="	width: 24px; height: 24px; margin: 10px auto;" src="http://0.ptlp.co/resource-all/icon/svg/book-grey.svg" />
			</a>
		</div>
		<div class="col-xs-2 col-sm-2 col-md-2 col-lg-2" style="position: relative;">
			<a style="display: block;" <#if user.isGuest() == true>href="/login?ret=/notifications"<#else>href="/notifications?ret=${requestUrl}"</#if>>
				<img style="width: 24px; height: 24px; margin: 10px auto;" src="http://0.ptlp.co/resource-all/icon/svg/bell-grey.svg" />
				<#--
				<#if ( ( user.isGuest() == false ) && ( newNotificationCount > 0 ) ) >	
					<span class="badge" style="position: absolute;right: 0px;top: 4%;background-color: #d0021b;padding:4px 7px 3px 7px;">${ newNotificationCount }</span>
				</#if>
				-->
			</a>
		</div>				
		<div class="col-xs-2 col-sm-2 col-md-2 col-lg-2">
			<a style="display: block;" <#if user.isGuest() == true>href="/login?ret=${ requestUrl }"<#else>href="${ user.getProfilePageUrl() }"</#if>>
				<img style="width: 24px; height: 24px; margin: 10px auto;" 
					<#if user.isGuest() == true>src="http://0.ptlp.co/resource-all/icon/svg/user-header-grey.svg"<#else>src="${ user.getProfileImageUrl() }"</#if> />
			</a>
		</div>
	</div>
		<div class="row">
	  	  <div class="pull-left" style="padding-left: 15px;padding-right: 15px;">	
	  		<div class="form-group" style="margin-bottom: 5px;">
			  <select class="form-control" id="select-language" style="box-shadow:none;padding:0px;border:none;color:red;height:30px;">
			  	<option selected disabled>${ _strings.pratilipi }</option>
			  </select>
			</div>
		  </div>
		  <div style="padding-left: 2px;padding-right: 15px;">
			<form style="" method="get" action="/search">
				<div class="form-group" style="margin-bottom:0px;">
					<div class="input-group">
						<input type="text" class="form-control" name="q" placeholder="${ _strings.search }" style="display: table-cell; width:100%;" maxlength="120" <#if pratilipiListSearchQuery?? >value="${ pratilipiListSearchQuery }"</#if> >
						<div style="background: #f5f5f5" class="input-group-addon"><button class="search-button" type="submit">
							<img style="width: 16px; height: 16px;" src="http://0.ptlp.co/resource-all/icon/svg/search.svg">
						</button></div>
					</div>
				</div>
			</form>
	   </div>		
	</div>
</div>
<script>
	$( document ).ready(function() {
	  	function generateLanguageOptions() {
			var $select = $("#select-language");
			var language_map = ${ languageMap };
			$.each(language_map, function( key, value ) {
			  	var $option = $("<option>",{
			  		value: key.toLowerCase(),
			  	}).html(value);
			  	$select.append( $option );
			});
		}
		generateLanguageOptions();
	});
	$( "#select-language" ).change(function() {
		var $this = $(this);
		window.location = ( "http://" + $this.val() + ".pratilipi.com" );
		
	});
	function showPopup() {
		window.alert( "${ _strings.write_on_desktop_only }" );
	}
</script>
