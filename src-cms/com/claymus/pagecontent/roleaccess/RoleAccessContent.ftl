<!-- PageContent :: Genres :: Start -->

<div class="container">

	<h1>Role Access</h1>

	<table class="table table-hover">
		<thead>
			<tr>
				<th>Module</th>
				<th>Access</th>
				<#list roleIdList as roleId >
					<th>Role: ${ roleId }</th>
				</#list>
			</tr>
		</thead>
		<tbody>
			<#list pageContentHelperList as pageContentHelper >
				<#list pageContentHelper.getAccessList() as access >
					<tr>
						<td>${ pageContentHelper.getModuleName() }</td>
						<td>${ access.getDescription() }</td>
						<#list roleIdList as roleId >
							<td class="pageContent-Access" pratilipi-data="${ roleId }-${ access.getId() }">
								<#if ( dataAccessor.getRoleAccess( roleId, access.getId() ).hasAccess() ) ! access.getDefault() >
									<span class="glyphicon glyphicon-ok text-success"></span>
								<#else>
									<span class="glyphicon glyphicon-remove text-danger"></span>
								</#if>
							</td>
						</#list>
					</tr>
				</#list>
			</#list>
		</tbody>
	</table>

</div>

<!-- PageContent :: Genres :: End -->