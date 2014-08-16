<div id="PageContent-PratilipiHome">

	<div class="background">
		<img src="theme.pratilipi/images/home-background.jpg" />
	</div>

	<div class="links-left">
			<li><a href="/about" 
		        onmouseover="mopen('about')"
		        onmouseout="mclosetime()">ABOUT</a>
		        <div id="about" 
		            onmouseover="mcancelclosetime()" 
		            onmouseout="mclosetime()">
			        <a href="#">Pratilipi</a>
			        <a href="#">Team</a>
			        <a href="#">Founding Readers</a>
		        </div>
	   		</li>
			<li><a href="/give-away">GIVE AWAY</a></li>
	</div>
	<div class="links-right">
		<li><a href="#subscribe">SUBSCRIBE</a></li>
	</div>

	<div class="title">
		<a href="/">Pratilipi</a>
	</div>
	<div class="tag-line">
		<span>you become what you read ...</span>
	</div>

	<div class="launching-soon">
		<span>We are launching soon !</span><br/>
		<span><a href="#subscribe">Subscribe</a> to be the first one to know.</span>
	</div>
	
	<div class="teaser">
		<iframe width="560" height="315" src="//www.youtube.com/embed/8L71YwC7BOQ?rel=0&controls=2&showinfo=0&modestbranding=1" frameborder="0" allowfullscreen></iframe>
	</div>

</div>

<script>
	function setPageContentSize() {
    	window.document
    			.getElementById( "PageContent-PratilipiHome" )
    			.setAttribute( "style", "height:" + window.innerHeight + "px" );
	}

	window.onload = setPageContentSize;
	window.onresize = setPageContentSize;
</script>
