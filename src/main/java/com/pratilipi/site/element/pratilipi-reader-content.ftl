<style>		
	#reader-content * {
		font-size: ${ fontSize }px!important;
		text-align: justify;
		-webkit-font-smoothing: antialiased;
		line-height: 1.6em!important;
		overflow: hidden;
	}
	
	#reader-content h1, #reader-content h2 {
		text-align: center;
	}
	
	#reader-content img {
		max-width: 100%!important;
	}
	
	#reader-content h2 {
		font-size: ${ fontSize + 4 }px!important;
	}
	#reader-content h1 {
		font-size: ${ fontSize + 8 }px!important;
	}
	.reader-icon-alert {
		width: 24px;
		height: 24px;
		margin-left: 10px;
	}
	
	/* Bootstrap override */
	.alert {
		margin: 64px auto;
	}
	.alert-success {
		color: #333;
		background-color: #ffffff;
		border-color: #333;
	}
	.alert-success .alert-link,.alert-success .alert-link:hover, .alert-success .alert-link:focus {
		color: #107FE5;
		font-weight: inherit;
	}
</style>

<script>
	$( document ).ready(function() {
		$( "#reader-content" ).css( 'min-height', ( window.innerHeight - 64 ) + "px" );
	});
</script>
	
<div class="secondary-500 pratilipi-shadow box" style="margin-bottom: 5px; padding: 16px 22px; width: 100%;">
	<div id="reader-content">
		${ content }
	</div>
	<#if pageNo == pageCount>
		<div class="alert alert-success" role="alert">
				<h3 class="text-center" style="margin-top: 24px;">${ _strings.reader_enjoyed_reading_part1 }</h3>
				<a <#if user.isGuest == true>href="/login?ret=${ pratilipi.getPageUrl() }?review=write%26ret=/read?id=${ pratilipi.getId()?c }%26pageNo=${ pageNo }"<#else>href="${ pratilipi.getPageUrl() }?review=write&ret=/read?id=${ pratilipi.getId()?c }%26pageNo=${ pageNo }"</#if> >
					<h6 class="text-center" style="margin: 15px;">${ _strings.reader_enjoyed_review_book }</h6>
				</a>
				<div class="text-center div-center">
					<h6 style="margin: 15px;display: inline-block;">${ _strings.reader_enjoyed_reading_part2 } : </h6>
					<a style="cursor: pointer;" onCLick="shareOnFacebook()">
						<img class="reader-icon-alert" src="http://0.ptlp.co/resource-all/icon/svg/facebook2.svg">
					</a>
					<a style="cursor: pointer;" onCLick="shareOnTwitter()">
						<img class="reader-icon-alert" src="http://0.ptlp.co/resource-all/icon/svg/twitter.svg">
					</a>
					<a style="cursor: pointer;" onCLick="shareOnGplus()">
						<img class="reader-icon-alert" src="http://0.ptlp.co/resource-all/icon/svg/google-plus2.svg">
					</a>
				</div>
		</div>
	</#if>
</div>