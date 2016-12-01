var transliterationApp = function( $transliterable_elem ) {
  this.$transliterable_elem = $transliterable_elem; 
};

transliterationApp.prototype.init = function() {
  var _this = this;
  this.suggester = new Suggester('.word-suggester', _this.onSuggestionPicked, _this.$transliterable_elem );
  this.suggester.init();
  this.$transliterable_elem.on('keypress', _this.suppressKeypress.bind( _this ));
  this.$transliterable_elem.on('keydown', _this.suppressKeydown.bind( _this ));
  this.$transliterable_elem.on("click", function(event) {
    if( _this.suggester.getMode() && ( !$(event.target).closest('.word-suggester').length ) ) {
      _this.suggester.clear();
    }
  });   
};

function getFirstRange() {
  var sel = rangy.getSelection();
  return sel.rangeCount ? sel.getRangeAt(0) : null;
};

transliterationApp.prototype.onSuggestionPicked = function(word, eng_word) {
  // console.log(':::::::Resolved with word ' + word + '::::::::')
  if (window.getSelection) {
    var text = ( word ? word : eng_word ) + "\u00A0";
    sel = rangy.getSelection();
    var range = getFirstRange();
    if (range) {
      var text_node = document.createTextNode(text);
      range.insertNode( text_node );
      range.setStartAfter( text_node );
      // sel.collapseToEnd();
      sel.setSingleRange(range);
    }
  }
};

function insertNodeAtRange() {
  var range = getFirstRange();
  if (range) {
    var el = document.createElement("span");
    el.className = "curWord";
    range.insertNode(el);
    rangy.getSelection().setSingleRange(range);
    return el;
  }
};

transliterationApp.prototype.suppressKeypress = function(e) {
  var keycode = e.keycode || e.which;
  if( this.shouldPreventKeypress( keycode ) ) {
    e.preventDefault();
    this.sendKeyToSuggester(keycode);
  }
};

transliterationApp.prototype.sendKeyToSuggester = function(keycode) {
  this.suggester.type(keycode);
};

transliterationApp.prototype.shouldPreventKeypress = function( keycode ) {
  var translation = new Translation( keycode );
  return ( this.isNotBackspaceKey(translation) && !this.isKeydownActionKey(translation) && ( this.isNotKeypressActionKey(translation) || this.suggester.getMode() ) );
};

transliterationApp.prototype.isNotKeypressActionKey = function( translation ) {
  return ( translation.action != 'space' );
};

transliterationApp.prototype.isNotBackspaceKey = function( translation ) {
  return translation.action != 'backspace';
};

transliterationApp.prototype.suppressKeydown = function(e) {
  var keycode = e.keycode || e.which;
  if( this.shouldPreventKeydown( keycode ) ) {
    e.preventDefault();
    this.sendKeyToSuggester(keycode);
  }
};

transliterationApp.prototype.shouldPreventKeydown = function( keycode ) {
  var translation = new Translation( keycode );
  return ( this.isKeydownActionKey(translation) && this.suggester.getMode() );
};

transliterationApp.prototype.isKeydownActionKey = function( translation ) {
  return ( translation.action == 'up' || translation.action == 'down' || translation.action == 'left' || translation.action == 'right' || translation.action == 'escape');
};


window.onload = function() {
  if(screen.width > 480) {
    rangy.init();
  } 
};
