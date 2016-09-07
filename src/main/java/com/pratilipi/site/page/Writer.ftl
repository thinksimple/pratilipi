<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
	<script src="https://code.jquery.com/jquery-3.1.0.min.js" integrity="sha256-cCueBR6CsyA4/9szpPfrX3s49M9vUU5BgtiJj06wt/s=" crossorigin="anonymous"></script>
	<!-- Latest compiled and minified CSS -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

	<!-- Latest compiled and minified JavaScript -->
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
	<title>Writer Panel</title>
    <style>
        html, body { 
          margin:0; 
          padding:0; 
          height:100%; 
          min-height: 100%;
        }

/*        #header1, #editor
        {
           -webkit-backface-visibility: hidden;
        }*/
        
        .editor-old{
            position: fixed !important;
            top: 0;
            left: 0;
            margin-top: 0px !important;
            width: 100%;
            padding-left: 0px;
            z-index: 1;
            border-bottom: 1px solid #ddd;

        }
        #header1.affix-top {
            /*display:block;*/
            /*visibility: visible !important;*/
        }        
        #header1.affix {
            position: fixed !important;
            top: 0;
            /*left: 9%;*/
            left:0;
            right: 0;
            margin-top: 0px !important;
            /*width: 82%;*/
            width: 100%;
            /*padding-left: 0px;*/
            padding-left: 4%;
            padding-right:4%;
            z-index: 1;
            border-bottom: 1px solid #ddd;
            /*visibility:hidden;*/

        }        

        #editor.affix {
            position: fixed !important;
            top: 47px !important;
            left: 9%;
            margin-top: 0px !important;
            width: 82%;
            padding-left: 0px;
/*[]            z-index: 1;*/
            background: white;
            border-bottom: 1px solid #ddd;
        }   

        #editor.affix div {
            width: auto;
            float: right;
            margin-bottom: 10px;
            margin-top: 10px;
        } 

        #editor.affix-top {
            top: 0px !important;
        }    
        #toc_button,#toc_button:active,#toc_button:focus,#toc_button:visited,#toc_button:hover {
            background: white;
           /* border-color: #ddd !important;*/
           outline: 0 none;

        }
        .open #toc_button {
            border-radius: 4px 4px 0 0;
        }        
        .horizontal-form-input {
            border: none;
            box-shadow: none;
            border-bottom: 1px solid black;
            border-radius: 0px;
            /*color: #a5a0a0;*/
        }
        .horizontal-form-input:hover, .horizontal-form-input:focus {
            box-shadow: none;
        }
        .left-align-content {
            text-align: left;
        }
        form .form-group {
            margin-bottom: 30px;
            margin-top: 15px;
        }
        .pratilipi-red-button {
            background: #FFFFFF;
            border: 1px solid #d0021b;
            box-shadow: 0px 1px 1px 0px rgba(0, 0, 0, 0.50);
            border-radius: 5px;
            font-size: 13px;
            color: #d0021b;
            letter-spacing: 0.3px;
            line-height: 16px;
            text-shadow: 0px 1px 2px #FFFFFF;
            display: inline-block;
            padding: 10px;
            margin-right: 10px;
            magin-top: 5px;
            outline: none;
        } 
        .pratilipi-red-background-button {
            background: #d0021b !important;
            border: 1px solid #d0021b !important;  /* changed*/
            box-shadow: 0px 1px 1px 0px rgba(0, 0, 0, 0.50);
            border-radius: 5px;
            font-size: 13px;
            color: #ffffff;
            letter-spacing: 0.3px;
            line-height: 16px;
            text-shadow: 0px 1px 2px #FFFFFF;
            display: inline-block;
            padding: 10px;
            margin-right: 10px;
            margin-top: 5px;
            outline: none;
        } 
        .go-button {
            margin-top: 20px;
            padding-left: 20px;
            padding-right: 20px;
            font-size: 20px;
            text-shadow: none;
        } 
        .pratilipi-red {
            color: #d0021b;
        }
        .translucent {
            position: absolute;
            top: 0px;
            left: 0px;
            height: 50px;
            background: #999;
            text-align: center;
            opacity: 0.3;
            transition: opacity .13s ease-out;
            width: 100%;
            -webkit-font-smoothing: antialiased;
            padding-top: 5px;
            background: linear-gradient(black, #f9f9f9);
            box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
            z-index: 1;
        }
        /* base CSS element */
        div.lefttip, div.righttip {
            position: relative;
        }
        div.lefttip div {
            display: block;
            font-size: 16px;
            position: absolute;
            top: 12%;
            left: 50px;
            padding: 5px;
            z-index: 100;
            background: #ddd;
            color: black;
            -moz-border-radius: 5px; /* this works only in camino/firefox */
            -webkit-border-radius: 5px; /* this is just for Safari */
            text-shadow: none;
        }
        div.righttip div {
            display: block;
            font-size: 16px;
            position: absolute;
            top: 12%;
            right: 50px;
            padding: 5px;
            z-index: 100;
            background: #ddd;
            color: black;
            -moz-border-radius: 5px; /* this works only in camino/firefox */
            -webkit-border-radius: 5px; /* this is just for Safari */
            text-shadow: none;
        }        
        div.lefttip div:before{
            content:'';
            display:block;
            width:0;
            height:0;
            position:absolute;

            border-top: 8px solid transparent;
            border-bottom: 8px solid transparent;
            border-right:8px solid #ddd;
            left:-8px;

            top:20%;
        }
        div.righttip div:before{
            content:'';
            display:block;
            width:0;
            height:0;
            position:absolute;

            border-top: 8px solid transparent;
            border-bottom: 8px solid transparent;
            border-left:8px solid #ddd;
            right:-8px;

            top:20%;
        }
        .progress-bar:before{
            content:'';
            display:block;
            width:0;
            height:0;
            position:absolute;

            border-top: 8px solid transparent;
            border-bottom: 8px solid transparent;
            border-left:8px solid #ddd;
            right:-8px;

            top:20%;
        }    
        .book-name {
            text-align: center;
        }    
        @media only screen and (max-width: 992px) {
            .book-name {
                text-align: left;
            }
        }
        @media only screen and (max-width: 598px) {
            .small-screen-hidden {
                display: none;
            }
            #editor.affix div  {
                width: 100%;
                padding: 7px 0 !important;
            }  
            #toc_button {
                padding-left: 0;
                padding-right: 0;
            }
            .editor-action {
                width: 9% !important;
            }
        }
        @media only screen and (min-width: 599px) {
            .big-screen-hidden {
                display: none;
            }
        }  
        .popover-content {
            min-width: 100px;
        }                      

        /* arrows - :before and :after */
