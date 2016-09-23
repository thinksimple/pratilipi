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
    "createLink": {highlighted: "http://0.ptlp.co/resource-all/icon/svg/link-red.svg", unhighlighted: "http://0.ptlp.co/resource-all/icon/svg/link-505050.svg"} };
    this.$execCommandLinks = this.$editor_container.find(".execCommand");
    // this.$alignmentLinks = this.$editor_container.find( '[data-role="alignment"]' );
    this.$urlModal = $('#urlModal');
}

Editor.prototype.init = function() {
    this.attachExecCommandListeners();
    this.attachBlockquoteListener();
    this.attachLinkListener();
    this.attachLinkSelectionListener();
    this.addTextSelectionListener();
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
