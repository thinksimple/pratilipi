<#macro pratilipi_main_layout element>
	<div class="mdl-layout mdl-js-layout">
		<!-- ko component: "pratilipi-header" --><!-- /ko -->
		<main class="mdl-layout__content" data-bind="event: { scroll: appViewModel.notifyOfScrollEvent }">
			<div class="body-layout-row">
				<div class="body-layout-nav">
					<!-- ko component: "pratilipi-navigation-aside" --><!-- /ko -->
				</div>
				
				<div class="body-layout-content">
					<div class="mdl-grid mobile-grid" style="flex-grow: 1;">
						<!-- ko component: "${ element }" --><!-- /ko -->
					</div>
				</div>
				
			</div>
			<!-- ko component: "pratilipi-footer" --><!-- /ko -->
		</main>
	</div>
</#macro>
