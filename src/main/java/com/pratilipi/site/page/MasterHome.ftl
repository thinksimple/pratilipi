<!DOCTYPE html>
<html lang="en">

<head>
	<meta charset="utf-8">
	<link rel="shortcut icon" type="image/png" href="/favicon.png">
	<title>Read Hindi, Gujarati and Tamil Stories, Poems and Books</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<meta name="description" content="A platform to discover, read and share your favorite stories, poems and books in a language, device and format of your choice.">
	
	<script src='http://j.ptlp.co/third-party/jquery-2.1.4/jquery-2.1.4.min.js'></script>
	<script defer src='http://b.ptlp.co/third-party/bootstrap-3.3.4/js/bootstrap.min.js'></script>
	<link rel='stylesheet' href='http://f.ptlp.co/third-party/font-awesome-4.3.0/css/font-awesome.min.css'>
	<link rel='stylesheet' href='http://b.ptlp.co/third-party/bootstrap-3.3.4/css/bootstrap.min.css'>
	
	<link rel="stylesheet" type="text/css" href="/resources/style-home.css?201607">

	<#include "meta/GoogleAnalytics.ftl">

	<script>
		jQuery.ajax( {
		  url: 'http://0.ptlp.co/resource-all/elements.vulcanized.html',
		  cache: true,
		} );
	</script>
	
	<#-- Amplitude Tracking Code for http://www.pratilipi.com -->
	<script type="text/javascript">
	  (function(e,t){var n=e.amplitude||{_q:[],_iq:{}};var r=t.createElement("script");r.type="text/javascript";
	  r.async=true;r.src="https://d24n15hnbwhuhn.cloudfront.net/libs/amplitude-3.0.1-min.gz.js";
	  r.onload=function(){e.amplitude.runQueuedFunctions()};var i=t.getElementsByTagName("script")[0];
	  i.parentNode.insertBefore(r,i);function s(e,t){e.prototype[t]=function(){this._q.push([t].concat(Array.prototype.slice.call(arguments,0)));
	  return this}}var o=function(){this._q=[];return this};var a=["add","append","clearAll","prepend","set","setOnce","unset"];
	  for(var u=0;u<a.length;u++){s(o,a[u])}n.Identify=o;var c=function(){this._q=[];return this;
	  };var p=["setProductId","setQuantity","setPrice","setRevenueType","setEventProperties"];
	  for(var l=0;l<p.length;l++){s(c,p[l])}n.Revenue=c;var d=["init","logEvent","logRevenue","setUserId","setUserProperties","setOptOut","setVersionName","setDomain","setDeviceId","setGlobalUserProperties","identify","clearUserProperties","setGroup","logRevenueV2","regenerateDeviceId"];
	  function v(e){function t(t){e[t]=function(){e._q.push([t].concat(Array.prototype.slice.call(arguments,0)));
	  }}for(var n=0;n<d.length;n++){t(d[n])}}v(n);n.getInstance=function(e){e=(!e||e.length===0?"$default_instance":e).toLowerCase();
	  if(!n._iq.hasOwnProperty(e)){n._iq[e]={_q:[]};v(n._iq[e])}return n._iq[e]};e.amplitude=n;
	  })(window,document);
	
	  amplitude.getInstance().init("db959c1578a66b007dbf8b995b57da14");
	</script>
		

	<script>
		window.fbAsyncInit = function() {
			FB.init({
				appId      : '293990794105516',
				cookie     : true,
				xfbml      : true,
				version    : 'v2.0' 
			});
		};
		(function(d, s, id) {
			var js, fjs = d.getElementsByTagName(s)[0];
			if (d.getElementById(id)) return;
			js = d.createElement(s); js.id = id;
			js.src = "//connect.facebook.net/en_US/sdk.js";
			fjs.parentNode.insertBefore(js, fjs);
		}(document, 'script', 'facebook-jssdk'));
	</script>

	<script>
		$( document ).ready(function() {
			<#-- Setting tiles min-height -->
			jQuery( '.wrapper' ).css( "min-height", jQuery( window ).height() + "px" );
			<#-- Setting banner height -->
			if( $( window ).width() < 768 ) {
				jQuery( '.pratilipi-banner' ).height( jQuery( window ).height() );
			} else {
				var diff = ( jQuery( window ).height() - jQuery( '#tiles-container' ).height() ) / 2;
				jQuery( '.pratilipi-banner' ).height( jQuery( window ).height() - 20 - diff - 108 );
				jQuery( '.pratilipi-banner' ).css( "max-height", Math.max( jQuery( window ).height(), jQuery( '.content-wrapper' ).height() + 108 ) + "px" );
			}
			
		});
		function validateEmail( email ) {
			var re = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
			return re.test(email);
		}
		function startReading() {
			jQuery('html, body').animate( { scrollTop: jQuery( '#wrapper' ).offset().top }, 500 );
		}
		function addSubscription() {
			document.getElementById( "notify-elements" ).style.display = "block";
			document.getElementById( "notify-message" ).style.display = "none";
		}
		function mailingList( e, input ) {
			if( e != null ) {
				var code = ( e.keyCode ? e.keyCode : e.which );
				if( code != 13 )
					return;
			}
			var mailingList = document.getElementById( 'mailingListLanguage' ).value;
			var email = document.getElementById( 'mailingListEmail' ).value;
			var passed = true;
			
			if( mailingList == "none" ) {
				jQuery( '#mailingListLanguage' ).css( { "border": '#FF0000 1px solid' } );
				passed = false;
			} else {
				jQuery( '#mailingListLanguage' ).css( { "border": 'none' } );
			}
			
			if( email.trim() == "" || !validateEmail( email ) ) {
				jQuery( '#mailingListEmail' ).css( { "border": '#FF0000 1px solid' } );
				passed = false;
			} else {
				jQuery( '#mailingListEmail' ).css( { "border": 'none' } );
			}
			
			if( !passed )
				return;

			<#-- Spinner active -->
			jQuery( '#notify-me-wrapper' ).height( jQuery( '#notify-me-wrapper' ).height() );
			document.getElementById( "notify-elements" ).style.display = "none";
			document.getElementById( 'notify-loader' ).style.display = "block";
		
			$.ajax({
				type: 'post',
				url: '/api/mailinglist/subscribe',
				data: { 
					'email': email, 
					'mailingList': mailingList
				},
				success: function( response ) {
					document.getElementById( 'notify-loader' ).style.display = "none";
					document.getElementById( 'mailingListLanguage' ).value = "none";
					document.getElementById( 'mailingListEmail' ).value = null;
					document.getElementById( "notify-message-text" ).innerText = "Thank You! You will be notified when we launch the language!";
					document.getElementById( "notify-message" ).style.display = "block";
				},
				error: function( response ) {
					var messageJson = jQuery.parseJSON( response.responseText );
					var message = "";
					if( messageJson["message"] != null )
						message = messageJson["message"];
					else
						message = "Failed due to some reason! Please try again!";

					document.getElementById( 'notify-loader' ).style.display = "none";
					document.getElementById( "notify-message-text" ).innerText = message;
					document.getElementById( "notify-message" ).style.display = "block";
				}
			});
		}
		function facebookLogin() {
			FB.login( function( response ) {
				$.ajax({
			
					type: 'post',
					url: '/api/user/login/facebook',

					data: { 
						'fbUserAccessToken': response.authResponse.accessToken
					},
					
					success: function( response ) {
						var displayName = "Hello " + jQuery.parseJSON( response )[ "displayName" ];
						
						document.getElementById( "login-signup" ).style.display = "none";
						document.getElementById( "user-dropdown" ).style.display = "block";
						document.getElementById( "username" ).innerText = displayName;
						
						setTimeout( function() {
					        jQuery( "#pratilipiUserLogin" ).modal( 'hide' );
					        jQuery( "#pratilipiUserRegister" ).modal( 'hide' );
						}, 1500); 
					},
					
					error: function( response ) {
						var message = jQuery.parseJSON( response.responseText );
						var status = response.status;

						if( message["message"] != null )
							alert( "Error " + status + " : " + message["message"] ); 
						else
							alert( "Invalid Credentials" );
					}
				});
			}, { scope: 'public_profile,email,user_birthday' } );
		}
	</script>
