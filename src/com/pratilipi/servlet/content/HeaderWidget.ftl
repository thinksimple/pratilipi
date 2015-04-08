<script>
function validateForm() {
    var x = document.forms["searchForm"]["q"].value;
    if (x==null || x=="") {
        return false;
    }
}
</script>
<header>
	<div class="container">		
	
		<div id="Pratilipi-Search-UserAccess" style="height:50px;">
			<div id="Pratilipi-User-Access" style="display:none; vertical-align: middle;">
				<#if isUserLoggedIn>
					<div class="pull-right" style="padding-left: 15px;">
						<ul class="nav navbar-nav">
						<li class="dropdown">
			                <a href="#" class="dropdown-toggle" data-toggle="dropdown">Welcome <#if user.getFirstName()??>
																									${ user.getFirstName() }
																								<#else>
																									${ user.getEmail() }
																								</#if><span class="caret"></span></a>
			                <ul class="dropdown-menu" role="menu">
			                	<#if authorPageUrl?? >
			                		<li style="width: 170px;"><a href='${ authorPageUrl }'>My Profile</a></li>
			                	</#if>
		                		<li style="width: 170px;"><a href="#signout">Logout</a></li>
			                </ul>
			          	</li>
			          	</ul>
					</div>
				<#else>
					<div class="pull-right" style="padding-top: 9px;" >
						<a href="" data-toggle='modal' data-target="#loginModal">Login</a> | 
						<a href="" data-toggle='modal' data-target="#signupModal">Signup</a>
					</div>
				</#if>
			</div>
			<div id="Pratilipi-search" class="pull-right">
				<form id="qp-search" name="searchForm" class="navbar-form" action="/search" onsubmit="return validateForm()" method="GET">
					<div class="input-group">
						<input name="q" type="text" class="form-control pull-right" value="" placeholder="Search Pratilipi" style="height: 25px; padding: 3px 6px; font-size: 11px; width: 150px;">
						<span class="input-group-btn">
							<button class="btn btn-default" type="submit" style="height: 25px; padding: 2px 6px;"><span class="glyphicon glyphicon-search"></span></button>
						</span>
					</div><!-- /input-group -->
				</form>
			</div>
		</div>
		
		<div class="navbar navbar-default" role="navigation">
          <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target=".navbar-collapse">
              <span class="sr-only">Toggle navigation</span>
              <span class="icon-bar"></span>
              <span class="icon-bar"></span>
              <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="/" style="width:100px;"><img src="/theme.pratilipi/logo.png" alt="Pratilipi" width="60px;" style="position:absolute; bottom:15px;"/></a>
          </div>
          <div class="navbar-collapse collapse" style="height: 1px;">
            <ul class="nav navbar-nav">
            <li class="dropdown">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown">Books<span class="caret"></span></a>
                <ul class="dropdown-menu" role="menu">
                  <li><a href="/books">All</a></li>
                  <li><a href="/books/hindi">Hindi</a></li>
                  <li><a href="/books/gujarati">Gujarati</a></li>
                  <li><a href="/books/tamil">Tamil</a></li>
                </ul>
          	</li>
            <li class="dropdown">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown">Poems<span class="caret"></span></a>
                <ul class="dropdown-menu" role="menu">
                  <li><a href="/poems">All</a></li>
                  <li><a href="/poems/hindi">Hindi</a></li>
                  <li><a href="/poems/gujarati">Gujarati</a></li>
                  <li><a href="/poems/tamil">Tamil</a></li>
                </ul>
          	</li>
            <li class="dropdown">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown">Stories<span class="caret"></span></a>
                <ul class="dropdown-menu" role="menu">
                  <li><a href="/stories">All</a></li>
                  <li><a href="/stories/hindi">Hindi</a></li>
                  <li><a href="/stories/gujarati">Gujarati</a></li>
                  <li><a href="/stories/tamil">Tamil</a></li>
                </ul>
          	</li>
            <li class="dropdown">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown">Articles<span class="caret"></span></a>
                <ul class="dropdown-menu" role="menu">
                  <li><a href="/articles">All</a></li>
                  <li><a href="/articles/hindi">Hindi</a></li>
                  <li><a href="/articles/gujarati">Gujarati</a></li>
                  <li><a href="/articles/tamil">Tamil</a></li>
                </ul>
          	</li>
            <li class="dropdown">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown">Classics<span class="caret"></span></a>
                <ul class="dropdown-menu" role="menu">
                  <li><a href="/classics/books">Classic Books</a></li>
                  <li><a href="/classics/poems">Classic Poems</a></li>
                  <li><a href="/classics/stories">Classic Stories</a></li>
                </ul>
          	</li>
            <li>
              <a href="/magazines">Magazines</a>
            </li>
            <li class="dropdown">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown">About<span class="caret"></span></a>
                <ul class="dropdown-menu" role="menu">
                  <li><a href="/about/pratilipi">Pratilipi</a></li>
                  <li><a href="/about/team">Team Pratilipi</a></li>
                  <li><a href="/about/the-founding-readers">The Founding Readers</a></li>
                </ul>
            </li>
            <li id="Header-Navigation-Menu" class="dropdown" onclick="menu( event, this );">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown">More<span class="caret"></span></a>
                <ul class="dropdown-custom" role="menu" aria-labelledby="dLabel">
                  <li><a href="/author-interviews">Author Interviews</a></li>
                  <li><a href="/blog">Blog</a></li>
                  <li onclick="subMenu( event, this );" style="position:relative">
		                <a>Events<span id="Header-Navigation-Submenu-Caret" class="caret-right"></span></a>
		                <ul class="dropdown-subMenu">
			                <li class="subMenuItem" onclick="subMenuItemClick( event );" data-toggle="dropdown"><a href="/event/5724293958729728">Upado tamari kalam - Varta spardha</a></li>
			                <li class="subMenuItem" onclick="subMenuItemClick( event );" data-toggle="dropdown"><a href="/event/5641434644348928">Katha kadi</a></li>
			                <li class="subMenuItem" onclick="subMenuItemClick( event );" data-toggle="dropdown"><a href="/event/5085337277693952">Varta re varta </a></li>
			                <li class="subMenuItem" onclick="subMenuItemClick( event );" data-toggle="dropdown"><a href="/event/5133264616423424">Samarthini</a></li>
		                </ul>
		          </li>
                </ul>
            </li>
            </ul>
          </div><!--/.nav-collapse -->

      </div>

	</div>
