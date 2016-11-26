<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
  <style type="text/css" media="screen">
    @media only screen and (max-device-width: 549px)  {

      #mce-modal-block {
      }

      .mce-window {
        width: auto !important;
        top: 0 !important;
        left: 0 !important;
        right: 0 !important;
        bottom: 0 !important;
        background: none !important;
      }

      .mce-window-head {
        background: #fff !important;
      }

      .mce-window-body {
        background: #fff !important;
      }

      .mce-foot {
      }

      .mce-foot > .mce-container-body {
        padding: 10px !important;
      }

      .mce-foot button {
      }

      .mce-panel {
        max-width: 100% !important;
      }

      .mce-container {
        max-width: 100% !important;
        height: auto !important;
      }

      .mce-container-body {
        max-width: 100% !important;
        height: auto !important;
      }

      .mce-form {
        padding: 10px !important;
      }

      .mce-tabs {
        max-width: 100% !important;
      }

      .mce-tabs .mce-tab, .mce-tabs .mce-tab.mce-active {
      }

      .mce-formitem {
        margin: 10px 0 !important;
      }

      .mce-btn > button {
      }

      .mce-abs-layout-item {
        position: static !important;
        width: auto !important;
      }

      .mce-abs-layout-item.mce-label {
        display: block !important;
      }

      .mce-abs-layout-item.mce-textbox {
        -webkit-box-sizing: border-box !important;
        -moz-box-sizing: border-box !important;
        box-sizing: border-box !important;
        display: block !important;
        width: 100% !important;
      }

      .mce-abs-layout-item.mce-combobox {
        display: flex !important;
      }

      .mce-abs-layout-item.mce-combobox > .mce-textbox {
        -ms-flex: 1 1 auto;
        -webkit-flex: 1 1 auto;
        flex: 1 1 auto;
        height: 29px !important;
      }
    }
    body {
      font-family: Helvetica, 'Noto Sans', sans-serif;
    }
    #content-placeholder {
      
      border: whitesmoke 1px solid;
      padding: 10px;
      font-size: 20px;
      min-height: 400px;
    }

    .word-suggester {
      position: absolute;
      padding: 10px;
      border: whitesmoke 1px solid;
      display: inline-block;
      min-width: 100px;
      display: none;
    }

    .word-input {
      border: none;
      display: inline-block;
      border-right: 2px aqua solid;
    }

    .suggestions {

    }

    .suggestion {
      cursor: pointer;
    }
    .highlight-suggestion {
      background: lightgrey;
    }
    blockquote {
      padding: 10px 20px;
      margin: 0 0 20px;
      font-size: 17.5px;
      border-left: 5px solid #eee;
    }    
  </style>
    <!-- Latest compiled and minified CSS -->
<!--   <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous"> -->  
  <script src='https://cdn.tinymce.com/4.4/tinymce.min.js'></script>

</head>