</head>

<body>
	<#--
	<#include "../element/pratilipi-homepage-login.ftl">
	<#include "../element/pratilipi-homepage-register.ftl">
	<#include "../element/pratilipi-homepage-logout.ftl">
	-->
	
	<div class="container">
		<div class="landing-page">
			<div class="pratilipi-banner clearfix">
				<div class="pratilipi-background"></div>
				<#--
				<div id="login-signup" class="login-signup pull-right" style="display: <#if user.isGuest == true>block<#else>none</#if>;">
					<button class="login-btn" onClick="openLoginModal()">LOG IN</button>
					<button class="signup-btn" onClick="openRegisterModal()">SIGN UP</button>
				</div>
				
				<div id="user-dropdown" class="user-dropdown pull-right" style="display: <#if user.isGuest == true>none<#else>block</#if>;">
					<div class="dropdown">
						<button type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
						<span id="username" class="username"><#if user.isGuest == false>Hello ${ user.getDisplayName() }</#if></span>
						<span class="caret"></span>
						</button>
						<ul class="dropdown-menu pull-right">
							<li>
								<a onClick="logout()">
									Logout
								</a>
							</li>
						</ul>
					</div>
				</div>
				-->
				<div class="content-wrapper">

					<div class="heading-wrapper">
						<h2>2,000,000 Readers.&nbsp;</h2><h2>4,000 Writers.&nbsp;</h2><h2>1 Platform.</h2>
					</div>

					<div class="logo">
						<div class="pratilipi"></div>
						<h1>pratilipi</h1>
					</div>

					<div class="description description-mobile">
						<div class="bg-white">Read great stories and write your own on world's largest platform for Indian languages</div>
					</div>

					<div class="description description-widescreen">
						<h5 class="bg-white">Read great stories and write your own on</h5>
						<br>
						<h5 class="bg-white">world's largest platform for Indian languages</h5>
					</div>

					<button type="button" class="start-reading-btn" onClick="startReading()">START READING</button>

				</div>

			</div>

			<div class="wrapper" id="wrapper">
				<ul class="tiles-container" id="tiles-container">
					<li class="image">
						<a href="http://hindi.pratilipi.com/">
							<div class="tiles" style="background-image:url('http://0.ptlp.co/resource-all/home-page/pratilipi-hindi.jpg');">
								<span class="language">हिंदी</span>
							</div>
						</a>
					</li>
					<li class="image">
						<a href="http://tamil.pratilipi.com/">
							<div class="tiles" style="background-image:url('http://0.ptlp.co/resource-all/home-page/pratilipi-tamil.png');">
								<span class="language">தமிழ்</span>
							</div>
						</a>
					</li>
					<li class="image">
						<a href="http://malayalam.pratilipi.com/">
							<div class="tiles" style="background-image:url('http://0.ptlp.co/resource-all/home-page/pratilipi-malayalam.jpg');">
								<span class="language">മലയാളം</span>
							</div>
						</a>
					</li>
					<li class="image">
						<a href="http://bengali.pratilipi.com/">
							<div class="tiles" style="background-image:url('http://0.ptlp.co/resource-all/home-page/pratilipi-bengali.jpg'); background-size: cover; background-position: top;">
								<span class="language">বাংলা</span>
							</div>
						</a>
					</li>
					<li class="image">
						<a href="http://telugu.pratilipi.com/">
							<div class="tiles" style="background-image:url('http://0.ptlp.co/resource-all/home-page/pratilipi-telugu.jpg');">
								<span class="language">తెలుగు</span>
							</div>
						</a>
					</li>
					<li class="image">
						<a href="http://gujarati.pratilipi.com/">
							<div class="tiles" style="background-image:url('http://0.ptlp.co/resource-all/home-page/pratilipi-gujarati.jpg'); background-size: cover; background-position: top;">
								<span class="language">ગુજરાતી</span>
							</div>
						</a>
					</li>
					<li class="image">
						<a href="http://marathi.pratilipi.com/">
							<div class="tiles" style="background-image:url('http://0.ptlp.co/resource-all/home-page/pratilipi-marathi.jpg');">
								<span class="language">मराठी</span>
							</div>
						</a>
					</li>
					<li class="image">
						<a href="http://kannada.pratilipi.com/">
							<div class="tiles" style="background-image:url('http://0.ptlp.co/resource-all/home-page/pratilipi-kannada.jpg');">
								<span class="language">ಕನ್ನಡ</span>
							</div>
						</a>
					</li> 
				</ul>

				<div class="tiles-container-mobile" id="tiles-container-mobile">
					<a class="language-button" href="http://hindi.pratilipi.com/">
						<span>हिंदी</span>
					</a>
					<a class="language-button" href="http://tamil.pratilipi.com/">
						<span>தமிழ்</span>
					</a>
					<a class="language-button" href="http://malayalam.pratilipi.com/">
						<span class="language">മലയാളം</span>
					</a>
					<a class="language-button" href="http://bengali.pratilipi.com/">
						<span class="language">বাংলা</span>
					</a>
					<a class="language-button" href="http://telugu.pratilipi.com/">
						<span class="language">తెలుగు</span>
					</a>
					<a class="language-button" href="http://gujarati.pratilipi.com/">
						<span class="language">ગુજરાતી</span>
					</a>
					<a class="language-button" href="http://marathi.pratilipi.com/">
						<span class="language">मराठी</span>
					</a>
					<a class="language-button" href="http://kannada.pratilipi.com/">
						<span class="language">ಕನ್ನಡ</span>
					</a>
				</div>

			</div>

			<div class="notify-me-wrapper" id="notify-me-wrapper">

				<div class="notify-elements" id="notify-elements">
					<h3>Be the first to know when your language gets added</h3>

					<select id="mailingListLanguage" name="Language" class="language-selection">
						<option value="none" selected disabled>Language</option>
						<option value="LAUNCH_ANNOUNCEMENT_URDU">		Urdu		</option>
						<option value="LAUNCH_ANNOUNCEMENT_ODIA">		Odia		</option>
						<option value="LAUNCH_ANNOUNCEMENT_PUNJABI">	Punjabi		</option>
						<option value="LAUNCH_ANNOUNCEMENT_ASSAMESE">	Assamese	</option>
						<option value="LAUNCH_ANNOUNCEMENT_MAITHILI">	Maithili	</option>
						<option value="LAUNCH_ANNOUNCEMENT_BHOJPURI">	Bhojpuri	</option>
						<option value="LAUNCH_ANNOUNCEMENT_OTHER">		Any Other	</option>
					</select>
					<input class="input-field" type="email" name="mailingListEmail" id="mailingListEmail" placeholder="Email" onKeyPress="mailingList( event, this )">
					<button class="notify-me-btn" onClick="mailingList()">NOTIFY ME!</button>
				</div>
				
				<div id="notify-loader" class="notify-loader"></div>

				<div class="notify-message" id="notify-message">
					<h2 id="notify-message-text"></h2>
					<button class="notify-me-btn" onClick="addSubscription()">ADD ANOTHER SUBSCRIPTION</button>
				</div>
			</div>
			<div class="pratilipi-footer">
				<div class="about-pratilipi">
					<h5>Pratilipi</h5>
					<a href="mailto:contact@pratilipi.com"><span>contact@pratilipi.com</span></a>
					<br/>
					<a href="tel:+918041710149"><span>080-41710149</span></a>
				</div>
				<div class="about-part-3">
					<span>Follow us on Social Media :</span>
					<div class="social-media-links">
						<a href="https://www.facebook.com/Pratilipidotcom" target="_blank"><i class="fa fa-facebook fa-lg" aria-hidden="true"></i></a>
						<a href="https://twitter.com/TeamPratilipi" target="_blank"><i class="fa fa-twitter fa-lg" aria-hidden="true"></i></a>
						<a href="https://plus.google.com/+PratilipiTeam" target="_blank"><i class="fa fa-google-plus fa-lg" aria-hidden="true"></i></a>
						<a href="https://www.linkedin.com/company/pratilipi" target="_blank"><i class="fa fa-linkedin fa-lg" aria-hidden="true"></i></a>
						<a href="https://www.quora.com/topic/Pratilipi-company" target="_blank"><img style="width: 20px; position: absolute;" src="http://0.ptlp.co/resource-all/icon/svg/quora.svg" /></a>
					</div>
				</div>
				<p class="copyrights">&copy;&nbsp;2015-2016 Nasadiya Tech. Pvt. Ltd.</p>
			</div>
		</div>
	</div>
</body>

</html>