</header>

<div class="modal fade" id="loginModal">
	<div class="modal-dialog">
		<div id="login" class="modal-content" style="padding: 15px;"></div>
	</div>
</div>
<div class="modal fade" id="signupModal">
	<div class="modal-dialog">
		<div id="signup" class="modal-content" style="padding: 15px;"></div>
	</div>
</div>
<div class="modal fade" id="forgotPasswordModal" style="padding: 15px;">
	<div class="modal-dialog">
		<div id="forgotPassword" class="modal-content" style="padding: 15px;"></div>
	</div>
</div>
<style>
	.caret-right {
	    border-bottom: 4px solid transparent;
	    border-top: 4px solid transparent;
	    border-left: 4px solid;
	    margin-left: 2px;
	    display: inline-block;
	    height: 0;
	    opacity: 1;
	    vertical-align: middle;
	    width: 0;
	}
	
	.dropdown-custom{
		top: 100%;
		left: 0px;
		white-space: nowrap;
	}
	
	.dropdown-subMenu {
		top: 0px;
	}
	
	.dropdown-subMenu, .dropdown-custom {
		position: absolute;
		display: none;
		float: left;
		z-index: 1000;
		padding: 5px 0px;
		margin: 2px 0px 0px;
		font-size: 14px;
		text-align: left;
		list-style: outside none none;
		background-color: white;
		background-clip: padding-box;
		border: 1px solid rgba(0, 0, 0, 0.15);
		box-shadow: 0px 6px 12px rgba(0, 0, 0, 0.176);
	}
	
	.subMenuItem:hover, .subMenuItem:focus, .dropdown-custom li:hover, .dropdown-custom li:focus{
		background-color: #F5F5F5;
	}
	
	.subMenuItem a:hover, .subMenuItem a:focus, .dropdown-custom a:hover, .dropdown-custom a:focus{
		text-decoration: none;
	}
	
	.subMenuItem, .dropdown-custom li {
		width: 100%;
		text-align: left;
		cursor: pointer; 
		padding: 3px 20px;
		clear: both;
		font-weight: 400;
		line-height: 1.42857;
		white-space: nowrap;
	}
