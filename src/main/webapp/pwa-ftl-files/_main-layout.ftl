<#macro pratilipi_main_layout element>
	<div class="mdl-layout mdl-js-layout mdl-layout--fixed-header">
		<!-- ko component: "pratilipi-header" --><!-- /ko -->
		<div class="mdl-layout__content" style="z-index: initial;">
			<div class="mdl-components mdl-js-components">
				<!-- ko component: "pratilipi-navigation-aside" --><!-- /ko -->
				<div class="mdl-grid mobile-grid" style="flex-grow: 1;">
					<!-- ko component: "${ element }" --><!-- /ko -->
				</div>
			</div>
			<!-- ko component: "pratilipi-footer" --><!-- /ko -->
		</div>
	</div>
	<div class="word-suggester"><div class="word-input"></div><div class="suggestions"><div class="suggestion"></div></div></div>
</#macro>
