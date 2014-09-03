<!-- PageContent :: Languages :: Start -->

<#setting time_zone="${ timeZone }">

<#if showAddOption>
	<div id="PageContent-Languages-DataInput"></div>
</#if>

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
				<#assign _languageName="${ language.getName() } (${ language.getNameEn() })">  
				<#assign _languagePageUrl="${ languagePageUrl }${ language.getId()?string(\"#\") }">
				<tr>
					<td>${ language_index + 1 }</td>
					<td><a href="${ _languagePageUrl }">${ _languageName }</td>
					<#if showMetaData>
						<td>${ language.getCreationDate()?date }</td>
					</#if>
				</tr>
			</#list>
		</tbody>
	</table>

</div>

<#if showAddOption>
	<script type="text/javascript" language="javascript" src="/pagecontent.languages/pagecontent.languages.nocache.js" defer></script>
</#if>

<!-- PageContent :: Languages :: End -->