<style>
	.menu-item {
		margin:32px 16px;
		display: block;
		text-align: left;
	}
	.menu-item img {
		margin-right: 12px;
		width: 20px;
		height: 20px;
	}
	.menu-item span {
		font-size: 14px;
	}
</style>

<div class="secondary-500 pratilipi-shadow box">
	
	<h3 style="margin-top: 20px;" class="pratilipi-red text-center">${ pratilipi.getTitle()!pratilipi.getTitleEn() }</h3>
	
	<!-- Increase and Decrease size buttons -->
	<!-- In case of PRATILIPI contentType -->
	<#if contentType == "PRATILIPI">
		<a class="menu-item" style="cursor: pointer;" onCLick="increaseFontSize()">
			<img src="http://0.ptlp.co/resource-all/icon/svg/zoom-in.svg"/>
			<span>${ _strings.increase_font_size }</span>
		</a>
		<a class="menu-item" style="cursor: pointer;" onCLick="decreaseFontSize()">
			<img src="http://0.ptlp.co/resource-all/icon/svg/zoom-out.svg"/>
			<span>${ _strings.decrease_font_size }</span>
		</a>
	<#elseif contentType == "IMAGE">
		<a class="menu-item" style="cursor: pointer;" onCLick="increaseImageSize()">
			<img src="http://0.ptlp.co/resource-all/icon/svg/zoom-in.svg"/>
			<span>${ _strings.increase_image_size }</span>
		</a>
		<a class="menu-item" style="cursor: pointer;" onCLick="decreaseImageSize()">
			<img src="http://0.ptlp.co/resource-all/icon/svg/zoom-out.svg"/>
			<span>${ _strings.decrease_image_size }</span>
		</a>
	</#if>
	
	<div style="min-height: 1px;"></div>
	
	<#-- Library Section -->
	<#if user.isGuest == true>
		<a class="menu-item" href="/login?ret=/${ pageUrl }?id=${ pratilipi.getId()?c }%26addToLib=true">
			<img src="http://0.ptlp.co/resource-all/icon/svg/folder-plus.svg"/>
			<span>${ _strings.add_to_library }</span>
		</a>
	<#else>
		<#if userpratilipi?? && userpratilipi.isAddedtoLib()??>
			<#if userpratilipi.isAddedtoLib() == true>
				<a class="menu-item" style="cursor: pointer;" onCLick="removeFromLibrary()">
					<img src="http://0.ptlp.co/resource-all/icon/svg/folder-minus.svg"/>
					<span>${ _strings.remove_from_library }</span>
				</a>							
			<#else>
				<a class="menu-item" style="cursor: pointer;" onClick="addToLibrary()">
					<img src="http://0.ptlp.co/resource-all/icon/svg/folder-plus.svg"/>
					<span>${ _strings.add_to_library }</span>
				</a>
			</#if>
		<#else>
			<a class="menu-item" style="cursor: pointer;" onClick="addToLibrary()">
				<img src="http://0.ptlp.co/resource-all/icon/svg/folder-plus.svg"/>
				<span>${ _strings.add_to_library }</span>
			</a>
		</#if>
	</#if>
	
	<a class="menu-item" <#if user.isGuest == true>href="/login?ret=/library"<#else>href="/library"</#if>>
		<img src="http://0.ptlp.co/resource-all/icon/svg/books.svg"/>
		<span>${ _strings.my_library }</span>
	</a>
	
	<div style="min-height: 1px;"></div>
	
	<#-- Redirects -->
	<a class="menu-item" href="${ pratilipi.getPageUrl() }">
		<img src="http://0.ptlp.co/resource-all/icon/svg/book.svg"/>
		<span>${ _strings.reader_goto_content_page }</span>
	</a>				
	<a class="menu-item" href="${ pratilipi.getAuthor().getPageUrl() }">
		<img src="http://0.ptlp.co/resource-all/icon/svg/user.svg"/>
		<span>${ _strings.reader_goto_author_profile }</span>
	</a>
	<a class="menu-item" href="/">
		<img src="http://0.ptlp.co/resource-all/icon/svg/home3.svg"/>
		<span>${ _strings.reader_goto_home_page }</span>
	</a>
</div>