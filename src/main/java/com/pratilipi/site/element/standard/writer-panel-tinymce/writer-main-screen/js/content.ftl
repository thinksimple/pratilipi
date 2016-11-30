var Content = function (content_container_id, parent_object) {
  var id_content_container = "#" + content_container_id;
  this.$content_container = $( id_content_container );
  this.tinymce_content_container = tinyMCE.get( content_container_id );
  this.parent_object = parent_object;
};

Content.prototype.init = function() {
};


Content.prototype.populateContent = function( response ) {
  if( response == undefined ) {
    this.reset();
  }
  else {
    this.tinymce_content_container.setContent( response );
    // if( isMobile() ) {
    //   $all_images.attr( "src", function( ) {
    //     return $(this).attr("src") + "&width=240";
    //   });
    // }
  }
}; 

Content.prototype.reset = function() {
  this.tinymce_content_container.setContent("");
};

Content.prototype.getContent = function() {
  this.convertTextNodesToParagraphs();
  return this.tinymce_content_container.getContent().split("\n").join("");
};


Content.prototype.convertTextNodesToParagraphs = function() {
  this.$content_container.get(0).normalize();
  this.$content_container.contents().filter(function() {
    return this.nodeType == 3;
  }).replaceWith(function() {
    return "<p>" + $(this).text() + "</p>";
  });
};

Content.prototype.checkFirstChild = function() {
  <#-- test it properly -->
  if( !( this.$content_container.children().first().is("p,blockquote,img") ) ) {
    this.$content_container.children().first().replaceWith( function() {
      return "<p>" + this.outerHTML + "</p>";
    } );
  }
};


Content.prototype.hasEmptyText = function() {
  return ( this.$content_container.children().length == 0 || !( this.$content_container.children().first().is("p,blockquote,img") ) );
};

Content.prototype.wrapInParagraph = function() {
  <#-- this.$content_container.wrapInner("<p>"); -->
  document.execCommand('formatBlock', false, 'p');
};