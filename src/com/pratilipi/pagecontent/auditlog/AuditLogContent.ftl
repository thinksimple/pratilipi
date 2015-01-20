<#setting time_zone="${ timeZone }">

<!-- PageContent :: Audit Log :: Start -->
<div class="container">
	<#list auditLogList as auditLog>
		<div style="border-bottom: 1px solid #EBEBE6;">
			<div id="PageContent-AuditLog-Header-${ auditLog.getId()?c }" onclick="toggleDetail( this );" style="cursor: pointer; background-color: #FAFAFA;">
				<span id="PageContent-AuditLog-Header-Collapse-${ auditLog.getId()?c }">[&#43;]</span>
				<span>${ auditLog.getCreationDate()?datetime }</span>
				<#if userList[ auditLog.getId()?c ].getEmail()??>
					<span>${ userList[ auditLog.getId()?c ].getEmail() }</span>
				<#else>
					<span>anonymous</span>
				</#if>
				<span>${ auditLog.getEventId() }</span>
			</div>
			<div id="PageContent-AuditLog-Detail-${ auditLog.getId()?c }" style="display: none;" >
				<#if oldEventData[ auditLog.getId()?c ]??>
					<table>
						<tr>
							<td>Title : </td>
							<td>
								<#if newEventData[ auditLog.getId()?c ].getTitle() == oldEventData[ auditLog.getId()?c ].getTitle()>
									<span>${ newEventData[ auditLog.getId()?c ].getTitle() }</span>
								<#else>
									<span class="deleted"> &#8211; ${ oldEventData[ auditLog.getId()?c ].getTitle() }</span>
									<span class="added"> &#43; ${ newEventData[ auditLog.getId()?c ].getTitle() }</span>
								</#if>
							</td>
						</tr>
						<#if newEventData[ auditLog.getId()?c ].getAuthorId()?? || oldEventData[ auditLog.getId()?c ].getAuthorId()??>
							<tr>
								<td>Author : </td>
								<td>
									<#if newEventData[ auditLog.getId()?c ].getAuthorId()?? &&
										 oldEventData[ auditLog.getId()?c ].getAuthorId()?? &&
										 newEventData[ auditLog.getId()?c ].getAuthorId() == oldEventData[ auditLog.getId()?c ].getAuthorId()>
										<span>${ newEventData[ auditLog.getId()?c ].getAuthorData().getFullName() } ( ${ newEventData[ auditLog.getId()?c ].getAuthorData().getFullNameEn() } )</span> 
									<#else>
										<#if oldEventData[ auditLog.getId()?c ].getAuthorId()??>
											<span class="deleted"> &#8211; ${ oldEventData[ auditLog.getId()?c ].getAuthorData().getFullName() } ( ${oldEventData[ auditLog.getId()?c ].getAuthorData().getFullNameEn() } )</span>
										</#if>
										<#if newEventData[ auditLog.getId()?c ].getAuthorId()??>
											<span class="added"> &#43; ${ newEventData[ auditLog.getId()?c ].getAuthorData().getFullName() } ( ${ newEventData[ auditLog.getId()?c ].getAuthorData().getFullNameEn() } )</span>
										</#if>
									</#if>
								</td>
							</tr>
						</#if>
						<#if newEventData[ auditLog.getId()?c ].getPublisherId()?? || oldEventData[ auditLog.getId()?c ].getPublisherId()??>
							<tr>
								<td>Publisher : </td>
								<td>
									<#if newEventData[ auditLog.getId()?c ].getPublisherId()?? &&
										 oldEventData[ auditLog.getId()?c ].getPublisherId()?? &&
										 newEventData[ auditLog.getId()?c ].getPublisherId() == oldEventData[ auditLog.getId()?c ].getPublisherId()>
										<span>${ newEventData[ auditLog.getId()?c ].getPublisherName() }</span>
									<#else>
										<#if oldEventData[ auditLog.getId()?c ].getAuthorId()??>
											<span class="deleted"> &#8211; ${ oldEventData[ auditLog.getId()?c ].getPublisherName() }</span>
										</#if>
										<#if newEventData[ auditLog.getId()?c ].getAuthorId()??>
											<span class="added"> &#43; ${ newEventData[ auditLog.getId()?c ].getPublisherName() }</span>
										</#if>
									</#if>
								</td>
							</tr>
						</#if>
						<tr>
							<td>Language : </td>
							<td>
								<#if newEventData[ auditLog.getId()?c ].getLanguageId() == oldEventData[ auditLog.getId()?c ].getLanguageId()>
									<span>${ newEventData[ auditLog.getId()?c ].getLanguageData().getName() } ( ${ newEventData[ auditLog.getId()?c ].getLanguageData().getNameEn() } )</span>
								<#else>
									<span class="deleted"> &#8211; ${ oldEventData[ auditLog.getId()?c ].getLanguageData().getName() } ( ${ oldEventData[ auditLog.getId()?c ].getLanguageData().getNameEn() } )</span>
									<span class="added"> &#43; ${ newEventData[ auditLog.getId()?c ].getLanguageData().getName() } ( ${ newEventData[ auditLog.getId()?c ].getLanguageData().getNameEn() } )</span>
								</#if>
							</td>
						</tr>
						<tr>
							<td>State : </td>
							<td>
								<#if newEventData[ auditLog.getId()?c ].getState() == oldEventData[ auditLog.getId()?c ].getState()>
									<span>${ newEventData[ auditLog.getId()?c ].getState() }</span>
								<#else>
									<span class="deleted"> &#8211; ${ oldEventData[ auditLog.getId()?c ].getState() }</span>
									<span class="added"> &#43; ${ newEventData[ auditLog.getId()?c ].getState() }</span>
								</#if>
							</td>
						</tr>
						<tr>
							<td>Summary : </td>
							<td>
								<#if newEventData[ auditLog.getId()?c ].getSummary()?? && 
									 oldEventData[ auditLog.getId()?c ].getSummary()?? &&
									 newEventData[ auditLog.getId()?c ].getSummary() == oldEventData[ auditLog.getId()?c ].getSummary() >
									<span>${ newEventData[ auditLog.getId()?c ].getSummary() }</span>
								<#else>
									<#if oldEventData[ auditLog.getId()?c ].getSummary()??>
										<span class="deleted"> &#8211; &nbsp;&nbsp; ${ oldEventData[ auditLog.getId()?c ].getSummary() }</span>
									</#if>
									<#if newEventData[ auditLog.getId()?c ].getSummary()??>
										<span class="added"> &#43; ${ newEventData[ auditLog.getId()?c ].getSummary() }</span>
									</#if>
								</#if>
							</td>
						</tr>
					</table>
				<#else>
					<table>
						<tr>
							<td>Title : </td>
							<td>
								<span class="added"> &#43; ${ newEventData[ auditLog.getId()?c ].getTitle() }</span>
							</td>
						</tr>
						<tr>
							<td>Author : </td>
							<td>
								<span class="added"> &#43; ${ newEventData[ auditLog.getId()?c ].getAuthorData().getFullName() } ( ${ newEventData[ auditLog.getId()?c ].getAuthorData().getFullNameEn() } )</span>
							</td>
						</tr>
						<tr>
							<td>Language : </td>
							<td>
								<span class="added"> &#43; ${ newEventData[ auditLog.getId()?c ].getLanguageData().getName() } ( ${ newEventData[ auditLog.getId()?c ].getLanguageData().getNameEn() } )</span>
							</td>
						</tr>
						<tr>
							<td>Language : </td>
							<td>
								<span class="added"> &#43; ${ newEventData[ auditLog.getId()?c ].getLanguageData().getName() } ( ${ newEventData[ auditLog.getId()?c ].getLanguageData().getNameEn() } )</span>
							</td>
						</tr>
						<tr>
							<td>State : </td>
							<td>
								<span class="added"> &#43; ${ newEventData[ auditLog.getId()?c ].getState() }</span>
							</td>
						</tr>
						<tr>
							<td>Summary : </td>
							<td>
								<#if newEventData[ auditLog.getId()?c ].getSummary()??>
									<span class="added"> &#43; ${ newEventData[ auditLog.getId()?c ].getSummary() }</span>
								</#if>
							</td>
						</tr>
					</table>
				</#if>
			</div>
		</div>
	</#list>
	<div id="" onclick="getOlderLogs();" style="margin-top: 20px; border: 1px solid #C0C0C0; text-align: center; cursor: pointer; background-color: #F3F3F3; color: grey !important; font-size: 14px; padding: 2px 0 2px 0; ">Show Older Logs</div>
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
	var cursor = 100;
	var resultCount = 100;
	
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
	
	function getOlderLogs(){
		jQuery.ajax({
			url: "/api.pratilipi/auditlogcontent",
			type: "GET",
			contentType: "application/json",
			dataType: "json",
			handleAs: "json",
			data: 'cursor=' + cursor,
			beforeSend: function( data, object ){
			},
			success: function( response, status, xhr ) {
				handleAjaxGetResponse( response );
			}, 
			error: function( xhr, status, error) {
				alert( status + " : " + error );
			},
			complete: function( event, response ){
				console.log( response );
			}
		});
	}
	
	function handleAjaxGetResponse( response ){
		cursor = cursor + 100;
		resultCount = resposne[ 'resultCount' ];
	}
</script>

<!-- PageContent :: Audit Log :: End -->
