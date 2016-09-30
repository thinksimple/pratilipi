var Editor = function ( editor_container, content_object ) {
    this.$editor_container = editor_container;
    this.content_object = content_object;
    this.icons_object = {"bold": {highlighted: "http://0.ptlp.co/resource-all/icon/svg/bold-red.svg", unhighlighted: "http://0.ptlp.co/resource-all/icon/svg/bold.svg"},
    "italic": {highlighted: "http://0.ptlp.co/resource-all/icon/svg/italic-red.svg", unhighlighted: "http://0.ptlp.co/resource-all/icon/svg/italic.svg"},
    "underline": {highlighted: "http://0.ptlp.co/resource-all/icon/svg/underline-red.svg", unhighlighted: "http://0.ptlp.co/resource-all/icon/svg/underline.svg"},
    "justifyLeft": {highlighted: "http://0.ptlp.co/resource-all/icon/svg/paragraph-left-red.svg", unhighlighted: "http://0.ptlp.co/resource-all/icon/svg/paragraph-left.svg"},
    "justifyCenter": {highlighted: "http://0.ptlp.co/resource-all/icon/svg/paragraph-center-red.svg", unhighlighted: "http://0.ptlp.co/resource-all/icon/svg/paragraph-center.svg"},
    "justifyRight": {highlighted: "http://0.ptlp.co/resource-all/icon/svg/paragraph-right-red.svg", unhighlighted: "http://0.ptlp.co/resource-all/icon/svg/paragraph-right.svg"},
    "blockquote": {highlighted: "http://0.ptlp.co/resource-all/icon/svg/quotes-left-red.svg", unhighlighted: "http://0.ptlp.co/resource-all/icon/svg/quotes-left.svg"},
    "createLink": {highlighted: "http://0.ptlp.co/resource-all/icon/svg/link-red.svg", unhighlighted: "http://0.ptlp.co/resource-all/icon/svg/link-505050.svg"},
    "insertImage": {highlighted: "http://0.ptlp.co/resource-all/icon/svg/trash-d0021b.svg", unhighlighted: "http://0.ptlp.co/resource-all/icon/svg/camera2.svg"} };
    this.$execCommandLinks = this.$editor_container.find(".execCommand");
    // this.$alignmentLinks = this.$editor_container.find( '[data-role="alignment"]' );
    this.$urlModal = $('#urlModal');
}

Editor.prototype.init = function() {
    this.attachExecCommandListeners();
    this.attachBlockquoteListener();
    this.attachLinkListener();
    this.attachLinkSelectionListener();
    this.attachImageSelectionListener();
    this.addTextSelectionListener();
    this.addImageListener();
    this.addRemoveImageListener();
    this.removeEventListenersOnUrlModalHide();
}

Editor.prototype.attachExecCommandListeners = function() {
    // console.log(this.$execCommandLinks);
    var _this = this;
    this.$execCommandLinks.each(function( i ) {
        $(this).on("click", function(e) {
            e.preventDefault();
            if ( !( _this.content_object.isSelectionInsideElement("blockquote") && $(this).hasClass("alignment") ) ) {
                document.execCommand(this.id, false, null);
                _this.highlightEditorsOptions();
            }

        });
    });
}

Editor.prototype.attachBlockquoteListener = function() {
    var _this = this;
    this.$editor_container.find("#blockquote").on("click", function( e ) {
        e.preventDefault;
        _this.toggleBlockquote();
        _this.highlightBlockquoteOption();
    });
}


Editor.prototype.attachLinkListener = function() {
    var _this = this;
    this.$editor_container.find("#link").on("click", function( e ) {
        e.preventDefault;
        if( _this.content_object.itemIsLinked() ) {
            document.execCommand("unlink", false, null);
            _this.content_object.$content_container.find(".popover").remove();
        }
        else {
            _this.promptUrlBox();
        }
        
    });
}

Editor.prototype.saveSelection = function() {
    if (window.getSelection) {
        var sel = window.getSelection();
        if (sel.getRangeAt && sel.rangeCount) {
            return sel.getRangeAt(0);
        }
    } else if (document.selection && document.selection.createRange) {
        return document.selection.createRange();
    }
    return null;
};

Editor.prototype.restoreSelection = function(range) {
    if (range) {
        if (window.getSelection) {
        	console.log("pehe wale mein");
            var sel = window.getSelection();
            sel.removeAllRanges();
            sel.addRange(range);
        } else if (document.selection && range.select) {
            range.select();
        }
    }
};

Editor.prototype.promptUrlBox = function() {
	var saved_selection = this.saveSelection();
	var _this = this;
	this.$urlModal.modal('show');
	this.$urlModal.find("#input_url").val("");
	this.$urlModal.find('#url_ok_button').one('click', function() {
	    var url = _this.$urlModal.find("#input_url").val();
	    _this.$urlModal.modal('hide');
	    _this.restoreSelection( saved_selection );
	    _this.linkUrlToText( url );
    });

}

Editor.prototype.linkUrlToText = function(url) {
	if(url.length) {
	    document.execCommand("createLink", false, url);
	}
};

