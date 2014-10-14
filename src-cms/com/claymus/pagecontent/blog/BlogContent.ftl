<#import "../../../../com/claymus/commons/client/ui/Social.ftl" as social>

<!-- PageContent :: Blog :: Start -->

<div id="PageContent-Blog" list-cursor="${ cursor }">
	<#list blogPostHtmlList as blogPostHtml>
		${ blogPostHtml }
	</#list>
</div>

<script type="text/javascript" language="javascript" src="/pagecontent.blog/pagecontent.blog.nocache.js" defer></script>

<!-- PageContent :: Blog :: End -->