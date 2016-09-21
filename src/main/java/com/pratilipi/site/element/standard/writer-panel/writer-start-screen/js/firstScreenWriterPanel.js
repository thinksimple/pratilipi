var FirstScreenWriterPanel = function (panel_container) {
    this.$panel_container = panel_container;
    this.$form = this.$panel_container.find("form");
    this.$vernacular_title = this.$form.find("#title-vernacular");
    this.$english_title = this.$form.find("#title-english");
    this.$copyright_checkbox = this.$form.find("#copyright_checkbox");
    this.form_validated = true;
}

FirstScreenWriterPanel.prototype.init = function() {
    this.generateAndPopulateLanguageOptions();
    this.attachLanguageChangeListener();
    this.attachFormSubmitListener();
}

FirstScreenWriterPanel.prototype.generateAndPopulateLanguageOptions = function() {
	var _this = this;
	 this.$select = this.$panel_container.find("#select-language");
	var language_map = ${ languageMap };
	$.each(language_map, function( key, value ) {
	  	var $option = $("<option>",{
	  		value: key.toLowerCase(),
	  	}).html(value);
	  	_this.$select.append( $option );
	});
	this.$select.val( "${ language }".toLowerCase() );
}

FirstScreenWriterPanel.prototype.attachLanguageChangeListener = function() {
	this.$select.change(function() {
		var $this = $(this);
		window.location = ( "http://" + $this.val() + ".pratilipi.com" + window.location.pathname + window.location.search );
		
	});	
};

FirstScreenWriterPanel.prototype.attachFormSubmitListener = function() {
	var _this = this;
	this.$form.on( "submit", function(e) {
		e.preventDefault();
		_this.validateForm();
	} );
};

FirstScreenWriterPanel.prototype.validateForm = function() {
	this.resetErrorStates();
	if( this.isEmptyStr( this.$vernacular_title.val() ) ) {
		this.$vernacular_title.closest(".form-group").addClass("has-error");
		this.$vernacular_title.after('<span class="error-exclamation glyphicon glyphicon-exclamation-sign form-control-feedback" aria-hidden="true"></span>');
		this.form_validated = false;
	}
	
	if( this.isEmptyStr( this.$english_title.val() ) ) {
		this.$english_title.closest(".form-group").addClass("has-error");
		this.$english_title.after('<span class="error-exclamation glyphicon glyphicon-exclamation-sign form-control-feedback" aria-hidden="true"></span>');
		this.form_validated = false;
	}
	
	if ( !this.$copyright_checkbox.prop("checked") )  {
		this.$copyright_checkbox.closest(".checkbox").addClass("has-error");
		this.form_validated = false;
	}
	
	if( this.form_validated ) {
		this.ajaxSubmitForm();
	}
	
};

FirstScreenWriterPanel.prototype.resetErrorStates = function() {
	this.form_validated = true;
	this.$form.find(".has-error").removeClass("has-error");
	this.$form.find(".error-exclamation").remove();
	
};

FirstScreenWriterPanel.prototype.ajaxSubmitForm = function() {
	var ajax_data = {
			title: this.$vernacular_title.val() ,
    		titleEn: this.$english_title.val(),
    		language: "${ language }",
    		state: "DRAFTED",            		
    	   };
	<#if ( authorId?? )>
		ajax_data.authorId = authorId;
	</#if>
	console.log( ajax_data );
    $.ajax({type: "POST",
        url: "/api/pratilipi",
        data: ajax_data,
        success:function(response){
        	console.log(response);
        	console.log(typeof response);
        	
        	var parsed_data = jQuery.parseJSON( response );
        	console.log(parsed_data);
  			window.location = ( window.location.href.split("/")[0] + "hindi.gamma.pratilipi.com" + window.location.pathname + "?action=write" );
		},
        fail:function(response){
        	var message = jQuery.parseJSON( response.responseText );
			alert(message);
		}			    		
		
});	
	
};

FirstScreenWriterPanel.prototype.isEmptyStr = function(str) {
	return ( str.length === 0 || !str.trim() );
};