Editor.prototype.attachLinkSelectionListener = function() {
    var _this = this;
    this.content_object.$content_container.on("selectstart", function() {
        $(document).one('mouseup', function() {
            if ( _this.content_object.itemIsLinked() ) {
                _this.$editor_container.find("#link img").attr( "src", _this.icons_object["createLink"]["highlighted"] );
            
                $( document ).one('mouseup', function() {
                    _this.$editor_container.find("#link img").attr( "src", _this.icons_object["createLink"]["unhighlighted"] );
                });

            }
        });
    });
};

Editor.prototype.attachImageSelectionListener = function() {
    var _this = this;
    var $add_image = this.$editor_container.find("#insertImage");
    var $remove_image = this.$editor_container.find("#removeImage");
    this.content_object.$content_container.on("focus", "img.writer-image", function(e) {
    	$( this ).addClass("remove");
    	$add_image.hide();
    	$remove_image.show();
    });
    this.content_object.$content_container.on("blur", "img.writer-image", function(e) {
    	_this.content_object.$content_container.find("img.remove").removeClass("remove");
    	$remove_image.hide();
    	$add_image.show();
    });    
};

Editor.prototype.addTextSelectionListener = function() {
    var _this = this;
    this.content_object.$content_container.on("mouseup keyup", function() {
        _this.highlightEditorsOptions();
    });
}; 

Editor.prototype.highlightEditorsOptions = function() {
    var _this = this;
    this.$execCommandLinks.each(function( i ) {
        var img_src = document.queryCommandState( this.id ) ? _this.icons_object[ this.id ]["highlighted"] : _this.icons_object[ this.id ]["unhighlighted"];
        $(this).find("img").attr( "src", img_src );
    });
    this.highlightBlockquoteOption();
};

Editor.prototype.highlightBlockquoteOption = function() {
    var blockquote_img_src = this.content_object.isSelectionInsideElement( "blockquote" ) ? this.icons_object["blockquote"]["highlighted"] : this.icons_object[ "blockquote" ]["unhighlighted"];
    this.$editor_container.find( "#blockquote img" ).attr( "src", blockquote_img_src );
};

Editor.prototype.toggleBlockquote = function() {
    if( this.content_object.isSelectionInsideElement( "p" ) ) {

        document.execCommand("formatBlock", false, "blockquote");
    }
    else {
        document.execCommand("formatBlock", false, "p");
    }
};

Editor.prototype.addImageListener = function() {
	var _this = this;
	var $upload_image_select = $("#upload_images");
	this.$editor_container.find("#insertImage").on('click', function() {
		$upload_image_select.trigger('click');
	});
	console.log($upload_image_select);
	$upload_image_select.on('change', function(evt) {
			console.log("idhar hun mai");	
		    ImageTools.resize(this.files[0], {
		        width: 480, // maximum width
		        height: 480 // maximum height
		    }, function(blob, didItResize) {
		        // didItResize will be true if it managed to resize it, otherwise false (and will return the original file as 'blob')
		        var $current_element = $( _this.getSelectionStart() );
		        var $last_element;
		        if( $current_element.closest("p").length ) {
		        	$last_element =  $current_element.closest("p") ;
		        }
		        else if ( $current_element.closest("blockquote").length ) {
		        	$last_element =  $current_element.closest("blockquote") ;
		        }
		        else {
		        	$last_element = null;
		        }
				var $img = $("<img>").attr({
					"src": window.URL.createObjectURL(blob),
					"tabindex": -1,
				} ).addClass("writer-image").addClass("blur-image");
				if( $last_element ) {
					$img.insertAfter( $last_element );
				}
				else {
					_this.content_object.$content_container.append($img);
				}
				$("<p><br></p>").insertAfter($img);
				var cur_page = _this.content_object.parent_object.currChapter;
				var fd = new FormData();
					fd.append('data', blob);
					fd.append('pratilipiId', ${ pratilipiId?c } );  
					fd.append('pageNo', cur_page );
					
				$.ajax({
		            type:'POST',
		            url: '/api/pratilipi/content/image?pratilipiId=${ pratilipiId?c }&pageNo=' + cur_page,
		            data:fd,
		            cache:true,
		            contentType: false,
		            processData: false,
		            success:function(data){
		                console.log("success");
		                console.log(data);
		                var parsed_data = jQuery.parseJSON( data );
		                var image_name = parsed_data.imageName;
		                var image_url = "/api/pratilipi/content/image?pratilipiId=${ pratilipiId?c }&name=" + image_name;
		                $img.attr( "src", image_url ).attr( "name", image_name ).removeClass("blur-image");
		            },
		            error: function(data){
		                console.log("error");
		                console.log(data);
		                $img.remove();
		            }
		        });
		    });
	});	    
	
};

Editor.prototype.addRemoveImageListener = function() {
	var _this = this;
	var $removeImageLink = this.$editor_container.find("#removeImage");
	var $addImageLink = this.$editor_container.find("#insertImage");
	$removeImageLink.on("click", function(e) {
		_this.content_object.$content_container.find("img.remove").remove();
		$removeImageLink.hide();
		$addImageLink.show();
	});
};

Editor.prototype.getSelectionStart = function () {
   var node = document.getSelection().anchorNode;
   return (node.nodeType == 3 ? node.parentNode : node);
}

Editor.prototype.removeEventListenersOnUrlModalHide = function () {
 	var _this = this; 
	this.$urlModal.on('hidden.bs.modal', function (e) {
		_this.$urlModal.find('#url_ok_button').unbind("click");
	});
};