<script>
	$( document ).ready(function() {
		<#if ( pratilipi.getType()?? ) >
		    $("#type").val("${ pratilipi.getType() }");
	    </#if>	
		<#if ( pratilipi.getLanguage()?? ) >
		    $("#language").val("${ pratilipi.getLanguage() }");
	    </#if>	        
	});
    function isEmpty(str) {
    	return ( str.length === 0 || !str.trim() );
    }
    
	function appendErrorMsg(origMsg, newMsg) {
		if ( isEmpty( origMsg ) ) {
			origMsg += newMsg;
		}
		else {
			origMsg += ( "\n" + newMsg );
		}
		return origMsg;
	}
	
	function validateSettingsForm() {
    	var form = $("#pratilipi_settings_form");
    	var error_message = "";
    	if( isEmpty( form.find( "#title" ).val() ) && isEmpty( form.find( "#titleEn" ).val() ) ) {
    		var title_error = "Please provide a title in english or other language.";
    		error_message = appendErrorMsg(error_message, title_error);
    	}
    	
    	if( isEmpty( form.find( "#type" ).val() ) ) {
    		var content_type_error = "Please select the type of content.";
    		error_message = appendErrorMsg(error_message, content_type_error);
    	}
    	
    	if( isEmpty( error_message ) ) {
    		AjaxSubmitForm();
    	}
    	else {
    		window.alert(error_message);
    	}
    }
	function AjaxSubmitForm() {
		var form = $("#pratilipi_settings_form");
		var formData = new FormData(form[0]);
        $.ajax({
            type:'POST',
            url: '/api/pratilipi',
            data:formData,
            cache:false,
            contentType: false,
            processData: false,
            success:function(data){
            	console.log(response);
            	console.log(typeof response);
            	
            	var parsed_data = jQuery.parseJSON( response );
            	console.log(parsed_data);
      			window.location = ( window.location.href.split("/")[0] + parsed_data.pageUrl );
            },
            error: function(data){
            	var message = jQuery.parseJSON( response.responseText );
				alert(message);
            }
        });
	}
	function getUrlParameters() {
		var str = decodeURI( location.search.substring(1) ), 
			res = str.split("&"), 
			retObj = {};
		for( var i = 0; i < res.length; i++ ){
			var key = res[i].substring( 0, res[i].indexOf( '=' ) );
			var value = res[i].substring( res[i].indexOf( '=' ) + 1 );
			retObj[ key ] = value;
		}
		if( retObj[""] != null ) delete retObj[""];
		return retObj;
	}
	    
	function redirectToPreviousPage() {
		var retUrl = getUrlParameters().ret;
		if(  retUrl != null )
			window.location.href = retUrl;
		else
			window.location.href = "/"; 		
	}	
</script>
<div class="pratilipi-shadow secondary-500 box">
		<button class="pull-left pratilipi-grey-button pratilipi-without-margin" onclick="redirectToPreviousPage()" ><img style="width:16px;height:16px;" src="http://0.ptlp.co/resource-all/icon/svg/cross.svg"></button>
		<button class="pull-right pratilipi-red-button pratilipi-without-margin" onclick="validateSettingsForm()"><img style="width:16px;height:16px;" src="http://0.ptlp.co/resource-all/icon/svg/checkmark-red.svg"></button>
		<h3 class="text-center pratilipi-red" style="margin-top: 10px;"> ${ _strings.pratilipi_edit_info } </h3>
</div>
<div class="pratilipi-shadow secondary-500 box">
	<form id="pratilipi_settings_form">
		<div class="form-group">
			<label for="title">${ _strings.edit_pratilipi_title }</label>
			<input type="text" class="form-control" id="title" name="title" 
			<#if pratilipi.getTitle()?? >
				value="${ pratilipi.getTitle() }"
			</#if>	
			>
		</div>
		
		
		<div class="form-group">
			<label for="titleEn">${ _strings.edit_pratilipi_title_en }</label>
			<input type="text" class="form-control" id="titleEn" name="titleEn"
			<#if pratilipi.getTitleEn()?? >
				value="${ pratilipi.getTitleEn() }"
			</#if>	
			>
		</div>		
			
		<div class="form-group">
		  <label for="type">${ _strings.edit_pratilipi_type }:</label>
		  <select class="form-control" id="type" name="type">
		    <option value="BOOK">${ _strings._pratilipi_type_book }</option>
		    <option value="POEM">${ _strings._pratilipi_type_poem }</option>
		    <option value="STORY">${ _strings._pratilipi_type_story }</option>
		    <option value="ARTICLE">${ _strings._pratilipi_type_article }</option>
		    <option value="MAGAZINE">${ _strings._pratilipi_type_magazine }</option>
		  </select>
		</div>
		
		<fieldset disabled>
			<div class="form-group">
				<label for="language">${ _strings.edit_pratilipi_language }</label>	
			    <select id="language" name="language" class="form-control">
		        	<option value="HINDI">${ _strings.language_hi }</option>
		        	<option value="GUJARATI">${ _strings.language_gu }</option>
		        	<option value="TAMIL">${ _strings.language_ta }</option>
		        	<option value="MARATHI">${ _strings.language_mr }</option>
		        	<option value="MALAYALAM">${ _strings.language_ml }</option>
		        	<option value="BENGALI">${ _strings.language_bn }</option>
		        	<option value="TELUGU">${ _strings.language_te }</option>
		        	<option value="KANNADA">${ _strings.language_kn }</option>
		        </select>
			</div>
		</fieldset>	

		<div class="form-group">
		  	<label for="summary">${ _strings.edit_pratilipi_summary }:</label>
		  	<textarea rows="10" class="form-control" id="summary" name="summary">
		  		<#if pratilipi.getSummary()?? >
					${ pratilipi.getSummary() }
				</#if>
		  	</textarea>
		</div>
	</form>
</div>