/*        .tip:before {
            position: absolute;
            display: inline-block;
            border-top: 7px solid transparent;
            border-right: 7px solid #eee;
            border-bottom: 7px solid transparent;
            border-right-color: rgba(0, 0, 0, 0.2);
            left: -14px;
            top: 20px;
            content: '';
        }

        .tip:after {
            position: absolute;
            display: inline-block;
            border-top: 6px solid transparent;
            border-right: 6px solid #eee;
            border-bottom: 6px solid transparent;
            left: -12px;
            top: 21px;
            content: '';
        } */  
        .right23 {
            right: -23% !important
        }     
        .left25 {
            left: -25% !important;
        }        
    </style>
</head>
<body>
   

   
    
    <div class="container" style="height:100%;">
    <div class="header-wrapper">
        <div id="header1" style="background: white;padding-bottom: 10px;">
            <div class="dropdown" id="toc_container" style="display: inline;">
              <div class="dropdown-toggle" id="table_of_contents_dropdown" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true" style="display:inline;">  
                  <button class="btn btn-default" id="toc_button" style="margin-top: 4px;">
                      <img style="width: 28px;height: 20px;" src="http://0.ptlp.co/resource-all/icon/svg/list2.svg">
                      <h4 style="margin-top: 10px;margin-bottom: 5px;display:inline;">Book Name</h4>
                      <!-- Book Name               -->
                      <!-- <button style="border:none;background:white;"> -->
                        <!-- <img style="width: 48px;height: 20px;" src="http://0.ptlp.co/resource-all/icon/svg/chevron-right.svg" alt=""> -->
                      <!-- </button> -->
                      
                      <!-- <span class="glyphicon glyphicon-chevron-down" aria-hidden="true"></span> -->
                      <img style="width: 28px;height: 20px;" src="http://0.ptlp.co/resource-all/icon/svg/angle-down.svg" alt="">
                  </button>
              </div> 
              <ul class="dropdown-menu" aria-labelledby="table_of_contents_dropdown" style="margin-top:13px;border-radius: 0 0 4px 4px;">
                <li class="text-center"><strong>Table of Contents</strong></li>
                <li role="separator" class="divider"></li>
              </ul>
            </div>
          <!-- <div class="pull-left"></div>   -->
          <div class="pull-right">
              <button class="pratilipi-red-background-button" style="padding: 6px 12px;margin-right:0;">Publish</button>
              <button class="btn btn-default small-screen-hidden" style="margin: 0 5px 0 5px; font-size: 13px;">Save</button>
              <button class="btn btn-default small-screen-hidden" style="margin: 0 0px 0 5px; font-size: 13px;">Preview</button>       
               <img style="width: 25px;height: 25px;margin-right:-12px;" src="http://0.ptlp.co/resource-all/icon/svg/dots-three-vertical.svg" class="big-screen-hidden" data-container="body" data-toggle="popover" data-content="
      <a href='#' class='pratilipi-red'>
        Preview
      </a><hr style='margin: 1px 0;'><a href='#' class='pratilipi-red'>
        Save
      </a>" data-html="true" data-placement="bottom" >      
          </div>                
        </div>
    </div>     
        <!-- panel -->
        <div class="panel panel-default" style="margin-bottom: 5px;min-height: 100%;margin-top:12px;"> 
            <div class="panel-body" style="text-align: center;padding: 0 0 0 5px;">
            
                <form style="    width: 90%;margin-left: auto;margin-right: auto;">
                <div class="editor-wrapper">
                  <div class="form-group" style="position: relative;">
                    <input  class="form-control horizontal-form-input" id="subtitle" style="padding-bottom: 25px;padding-top: 20px;font-size: 25px;border-bottom: 1px solid #ddd;" placeholder="Add Chapter Name">
                    <!-- editing panel -->
                    <div id="editor" style="position: absolute;right: 1%;top: 5%;">
                        <div style="background-color: #ddd;padding: 7px;">
          
                            <a href="#"><img class="editor-action" src='http://0.ptlp.co/resource-all/icon/svg/bold.svg' style='width:9%;height:20px;'></a>
                            <a href="#"><img class="editor-action" src='http://0.ptlp.co/resource-all/icon/svg/italic.svg' style='width:9%;height:20px;'></a>
                            <a href="#"><img class="editor-action" src='http://0.ptlp.co/resource-all/icon/svg/underline.svg' style='width:9%;height:20px;'></a>
                            |
                            <a href="#"><img class="editor-action" src='http://0.ptlp.co/resource-all/icon/svg/paragraph-left.svg' style='width:9%;height:20px;'></a>
                            <a href="#"><img class="editor-action" src='http://0.ptlp.co/resource-all/icon/svg/paragraph-center.svg' style='width:9%;height:20px;'></a>
                            <a href="#"><img class="editor-action" src='http://0.ptlp.co/resource-all/icon/svg/paragraph-right.svg' style='width:9%;height:20px;'></a>
                            |
                            <a href="#"><img class="editor-action" src='http://0.ptlp.co/resource-all/icon/svg/quotes-left.svg' style='width:9%;height:20px;'></a>
                            <a href="#"><img class="editor-action" src='http://0.ptlp.co/resource-all/icon/svg/attachment.svg' style='width:9%;height:20px;'></a>
                            <a href="#"><img class="editor-action" src='http://0.ptlp.co/resource-all/icon/svg/camera2.svg' style='width:9%;height:20px;'></a>
                        </div>
                    </div>
                  </div>
                </div>  
                  <textarea id="chapter-content" style="border: none; margin-bottom:10px; margin-top:10px;" class="form-control horizontal-form-input" placeholder="Tell your story here.."></textarea>  

               
                </form>
                <div class="clearfix"></div>
                       
            </div>            
        </div>
    </div>
        <div style="padding-top:10px;position: fixed; bottom: 0;right: 0;left: 0;background: whitesmoke;">         <!-- writer footer -->    
            <div class="col-lg-4 col-lg-offset-4 col-xs-6 col-xs-offset-3 text-center">
                    <a href="#"><img src="http://0.ptlp.co/resource-all/icon/svg/chevron-left.svg"></a>
                    <p style="display: inline;font-size: 20px;padding: 10px;"><input class="horizontal-form-input" type="text" value="12" style="width: 25px;">/25</p>   
                    <a href="#"><img src="http://0.ptlp.co/resource-all/icon/svg/chevron-right.svg"></a>
                <div class="clearfix"></div>
            </div>
            <!-- progress bar -->
            <div class="progress" style="height:2px;position: fixed;left: 0;right: 0;bottom:3%;">
              <div class="progress-bar progress-bar-danger" role="progressbar" aria-valuenow="80" aria-valuemin="0" aria-valuemax="100" style="width: 80%">
                <span class="sr-only">80% Complete (danger)</span>
              </div>
            </div>
            <img src='http://0.ptlp.co/resource-all/icon/svg/circle-red.svg' style='left: 80%;bottom:5%;width:15px;height:15px; position: fixed;'>  
            <!-- end of progress bar                             --> 
        </div>               
       
