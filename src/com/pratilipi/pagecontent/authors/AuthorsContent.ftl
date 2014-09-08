<!-- PageContent :: Authors :: Start -->

<#setting time_zone="${ timeZone }">

<#if showAddOption>
	<div id="PageContent-Authors-DataInput"></div>
</#if>

<div id="PageContent-Authors-List">

	<table class="table">
		<thead>
			<tr>
				<th>#</th>
				<th>Name</th>
				<th>Pen Name</th>
				<#if showMetaData>
					<th>Language</th>
					<th>Date Added</th>
				</#if>				
			</tr>
		</thead>
		<tbody>
			<#list authorList as author >
				<#assign _authorName>
					${ author.getFirstName() }<#if author.getLastName()??> ${ author.getLastName() }</#if>
					(${ author.getFirstNameEn() }<#if author.getLastNameEn()??> ${ author.getLastNameEn() }</#if>)
				</#assign>
				<#assign _authorPenName>
					<#if author.getPenName()??>${ author.getPenName() } </#if>
					<#if author.getPenNameEn()??>(${ author.getPenNameEn() })</#if>
				</#assign>
				<#assign _authorPageUrl="${ authorPageUrl }${ author.getId()?string(\"#\") }">
				<tr>
					<td>${ author_index + 1 }</td>
					<td><a href="${ _authorPageUrl }">${ _authorName }</td>
					<td><a href="${ _authorPageUrl }">${ _authorPenName }</td>
					<#if showMetaData>
						<td>${ languageIdNameMap[ author.getLanguageId()?string("#") ] }</td>
						<td>${ author.getRegistrationDate()?date }</td>
					</#if>
				</tr>
			</#list>
		</tbody>
	</table>

</div>

<script type="text/javascript" language="javascript" src="/pagecontent.authors/pagecontent.authors.nocache.js" defer></script>

<!-- PageContent :: Authors :: End -->