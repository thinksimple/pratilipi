<!DOCTYPE html>
<html lang="en">

<head>
	<meta charset="utf-8">
	<link rel="shortcut icon" type="image/png" href="/favicon.png">
	<title>${ title }</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<meta name="description" content="A platform to discover, read and share your favorite stories, poems and books in a language, device and format of your choice.">
	
	<script src='http://j.ptlp.co/third-party/jquery-2.1.4/jquery-2.1.4.min.js'></script>
	<script src='http://b.ptlp.co/third-party/bootstrap-3.3.4/js/bootstrap.min.js'></script>
	<link rel='stylesheet' href='http://f.ptlp.co/third-party/font-awesome-4.3.0/css/font-awesome.min.css'>
	<link rel='stylesheet' href='http://b.ptlp.co/third-party/bootstrap-3.3.4/css/bootstrap.min.css'>
	
	<link rel="stylesheet" type="text/css" href="/resources/style-home.css?6">

	<script>
		$( document ).ready(function() {
			var diff = ( jQuery( window ).height() - jQuery( '#tiles-container' ).height() ) / 2;
			jQuery( '.pratilipi-banner' ).height( jQuery( window ).height() - 20 - diff - 108 );
			jQuery( '.wrapper' ).css( "min-height", jQuery( window ).height() + "px" );
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
		
			$.ajax({
				type: 'post',
				url: '/api/mailinglist/subscribe',
				data: { 
					'email': email, 
					'mailingList': mailingList
				},
				success: function( response ) {
					document.getElementById( 'mailingListLanguage' ).value = "none";
					document.getElementById( 'mailingListEmail' ).value = null;
					document.getElementById( "notify-elements" ).style.display = "none";
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

					document.getElementById( "notify-elements" ).style.display = "none";
					document.getElementById( "notify-message-text" ).innerText = message;
					document.getElementById( "notify-message" ).style.display = "block";
				}
			});
		}
	</script>
</head>

<body>
	<#include "../element/pratilipi-homepage-login.ftl">
	<#include "../element/pratilipi-homepage-register.ftl">
	<#include "../element/pratilipi-homepage-logout.ftl">
	
	<div class="container">
		<div class="landing-page">
			<div class="pratilipi-banner clearfix">
				<div id="login-signup" class="login-signup pull-right" style="display: <#if user.isGuest == true>block<#else>none</#if>;">
					<button class="login-btn" onClick="openLoginModal()">LOG IN</button>
					<button class="signup-btn" onClick="openRegisterModal()">SIGN UP</button>
				</div>
				
				<div id="user-dropdown" class="user-dropdown pull-right" style="display: <#if user.isGuest == true>none<#else>block</#if>;">
					<div class="dropdown">
						<button type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
						<span id="username"></span>
						<span class="caret"></span>
						</button>
						<ul class="dropdown-menu pull-right">
							<li>
								<a style="padding: 5px; margin: 0;" onClick="logout()">
									LOGOUT
								</a>
							</li>
						</ul>
					</div>
				</div>

				<div class="content-wrapper">
					<h2>2,000,000 Readers. 3,000 Writers. 1 Platform </h2>
					<div class="logo">
						<div class="pratilipi"></div>
						<h1>pratilipi</h1>
					</div>
					<div class="description">
						<h5 class="bg-white">Read great stories and write your own on</h5>
						<br>
						<h5 class="bg-white">world's largest platform for Indian languages</h5>
					</div>
					<button type="button" class="start-reading-btn" onClick="startReading()">START READING</button>

				</div>

			</div>

			<div class="wrapper" id="wrapper">
				<ul id="tiles-container">
					<li class="image image-left">
						<a href="http://hindi.pratilipi.com/">
							<div class="tiles" style="background-image:url('http://0.ptlp.co/resource-all/home-page/hindi.jpg');">
								<span class="language">Hindi</span>
							</div>
						</a>
					</li>
					<li class="image image2">
						<a href="http://tamil.pratilipi.com/">
							<div class="tiles" style="background-image:url('http://0.ptlp.co/resource-all/home-page/tamil.png');">
								<span class="language">Tamil</span>
							</div>
						</a>
					</li>
					<li class="image image-right">
						<a href="http://malayalam.pratilipi.com/">
							<div class="tiles" style="background-image:url('http://0.ptlp.co/resource-all/home-page/malayalam.jpg');">
								<span class="language">Malayalam</span>
							</div>
						</a>
					</li>
					<li class="image image-left">
						<a href="http://bengali.pratilipi.com/">
							<div class="tiles" style="background-image:url('http://0.ptlp.co/resource-all/home-page/bengali.jpg');">
								<span class="language">Bengali</span>
							</div>
						</a>
					</li>
					<li class="image image3">
						<a href="http://telugu.pratilipi.com/">
							<div class="tiles" style="background-image:url('http://0.ptlp.co/resource-all/home-page/telugu.jpg');">
								<span class="language">Telugu</span>
							</div>
						</a>
					</li>
					<li class="image image-right">
						<a href="http://gujarati.pratilipi.com/">
							<div class="tiles" style="background-image:url('http://0.ptlp.co/resource-all/home-page/gujarati.jpg');">
								<span class="language">Gujarati</span>
							</div>
						</a>
					</li>
					<li class="image image-left image-none" style="height: 0px;"></li>
					<li class="image image4">
						<a href="http://marathi.pratilipi.com/">
							<div class="tiles" style="background-image:url('http://0.ptlp.co/resource-all/home-page/marathi.jpg');">
								<span class="language">Marathi</span>
							</div>
						</a>
					</li> 
				</ul>
			</div>

			<div class="notify-me-wrapper">

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
				
				<div class="notify-message" id="notify-message">
					<h2 id="notify-message-text"></h2>
					<button class="notify-me-btn" onClick="addSubscription()">ADD ANOTHER SUBSCRIPTION</button>
				</div>
			</div>
			<div class="pratilipi-footer">
				<div class="col-45 about-pratilipi">
					<h5>Pratilipi</h5>
					<a href="mailto:contact@pratilipi.com"><span>contact@pratilipi.com</span></a>
					<a href="tel:+919789316700"><span>+91 9789316700</span></a>
					<p class="copyrights">&copy;&nbsp;2014-2015 Nasadiya Tech. Pvt. Ltd.</p>
				</div>
				<div class="col-25 about-part-2">
					<ul class="col-50">
						<li><a href="#">About us</a></li>
						<li><a href="#">Work with us</a></li>
					</ul>
				</div>
				<div class="col-25 about-part-3">
					<span>Follow us on Social Media :</span>
					<div class="social-media-links">
						<a href="#"><i class="fa fa-twitter fa-lg" aria-hidden="true"></i></a>
						<a href="#"><i class="fa fa-google-plus fa-lg" aria-hidden="true"></i></a>
						<a href="#"><i class="fa fa-facebook fa-lg" aria-hidden="true"></i></a>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>

</html>