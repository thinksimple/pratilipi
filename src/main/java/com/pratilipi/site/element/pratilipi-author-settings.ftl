<#assign current_user = user.getAuthor()>
<div class="pratilipi-shadow secondary-500 box">
		<button class="pull-left pratilipi-grey-button pratilipi-without-margin">Cancel</button>
		<button class="pull-right pratilipi-light-blue-button pratilipi-without-margin">Save</button>
		<h3 class="text-center pratilipi-red" style="margin-top: 10px;">Edit Profile </h3>
</div>
<div class="pratilipi-shadow secondary-500 box">
	<form>
		<div class="form-group">
			<label for="first_name">First Name(Vernacular)</label>
			<input type="text" class="form-control" id="first_name_vernacular" 
			<#if current_user.getFirstName()?? >
				value="${ current_user.getFirstName() }"
			</#if>	
			>
		</div>
		
		<div class="form-group">
			<label for="last_name">Last Name(Vernacular)</label>
			<input type="text" class="form-control" id="last_name_vernacular" 
				<#if current_user.getLastName()?? >
					value="${ current_user.getLastName() }"
				</#if>
			>
		</div>
		
		<div class="form-group">
			<label for="first_name">First Name(English)</label>
			<input type="text" class="form-control" id="first_name_en" 
			<#if current_user.getFirstNameEn()?? >
				value="${ current_user.getFirstNameEn() }"
			</#if>	
			>
		</div>
		
		<div class="form-group">
			<label for="last_name">Last Name(English)</label>
			<input type="text" class="form-control" id="last_name_english" 
				<#if current_user.getLastNameEn()?? >
					value="${ current_user.getLastNameEn() }"
				</#if>
			>
		</div>	
			
		<div class="form-group">
			<label for="pen_name">Pen Name(Vernacular)</label>
			<input type="text" class="form-control" id="pen_name_vernacular" 
				<#if current_user.getPenName()?? >
					value="${ current_user.getPenName() }"
				</#if>			
			>
		</div>	
		<div class="form-group">
			<label for="pen_name">Pen Name(English)</label>
			<input type="text" class="form-control" id="pen_name_en" 
				<#if current_user.getPenNameEn()?? >
					value="${ current_user.getPenName() }"
				</#if>			
			>
		</div>			
		<div class="form-group">
			<label for="date_of_birth">Date of Birth</label>
			<input type="date" class="form-control" id="date_of_birth"
				<#if current_user.getDateOfBirth()?? >
					value="${ current_user.getDateOfBirth() }"
				</#if>			
			>
		</div>
		
		<div class="form-group">
			<label for="gender">Gender</label><br>
			<label class="radio-inline"><input type="radio" name="gender">Male</label>
			<label class="radio-inline"><input type="radio" name="gender">Female</label>
			<label class="radio-inline"><input type="radio" name="gender">Other</label>
		</div>
			
		<div class="form-group">
		  <label for="language">Language:</label>
		  <select class="form-control" id="language">
		    <option value="hindi">Hindi</option>
		    <option value="tamil">Tamil</option>
		    <option value="kannada">Kannada</option>
		    <option value="bengali">Bengali</option>
		    <option value="telugu">Telugu</option>
		    <option value="gujarati">Gujarati</option>
		    <option value="marathi">Marathi</option>
		    <option value="malayalam">Malayalam</option>
		  </select>
		</div>

		<div class="form-group">
		  	<label for="biography">Biography:</label>
		  	<textarea class="form-control" id="biography"
				<#if current_user.getSummary()?? >
					value="${ current_user.getSummary() }"
				</#if>		  	
		  	></textarea>
		</div>
	</form>
</div>