<body>
    <div id="content-placeholder"></div>
    <div class="word-suggester">
        <div class="word-input" name=""></div>
        <div class="suggestions">
          <div class="suggestion"></div>
        </div>
      </div>
    <button style="margin-top: 50px;" onClick="log()">log</button>
    <form id="my_form" action="/upload/" target="form_target" method="post" enctype="multipart/form-data" style="width:0px;height:0;overflow:hidden">
        <input name="image" type="file" onchange="uploadOnServer()">
    </form>
    <input type="hidden" id="field_name" value="" />
       
     <style>
        @import url(http://fonts.googleapis.com/earlyaccess/notosanstamil.css);*{font-family: Helvetica, 'Noto Sans Tamil', sans-serif;}
        #content-placeholder img {
            display: block;
            margin: 8px auto;
        }
     </style>
      <script src="https://code.jquery.com/jquery-3.1.1.min.js" integrity="sha256-hVVnYaiADRTO2PzUGmuLJr8BLUSjGIZsDYGmIJLv2b8=" crossorigin="anonymous"></script>

      <script>
        if(screen.width > 480) {
            $.getScript( "https://cdnjs.cloudflare.com/ajax/libs/rangy/1.3.0/rangy-core.min.js", function( data, textStatus, jqxhr ) {
              // console.log( "Load was performed." );
              $.getScript("https://cdnjs.cloudflare.com/ajax/libs/rangy/1.3.0/rangy-selectionsaverestore.min.js", function( data, textStatus, jqxhr ) {
                // console.log( "Load was performed 2  ." );
                $.getScript("resources/js/tinymce-writer/suggester.js", function( data, textStatus, jqxhr ) {
                  console.log( "Load was performed 3  ." );
                  $.getScript("resources/js/tinymce-writer/app.js", function( data, textStatus, jqxhr ) {
                   console.log( "Load was performed 4  ." );
                 });                  
               });                
              });                  
            });
        }
      </script>
      <!-- <script src="https://cdnjs.cloudflare.com/ajax/libs/rangy/1.3.0/rangy-core.min.js"></script> -->   
  <script>
  tinymce.init({
    // initialise and auto focus
    selector: '#content-placeholder',
    auto_focus: 'content-placeholder',
    inline: true,
    min_height: 400,
    max_height: 500,
    keep_styles: false,
    block_formats: 'Paragraph=p;',  
    //plugins needed and setting up toolbar
    plugins : ['autoresize autolink lists link image charmap print preview anchor',
    'searchreplace visualblocks code fullscreen',
    'insertdatetime media table paste code'],
    menubar: false,
    statusbar: false,
    toolbar: 'bold italic underline | alignleft aligncenter alignright | blockquote link image | bullist numlist  visualblocks code',
    height: 300,
    language: 'hi_IN',
    language_url : 'https://storage.googleapis.com/devo-pratilipi.appspot.com/hi_IN.js',
    // images_upload_url: '/api/pratilipi/content/image?pratilipiId=5179861627830272',
    // images_upload_base_path: '/some/basepath', 
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

    // paste_retain_style_proper  ties: "color font-size",
    
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
        // alignleft: { selector: 'p', classes: 'left' },
        // alignright: { selector: 'p', classes: 'right' },
        // aligncenter: { selector: 'p', classes: 'center' }
    },
    setup : function(ed) {
      if( screen.width > 480 ) {
        ed.on("keydown", function(e){
            var keycode = e.keycode || e.which;
            var translation = new Translation( keycode );
            if( ( translation.action == 'backspace' || translation.action == 'enter' ) && suggester.getMode()) {
              // console.log(   )
              sendKeyToSuggester(e.which);
              e.preventDefault(); 
            }
        });
      }           
    },
    images_upload_handler: function (blobInfo, success, failure) {
      console.log("coding karni kab seekhoge");
      console.log(blobInfo.blob());
      // console.log(failure);
      var fd = new FormData();
      fd.append('data', blobInfo.blob()); 
      $.ajax({
          type:'POST',
          url: '/api/pratilipi/content/image?pratilipiId=5179861627830272&pageNo=1',
          data:fd,
          cache:true,
          contentType: false,
          processData: false,
          success:function(data){
              var parsed_data = jQuery.parseJSON( data );
              var image_name = parsed_data.name;
              var image_url = "/api/pratilipi/content/image?pratilipiId=5179861627830272&name=" + image_name;
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
         $("#my_form input").click(); 
        }

        
      // win.document.getElementById(field_name).value = 'http://hindi.gamma.pratilipi.com/api/pratilipi/content/image?pratilipiId=5179861627830272&name=1479906571795';

          // tinyMCE.activeEditor.uploadImages(function(success) {
        //   console.log("coding sahi mein nahi aati");
        //   console.log(success);
        //   $.post('ajax/post.php', tinymce.activeEditor.getContent()).done(function() {
        //     console.log("Uploaded images and posted content as an ajax request.");
        //   });
        // });    
    },

    // init_instance_callback: function (editor) {
    //   editor.on('NodeChange', function (e) {
    //     console.log(e.element.tagName);
    //     if(e.element.tagName == 'IMG' && $(e.element).parent().is("p")) {
    //         $(e.element).unwrap();
    //       console.log("success");
    //     }
    //   });
    // },

    // enforcing rules to editor
    valid_elements : 'p[style],img[src|width|height],blockquote,b,i,u,a[href|target=_blank],br,b/strong,i/em,ol,ul,li',
    extended_valid_elements: 'img[src|width|height],p[style],blockquote,ul,ol,li,a[href|target=_blank]',
    valid_children : 'body[p|img|blockquote|ol|ul],-body[#text],p[b|i|u|a[href]|br],-p[img],blockquote[b|i|u|a[href]|br],ol[li],ul[li],li[b|i|u|a|br]',

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
    invalid_elements : "div",
    valid_styles: {'p': 'text-align'},


    // image
    image_description: false,
    image_dimensions: false,
  });
 
  </script>
    <script>
        function log() {
            var content = tinyMCE.get('content-placeholder').getContent();
            console.log(content);
            // tinyMCE.get('content-placeholder').setContent(content);
        }
        function uploadOnServer() {
          var field_name = "#" + $('#field_name').val();
          var fd = new FormData();
          var blob = $("#my_form input").get(0).files[0];
          console.log(blob);
          fd.append('data', blob);
          // fd.append('pratilipiId', 5179861627830272 );  
          // fd.append('pageNo', cur_page );  
          console.log(fd.get('data'));
          console.log(blob);    

          $.ajax({
              type:'POST',
              url: '/api/pratilipi/content/image?pratilipiId=5179861627830272&pageNo=1',
              data:fd,
              cache:true,
              contentType: false,
              processData: false,
              success:function(data){
                  var parsed_data = jQuery.parseJSON( data );
                  var image_name = parsed_data.name;
                  var image_url = "/api/pratilipi/content/image?pratilipiId=5179861627830272&name=" + image_name;
                  // if( isMobile() ) {
                  //   image_url += "&width=240";
                  // }
                  $("#my_form input").val("");
                  $(field_name).val( image_url );
              },
              error: function(data){
                  $("#my_form input").val("");              
              }
          });          
        }

    </script>       
</body>
</html>
