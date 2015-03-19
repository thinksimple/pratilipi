<!-- PageContent :: Authors :: Start -->

<#setting time_zone="${ timeZone }">


<div id="PageContent-Author-List" class="container">
	<#if showAddOption>
		<div style="margin: 20px 0px;">
			<paper-fab
					mini class="bg-green" style="margin-right:10px"
					icon="add" title="Add Author"
					on-tap="{{ newAuthor }}"></paper-fab>
		</div>
	</#if>
	<pagecontent-authors apiUrl="/api.pratilipi/author/list" pageSize=20 showMetaData=${ showMetaData?c }></pagecontent-authors>
</div>

<paper-action-dialog
		backdrop
		id="PageContent-New-Author"
		heading="New Author"
		transition="core-transition-top"
		layered="true">
	<pagecontent-author-form apiUrl="/api.pratilipi/author"></pagecontent-authors>
</paper-action-dialog>

<core-ajax
	id="AjaxGet"
	url="/api.pratilipi/author/list"
	contentType="application/json"
	method="PUT"
	handleAs="json"
	on-core-response="{{handleAjaxResponse}}"
	on-core-error="{{handleAjaxError}}" >
</core-ajax>

<script language="javascript">

	var scope = document.querySelector( '#Polymer' );

	var pageContentAuthorList = document.querySelector( 'PageContent-Authors' );;
	var pageContentAuthorListDiv = jQuery( '#PageContent-Author-List' );
	var newAuthorDialog;
	
	function pageContentAuthorListLoad() {
		if( pageContentAuthorList.isFinished )
			return;
			
		var heightReq = jQuery( window ).scrollTop()
				- pageContentAuthorListDiv.position().top
				+ jQuery( window ).height()
				+ 3 * jQuery( window ).height();

		if( pageContentAuthorListDiv.outerHeight( true ) > heightReq )
			return;
			
		pageContentAuthorList.loadAuthorList();
	}
	
	scope.newAuthor = function( e ){
		console.log( "New Author function called" );
		newAuthorDialog.open();
	}

	jQuery( '#Polymer' ).bind( 'template-bound', function( e ) {
			pageContentAuthorListLoad();
	});
		
	jQuery( '#Polymer-Window' ).bind( 'scroll', function( e ) {	
			pageContentAuthorListLoad();
	});
		
	jQuery( pageContentAuthorList ).bind( 'load-error load-success', function( e ) {
			pageContentAuthorListLoad();
	});
	
	addEventListener( 'template-bound', function(e){
		newAuthorDialog = document.querySelector( '#PageContent-New-Author' );
	});
	
</script>

<script type="text/javascript" language="javascript" src="/pagecontent.authors/pagecontent.authors.nocache.js" defer></script>

<!-- PageContent :: Authors :: End -->