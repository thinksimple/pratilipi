<#-- Default Font -->
<#assign url="https://fonts.googleapis.com/css?family=Montserrat">
<#assign font="Montserrat">

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
<style>@import url(${ url });*{font-family: "${ font }", "Helvetica", "Arial", sans-serif;}</style>