</body>

<script type="text/javascript">


    $(document).ready(function() {  
      var mainWriterPanelObject = new MainWriterPanel();
      mainWriterPanelObject.init();
    });

    var Chapter = function (ch_object) {
        this.name = ch_object.name;
        this.$ListDomElement = null;
    }

    Chapter.prototype.getListDomElement = function () {
        if ( this.$ListDomElement ) {
            return this.$ListDomElement;
        }

        this.$ListDomElement = $("<li>");
        var $name = $("<a>", {
            href: "#"
        }).text(this.name);

        var $delete = $("<img>", {
            "class": "pull-right",
             src: "http://0.ptlp.co/resource-all/icon/svg/trash.svg"
        }).data("relatedObject", this).css({width: "20px", height: "20px"});

        $name.append( $delete );
        this.$ListDomElement.append($name)
        console.log(this.$ListDomElement);
        return this.$ListDomElement;        

    } 

    var TableOfContents = function(toc_container) {
        this.toc_container = toc_container;
        this.$dropdown_menu_list = toc_container.find(".dropdown-menu");
        this.chapters = [];
        console.log(this.chapters);

    }

    TableOfContents.prototype.init = function () {
        this.setListWidth();
        this.populateChapters();
        this.addNewChapterButton();
    }

    TableOfContents.prototype.setListWidth = function() {
        // setting height of dropdown ul equal to height of the trigger div
        this.$dropdown_menu_list.width( this.toc_container.find("#table_of_contents_dropdown").width() - 2);
    };

    TableOfContents.prototype.populateChapters = function() {
        var _this = this;
        console.log(this);
        this.ajaxChaptersList = [{ name: "Untitled 1"},{ name: "Untitled 2"},{ name: "Untitled 3"},{ name: "Untitled 1"},{ name: "Untitled 2"},{ name: "Untitled 3"},{ name: "Untitled 1"},{ name: "Untitled 2"},{ name: "Untitled 3"},{ name: "Untitled 1"},{ name: "Untitled 2"},{ name: "Untitled 3"}    ];
        $.each(this.ajaxChaptersList, function(index, chapter) {
            var ch = new Chapter( chapter );
            _this.chapters.push(ch);
            _this.$dropdown_menu_list.append( ch.getListDomElement() );
        });
    };

    TableOfContents.prototype.addNewChapterButton = function() {
        var $divider = $("<li>", {
            role: "separator",
            "class": "divider"
        });

        var $boldNewChapter = $("<strong>").text(" + New Chapter");
        var $aNewChapter = $("<a>", {
            href: "#"
        }).append( $boldNewChapter );
        var $liNewChapter = $("<li>", {
            "class": "text-center"
        }).append( $aNewChapter );

        this.$dropdown_menu_list.append( $divider ).append( $liNewChapter );
    };

    var MainWriterPanel = function() {
    };


    MainWriterPanel.prototype.init = function() {
        this.addAffixClasses();
        this.setWrappersHeight();

        var toc_container = $("#toc_container");
        this.table_of_contents_object = new TableOfContents(toc_container);
        this.table_of_contents_object.init();
    };

    MainWriterPanel.prototype.addAffixClasses = function() {
        $('#editor').affix({
            offset: {
                top: 50,
                // bottom: 0
            }
        });
        $('#header1').affix({
            offset: {
                top: 50,
                // bottom: 0
            }
        });         
    };

    MainWriterPanel.prototype.setWrappersHeight = function() {
        $(".editor-wrapper").height($("#editor").height());
        $(".header-wrapper").height($("#header1").height());
         $('[data-toggle="popover"]').popover();
    };

    function h(e) {
      $(e).css({'height':'auto','overflow-y':'hidden'}).height(e.scrollHeight);
    }
    $('textarea').each(function () {
      h(this);
    }).on('input', function () {
      h(this);
    }); 
</script>
</html>