var Content = function (content_container) {
    this.$content_container = content_container;
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
        if (typeof $(this).data('toggle') == 'undefined') { 
            var href = $(this).attr("href");
            var a_tag = '<a class="tooltip_link" target="_blank" href="' + href + '">' + href + "</a>";
            // var templ = '<div class="tooltip" role="tooltip"><div class="tooltip-arrow"></div><div class="tooltip-inner"><a href="' + href + '">' + href + '</a></div></div>';
            // console.log( templ );
            $(this).attr('data-toggle', "popover").data("placement", "bottom").data("trigger", "manual").data("html","true").data("content", a_tag).popover('show');
            $(this).on('click mouseover', function() {
                // alert("hover ke andar");
                $(this).popover('show');
            });         
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


