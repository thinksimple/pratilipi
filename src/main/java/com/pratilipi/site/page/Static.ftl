<!DOCTYPE html>
<html lang="${lang}">

	<head>
		<#include "meta/Head.ftl">
	</head>
	
	<body class="fullbleed layout vertical">
		<template is="dom-bind">
			<paper-header-panel class="flex" mode="waterfall">
				<div class="paper-header">
					<pratilipi-user user='{{ user }}' user-data='${ userJson }'></pratilipi-user>
					<pratilipi-header user='[[ user ]]'></pratilipi-header>
					<pratilipi-edit-account user='[[ user ]]'></pratilipi-edit-account>
					<pratilipi-write pratilipi-types='${ pratilipiTypesJson }'></pratilipi-write>
				</div>
				<div class="fit" style="margin-top: 5px;">
					<div class="parent-container">
						<div class="container">
							<pratilipi-navigation
								class='pull-left hidden-xs hidden-sm'
								></pratilipi-navigation>
							<div class='secondary-500' style='padding:20px; overflow:hidden'>
								<h3>${ title }</h3>
								<div>${ content }</div>
							</div>
						</div>
					</div>
					<pratilipi-footer></pratilipi-footer>
				</div>
			</paper-header-panel>
		</template>
	</body>
</html>
