<header>
	<div class="container">		
	
		<div class="row">
		
			<h1 class="col-xs-6" style="margin:0px;">
				<a href="/" style="color:#E74C3C !important; text-decoration:none;">Pratilipi</a>
				<small style="position:absolute; top:0px; padding-left: 3px;"><small>beta</small></small>
			</h1>

			<div class="col-xs-6" id="Pratilipi-User-Access" style="display: none;">
				<#if isUserLoggedIn>
					<div class="pull-right">
						<ul class="nav navbar-nav">
						<li class="dropdown">
			                <a href="#" class="dropdown-toggle" data-toggle="dropdown">Welcome <#if user.getFirstName()??>
																									${ user.getFirstName() }
																								<#else>
																									${ user.getEmail() }
																								</#if><span class="caret"></span></a>
			                <ul class="dropdown-menu" role="menu">
			                  <li style="width: 170px;"><a href="#signout">Logout</a></li>
			                </ul>
			          	</li>
			          	</ul>
					</div>
				<#else>
					<div class="pull-right">
						<a href="" data-toggle='modal' data-target="#loginModal">Login</a> | 
						<a href="" data-toggle='modal' data-target="#signupModal">Signup</a>
					</div>
				</#if>
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
          </div>
          <div class="navbar-collapse collapse" style="height: 1px;">
            <ul class="nav navbar-nav">
            <li class="dropdown">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown">Books<span class="caret"></span></a>
                <ul class="dropdown-menu" role="menu">
                  <li><a href="/books">All</a></li>
                  <li><a href="/books/hindi">Hindi</a></li>
                  <li><a href="/books/gujarati">Gujarati</a></li>
                </ul>
          	</li>
            <li class="dropdown">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown">Poems<span class="caret"></span></a>
                <ul class="dropdown-menu" role="menu">
                  <li><a href="/poems">All</a></li>
                  <li><a href="/poems/hindi">Hindi</a></li>
                  <li><a href="/poems/gujarati">Gujarati</a></li>
                </ul>
          	</li>
            <li class="dropdown">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown">Stories<span class="caret"></span></a>
                <ul class="dropdown-menu" role="menu">
                  <li><a href="/stories">All</a></li>
                  <li><a href="/stories/hindi">Hindi</a></li>
                  <li><a href="/stories/gujarati">Gujarati</a></li>
                </ul>
          	</li>
            <li class="dropdown">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown">Articles<span class="caret"></span></a>
                <ul class="dropdown-menu" role="menu">
                  <li><a href="/articles">All</a></li>
                  <li><a href="/articles/hindi">Hindi</a></li>
                  <li><a href="/articles/gujarati">Gujarati</a></li>
                </ul>
          	</li>
            <li class="dropdown">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown">Classics<span class="caret"></span></a>
                <ul class="dropdown-menu" role="menu">
                  <li><a href="/classics/books">Classic Books</a></li>
                  <li><a href="/classics/poems">Classic Poems</a></li>
                  <li><a href="/classics/stories">Classic Stories</a></li>
                  <li><a href="/classics/articles">Classic Articles</a></li>
                </ul>
          	</li>
            <li class="dropdown">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown">About<span class="caret"></span></a>
                <ul class="dropdown-menu" role="menu">
                  <li><a href="/about/pratilipi">Pratilipi</a></li>
                  <li><a href="/about/team">Team Pratilipi</a></li>
                  <li><a href="/about/the-founding-readers">The Founding Readers</a></li>
                </ul>
            </li>
            </ul>
          </div><!--/.nav-collapse -->

      </div>

	</div>
</header>
<div class="modal fade" id="loginModal">
	<div class="modal-dialog">
		<div id="login" class="modal-content"></div>
	</div>
</div>
<div class="modal fade" id="signupModal">
	<div class="modal-dialog">
		<div id="signup" class="modal-content"></div>
	</div>
</div>
<div class="modal fade" id="forgotPasswordModal">
	<div class="modal-dialog">
		<div id="forgotPassword" class="modal-content"></div>
	</div>
</div>