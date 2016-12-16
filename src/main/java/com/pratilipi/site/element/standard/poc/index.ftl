<!DOCTYPE html>
<html lang="${ lang }">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
        <meta name="description" content="A platform to discover, read and share your favorite stories, poems and books in a language, device and format of your choice.">
        <title>Knockout Test</title>

        <#-- Google Analytics -->
		<#include "meta/GoogleAnalytics.ftl">
		
		
		<#-- Clevertap Script -->
		<script type="text/javascript">
			var clevertap = {event:[], profile:[], account:[], onUserLogin:[], notifications:[]};
			clevertap.account.push({"id": "TEST-Z88-4ZZ-974Z"});
			(function () {
			 var wzrk = document.createElement('script');
			 wzrk.type = 'text/javascript';
			 wzrk.async = true;
			 wzrk.src = ('https:' == document.location.protocol ? 'https://d2r1yp2w7bby2u.cloudfront.net' : 'http://static.clevertap.com') + '/js/a.js';
			 var s = document.getElementsByTagName('script')[0];
			 s.parentNode.insertBefore(wzrk, s);
			})();
		</script>

        <script src="http://0.ptlp.co/resource-all/jquery.knockout.boostrap.js" type="text/javascript"></script>
        <link rel='stylesheet' href='http://1.ptlp.co/third-party/bootstrap-3.3.4/css/bootstrap.min.css'>

        <style>
            html, body {
                padding: 0;
                margin: 0;
                width: 100%;
            }
            .header {
                height: 60px;
                min-height: 60px;
                width: 100%;
                position: fixed;
                background: #f5f5f5;
                border-bottom: 1px solid #d3d3d3;
                z-index: 1;
            }
            .pratilipi-icon {
                width: 48px;
                height: 48px;
                margin-left: 4px;
                margin-top: 8px;
                float: left;
            }
            .container {
                width: 100%;
                padding-top: 68px;
                margin-bottom: 10px;
                padding-left: 8px;
                padding-right: 8px;
            }
            .navigation-bar {
                margin-right: 12px;
                background: #f5f5f5;
                padding: 20px 32px;
                margin-bottom: 12px;
                border: 1px solid #d3d3d3;
            }
            .navigation-title {
                color: #d0021b;
                font-weight: 700;
                font-size: 18px;
            }
            .navigation-link {
                display: block;
                color: #333;
                line-height: 24px;
                text-decoration: none;
            }
            .main-content {
                overflow:hidden;
                padding: 0 24px;
                background: #f5f5f5;
                border: 1px solid #d3d3d3;
            }
            .section-title {
                border-bottom: 1px solid #333;
            }
            .section-title a {
                color: #d0021b;
                text-decoration: none;
            }
            .footer {
                clear: both;
                width: 100%;
                background: #f5f5f5;
                padding: 20px 0;
				border-top: 1px solid #d3d3d3;
            }

            .pratilipi-card {
                margin-bottom: 20px;
            }
            .pratilipi-card .image-holder {
                float: left;
                width: 100px;
                height: 150px;
            }
            .pratilipi-card .info-holder {
                margin-left: 8px;
                float: left;
                width: calc( 100% - 120px );
                overflow: hidden;
            }
            .pratilipi-card .info-holder .pratilipi-title {
                margin: 0;
                margin-top: 8px;
            }
            .pratilipi-card .info-holder .pratilipi-title a {
                text-decoration: none;
                color: #333;
            }
            .pratilipi-card .info-holder .author-name {
                margin: 0;
                margin-top: 8px;
            }
            .pratilipi-card .info-holder .author-name a {
                text-decoration: none;
                color: #333;
            }
            .pratilipi-card .info-holder .meta-info {
                margin-top: 4px;
                margin-bottom: 16px;
            }
            .pratilipi-card .info-holder .read-button {
                background: #F1F8FB;
                border: 1px solid #0C68BD;
                box-shadow: 0px 1px 1px 0px rgba(0,0,0,0.50);
                border-radius: 2px;
                font-size: 13px;
                color: #0C68BD;
                letter-spacing: 0.3px;
                line-height: 16px;
                text-shadow: 0px 1px 2px #FFFFFF;
                display: inline-block;
                padding: 10px 15px 10px 15px;
                margin-right: 10px;
                outline: none;
                text-decoration: none;
            }
            
            .carousel {
                max-width: 1000px;
                margin: 12px auto;
            }
        </style>
    </head>
    <body>
        <div class="header">
            <a href="/">
                <img class="pratilipi-icon" title="Pratilipi" alt="Pratilipi" src="http://0.ptlp.co/resource-ta/logo/pratilipi_logo.png" />
            </a>
        </div>
        <div class="container">
            <div data-bind="foreach: { data: navigationList, as: 'navigation' }" class="navigation-bar pull-left hidden-xs hidden-sm">
                <div class="navigation-title" data-bind="text: title"></div>
                <div data-bind="foreach: {data: linkList, as: 'link'}">
                    <a class="navigation-link" data-bind="attr: {href: url,title: name}, text: name"></a>
                </div>
                <br/>
            </div>
            

            <div class="main-content">
                <div id="carousel" class="carousel slide" data-ride="carousel">
                    <ol class="carousel-indicators">
                        <li data-target="#carousel" data-slide-to="0" class="active"></li>
                        <li data-target="#carousel" data-slide-to="1"></li>
                        <li data-target="#carousel" data-slide-to="2"></li>
                        <li data-target="#carousel" data-slide-to="3"></li>
                        <li data-target="#carousel" data-slide-to="4"></li>
                    </ol>
                    <div class="carousel-inner">
                        <div class="item active">
                            <a href="/event/ore-oru-oorla" target="_blank"><img src="http://4.ptlp.co/resource-ta/home-page-banner/pratilipi-tamil-carousel-19.jpg" /></a>
                        </div>
                        <div class="item">
                            <a on-click="write"><img src="http://0.ptlp.co/resource-ta/home-page-banner/pratilipi-tamil-carousel-16.jpg" /></a>
                        </div>
                        <div class="item">
                            <a href="/horror" target="_blank"><img src="http://1.ptlp.co/resource-ta/home-page-banner/pratilipi-tamil-carousel-17.jpg" /></a>
                        </div>
                        <div class="item">
                            <a href="/education" target="_blank"><img src="http://2.ptlp.co/resource-ta/home-page-banner/pratilipi-tamil-carousel-18.jpg" /></a>
                        </div>
                        <div class="item">
                            <a href="/fiveminstories" target="_blank"><img src="http://3.ptlp.co/resource-ta/home-page-banner/pratilipi-tamil-carousel-13.jpg" /></a>
                        </div>
                    </div> 
                    <a href="#carousel" class="left carousel-control" data-slide="prev">
                        <span class="glyphicon glyphicon-chevron-left"></span>
                    </a>
                    <a href="#carousel" class="right carousel-control" data-slide="next">
                        <span class="glyphicon glyphicon-chevron-right"></span>
                    </a>
                </div>
                <div class="loading-spinner" data-bind="visible: sectionList().length == 0">Loading Data...</div>
                <div data-bind="foreach: {data: sectionList, as: 'section'}, visible: sectionList().length > 0">                
                    <h3 class="section-title"><a data-bind="text: title, attr: {href:listPageUrl, target:'_blank'}"></a></h3>
                    <div class="row" data-bind="foreach: {data: pratilipiList, as: 'pratilipi'}">
                        <div class="col-xs-12 col-sm-6 col-md-6 col-lg-4 pratilipi-card">
                            <div class="image-holder">
                                <a data-bind="attr: {href:pageUrl}">
                                    <img width="100" height="150" data-bind="attr: {src: coverImageUrl+'&width=150'}" />
                                </a>
                            </div>
                            <div class="info-holder">
                                <h4 class="pratilipi-title"><a data-bind="attr: {href:pageUrl,title:title}, text: title"></a></h4>
                                <h5 class="author-name"><a data-bind="attr: {href:author.pageUrl,title:author.name,target:'_blank'}, text: author.name"></a></h4>
                                <div class="meta-info">
                                    <div data-bind="visible: averageRating > 0"><span data-bind="text: averageRating.toFixed(1)"></span>&nbsp;Stars</div>
                                </div>
                                <a data-bind="attr: {href:readPageUrl}" class="read-button">Read</a>
                            </div>
                        </div>
                    </div>
                    <div style="min-height: 20px;">&nbsp;</div>
                </div>
            </div>            
        </div>
        <div class="footer">
            Footer
        </div>


     <script type="application/javascript">
        $(function(){
            var ViewModel = {
                user: ${ userJson },
                pratilipiTypes: ${ pratilipiTypesJson },
                navigationList: ${ navigationListJson },
                languageMap: ${ languageMap },
                sectionList: ko.observableArray([]),
                pushToSectionList: function(sectionList){for(var i=0;i<sectionList.length;i++)this.sectionList.push(sectionList[i]);}
            };
            
            ko.applyBindings( ViewModel );
            $( '.carousel' ).carousel();
            $.ajax({
                type: 'get',
                url: '/api/init?_apiVer=2',
                data: { 
                    'language': "${ language }"
                },
                success: function( response ) {
                	var res = jQuery.parseJSON( response );
					ViewModel.pushToSectionList(res["sections"]);
                },
                error: function( response ) {
                    console.log( response );
                	console.log( typeof(response) );
                }
            });
        });
    </script>
    <script src="https://www.gstatic.com/firebasejs/3.0.4/firebase.js" async defer></script>
    
    
    
  <#-- Testing Tinymce -->
  <script src="https://cdn.tinymce.com/4/tinymce.min.js"></script>
  <script>
  tinymce.init({

    // initialise and auto focus
    selector: '#myeditablediv',
    auto_focus: 'myeditablediv',
    inline: true,

    //plugins needed and setting up toolbar
    plugins : ["image","imagetools","link","paste"],
    menubar: false,
    statusbar: false,
    toolbar: 'bold italic underline | alignleft aligncenter alignright | blockquote link image',
    height: 300,

    // pasting from other sources
    paste_data_images: false,
    paste_remove_styles: true,
    paste_remove_styles_if_webkit: true,
    paste_strip_class_attributes: true,
    paste_text_sticky: true,
    paste_text_sticky_default: true,
    paste_as_text: true,
    paste_auto_cleanup_on_paste : true,

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

    // enforcing rules to editor
    valid_elements : 'p[style],img[src],blockquote,b,i,u,a[href|target=_blank],br,b/strong,i/em',
    extended_valid_elements: 'img[src],p[style],blockquote',
    valid_children : 'body[p|img|blockquote],p[b|i|u|a|br],blockquote[b|i|u]',
    valid_styles: {'p': 'text-align'},

    // image
    image_description: false,
    image_dimensions: false,
    file_browser_callback: function(field_name, url, type, win) {
		if(type=='image') $('#file_name').click(); $('#field_name').val(field_name);
	}
  });
  </script>

  <script>
        function log() {
            console.log(tinyMCE.get('myeditablediv').getContent());
        }
    </script>

    <div id="myeditablediv"></div>
    <button style="margin-top: 50px;" onClick="log()">log</button>

    <form enctype="multipart/form-data" id="form_file" style="width:0px; height:0px; overflow:hidden">
		<input id="file_name" name="file" type="file" />
	</form>
		<input type="hidden" id="field_name" value="" />
    
    
    <script>
    	$('#file_name').change(function(){
			var field_name = $('#field_name').val()
			document.getElementById(field_name).value='';
			var file = this.files[0];
			var name = file.name;
			var size = file.size;
			var type = file.type;
			var field_name = $('#field_name').val()
			var type = type.substring(0, 5);
			if(type=='image') { 
			
			    var formData = new FormData($('#form_file')[0]);
			
			    $.ajax({
			        url: "/api/pratilipi/content/image?pratilipiId=4853358213988352",
			        type: "POST",
			        data: formData,
			        success: function (response) {
			        	var res = jQuery.parseJSON( response );
			            document.getElementById(field_name).value = "/api/pratilipi/content/image?pratilipiId=4853358213988352&name=" + res.name;        
			        },
			        cache: false,
			        contentType: false,
			        processData: false
			    });
			
			} else {
			    alert('Le fichier doit etre une image') 
			}
		});
    </script>
    
    <#include "meta/Font.ftl">
     <style>
        #myeditablediv  img {
            display: block;
            margin: 8px auto;
        }
    </style>
  </body>
</html>

