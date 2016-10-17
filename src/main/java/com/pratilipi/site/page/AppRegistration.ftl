<!DOCTYPE html>
<html>
<head>  
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
    <meta property="og:image" content="http://0.ptlp.co/resource-hi/logo/pratilipi_logo.png"/> 
    <script src="https://code.jquery.com/jquery-3.1.0.min.js" integrity="sha256-cCueBR6CsyA4/9szpPfrX3s49M9vUU5BgtiJj06wt/s=" crossorigin="anonymous"></script>
    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

    <!-- Latest compiled and minified JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
    <title>${ title }</title>
    <script>
    	var isMobile;
    	if( /Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent) ) {
		 	console.log("mobile");
		 	isMobile = true;
		}
		else {
			isMobile = false;
			console.log("not mobile");
		}
		console.log( isMobile );
    </script>
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
          color: #222;
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
          color: #333;
          position: relative;
          right: auto !important;
          left: auto !important;
        }
        .carousel-caption h3 {
            margin-top: 5px;
            margin-bottom: 0px;
        }
        .carousel-control .glyphicon-chevron-left, .carousel-control .glyphicon-chevron-right, .carousel-control .icon-next, .carousel-control .icon-prev  {
          width: 40px !important;
          height: 48px !important;
          top: 50%;
        }   
        .carousel-control .glyphicon-chevron-left, .carousel-control .icon-prev {
            left: 0%;   
        }  
        .carousel-control .glyphicon-chevron-right, .carousel-control .icon-next {
            right: 0%; 
        }
        .carousel-inner>.item>a>img, .carousel-inner>.item>img {
            box-shadow:  0 0 10px  rgba(0,0,0,0.9);
            -moz-box-shadow: 0 0 10px  rgba(0,0,0,0.9);
            -webkit-box-shadow: 0 0 10px  rgba(0,0,0,0.9);
            -o-box-shadow: 0 0 10px  rgba(0,0,0,0.9);
        }
        .caption-line-2 {
          margin-top: 10px;
          text-shadow: none;
          font-size: 16px;
        }
        .subcaption {
          padding-bottom: 0;
        }
        .glyph-red-background {
            background-color: #d0021b;
            border-radius: 50%;
            padding: 7px 7px;
        }
    </style>
