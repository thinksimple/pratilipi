<script>
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
	};
	
	var processDateOfBirth = function( input ) {
		if ( isEmpty( input ) ) {
			return "";
		}
		var datePart = input.match(/\d+/g);
		if( parseInt( datePart[0] ) < 1800 ) return input;
		var year = datePart[0], month = datePart[1], day = datePart[2];
		return day+'-'+month+'-'+year;
	}
		
	
	function AjaxSubmitForm() {
		var form = $("#user_settings_form");
	    $.ajax({type: "POST",
	            url: "/api/author",
	            data: { authorId: "${ author.getId()?c }",
	            		firstName: form.find("#first_name").val() ,
	            		lastName: form.find("#last_name").val(),
	            		penName: form.find("#pen_name").val(),
	            		firstNameEn: form.find("#first_name_en").val(),
	            		lastNameEn: form.find("#last_name_en").val(),
	            		penNameEn: form.find("#pen_name_en").val(),
	            		gender: form.find('input[name=gender]:checked').val(),
	            		dateOfBirth: processDateOfBirth( form.find( "#date_of_birth" ).val() ),
	            		language: form.find("#language").val(),
	            		
	            	   },
	            success:function(response){
	            	console.log(response);
	            	console.log(typeof response);
	            	
	            	var parsed_data = jQuery.parseJSON( response );
	            	console.log(parsed_data);
	      			window.location = ( window.location.href.split("/")[0] + parsed_data.pageUrl );
	    		},
	            fail:function(response){
	            	var message = jQuery.parseJSON( response.responseText );
					alert(message);
	    		}			    		
	    		
	    });		
	}
    
    
    function validateSettingsForm() {
    	var form = $("#user_settings_form");
    	var error_message = "";
    	if( isEmpty( form.find( "#first_name_en" ).val() ) && isEmpty( form.find( "#first_name" ).val() ) ) {
    		var first_name_error = "Please provide a first name in english or other language.";
    		error_message = appendErrorMsg(error_message, first_name_error);
    	}
    	
    	if( isEmpty( form.find( "#language" ).val() ) ) {
    		var language_error = "Please select your language.";
    		error_message = appendErrorMsg(error_message, language_error);
    	}
    	
    	if( isEmpty( error_message ) ) {
    		AjaxSubmitForm();
    	}
    	else {
    		window.alert(error_message);
    	}
    }
	$( document ).ready(function() {
		<#if ( author.getLanguage()?? ) >
		    $("#language").val("${ author.getLanguage() }");
	    </#if>
	    <#if ( author.getDateOfBirth()?? ) >
	    	var convertDobToStandardFormat = function( input ) {
				return input.split("-").reverse().join("-"));
			};
		    $("#date_of_birth").val( convertDobToStandardFormat( "${ author.getDateOfBirth() }" ) );
	    </#if>

	    
	});
</script>
<div class="pratilipi-shadow secondary-500 box">
		<button class="pull-left pratilipi-grey-button pratilipi-without-margin">Cancel</button>
		<button class="pull-right pratilipi-light-blue-button pratilipi-without-margin" onclick="validateSettingsForm()">Save</button>
		<h3 class="text-center pratilipi-red" style="margin-top: 10px;"> Edit Profile </h3>
</div>
<div class="pratilipi-shadow secondary-500 box">
	<form id="user_settings_form">
		<div class="form-group">
			<label for="first_name">First Name(Vernacular)</label>
			<input type="text" class="form-control" id="first_name" 
			<#if author.getFirstName()?? >
				value="${ author.getFirstName() }"
			</#if>	
			>
		</div>
		
		
		<div class="form-group">
			<label for="first_name_en">First Name(English)</label>
			<input type="text" class="form-control" id="first_name_en" 
			<#if author.getFirstNameEn()?? >
				value="${ author.getFirstNameEn() }"
			</#if>	
			>
		</div>		
		
		<div class="form-group">
			<label for="last_name">Last Name(Vernacular)</label>
			<input type="text" class="form-control" id="last_name" 
				<#if author.getLastName()?? >
					value="${ author.getLastName() }"
				</#if>
			>
		</div>

		
		<div class="form-group">
			<label for="last_name_en">Last Name(English)</label>
			<input type="text" class="form-control" id="last_name_en" 
				<#if author.getLastNameEn()?? >
					value="${ author.getLastNameEn() }"
				</#if>
			>
		</div>	
			
		<div class="form-group">
			<label for="pen_name">Pen Name(Vernacular)</label>
			<input type="text" class="form-control" id="pen_name" 
				<#if author.getPenName()?? >
					value="${ author.getPenName() }"
				</#if>			
			>
		</div>	
		<div class="form-group">
			<label for="pen_name_en">Pen Name(English)</label>
			<input type="text" class="form-control" id="pen_name_en" 
				<#if author.getPenNameEn()?? >
					value="${ author.getPenNameEn() }"
				</#if>			
			>
		</div>			
		<div class="form-group">
			<label for="date_of_birth">Date of Birth</label>
			<input type="date" class="form-control" id="date_of_birth">
		</div>
		
		<div class="form-group">
			<label for="gender">Gender</label><br>
			<label class="radio-inline"><input type="radio" name="gender" value="MALE"
				<#if author.getGender()?? && author.getGender()=="MALE" >
					checked
				</#if>	
			>Male</label>
			<label class="radio-inline"><input type="radio" name="gender" value="FEMALE"
				<#if author.getGender()?? && author.getGender()=="FEMALE" >
					checked
				</#if>			
			>Female</label>
			<label class="radio-inline"><input type="radio" name="gender" value="OTHER"
				<#if author.getGender()?? && author.getGender()=="OTHER" >
					checked
				</#if>			
			>Other</label>
		</div>
			
		<div class="form-group">
		  <label for="language">Language:</label>
		  <select class="form-control" id="language">
		    <option value="HINDI">${ _strings.language_hi }</option>
		    <option value="TAMIL">${ _strings.language_ta }</option>
		    <option value="KANNADA">${ _strings.language_kn }</option>
		    <option value="BENGALI">${ _strings.language_bn }</option>
		    <option value="TELUGU">${ _strings.language_te }</option>
		    <option value="GUJARATI">${ _strings.language_gu }</option>
		    <option value="MARATHI">${ _strings.language_mr }</option>
		    <option value="MALAYALAM">${ _strings.language_ml }</option>
		  </select>
		</div>

		<div class="form-group">
		  	<label for="biography">Biography:</label>
		  	<textarea rows="10" class="form-control" id="biography">
		  		<#if author.getSummary()?? >
					${ author.getSummary() }
				</#if>
		  	</textarea>
		</div>
	</form>
</div>