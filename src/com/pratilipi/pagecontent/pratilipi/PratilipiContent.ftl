<div class="container-fluid">
	
	<div class="row">
		<div class="col-sm-2">
			<a href="${ pratilipiHomeUrl }">
				<img class="img-responsive img-thumbnail" src="${ pratilipiCoverUrl }">
			</a>
			<#if showEditOptions>
				<div id="PageContent-Pratilipi-CoverImage-EditOptions"></div>
				<div id="PageContent-Pratilipi-WordContent-EditOptions"></div>
			</#if>
		</div>
		<div class="col-sm-10">
			<h3>
				<a href="${ pratilipiHomeUrl }">${ pratilipi.getTitle() }</a>
			</h3>
			<h4>
				<a href="${ authorHomeUrl }">${ author.getFirstNameEn() }<#if author.getLastNameEn()??> ${ author.getLastNameEn() }</#if></a>
			</h4>
			<div>
				<div id="PageContent-Pratilipi-Summary">
					<#if pratilipi.getSummary()?? >
						${ pratilipi.getSummary() }
					</#if>
				</div>
				<#if showEditOptions>
					<div id="PageContent-Pratilipi-Summary-EditOptions"></div>
				</#if>
			</div>
		</div>
	</div>

	<#if showContent>
		<div id="PageContent-Pratilipi-Content">
			<#if pratilipi.getContent()?? >
				${ pratilipi.getContent() }
			</#if>
		</div>
		<#if showEditOptions>
			<div id="PageContent-Pratilipi-Content-EditOptions"></div>
		</#if>
	</#if>

	<#if showAddReviewOption>
		<div id="PageContent-Pratilipi-Review"></div>
		<div id="PageContent-Pratilipi-Review-AddOptions"></div>
	</#if>

	<div id="PageContent-Pratilipi-ReviewList">
		<#list reviewList as review >
			<div class="panel panel-default">
				<div class="panel-body">
					<h4>${ userIdNameMap[ review.getUserId()?string("#") ] },<small> ${ review.getReviewDate()?date }</small></h4>
					${ review.getReview() }
				</div>
			</div>
		</#list>
	</div>
	
</div>

<script type="text/javascript" language="javascript" src="/pagecontent.pratilipi/pagecontent.pratilipi.nocache.js" defer></script>
<#if showEditOptions>
	<script type="text/javascript" language="javascript" src="/pagecontent.pratilipi.edit/pagecontent.pratilipi.edit.nocache.js" defer></script>
</#if>
