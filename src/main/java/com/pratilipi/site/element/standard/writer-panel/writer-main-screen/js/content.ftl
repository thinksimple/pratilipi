var Content = function (content_container, parent_object) {
    this.$content_container = content_container;
    this.parent_object = parent_object;
};

Content.prototype.init = function() {
    this.changeDefaultToParagraph();
    this.unformatPastedData();
    this.delegateTargetBlankToLinks();
    this.dismissPopoverOnClickingOutside();
};

Content.prototype.changeDefaultToParagraph = function() {
    var _this = this;
    this.$content_container.one("click", function() {
        document.execCommand('formatBlock', false, 'p');
    });
    this.$content_container.on('keypress', function(ev){
        if( ev.keyCode == '13' && !_this.isSelectionInsideElement( "blockquote" ) ) {    
            document.execCommand('formatBlock', false, 'p');
            // return false;
        }
        else if( ev.keyCode == '13' && _this.isSelectionInsideElement( "blockquote" ) ) {
            document.execCommand('InsertParagraph');
            document.execCommand('Outdent');
            return false;
        }
    });
};

Content.prototype.unformatPastedData = function() {
    this.$content_container.on("paste", function(e) {
        // cancel paste
        e.preventDefault();
        // console.log("here");

        // get text representation of clipboard
        var text = e.originalEvent.clipboardData.getData("text/plain");
        // console.log(text);
        // insert text manually
        document.execCommand("insertHTML", false, text);
    });
};

Content.prototype.delegateTargetBlankToLinks = function() {
    this.$content_container.on( "click mouseover", "a:not(.tooltip_link)", function( event ) {
        // window.open( $( this ).attr( "href"), '_blank');
        if (typeof $(this).data('toggle') == 'undefined' || $(this).data("popover") == "absent") { 
            var href = $(this).attr("href");
            var a_tag = '<a class="tooltip_link" target="_blank" href="' + href + '">' + href + "</a>";

            $(this).attr('data-toggle', "popover").data("placement", "bottom").data("trigger", "manual").data("html","true").data("content", a_tag).popover('show');
            $(this).on('click mouseover', function() {
               
                $(this).popover('show');
            }); 
            if( $(this).data("popover") == "absent" ) {
            	$(this).data("popover","present");
            }    
        }
    });

    this.$content_container.on( "click", "a.tooltip_link", function(event) {
        window.open( $( this ).attr( "href"), '_blank');
    });

};

Content.prototype.dismissPopoverOnClickingOutside = function() {
    $(document).on('click', function (e) {
        //did not click a popover toggle or popover
        if ($(e.target).data('toggle') !== 'popover'
            && $(e.target).parents('.popover.in').length === 0) { 
            $('[data-toggle="popover"]').popover('hide');
        }
    });
};

Content.prototype.isSelectionInsideElement = function( tagName ) {
    var sel, containerNode;
    tagName = tagName.toUpperCase(); 
    if (window.getSelection) {
        sel = window.getSelection();
        if (sel.rangeCount > 0) {
            containerNode = sel.getRangeAt(0).commonAncestorContainer;
        }
    } else if ( (sel = document.selection) && sel.type != "Control" ) {
        containerNode = sel.createRange().parentElement();
    }
    while (containerNode) {
        if ( ( containerNode.nodeType == "1" || containerNode.nodeType == 1 ) && containerNode.tagName == tagName) {
            return true;
        }
        containerNode = containerNode.parentNode;
    }
    return false;    
};

Content.prototype.itemIsLinked = function(){
    if(window.getSelection().toString() != ""){
        var selection = window.getSelection().getRangeAt(0);
        if (selection && ( selection.startContainer.parentNode.tagName === 'A' || selection.endContainer.parentNode.tagName === 'A' ) ) {
            return true;
        } 
    }
    return false;
};

Content.prototype.populateContent = function( response ) {
	if( response == undefined ) {
		this.reset();
	}
	else {
		this.$content_container.html( response );
		var $delete_icon = $("<img>").attr("src", "http://0.ptlp.co/resource-all/icon/svg/trash.svg").attr("data-behaviour", "remove-image");
		this.$content_container.find("img").addClass("writer-image").attr({
			"tabindex": "-1",
			'data-toggle': "popover",
			"data-placement": "right",
			"data-trigger": "focus",
			"data-html":"true",
			"data-content": $delete_icon,
		});
		this.$content_container.find("a").data("popover", "absent");
	}
}; 

Content.prototype.reset = function() {
	this.$content_container.empty();
};

Content.prototype.getContent = function() {
	return this.$content_container.html();
};

Content.prototype.hasEmptyText = function() {
	return ( this.$content_container.children().length == 0 );
};

Content.prototype.wrapInParagraph = function() {
	this.$content_container.wrapInner("<p>");
};