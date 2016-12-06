var Editor = function ( parent_object ) {
  this.$image_input = $("#image_input");
  this.parent_object = parent_object;
};

Editor.prototype.init = function() {
  var _this = this;
  this.attachImageInputListener();

  tinymce.init({
    // initialise and auto focus
    selector: '#chapter-content',
    auto_focus: 'chapter-content',
    inline: true,
    min_height: 400,
    max_height: 500,
    block_formats: 'Paragraph=p;',  
    //plugins needed and setting up toolbar
    plugins : ['autoresize autolink lists link image',
    'visualblocks code fullscreen',
    'paste'],
    menubar: false,
    statusbar: false,
    toolbar: 'bold italic underline | alignleft aligncenter alignright | blockquote link imageCustom | bullist numlist  visualblocks code',
    height: 300,
    language: 'hi_IN',
    language_url : 'https://storage.googleapis.com/devo-pratilipi.appspot.com/hi_IN.js',
    link_context_toolbar: true,
    default_link_target:"_blank", 
    allow_unsafe_link_target: false,
    target_list: false,
    link_title: false,
    
    // pasting from other sources
    paste_data_images: true,
     // paste_merge_formats: true, 
    // paste_remove_styles: false,
    // paste_remove_styles_if_webkit: false,
    // paste_strip_class_attributes: true,
    // paste_text_sticky: true,
    // paste_text_sticky_default: true,
    paste_as_text: false,
    paste_auto_cleanup_on_paste : true,
    // paste_enable_default_filters: true,
    // paste_filter_drop: true,
    // paste_word_valid_elements: "i,em,u",
    paste_webkit_styles: "none",
    paste_remove_styles_if_webkit: false,
    paste_text_linebreaktype: "p",

    // paste_retain_style_properties: "color font-size",
    
    // spell check and other options
    browser_spellcheck: false,
    allow_conditional_comments: false,
    allow_html_in_named_anchor: false,
    
    // p on clicking Enter key
    forced_root_block : false,
    force_br_newlines : false,
    force_p_newlines : true,
    remove_trailing_brs: true,
      
    // u instead of text decoration
    formats : {
      bold: {inline : 'b', exact : true},
      italic: {inline : 'i', exact : true},
      underline : {inline : 'u', exact : true},
      blockquote: {block: 'blockquote'},
	  img: { block:'img' },
	  alignleft : {selector : 'p', styles : {textAlign : 'left'} },
	  aligncenter :{selector : 'p', styles : {textAlign : 'center'} },
	  alignright : {selector : 'p', styles : {textAlign : 'right'} },        
    },
    setup : function(ed) {
	  ed.on('init', function (e) {
	    console.log('Editor was initialized.');
	    _this.parent_object.initializeData();
	  });
      if( screen.width > 480 ) {
        ed.on("keydown", function(e){
            var keycode = e.keycode || e.which;
            var translation = new Translation( keycode );
            if( ( translation.action == 'backspace' || translation.action == 'enter' ) && _this.parent_object.content_transliteration_object.suggester.getMode()) {
              // console.log(   )
              _this.parent_object.content_transliteration_object.sendKeyToSuggester(e.which);
              e.preventDefault(); 
            }
        });
      }
      ed.addButton('imageCustom', {
        icon: 'image',
        tooltip: "Insert/edit image",
        cmd: "mceImage",
        onpostrender: monitorNodeChange
      }); 
      function lowercasedElemName(elem) {
        return elem.nodeName.toLowerCase();
      }
      function monitorNodeChange() {
        var btn = this;
        ed.on('NodeChange', function(e) {
          var parents = e.parents.map(lowercasedElemName);
          // console.log(parents.includes("blockquote"));
          btn.disabled(parents.includes("blockquote") || parents.includes("li"));
        });
      }                 
    },
    images_upload_handler: function (blobInfo, success, failure) {
      // console.log("coding karni kab seekhoge");
      // console.log(blobInfo.blob());
      var fd = new FormData();
      var cur_page = _this.parent_object.currChapter;
      fd.append('data', blobInfo.blob());
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
          var parsed_data = jQuery.parseJSON( data );
          var image_name = parsed_data.name;
          var image_url = "/api/pratilipi/content/image?pratilipiId=${ pratilipiId?c }&name=" + image_name;
          _this.parent_object.setNewImageFlag( true );
          success(image_url);  
        },
        error: function(data){
          failure('HTTP Error: ' + data.status);
          return;
            // $("#my_form input").val("");              
        }
      });    
    },

    file_browser_callback: function(field_name, url, type, win) {
      if(type=='image') {
        $('#field_name').val(field_name);
        _this.$image_input.click(); 
      }  
    },

    paste_postprocess: function(plugin, args){
      console.log(args.node);
      $(args.node).find("div").replaceWith(function() {
        if( $(this).text().length ) {
          return "<p>" + $(this).html() + '</p>';
        } else {
          return "";
        }
      });
      $(args.node).find("h1,h2,h3,h4,h5,h6").replaceWith(function() {
        if( $(this).text().length ) {
          return "<b>" + $(this).html() + '</b>';
        } else {
          return "";
        }
      });      
    },    

    // enforcing rules to editor
    valid_elements : 'p[style],img[src|width|height],blockquote,b,i,u,a[href|target=_blank],br,b/strong,i/em,ol,ul,li',
    extended_valid_elements: 'img[src|width|height],p[style],blockquote,ul,ol,li,a[href|target=_blank]',
    valid_children : 'body[p|img|blockquote|ol|ul],-body[br],p[b|i|u|a[href]|br],-p[img],blockquote[b|i|u|a[href]|br],-blockquote[blockquote|img],ol[li],ul[li],-ul[ul|ol|img],li[b|i|u|a[href]|br],-li[img|blockquote]', 
    invalid_elements : "div",
    valid_styles: {'p': 'text-align'},

    // image
    image_description: false,
    image_dimensions: false,
  });    
};

Editor.prototype.attachImageInputListener = function() {
  var _this = this;
  this.$image_input.on("change", function() {
    _this.uploadOnServer();
  });
};

Editor.prototype.uploadOnServer = function() {

  var _this = this;
  var field_name = "#" + $('#field_name').val();
  var fd = new FormData();
  var cur_page = this.parent_object.currChapter;
  var blob = this.$image_input.get(0).files[0];
  console.log(blob);
  fd.append('data', blob);
  fd.append('pratilipiId', ${ pratilipiId?c } );  
  fd.append('pageNo', cur_page );  
  // console.log(fd.get('data'));
  // console.log(blob);    

  $.ajax({
    type:'POST',
    url: '/api/pratilipi/content/image?pratilipiId=${ pratilipiId?c }&pageNo=' + cur_page,
    data:fd,
    cache:true,
    contentType: false,
    processData: false,
    success:function(data){
      var parsed_data = jQuery.parseJSON( data );
      var image_name = parsed_data.name;
      var image_url = "/api/pratilipi/content/image?pratilipiId=${ pratilipiId?c }&name=" + image_name;
      // if( isMobile() ) {
      //   image_url += "&width=240";
      // }
      _this.$image_input.val("");
      $(field_name).val( image_url );
      _this.parent_object.setNewImageFlag( true );
    },
    error: function(data){
      _this.$image_input.val("");              
    }
  }); 
};

