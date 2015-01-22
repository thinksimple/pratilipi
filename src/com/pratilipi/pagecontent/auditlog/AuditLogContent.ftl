<#setting time_zone="${ timeZone }">

<!-- PageContent :: Audit Log :: Start -->
<div id="PageContent-AuditLogs">
	<pagecontent-auditlog apiUrl="/api.pratilipi/auditlog" pageSize=20></pagecontent-auditlog>
</div>



<style>
	.deleted {
		background-color: #FF9494;
		display: block;
	}
	
	.added {
		background-color : #85E085;
		display: block;
	}
</style>


<script language="javascript">

	var pageContentAuditLog = document.querySelector( 'pagecontent-auditlog' );
	var pageContentAuditLogDiv = jQuery( '#PageContent-AuditLogs' );
	
	function pageContentAuditLogLoad() {
		if( pageContentAuditLog.isFinished )
			return;
			
		var heightReq = jQuery( window ).scrollTop()
				- pageContentAuditLogDiv.position().top
				+ jQuery( window ).height()
				+ 3 * jQuery( window ).height();

		if( pageContentAuditLogDiv.outerHeight( true ) > heightReq )
			return;
			
		pageContentAuditLog.loadAuditLogList();
	}

	jQuery( '#Polymer' ).bind( 'template-bound', function( e ) {
			pageContentAuditLogLoad();
	});
		
	jQuery( '#Polymer-Window' ).bind( 'scroll', function( e ) {
			pageContentAuditLogLoad();
	});
		
	jQuery( pageContentAuditLog ).bind( 'load-error load-success', function( e ) {
			pageContentAuditLogLoad();
	});
	
	function toggleDetail( object ){
		var headerId = jQuery( object ).attr( "id" );
		var subStr = headerId.substring( headerId.lastIndexOf( "-" ) +1 );
		var detailDiv = jQuery( "#PageContent-AuditLog-Detail-" + subStr );
		toggleCollapseSpan( detailDiv, subStr )
		detailDiv.slideToggle();
	}
	
	function toggleCollapseSpan( detailDiv, subStr ){
		var collapseSpan = jQuery( "#PageContent-AuditLog-Header-Collapse-" + subStr );
		if( jQuery( detailDiv ).is( ":visible" ) )
			jQuery( collapseSpan ).html( "[&#43;]" );
		else
			jQuery( collapseSpan ).html( "[&#8211;]" );
	}

</script>

<!-- PageContent :: Audit Log :: End -->
