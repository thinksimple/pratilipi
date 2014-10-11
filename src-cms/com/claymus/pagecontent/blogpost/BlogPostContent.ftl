<!-- PageContent :: BlogPost :: Start -->

<div class="container">
	<h1 id="PageContent-BlogPost-Title" page-content-id="${ (blogPostContent.getId()?c)! }">${ blogPostContent.getTitle() }</h1>
	<div id="PageContent-BlogPost-Content">${ blogPostContent.getContent() }</div>
	<#if showEditOptions>
		<div id="PageContent-BlogPost-EditOptions"></div>
	</#if>
</div>

<#if showEditOptions>
	<script type="text/javascript" language="javascript" src="/pagecontent.blogpost/pagecontent.blogpost.nocache.js" defer></script>
</#if>

<!-- PageContent :: BlogPost :: End -->