</head>
<body>
    <div class="alert alert-danger alert-dismissible" data-behaviour="email-alert" role="alert" style="margin-bottom: 0;display:none;">
      <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
      <strong>${ _strings.android_email_incorrect }</strong> 
    </div>
    <div class="alert alert-danger alert-dismissible" data-behaviour="mobile-alert" role="alert" style="margin-bottom: 0;display:none;">
      <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
      <strong>${ _strings.android_mobile_incorrect }</strong>
    </div>
    <div class="alert alert-danger alert-dismissible" data-behaviour="server-error-alert" role="alert" style="margin-bottom: 0;display:none;">
      <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
      <strong>${ _strings.server_error_message }</strong>
    </div>    
    <div class="container">
        <a style="cursor: pointer;margin-top:10px;  " class="pull-left">
            <img style="width: 50px;height: 50px;" src="http://0.ptlp.co/resource-hi/logo/pratilipi_logo.png">
        </a>
        
        <center>
            <h3> ${ _strings.android_banner_5 } </h3>
            <form data-behaviour="app-registration" style="width: 80%; margin: 0 auto;">
                <div class="form-group has-feedback" style="margin-top:0px;">
                  <input  class="form-control horizontal-form-input" id="mobile_no" placeholder="${ _strings.user_phone }">
                </div>   
                <h5 style="margin: 0;">OR</h5>         
                <div class="form-group has-feedback" style="margin-top: 0px;">
                  <input  class="form-control horizontal-form-input" id="email" placeholder="${ _strings.user_email }">
                </div>                  
                <button type="submit" class="pratilipi-red-background-button go-button">${ _strings.android_submit }</button>
            </form>
            <div data-behaviour="invite_friends" class="well" style="width: 90%; margin: 0 auto;background-color: white;margin-top:20px;">
                <span class="glyph-red-background badge">
                    <img style="width:20px;height:20px;" src="http://0.ptlp.co/resource-all/icon/svg/user-check-white-red.svg">
                </span><br>
                ${ _strings.android_success_feedback }
                <h3> ${ _strings.android_invite_friends }! </h3>
                <a href="http://www.facebook.com/sharer.php?u=http://hindi.pratilipi.com/android-registration" target="_blank"><img style="width:40px;height:40px;" class="img-circle" src="http://0.ptlp.co/resource-all/icon/footer/facebook.png"></a>
                <a style="margin-left: 7px;" data-behaviour="share_whatsapp" href="whatsapp://send?text=Pratilipi+Android+app+is+coming.+Download+unlimited+books+and+Read+without+internet%0Ahttp%3A%2F%2Fhindi.gamma.pratilipi.com%2Fandroid-registration"><img style="width:40px;height:40px;" class="img-circle" src="http://0.ptlp.co/resource-all/home-page/WhatsAppLogo.png"></a>            
            </div>
        </center>
        <div id="app-image-carousel" class="carousel slide" data-ride="carousel" style="width: 280px;margin: 0 auto;margin-top: 10px;height: 410px;">
        <!-- Indicators -->
            <ol class="carousel-indicators">
                <li data-target="#app-image-carousel" data-slide-to="0" class="active"></li>
                <li data-target="#app-image-carousel" data-slide-to="1"></li>
                <li data-target="#app-image-carousel" data-slide-to="2"></li>
                <li data-target="#app-image-carousel" data-slide-to="3"></li>
                <li data-target="#app-image-carousel" data-slide-to="4"></li>                
            </ol>

            <!-- Wrapper for slides -->
            <div class="carousel-inner" role="listbox" style="width: 100%;margin: 0 auto;">
                <div class="item active">
                    <div class="carousel-caption" style="bottom: 0;padding-top:0;padding-bottom: 5px;">
                        <h4>${ _strings.android_banner_home_1 }</h4>
                    </div>
                    <img src="http://0.ptlp.co/resource-hi/android-app-launch/android-registration-${lang}-1.png" alt="..." style="height:360px;margin: 0 auto;">
                    <div class="carousel-caption subcaption">
                        <p class="caption-line-2">${ _strings.android_banner_home_3 }</p>
                    </div>
                </div>
                <div class="item">
                    <div class="carousel-caption" style="bottom: 0;padding-top:0;padding-bottom: 5px;">
                        <h4>${ _strings.android_banner_library_1 }</h4>
                    </div>
                    <img src="http://0.ptlp.co/resource-hi/android-app-launch/android-registration-${lang}-2.png" alt="..." style="height:360px;margin: 0 auto;">
                    <div class="carousel-caption subcaption">
                        <p class="caption-line-2">${ _strings.android_banner_library_2 }</p>
                    </div>
                </div>
                <div class="item">
                    <div class="carousel-caption" style="bottom: 0;padding-top:0;padding-bottom: 5px;">
                        <h4>${ _strings.android_banner_reader_1 }</h4>
                    </div>
                    <img src="http://0.ptlp.co/resource-hi/android-app-launch/android-registration-${lang}-4.png" alt="..." style="height:360px;margin: 0 auto;">
                    <div class="carousel-caption subcaption">
                        <p class="caption-line-2">${ _strings.android_banner_reader_3 }</p>
                    </div>
                </div>                
                <div class="item">
                    <div class="carousel-caption" style="bottom: 0;padding-top:0;padding-bottom: 5px;">
                        <h4>${ _strings.android_banner_notification_1 }</h4>
                    </div>
                    <img src="http://0.ptlp.co/resource-hi/android-app-launch/android-registration-${lang}-3.png" alt="..." style="height:360px;margin: 0 auto;">
                    <div class="carousel-caption subcaption">
                        <p class="caption-line-2">${ _strings.android_banner_notification_2 }</p>
                    </div>
                </div>
                <div class="item">
                    <div class="carousel-caption" style="bottom: 0;padding-top:0;padding-bottom: 5px;">
                        <h4>${ _strings.android_banner_profile_1 }</h4>
                    </div>
                    <img src="http://0.ptlp.co/resource-hi/android-app-launch/android-registration-${lang}-5.png" alt="..." style="height:360px;margin: 0 auto;">
                    <div class="carousel-caption subcaption">
                        <p class="caption-line-2">${ _strings.android_banner_profile_3 }</p>
                    </div>
                </div>                                
            </div>
            <!-- Controls -->
            <a class="left carousel-control" href="#app-image-carousel" role="button" data-slide="prev">
                <span class="glyphicon glyphicon-chevron-left glyphicon-menu-left" aria-hidden="true"></span>
                <span class="sr-only">Previous</span>
            </a>
            <a class="right carousel-control" href="#app-image-carousel" role="button" data-slide="next">
                <span class="glyphicon glyphicon-chevron-right glyphicon-menu-right" aria-hidden="true"></span>
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
      this.$invite_block = $('[data-behaviour="invite_friends"]');
      this.$email = this.$form.find("#email");
      this.$mobile_no = this.$form.find("#mobile_no");
      this.$form_groups = this.$form.find(".form-group");
      this.form_validated = true;
      this.email_regex = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
      this.mobile_no_regex = /^\d{10}$/;
      this.$email_alert = $('[data-behaviour="email-alert"]');
      this.$mobile_alert = $('[data-behaviour="mobile-alert"]');
      this.$server_error_alert = $('[data-behaviour="server-error-alert"]');
    };

    AppRegistration.prototype.init = function() {
      this.$invite_block.hide();
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
      var _this = this;
      this.resetErrorStates();
      var email_val = this.$email.val();
      var mobile_no_val = this.$mobile_no.val();
      if( this.isEmptyStr( email_val ) && this.isEmptyStr( mobile_no_val ) ) {
        this.$form_groups.addClass("has-error").append('<span class="error-exclamation glyphicon glyphicon-exclamation-sign form-control-feedback" aria-hidden="true"></span>');
        this.form_validated = false;
      }
      else {
        if( !this.isEmptyStr( email_val ) && this.isNotValidEmail( email_val ) ) {
            this.form_validated = false;
            this.$email_alert.show();
            setTimeout(function(d){
              _this.$email_alert.hide('slow');
            }, 2000);            
        }
        if ( !this.isEmptyStr( mobile_no_val ) && this.isNotValidMobile( mobile_no_val ) ) {
            this.form_validated = false;
            this.$mobile_alert.show();
            setTimeout(function(d){
              _this.$mobile_alert.hide('slow');
            }, 2000);             
        }
      }
      
      if( this.form_validated ) {
        _this.ajaxSubmitForm();
      }
      
    }; 

    AppRegistration.prototype.ajaxSubmitForm = function() {
      var _this = this;

      var ajax_data = {
            email: this.$email.val() ,
            phone: this.$mobile_no.val(),
            language: "${ language }",
            mailingList: "LAUNCH_ANNOUNCEMENT_ANDROID_APP",                
             };
        $.ajax({type: "POST",
            url: "/api/mailinglist/subscribe",
            dataType: "jsonp",
            jsonpCallback: "onJSONPLoad",
            data: ajax_data,
            success:function(response){
              
              var parsed_data = jQuery.parseJSON( response );
              _this.showInviteBlock();
        },
            error:function(xhr, status, text){
				if (xhr.status == 400) {
				  _this.showInviteBlock();
				} else {
                  _this.$server_error_alert.show();
                  setTimeout(function(d){
                    _this.$server_error_alert.hide('slow');
                  }, 2000); 					
				}
     
        }             
        
      }); 
      
    };  
    
    AppRegistration.prototype.showInviteBlock = function() {
	      if( !isMobile ) {
	      	 this.$invite_block.find('[data-behaviour="share_whatsapp"]').css("display","none");
	      }
	      this.$form.hide();
	      this.$invite_block.show();        	
    };

    AppRegistration.prototype.isNotValidEmail = function( email ) {
      return !this.email_regex.test( email );
    };

    AppRegistration.prototype.isNotValidMobile = function( mob_no ) {
      return !this.mobile_no_regex.test( mob_no );
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