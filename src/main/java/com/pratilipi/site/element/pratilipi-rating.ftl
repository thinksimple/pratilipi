<style>
	.glyphicon-star {
		font-size: 17px;
	}
	.empty {
		color: #bdc3c7;
	}
	.half {
		position: relative;
	}
	.half:before {
		position: relative;
		z-index: 9;
		width: 47%;
		display: block;
		overflow: hidden;
		color: #D0021B;
	}
	.half:after {
		content: '\e006';
		position: absolute;
		z-index: 8;
		color: #bdc3c7;
		top: 0;
		left: 0;
	}
	.full {
		color: #D0021B;
	}
</style>

<div class="rating" style="margin-right: 10px; display: inline-block;">
	<#if rating <= 0 >
		<i class="glyphicon glyphicon-star empty"></i>
	<#elseif rating <= 0.5 >
		<i class="glyphicon glyphicon-star half"></i>
	<#else>
		<i class="glyphicon glyphicon-star full"></i>
	</#if>
	
	<#if rating <= 1 >
		<i class="glyphicon glyphicon-star empty"></i>
	<#elseif rating <= 1.5 >
		<i class="glyphicon glyphicon-star half"></i>
	<#else>
		<i class="glyphicon glyphicon-star full"></i>
	</#if>
	
	<#if rating <= 2 >
		<i class="glyphicon glyphicon-star empty"></i>
	<#elseif rating <= 2.5 >
		<i class="glyphicon glyphicon-star half"></i>
	<#else>
		<i class="glyphicon glyphicon-star full"></i>
	</#if>
	
	<#if rating <= 3 >
		<i class="glyphicon glyphicon-star empty"></i>
	<#elseif rating <= 3.5 >
		<i class="glyphicon glyphicon-star half"></i>
	<#else>
		<i class="glyphicon glyphicon-star full"></i>
	</#if>
	
	<#if rating <= 4 >
		<i class="glyphicon glyphicon-star empty"></i>
	<#elseif rating <= 4.5 >
		<i class="glyphicon glyphicon-star half"></i>
	<#else>
		<i class="glyphicon glyphicon-star full"></i>
	</#if>
</div>
<div style="display: inline-block;">
	<small>(${rating})</small>
</div>
		