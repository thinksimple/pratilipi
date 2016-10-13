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
    <title>${ title }</title>
    <style>
        .horizontal-form-input {
            border: none;
            box-shadow: none !important;
            border-bottom: 1px solid black;
            border-radius: 0px;
            /*color: #a5a0a0;*/
        }
        .form-control:focus {
            border-color: #d0021b;
        }
        .horizontal-form-input:hover, .horizontal-form-input:focus {
            box-shadow: none;
        }

        .pratilipi-red-background-button {
            background: #d0021b;
            border: 1px solid #d0021b;
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
            magin-top: 5px;
            outline: none;
        } 
        .go-button {
            margin-top: 5px;
            padding: 5px 10px;
            font-size: 16px;
            text-shadow: none;
        }     
        .pratilipi-red {
          color:#d0021b;
        }   
        form .form-group {
            margin-bottom: 15px;
            margin-top: 5px;
        }
        .carousel-inner img {
          margin: 0 auto;
        }
        .carousel-control.right, .carousel-control.left {
          background-image: none;
        }
        .carousel-control {
          color:black;
          font-size: 40px !important;
        }
        .carousel-control:focus, .carousel-control:hover {
          color: black;  
        }    
        .carousel-indicators .active {
          background-color: #d0021b;
        } 
        .carousel-indicators li {
          border-color: #d0021b;
        }
        .carousel-caption {
          color: black;
          position: relative;
          right: auto !important;
          left: auto !important;
        }
        .carousel-caption h3 {
            margin-top: 5px;
            margin-bottom: 0px;
        }
        .carousel-control .glyphicon-chevron-left, .carousel-control .glyphicon-chevron-right, .carousel-control .icon-next, .carousel-control .icon-prev  {
          width: 48px !important;
          height: 48px !important;
        }   
        .carousel-control .glyphicon-chevron-left, .carousel-control .icon-prev {
            left: 0%;   
        }  
        .carousel-control .glyphicon-chevron-right, .carousel-control .icon-next {
            right: 0%; 
        }
    </style>
</head>
<body>
    <div class="container">
        <a style="cursor: pointer;margin-top:10px;  " class="pull-left">
            <img style="width: 50px;height: 50px;" src="http://0.ptlp.co/resource-hi/logo/pratilipi_logo.png">
        </a>
        
        <center>
            <h3> Register for App </h3>
            <form data-behaviour="app-registration" style="width: 80%; margin: 0 auto;">
                <div class="form-group has-feedback" style="margin-top:0px;">
                  <input  class="form-control horizontal-form-input" id="mobile_no" placeholder="Phone No">
                </div>   
                <h5 style="margin: 0;">OR</h5>         
                <div class="form-group has-feedback" style="margin-top: 0px;">
                  <input  class="form-control horizontal-form-input" id="email" placeholder="Email">
                </div>                  
                <button type="submit" class="pratilipi-red-background-button go-button">Submit</button>
            </form>

        </center>
        <div id="app-image-carousel" class="carousel slide" data-ride="carousel" style="width: 280px;margin: 0 auto;margin-top: 20px;height: 360px;">
        <!-- Indicators -->
            <ol class="carousel-indicators">
                <li data-target="#app-image-carousel" data-slide-to="0" class="active"></li>
                <li data-target="#app-image-carousel" data-slide-to="1"></li>
                <li data-target="#app-image-carousel" data-slide-to="2"></li>
                <li data-target="#app-image-carousel" data-slide-to="3"></li>
                <li data-target="#app-image-carousel" data-slide-to="4"></li>                
            </ol>

            <!-- Wrapper for slides -->
            <div class="carousel-inner" role="listbox" style="width: 210px;margin: 0 auto;">
                <div class="item active">
                    <img src="https://storage.googleapis.com/devo-pratilipi.appspot.com/app-screenshot/1.png" alt="..." style="height:360px;margin: 0 auto;">
                    <div class="carousel-caption">
                        <h3>Caption 1</h3>
                    </div>
                </div>
                <div class="item">
                    <img src="https://storage.googleapis.com/devo-pratilipi.appspot.com/app-screenshot/2.png" alt="..." style="height:360px;margin: 0 auto;">
                    <div class="carousel-caption">
                        <h3>Caption 1</h3>
                    </div>
                </div>
                <div class="item">
                    <img src="https://storage.googleapis.com/devo-pratilipi.appspot.com/app-screenshot/3.png" alt="..." style="height:360px;margin: 0 auto;">
                    <div class="carousel-caption">
                        <h3>Caption 1</h3>
                    </div>
                </div>
                <div class="item">
                    <img src="https://storage.googleapis.com/devo-pratilipi.appspot.com/app-screenshot/4.png" alt="..." style="height:360px;margin: 0 auto;">
                    <div class="carousel-caption">
                        <h3>Caption 1</h3>
                    </div>
                </div>
                <div class="item">
                    <img src="https://storage.googleapis.com/devo-pratilipi.appspot.com/app-screenshot/5.png" alt="..." style="height:360px;margin: 0 auto;">
                    <div class="carousel-caption">
                        <h3>Caption 1</h3>
                    </div>
                </div>                
            </div>

            <!-- Controls -->
            <a class="left carousel-control" href="#app-image-carousel" role="button" data-slide="prev">
                <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
                <span class="sr-only">Previous</span>
            </a>
            <a class="right carousel-control" href="#app-image-carousel" role="button" data-slide="next">
                <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
                <span class="sr-only">Next</span>
            </a>
        </div> 
    </div>                              
