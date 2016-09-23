var MainWriterPanel = function() {
};


MainWriterPanel.prototype.init = function() {
    this.addAffixClasses();
    this.setWrappersHeight();

    var toc_container = $( "#toc_container" );
    this.table_of_contents_object = new TableOfContents( toc_container );
    this.table_of_contents_object.init();

    var content_container = $( "#chapter-content" );
    this.content_object = new Content( content_container );
    this.content_object.init();

    var editor_container = $( "#editor");
    this.editor_object = new Editor( editor_container, this.content_object );
    this.editor_object.init();
    
    this.hideProgressBarOnMobileFocus();
    
};

MainWriterPanel.prototype.addAffixClasses = function() {
    $('#editor').affix({
        offset: {
            top: 50,
            // bottom: 0
        }
    });
    $('#header1').affix({
        offset: {
            top: 50,
            // bottom: 0
        }
    }); 

    $('#header1').on('affixed.bs.affix', function(e) {
        $("#chapter-content").css("margin-top","50px");
    });
    $('#header1').on('affixed-top.bs.affix', function(e) {
        $("#chapter-content").css("margin-top","10px");
    });
    //make textarea empty to show placeholder.
    $("#chapter-content").empty();
                    
};

MainWriterPanel.prototype.setWrappersHeight = function() {
    $(".editor-wrapper").height($("#editor").height());
    $(".header-wrapper").height($("#header1").height());
     $('[data-toggle="popover"]').popover();
};

MainWriterPanel.prototype.hideProgressBarOnMobileFocus = function() {
     if(navigator.userAgent.indexOf('Android') > -1 ){
	     this.lastWindowHeight = $(window).height();
	     this.lastWindowWidth = $(window).width();
	     var _this = this;
	     
	     $(window).resize(function () {
		    if ($("#subtitle,#chapter-content").is(":focus")) {
		        var keyboardIsOn =
		           ((_this.lastWindowWidth == $(window).width()) && (_this.lastWindowHeight > $(window).height()));
		    }   
		    if(keyboardIsOn){
				$("#pagination").hide();
		    } else{
				$("#pagination").show();
		    }
		 }); 
     }
};