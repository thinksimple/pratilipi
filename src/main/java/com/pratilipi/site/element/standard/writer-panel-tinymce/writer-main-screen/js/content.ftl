var Content = function (content_container_id, parent_object) {
  var id_content_container = "#" + content_container_id;
  this.$content_container = $( id_content_container );
  this.tinymce_content_container = tinyMCE.activeEditor;
  this.parent_object = parent_object;
  this.popoverSettings = {
    placement: 'top',
    container: 'body',
    html: true,
    trigger: "focus",
    selector: '[rel="popover"]',
    content: '<div class="sprites-icon delete-icon image-delete-popover"></div>'
  };
};

Content.prototype.init = function() {
   this.delegateRemoveImageListeners();
   this.attachImageRemovalListener(); 
   this.dismissPopoversOnClickingOutside();
};

Content.prototype.attachImageRemovalListener = function() {
	$("body").on("click", ".image-delete-popover", function() {
		$(".remove-image").remove();
		$('#chapter-content img').popover('hide');
	});
};

Content.prototype.delegateRemoveImageListeners = function() {
  var _this = this;
  this.$content_container.on("click", "img", function() {
  	$(".remove-image").removeClass("remove-image");
  	$( this ).addClass("remove-image");
  	$( this ).popover( _this.popoverSettings ).popover('show');
  });
};

Content.prototype.dismissPopoverOnClickingOutside = function() {
  $(document).on('click', function (e) {
    if (!$(e.target).is("#chapter-content img") && $(e.target).parents('.popover.in').length === 0) { 
       $('#chapter-content img').popover('hide');
    }
  });
};

Content.prototype.populateContent = function( response ) {
  if( response == undefined ) {
    this.reset();
  }
  else {
    this.tinymce_content_container.setContent( response );
    /* if( isMobile() ) {
       $all_images.attr( "src", function( ) {
         return $(this).attr("src") + "&width=240";
       });
     } */
  }
}; 

Content.prototype.reset = function() {
  this.tinymce_content_container.setContent("<p></p>");
};
<#-- get content without line breaks -->
Content.prototype.getContent = function( ) {
  return this.tinymce_content_container.getContent().split("\n").join("");
};

Content.prototype.getContentWithoutNbsps = function( ) {
  return this.getContent().replace(/&nbsp;/g, ' ');
};

<#-- Change content before saving -->
Content.prototype.getContentBeforeSaving = function() {
  this.convertTextNodesToParagraphs();
  this.removeBrs();
  return this.getContentWithoutNbsps();
};

Content.prototype.setContent = function( content ) {
  this.tinymce_content_container.setContent( content );
};

Content.prototype.convertTextNodesToParagraphs = function() {
  this.$content_container.get(0).normalize();
  this.$content_container.contents().filter(function() {
    return this.nodeType == 3;
  }).replaceWith(function() {
    return "<p>" + $(this).text() + "</p>";
  });
};

Content.prototype.removeBrs = function() {
  this.$content_container.children("br:not([data-mce-bogus])").remove();
};

Content.prototype.replaceNbsps = function() {
	this.setContent( this.getContentWithoutNbsps() );
};

Content.prototype.hasEmptyText = function() {
  return ( this.$content_container.children().length == 0 || !( this.$content_container.children().first().is("p,blockquote,img") ) );
};