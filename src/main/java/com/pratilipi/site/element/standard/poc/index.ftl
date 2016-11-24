<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
  <style type="text/css" media="screen">
    body {
      font-family: Helvetica, 'Noto Sans', sans-serif;
    }
    #content-placeholder {
      
      border: whitesmoke 1px solid;
      padding: 10px;
      font-size: 20px;
      height: 400px;
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
  </style>
  <script src='https://cdn.tinymce.com/4/tinymce.min.js'></script>

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
              });                  
            });
        }
      </script>
      <!-- <script src="https://cdnjs.cloudflare.com/ajax/libs/rangy/1.3.0/rangy-core.min.js"></script> -->
      <script><#include "suggester.ftl"></script>
      <script><#include "app.ftl"></script>   
  <script>
  tinymce.init({
    // initialise and auto focus
    selector: '#content-placeholder',
    auto_focus: 'content-placeholder',
    inline: true,
    block_formats: 'Paragraph=p;',  
    //plugins needed and setting up toolbar
    plugins : ['autolink lists link image charmap print preview anchor',
    'searchreplace visualblocks code fullscreen',
    'insertdatetime media table paste code'],
    menubar: false,
    statusbar: false,
    toolbar: 'bold italic underline | alignleft aligncenter alignright | blockquote link image | bullist numlist visualblocks',
    height: 300,
    images_upload_url: "/kuchbhi.php",
    images_upload_base_path: '/some/basepath',  
    
    // pasting from other sources
    paste_data_images: false,
     // paste_merge_formats: true, 
    // paste_remove_styles: false,
    // paste_remove_styles_if_webkit: false,
    // paste_strip_class_attributes: true,
    // paste_text_sticky: true,
    // paste_text_sticky_default: true,
    paste_as_text: false  ,
    // paste_auto_cleanup_on_paste : true,
    // paste_enable_default_filters: false,
    // paste_filter_drop: false,
    // paste_word_valid_elements: "i,em,u",
    // paste_webkit_styles: "color font-size",
    // paste_retain_style_proper  ties: "color font-size",
    
    // spell check and other options
    browser_spellcheck: false,
    allow_conditional_comments: false,
    allow_html_in_named_anchor: false,
    
    // p on clicking Enter key
    forced_root_block : 'p',
    force_br_newlines : false,
    force_p_newlines : true,
    remove_trailing_brs: true,
      
    // u instead of text decoration
    formats : {
        bold: {inline : 'b', exact : true},
        italic: {inline : 'i', exact : true},
        underline : {inline : 'u', exact : true},
        blockquote: {block: 'blockquote', exact: true},
        img: { block:'img', exact: true },
        // alignleft: { selector: 'p', classes: 'left' },
        // alignright: { selector: 'p', classes: 'right' },
        // aligncenter: { selector: 'p', classes: 'center' }
    },
    setup : function(ed) {
        ed.on("keydown", function(e){
            var keycode = e.keycode || e.which;
            var translation = new Translation( keycode );
            if( ( translation.action == 'backspace' || translation.action == 'enter' ) && suggester.getMode()) {
              // console.log(   )
              sendKeyToSuggester(e.which);
              e.preventDefault(); 
            }

        });         
   },
  // images_upload_handler: function (blobInfo, success, failure) {
  //   console.log("coding karni kab seekhoge");
  //   var xhr, formData;
  //   xhr = new XMLHttpRequest();
  //   xhr.withCredentials = false;
  //   xhr.open('POST', 'postAcceptor.php');
  //   xhr.onload = function() {
  //     var json;

  //     if (xhr.status != 200) {
  //       failure('HTTP Error: ' + xhr.status);
  //       return;
  //     }
  //     json = JSON.parse(xhr.responseText);

  //     if (!json || typeof json.location != 'string') {
  //       failure('Invalid JSON: ' + xhr.responseText);
  //       return;
  //     }
  //     success(json.location);
  //   };
  //   formData = new FormData();
  //   formData.append('file', blobInfo.blob(), fileName(blobInfo));
  //   xhr.send(formData);
  // },   
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



    // enforcing rules to editor
    valid_elements : 'p[style],img[src|width|height],blockquote,b,i,u,a[href|target=_blank],br,b/strong,i/em,ol,ul,li',
    extended_valid_elements: 'img[src|width|height],p[style],blockquote,ul,ol,li',
    valid_children : '+body[p|img|blockquote|ol|ul],p[b|i|u|a|br],+blockquote[b|i|u|a|br],ol[li],ul[li],li[b|i|u|a|br]',
    valid_styles: {'p': 'text-align'},

    // image
    image_description: false,
    image_dimensions: false,
  });
 
  </script>
    <script>
        function log() {
            console.log(tinyMCE.get('content-placeholder').getContent());
        }
        function uploadOnServer() {
          var field_name = "#" + $('#field_name').val();
          var fd = new FormData();
          var blob = $("#my_form input").get(0).files[0];
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
                  if( isMobile() ) {
                    image_url += "&width=240";
                  }
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
