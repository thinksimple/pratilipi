<!-- PageContent :: Genres :: Start -->

<#setting time_zone="${ timeZone }">

<#if showAddOption>
	<div id="PageContent-Genres-DataInput"></div>
</#if>

<div>

	<table class="table">
		<thead>
			<tr>
				<th>#</th>
				<th>Genre</th>
				<#if showMetaData>
					<th>Date Added</th>
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
				</tr>
			</#list>
		</tbody>
	</table>

</div>

<#if showAddOption>
	<script type="text/javascript" language="javascript" src="/pagecontent.genres/pagecontent.genres.nocache.js" defer></script>
</#if>

<!-- PageContent :: Genres :: End -->