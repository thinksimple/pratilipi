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
				<#assign authorName="${ author.getFirstName() } ${ author.getLastName() } ${ author.getFirstNameEn() } ${ author.getLastNameEn() }">  
				<#assign authorPenName="${ author.getPenName() } ${ author.getPenNameEn() }">
				<#assign authorPageUrl="${ authorPageUrl }${ author.getId()?string(\"#\") }">
				<tr>
					<td>${ author_index + 1 }</td>
					<td><a href="${ authorPageUrl }">${ authorName }</td>
					<td><a href="${ authorPageUrl }">${ authorPenName }</td>
					<#if showMetaData>
						<td>${ author.getLanguageName() }</td>
						<td>${ author.getRegistrationDate()?date }</td>
					</#if>
				</tr>
			</#list>
		</tbody>
	</table>

</div>

<script type="text/javascript" language="javascript" src="/pagecontent.authors/pagecontent.authors.nocache.js" defer></script>

<!-- PageContent :: Authors :: End -->