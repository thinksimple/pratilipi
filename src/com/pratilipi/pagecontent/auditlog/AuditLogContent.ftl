<#setting time_zone="${ timeZone }">

<!-- PageContent :: Audit Log :: Start -->
<#list auditLogList as auditLog>
	<div id="PageContent-AuditLog-Header-${ auditLog.getId()?c }" onclick="toggleDetail( this );" style="border-bottom: 1px solid grey; cursor: pointer;">
		<span id="PageContent-AuditLog-Header-collapse-${ auditLog.getId()?c }">[+]</span>
		<span>${ auditLog.getCreationDate()?DateTime }</span>
		<span>${ userList.get( auditLog.getId() ).getEmail() }</span>
		<span>${ userList.get( auditLog.getId() ).getEvent() }</span>
		<div id="PageContent-AuditLog-Detail-${ auditLog.getId()?c }" style="display: none;" >
			<table>
				<tr>
					<td>Title : </td>
					<td>
						<#if newEventData.get( auditLog.get() ).getTitle() == newEventData.get( auditLog.get() ).getTitle()>
							<span>newEventData.get( auditLog.get() ).getTitle()</span>
						</else>
							<span class="deleted"> - &nbsp;&nbsp; oldEventData.get( auditLog.get() ).getTitle()</span>
							<span class="added"> + &nbsp;&nbsp; newEventData.get( auditLog.get() ).getTitle()</span>
						</#if>
					</td>
				</tr>
				<tr>
					<td>Author : </td>
					<td>
						<#if newEventData.get( auditLog.get() ).getAuthorId() == oldEventData.get( auditLog.get() ).getAuthorId()>
							<span>newEventData.get( auditLog.getId() ).getAuthorData().getFullName() ( newEventData.get( auditLog.getId() ).getAuthorData().getFullNameEn() )</span> 
						</else>
							<span class="deleted"> - &nbsp;&nbsp; oldEventData.get( auditLog.getId() ).getAuthorData().getFullName() ( oldEventData.get( auditLog.getId() ).getAuthorData().getFullNameEn() )</span>
							<span class="added"> + &nbsp;&nbsp; newEventData.get( auditLog.getId() ).getAuthorData().getFullName() ( newEventData.get( auditLog.getId() ).getAuthorData().getFullNameEn() )</span>
						</#if>
					</td>
				</tr>
				<tr>
					<td>Language : </td>
					<td>
						<#if newEventData.get( auditLog.get() ).getLanguageId() == oldEventData.get( auditLog.get() ).getLanguageId()>
							<span>newEventData.get( auditLog.getId() ).getLanguageData().getName() ( newEventData.get( auditLog.getId() ).getLanguageData().getNameEn() )</span>
						</else>
							<span class="deleted"> - &nbsp;&nbsp; oldEventData.get( auditLog.getId() ).getLanguageData().getName() ( oldEventData.get( auditLog.getId() ).getLanguageData().getNameEn() )</span>
							<span class="added"> + &nbsp;&nbsp; newEventData.get( auditLog.getId() ).getLanguageData().getName() ( newEventData.get( auditLog.getId() ).getLanguageData().getNameEn() )</span>
						</#if>
					</td>
				</tr>
				<tr>
					<td>Language : </td>
					<td>
						<#if newEventData.get( auditLog.get() ).getLanguageId() == oldEventData.get( auditLog.get() ).getLanguageId()>
							<span>newEventData.get( auditLog.getId() ).getLanguageData().getName() ( newEventData.get( auditLog.getId() ).getLanguageData().getNameEn() )</span>
						</else>
							<span class="deleted"> - &nbsp;&nbsp; oldEventData.get( auditLog.getId() ).getLanguageData().getName() ( oldEventData.get( auditLog.getId() ).getLanguageData().getNameEn() )</span>
							<span class="added"> + &nbsp;&nbsp; newEventData.get( auditLog.getId() ).getLanguageData().getName() ( newEventData.get( auditLog.getId() ).getLanguageData().getNameEn() )</span>
						</#if>
					</td>
				</tr>
				<tr>
					<td>State : </td>
					<td>
						<#if newEventData.get( auditLog.get() ).getLanguageId() == oldEventData.get( auditLog.get() ).getLanguageId()>
							<span>newEventData.get( auditLog.getId() ).getState()</span>
						</else>
							<span class="deleted"> - &nbsp;&nbsp; oldEventData.get( auditLog.getId() ).getState()</span>
							<span class="added"> + &nbsp;&nbsp; newEventData.get( auditLog.getId() ).getState()</span>
						</#if>
					</td>
				</tr>
				<tr>
					<td>Summary : </td>
					<td>
						<#if newEventData.get( auditLog.get() ).getSummary() == oldEventData.get( auditLog.get() ).getSummary()>
							<span>newEventData.get( auditLog.getId() ).getSummary()</span>
						</else>
							<span class="deleted"> - &nbsp;&nbsp; oldEventData.get( auditLog.getId() ).getSummary()</span>
							<span class="added"> + &nbsp;&nbsp; newEventData.get( auditLog.getId() ).getSummary()</span>
						</#if>
					</td>
				</tr>
			</table>
		</div>
	</div>
</#list>
<style>
	.deleted {
		background-color: red;
		display: block;
	}
	
	.added {
		background-color : green;
		display: block;
	}
</style>
<script>
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
			jQuery( collapseSpan ).innerHtml = "[-]";
		else
			jQuery( collapseSpan ).innerHtml = "[+]";
	}
</script>

<!-- PageContent :: Audit Log :: End -->
