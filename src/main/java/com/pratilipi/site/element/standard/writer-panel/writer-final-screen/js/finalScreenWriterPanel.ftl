var FinalScreenWriterPanel = function (panel_container) {
    this.$panel_container = panel_container;
    this.$form = this.$panel_container.find("form");
    this.$category_select = this.$form.find("#category");
    this.$image_container = this.$form.find(".image-container");
    this.$summary = this.$form.find("#summary");
    this.form_validated = true;
}

FinalScreenWriterPanel.prototype.init = function() {
    this.generateCategoryOptions();
    this.hideCoverImageForm();
    this.attachCoverImageListeners();
    this.attachFormSubmitListener();
	console.log("${pratilipiId?c}");

}

FinalScreenWriterPanel.prototype.generateCategoryOptions = function() {
	var _this = this;
	var category_map = ${ pratilipiTypesJson };
	$.each(category_map, function( key, value ) {
	  	var $option = $("<option>",{
	  		value: key,
	  	}).html( value["name"] );
	  	_this.$category_select.append( $option );
	});
};

FinalScreenWriterPanel.prototype.hideCoverImageForm = function() {
	$("#uploadPratilipiImageInput").hide();
};

FinalScreenWriterPanel.prototype.attachCoverImageListeners = function() {
    this.$image_container.on('click', function() {
    	$("#uploadPratilipiImageInput").trigger('click');
    });
    
    $("#uploadPratilipiImageInput").on('change', function() {
		$("#uploadPratilipiImage").submit();
	});
    
    $('#uploadPratilipiImage').on('submit',(function(e) {
        e.preventDefault();
        var formData = new FormData(this);

        $.ajax({
            type:'POST',
            url: $(this).attr('action'),
            data:formData,
            cache:false,
            contentType: false,
            processData: false,
            success:function(data){
                console.log("success");
                console.log(data);
            },
            error: function(data){
                console.log("error");
                console.log(data);
            }
        });
    }));    
};

FinalScreenWriterPanel.prototype.attachFormSubmitListener = function() {
	var _this = this;
	this.$form.on( "submit", function(e) {
		e.preventDefault();
		_this.validateForm();
	} );
};

FinalScreenWriterPanel.prototype.validateForm = function() {
	this.resetErrorStates();
	if( this.isEmptyStr( this.$category_select.val() ) ) {
		this.$category_select.closest(".form-group").addClass("has-error");
		this.$category_select.after('<span class="error-exclamation glyphicon glyphicon-exclamation-sign form-control-feedback" style="right: 5%;" aria-hidden="true"></span>');
		this.form_validated = false;
	}
	
	if( this.form_validated ) {
		this.ajaxSubmitForm();
	}
	
};

FinalScreenWriterPanel.prototype.resetErrorStates = function() {
	this.form_validated = true;
	this.$form.find(".has-error").removeClass("has-error");
	this.$form.find(".error-exclamation").remove();
	
};

FinalScreenWriterPanel.prototype.ajaxSubmitForm = function() {
	var ajax_data = {
			pratilipiId: ${ pratilipiId?c },
			type: this.$category_select.val() ,
    		summary: this.$summary.val(),
    		state: "PUBLISHED",            		
    	   };
	console.log( ajax_data );
    $.ajax({type: "POST",
        url: "/api/pratilipi",
        data: ajax_data,
        success:function(response){
        	console.log(response);
        	console.log(typeof response);
        	
        	var parsed_data = jQuery.parseJSON( response );
        	console.log(parsed_data);
  			alert( "success" );
		},
        fail:function(response){
        	var message = jQuery.parseJSON( response.responseText );
			alert(message);
		}			    		
		
});	
	
};

FinalScreenWriterPanel.prototype.isEmptyStr = function(str) {
	return ( str==null || str.length === 0 || !str.trim() );
};