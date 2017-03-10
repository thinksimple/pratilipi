<#-- Default -->
<#assign url="https://fonts.googleapis.com/css?family=Montserrat">
<#assign font="Montserrat">

<#assign font_xs="12">
<#assign font_s="13">
<#assign font_m="14">
<#assign font_l="15">
<#assign font_xl="16">

<#if language == "HINDI">
	<#assign url="https://fonts.googleapis.com/css?family=Noto+Sans&subset=devanagari">
	<#assign font="Noto Sans">
</#if>
<#if language == "BENGALI">
	<#assign url="http://fonts.googleapis.com/earlyaccess/notosansbengali.css">
	<#assign font="Noto Sans Bengali">
</#if>
<#if language == "MARATHI">
	<#assign url="http://fonts.googleapis.com/earlyaccess/notosansdevanagari.css">
	<#assign font="Noto Sans Devanagari">
</#if>
<#if language == "GUJARATI">
	<#assign url="http://0.ptlp.co/resource-gu/font/shruti.css">
	<#assign font="Shruti">
</#if>
<#if language == "TAMIL">
	<#assign url="http://fonts.googleapis.com/earlyaccess/notosanstamil.css">
	<#assign font="Noto Sans Tamil">
</#if>
<#if language == "MALAYALAM">
	<#assign url="http://fonts.googleapis.com/earlyaccess/notosansmalayalam.css">
	<#assign font="Noto Sans Malayalam">
</#if>
<#if language == "TELUGU">
	<#assign url="http://fonts.googleapis.com/earlyaccess/notosanstelugu.css">
	<#assign font="Noto Sans Telugu">
</#if>
<#if language == "KANNADA">
	<#assign url="http://fonts.googleapis.com/earlyaccess/notosanskannada.css">
	<#assign font="Noto Sans Kannada">
</#if>

<style>
	@import url(${ url });
	* { 
		font-family: "${ font }", "Helvetica", "Arial", sans-serif;
	}
	.font-xs {
		font-size: ${ font_xs }px !important;
	}
	.font-s {
		font-size: ${ font_s }px !important;
	}
	.font-m {
		font-size: ${ font_m }px !important;
	}
	.font-l {
		font-size: ${ font_l }px !important;
	}
	.font-xl {
		font-size: ${ font_xl }px !important;
	}
</style>
