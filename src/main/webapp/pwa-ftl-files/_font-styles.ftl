<#-- Default -->
<#assign url="https://fonts.googleapis.com/css?family=Montserrat">
<#assign font="Montserrat">

<#assign font_xs="12">
<#assign font_s="14">
<#assign font_m="16">
<#assign font_l="20">
<#assign font_xl="24">

<#assign font_xs_mob="12">
<#assign font_s_mob="14">
<#assign font_m_mob="16">
<#assign font_l_mob="20">
<#assign font_xl_mob="24">

<#if language == "HINDI">

	<#assign url="https://fonts.googleapis.com/css?family=Noto+Sans&subset=devanagari">
	<#assign font="Noto Sans">

	<#assign font_xs="13">
	<#assign font_s="15">
	<#assign font_m="17">
	<#assign font_l="21">
	<#assign font_xl="24">

	<#assign font_xs_mob="13">
	<#assign font_s_mob="14">
	<#assign font_m_mob="16">
	<#assign font_l_mob="21">
	<#assign font_xl_mob="24">

</#if>


<#if language == "BENGALI">

	<#assign url="http://fonts.googleapis.com/earlyaccess/notosansbengali.css">
	<#assign font="Noto Sans Bengali">

	<#assign font_xs="13">
	<#assign font_s="15">
	<#assign font_m="17">
	<#assign font_l="21">
	<#assign font_xl="24">

	<#assign font_xs_mob="13">
	<#assign font_s_mob="14">
	<#assign font_m_mob="16">
	<#assign font_l_mob="21">
	<#assign font_xl_mob="24">

</#if>


<#if language == "MARATHI">

	<#assign url="http://fonts.googleapis.com/earlyaccess/notosansdevanagari.css">
	<#assign font="Noto Sans Devanagari">

	<#assign font_xs="13">
	<#assign font_s="15">
	<#assign font_m="17">
	<#assign font_l="21">
	<#assign font_xl="24">

	<#assign font_xs_mob="13">
	<#assign font_s_mob="14">
	<#assign font_m_mob="16">
	<#assign font_l_mob="21">
	<#assign font_xl_mob="24">

</#if>


<#if language == "GUJARATI">

	<#assign url="http://0.ptlp.co/resource-gu/font/shruti.css">
	<#assign font="Shruti">

	<#assign font_xs="12">
	<#assign font_s="14">
	<#assign font_m="16">
	<#assign font_l="19">
	<#assign font_xl="22">

	<#assign font_xs_mob="12">
	<#assign font_s_mob="13">
	<#assign font_m_mob="15">
	<#assign font_l_mob="19">
	<#assign font_xl_mob="22">

</#if>


<#if language == "TAMIL">

	<#assign url="http://fonts.googleapis.com/earlyaccess/notosanstamil.css">
	<#assign font="Noto Sans Tamil">

	<#assign font_xs="12">
	<#assign font_s="14">
	<#assign font_m="16">
	<#assign font_l="19">
	<#assign font_xl="22">

	<#assign font_xs_mob="12">
	<#assign font_s_mob="13">
	<#assign font_m_mob="15">
	<#assign font_l_mob="19">
	<#assign font_xl_mob="22">

</#if>


<#if language == "MALAYALAM">

	<#assign url="http://fonts.googleapis.com/earlyaccess/notosansmalayalam.css">
	<#assign font="Noto Sans Malayalam">

	<#assign font_xs="13">
	<#assign font_s="15">
	<#assign font_m="17">
	<#assign font_l="21">
	<#assign font_xl="24">

	<#assign font_xs_mob="13">
	<#assign font_s_mob="14">
	<#assign font_m_mob="16">
	<#assign font_l_mob="21">
	<#assign font_xl_mob="24">

</#if>


<#if language == "TELUGU">

	<#assign url="http://fonts.googleapis.com/earlyaccess/notosanstelugu.css">
	<#assign font="Noto Sans Telugu">

	<#assign font_xs="13">
	<#assign font_s="15">
	<#assign font_m="17">
	<#assign font_l="21">
	<#assign font_xl="24">

	<#assign font_xs_mob="13">
	<#assign font_s_mob="14">
	<#assign font_m_mob="16">
	<#assign font_l_mob="21">
	<#assign font_xl_mob="24">

</#if>


<#if language == "KANNADA">

	<#assign url="http://fonts.googleapis.com/earlyaccess/notosanskannada.css">
	<#assign font="Noto Sans Kannada">

	<#assign font_xs="13">
	<#assign font_s="15">
	<#assign font_m="17">
	<#assign font_l="21">
	<#assign font_xl="24">

	<#assign font_xs_mob="13">
	<#assign font_s_mob="14">
	<#assign font_m_mob="16">
	<#assign font_l_mob="21">
	<#assign font_xl_mob="24">

</#if>

<style>
	@import url(${ url });
	*:not(.material-icons) { 
		font-family: "${ font }", "Helvetica", "Arial", sans-serif !important;
		letter-spacing: 0 !important;
	}
	@media only screen and (max-width: 767px) {
		.font-xs {
			font-size: ${ font_xs_mob }px !important;
		}
		.font-s {
			font-size: ${ font_s_mob }px !important;
		}
		.font-m {
			font-size: ${ font_m_mob }px !important;
		}
		.font-l {
			font-size: ${ font_l_mob }px !important;
		}
		.font-xl {
			font-size: ${ font_xl_mob }px !important;
		}
	}
	@media only screen and (min-width: 768px) {
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
	}	
</style>