</style>

<script language="javascript">
	function menu( event, object ){
		var event = event || window.event;
		event.stopPropagation();
		var offset = jQuery( object ).offset();
		var right = offset.left + jQuery( object ).width();
		var subMenuWidth = jQuery( object ).find( ".dropdown-subMenu" ).width();
		
		console.log( "Right : " + right );
		console.log( "Left : " + offset.left );
		console.log( "width : " + jQuery( object ).width() );
		console.log( jQuery( window ).width() );
		
		if( jQuery( window ).width() > 760 && right + 200 >= jQuery( window ).width() ){
			jQuery( object ).find( ".dropdown-subMenu" ).css( "right", "100%" );
			jQuery( object ).find( ".dropdown-subMenu" ).css( "left", "" );
			console.log( "This is working" );
		}
		else{
			jQuery( object ).find( ".dropdown-subMenu" ).css( "right", "" );
			jQuery( object ).find( ".dropdown-subMenu" ).css( "left", "100%" );
			console.log( "This is not working" );
		}
		
		if( jQuery( ".dropdown-custom" ).is( ":visible" ) ){
			jQuery( "#Header-Navigation-Menu" ).removeClass( "open" );
			jQuery( object ).find( ".dropdown-custom" ).hide();
		}else{
			jQuery( ".dropdown" ).removeClass( "open" );
			jQuery( "#Header-Navigation-Menu" ).addClass( "open" );
			jQuery( object ).find( ".dropdown-custom" ).show();
		} 
	}
	
	function subMenu( event, object ){
		var event = event || window.event;
		event.stopPropagation();
		if( jQuery( ".dropdown-subMenu" ).is( ":visible" ) ){
			jQuery( "#Header-Navigation-Submenu-Caret" ).removeClass( "caret" );
			jQuery( "#Header-Navigation-Submenu-Caret" ).addClass( "caret-right" );
			jQuery( object ).find( ".dropdown-subMenu" ).hide();
		} else {
			jQuery( "#Header-Navigation-Submenu-Caret" ).removeClass( "caret-right" );
			jQuery( "#Header-Navigation-Submenu-Caret" ).addClass( "caret" );
			jQuery( object ).find( ".dropdown-subMenu" ).show();
		}
	}
	
	function subMenuItemClick( event ){
		var event = event || window.event;
		event.stopPropagation();
		jQuery( "#Header-Navigation-Menu" ).removeClass( "open" );
		jQuery( ".dropdown-custom" ).hide();
		jQuery( "#Header-Navigation-Submenu-Caret" ).removeClass( "caret" );
		jQuery( "#Header-Navigation-Submenu-Caret" ).addClass( "caret-right" );
		jQuery( ".dropdown-subMenu" ).hide();
	}
	
	document.onclick = function(){
		jQuery( ".dropdown-subMenu" ).hide();
		jQuery( ".dropdown-custom" ).hide();
		jQuery( "#Header-Navigation-Submenu-Caret" ).removeClass( "caret" );
		jQuery( "#Header-Navigation-Submenu-Caret" ).addClass( "caret-right" );
	};
</script>
