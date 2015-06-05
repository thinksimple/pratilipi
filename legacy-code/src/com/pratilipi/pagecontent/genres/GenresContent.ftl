<!-- PageContent :: Genres :: Start -->

<#setting time_zone="${ timeZone }">

<div class="container">

	<#if showAddOption>
		<div id="PageContent-Genres-DataInput"></div>
	</#if>

	<h1>Genres</h1>

	<table class="table table-hover">
		<thead>
			<tr>
				<th>#</th>
				<th>Genre</th>
				<#if showMetaData>
					<th>Date Added</th>
				</#if>
				<#if showAddOption>
					<th>Add New</th>
				</#if>
			</tr>
		</thead>
		<tbody>
			<#list genreList as genre >
				<#assign _genrePageUrl="${ genrePageUrl }${ genre.getId()?string(\"#\") }">
				<tr>
					<td>${ genre_index + 1 }</td>
					<td><a href="${ _genrePageUrl }">${ genre.getName() }</td>
					<#if showMetaData>
						<td>${ genre.getCreationDate()?date }</td>
					</#if>
					<#if showAddOption>
						<td>Edit</td>
					</#if>
				</tr>
			</#list>
		</tbody>
	</table>

</div>

<#if showAddOption>
	<script type="text/javascript" language="javascript" src="/pagecontent.genres/pagecontent.genres.nocache.js" defer></script>
</#if>

<!-- PageContent :: Genres :: End -->