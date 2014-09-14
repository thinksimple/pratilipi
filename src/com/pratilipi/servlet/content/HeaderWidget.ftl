<header>
	<div class="container">		
	
		<div class="row">
		
			<div class="col-xs-6">
				<a href="/" style="color:#E74C3C !important; font-size:24px;"><b><i>Pratilipi.com</i></b></a>
			</div>

			<div class="col-xs-6">
				<#if isUserLoggedIn()>
					<div class="pull-right">
						<a href="#signout">Logout</a>
					</div>
				<#else>
					<div class="pull-right">
						<a href="#signin">Login</a> | 
						<a href="#signup">Signup</a>
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
            <li><a href="/books">Books</a></li>
            <li><a href="/poems">Poems</a></li>
            <li><a href="/stories">Stories</a></li>
            <li><a href="/articles">Articles</a></li>
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
