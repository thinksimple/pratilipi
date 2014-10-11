<#import "../../../../com/claymus/commons/client/ui/Social.ftl" as social>

<!-- PageContent :: BlogPost :: Start -->

<div class="container">
	<h1 id="PageContent-BlogPost-Title" page-content-id="${ (blogPostContent.getId()?c)! }">${ blogPostContent.getTitle() }</h1>
	<#if blogUrl??>
		<div style="margin-top:10px; margin-bottom:10px;"><@social.toolbar shareUrl=blogUrl/></div>
	</#if>
	<div id="PageContent-BlogPost-Content">${ blogPostContent.getContent() }</div>
	<#if showEditOptions>
		<div id="PageContent-BlogPost-EditOptions" style="margin-top:10px; text-align:right;"></div>
	</#if>
</div>

<#if showEditOptions>
	<script type="text/javascript" language="javascript" src="/pagecontent.blogpost/pagecontent.blogpost.nocache.js" defer></script>
</#if>

<!-- PageContent :: BlogPost :: End -->