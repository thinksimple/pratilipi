<header>
	<div class="container">		
	
		<div style="height:50px;">
			<div id="Pratilipi-User-Access" class="pull-right" style="display:none;">
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
            <a class="navbar-brand" href="/" style="width:100px;"><img src="/theme.pratilipi/logo.png" width="60px;" style="position:absolute; bottom:15px;"/></a>
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
            <li>
              <a href="/author-interviews">Author Interviews</a>
            </li>
            <li>
              <a href="/blog">Blog</a>
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
<div class="modal fade" id="forgotPasswordModal">
	<div class="modal-dialog">
		<div id="forgotPassword" class="modal-content" style="padding: 15px;"></div>
	</div>
</div>