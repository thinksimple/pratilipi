<!-- PageContent :: Languages :: Start -->

<div>

	<table class="table">
		<thead>
			<tr>
				<th>#</th>
				<th>Language</th>
				<#if showMetaData>
					<th>Date Added</th>
				</#if>				
			</tr>
		</thead>
		<tbody>
			<#list languageList as language >
				<tr>
					<td>${ language_index + 1 }</td>
					<td><a href="${ languagePageUrl }${ language.getId()?string("#") }">${ language.getName() }</td>
					<#if showMetaData>
						<td>${ language.getCreationDate()?date }</td>
					</#if>
				</tr>
			</#list>
			<#if showAddOption>
				<tr>
					<td>${ languageList?size + 1 }</td>
					<td id="PageContent-Languages-TextInput"></td>
					<td id="PageContent-Languages-AddButton"></td>
				</tr>
			</#if>
		</tbody>
	</table>

	<#if showAddOption>
		<script type="text/javascript" language="javascript" src="/pagecontent.languages/pagecontent.languages.nocache.js" defer></script>
	</#if>
	
</div>

<!-- PageContent :: Languages :: End -->