</body>
<script>
    $(document).ready(function() {  
      var $form = $('form[data-behaviour="app-registration"]');
      var appRegistrationObject = new AppRegistration( $form );
      appRegistrationObject.init();
    });
    var AppRegistration = function ( form ) {
      this.$form = form;
      this.$email = this.$form.find("#email");
      this.$mobile_no = this.$form.find("#mobile_no");
      this.$form_groups = this.$form.find(".form-group");
      this.form_validated = true;
    }

    AppRegistration.prototype.init = function() {
      this.attachFormSubmitListener();
    }; 

    AppRegistration.prototype.attachFormSubmitListener = function() {
      var _this = this;
      this.$form.on( "submit", function(e) {
          e.preventDefault();
          _this.validateForm();
      } );
    }; 
    AppRegistration.prototype.validateForm = function() {
      this.resetErrorStates();
      if( this.isEmptyStr( this.$email.val() ) && this.isEmptyStr( this.$mobile_no.val() ) ) {
        this.$form_groups.addClass("has-error").append('<span class="error-exclamation glyphicon glyphicon-exclamation-sign form-control-feedback" aria-hidden="true"></span>');
        this.form_validated = false;
      }
      
      if( this.form_validated ) {
        // this.ajaxSubmitForm();
      }
      
    }; 

    AppRegistration.prototype.ajaxSubmitForm = function() {
      var _this = this;
      // var $spinner_div = $("<div>").addClass("spinner");
      // this.$panel_container.append($spinner_div);
      var ajax_data = {
            email: this.$email.val() ,
            phone: this.$mobile_no.val(),
            language: "${ language }",
            mailingList: "LAUNCH_ANNOUNCEMENT_ANDROID_APP",                
             };
      console.log( ajax_data );
        $.ajax({type: "POST",
            url: "/api/mailinglist/subscribe",
            data: ajax_data,
            success:function(response){
              console.log(response);
              console.log(typeof response);
              
              var parsed_data = jQuery.parseJSON( response );
              console.log(parsed_data);
            window.location = window.location.origin + window.location.pathname + "?action=write&id=" + parsed_data.pratilipiId;
        },
            fail:function(response){
              var message = jQuery.parseJSON( response.responseText );
              alert(message);
        }             
        
      }); 
      
    };    

    AppRegistration.prototype.resetErrorStates = function() {
      this.form_validated = true;
      this.$form_groups.removeClass("has-error");
      this.$form.find(".error-exclamation").remove();
      
    };
    AppRegistration.prototype.isEmptyStr = function(str) {
      return ( str.length === 0 || !str.trim() );
    };             
</script>
</html>
