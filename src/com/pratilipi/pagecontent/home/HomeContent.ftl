<#import "../../../../com/pratilipi/commons/client/PratilipiView.ftl" as pratilipiView>

<div class="container">

	<h2 class="hr-below">Books</h2>
	<div class="row">
		<#list bookDataList as bookData >
			<@pratilipiView.thumbnail pratilipiData=bookData />
		</#list>
	</div>

	<h2 class="hr-below">Poems</h2>
	<div class="row">
		<#list poemDataList as poemData >
			<@pratilipiView.thumbnail pratilipiData=poemData />
		</#list>
	</div>

	<h2 class="hr-below">Stories</h2>
	<div class="row">
		<#list storyDataList as storyData >
			<@pratilipiView.thumbnail pratilipiData=storyData />
		</#list>
	</div>
	
</div>
