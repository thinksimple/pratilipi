var MainWriterPanel = function() {
};


MainWriterPanel.prototype.init = function() {
    this.addAffixClasses();
    this.setWrappersHeight();
    this.initializeGlobalVariables();

    var toc_container = $( "#toc_container" );
    this.table_of_contents_object = new TableOfContents( toc_container, this );
    this.table_of_contents_object.init();

    var content_container = $( "#chapter-content" );
    this.content_object = new Content( content_container, this );
    this.content_object.init();
    
    var chapter_name_container = $( "#subtitle" );
    this.chapter_name_object = new ChapterName( chapter_name_container, this );
    this.chapter_name_object.init();

    var editor_container = $( "#editor");
    this.editor_object = new Editor( editor_container, this.content_object );
    this.editor_object.init();
    
    this.hideProgressBarOnMobileFocus();
    this.initializeData();
    
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

MainWriterPanel.prototype.initializeGlobalVariables = function() {
	
	<#if indexJson?? >
		this.index = ${ indexJson };
		this.currChapter = 1;
	<#else>
		this.currChapter = 0;	
	</#if>	
};

MainWriterPanel.prototype.initializeData = function() {
	console.log("hello");
	//if indexJson is not null, book exists, get first chapter and populate the index too.
	<#if indexJson?? >
		//get first chapter and populate it in the writer
		console.log("${indexJson}");
		this.getChapter( 1 );
		this.table_of_contents_object.populateIndex( this.index );
		console.log("indexjson exists");
	<#else>
		//make a new chapter call asychrolously and populate the index
		console.log("indexjson doesnt exists");
		this.addNewChapter();
		console.log("indexjson doesnt exists");
	</#if>
};

MainWriterPanel.prototype.getChapter = function( chapterNum ) {
	var _this = this;
    $.ajax({type: "GET",
        url: "/api/pratilipi/content/chapter",
        data: {
        	pratilipiId: ${ pratilipiId?c },
        	chapterNo: chapterNum,
        	asHtml: true,
        	pageNo:1
        },
        success:function(response){
        	console.log(response);
        	
        	var parsed_data = jQuery.parseJSON( response );
        	console.log(parsed_data);
			_this.populateContent( parsed_data );
		},
        fail:function(response){
        	var message = jQuery.parseJSON( response.responseText );
			alert(message);
		}			    		
		
	});	
};

MainWriterPanel.prototype.populateContent = function( parsed_data ) {
	this.content_object.populateContent( parsed_data.content );
	this.chapter_name_object.change_name( parsed_data.chapterTitle );
};

MainWriterPanel.prototype.addNewChapter = function( chapterNum ) {
	var _this = this;
	var ajaxData = { pratilipiId: ${ pratilipiId?c } };
	if ( chapterNum !== undefined ){
    	ajaxData.chapterNo = chapterNum;   
  	}
    $.ajax({type: "POST",
        url: "/api/pratilipi/content/chapter/add",
        data: ajaxData,
        success:function(response){
        	console.log(response);
        	
        	var index = jQuery.parseJSON( response );
        	console.log(parsed_data);
        	this.index = index;
        	//increase current chapter, reset 
			_this.currChapter++;
			_this.resetContent();
			
		},
        fail:function(response){
        	var message = jQuery.parseJSON( response.responseText );
			alert(message);
		}			    		
		
	});	
};

MainWriterPanel.prototype.resetContent = function() {
	this.table_of_contents_object.populateIndex( index );
	this.content_object.reset();
	this.chapter_name_object.reset();
};