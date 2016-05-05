<style>
	.menu-item {
		padding-top: 10px;
		padding-bottom: 10px;
		display: block;
		text-align: center;
	}
</style>

<div class="secondary-500 pratilipi-shadow box">
	
	<h3 class="pratilipi-red">${ pratilipi.getTitle()!pratilipi.getTitleEn() }</h3>
	
	<#-- Increase and Decrease font-size buttons -->
	<a style="cursor: pointer;" onCLick="increaseFontSize()"><img src="http://0.ptlp.co/resource-all/icon/svg/zoom-in.svg"/></a>
	<a style="cursor: pointer;" onCLick="decreaseFontSize()"><img src="http://0.ptlp.co/resource-all/icon/svg/zoom-out.svg"/></a>
	
	<#-- Write Review -->
	<a <#if user.isGuest == true>href="/login?ret=${ pratilipi.getPageUrl() }?review=write%26ret=/read?id=${ pratilipi.getId()?c }%26pageNo=${ pageNo }"<#else>href="${ pratilipi.getPageUrl() }?review=write&ret=/read?id=${ pratilipi.getId()?c }%26pageNo=${ pageNo }"</#if> >
		<img src="http://0.ptlp.co/resource-all/icon/svg/pencil.svg"/>
	</a>
	
	
	<#-- Library Section -->
	<#if user.isGuest == true>
		<a href="/login?ret=/read?id=${ pratilipi.getId()?c }%26addToLib=true">
			<img src="http://0.ptlp.co/resource-all/icon/svg/folder-plus.svg"/>
		</a>
	<#else>
		<#if userpratilipi?? && userpratilipi.isAddedtoLib()??>
			<#if userpratilipi.isAddedtoLib() == true>
				<a style="cursor: pointer;" onCLick="removeFromLibrary()">
					<img src="http://0.ptlp.co/resource-all/icon/svg/folder-minus.svg"/>
				</a>							
			<#else>
				<a style="cursor: pointer;" onClick="addToLibrary()">
					<img src="http://0.ptlp.co/resource-all/icon/svg/folder-plus.svg"/>
				</a>
			</#if>
		<#else>
			<a style="cursor: pointer;" onClick="addToLibrary()">
				<img src="http://0.ptlp.co/resource-all/icon/svg/folder-plus.svg"/>
			</a>
		</#if>
	</#if>
	<a href="/library"><img src="http://0.ptlp.co/resource-all/icon/svg/books.svg"/></a>
	
	<#-- Redirects -->
	<a href="${ pratilipi.getPageUrl() }"><img src="http://0.ptlp.co/resource-all/icon/svg/book.svg"/></a>				
	<a href="${ pratilipi.getAuthor().getPageUrl() }"><img src="http://0.ptlp.co/resource-all/icon/svg/user.svg"/></a>
	<a href="/"><img src="http://0.ptlp.co/resource-all/icon/svg/home3.svg"/></a>
</div>