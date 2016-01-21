<style type="text/css">

	.box {
		margin-top: 0px;
	}
	
	.tftextinput {
		margin: 0;
		padding: 5px 15px;
		font-size:14px;
		border:1px solid #333; border-right:0px;
		border-top-left-radius: 5px 5px;
		border-bottom-left-radius: 5px 5px;
	}
	
	.tfbutton {
		margin: 0;
		padding: 5px 10px;
		font-size:14px;
		outline: none;
		cursor: pointer;
		text-align: center;
		text-decoration: none;
		color: #ffffff;
		border: solid 1px #333; border-right:0px;
		background: #D0021B;
		background: -webkit-gradient(linear, left top, left bottom, from(#d93448), to(#D0021B));
		background: -moz-linear-gradient(top,  #d93448,  #D0021B);
		border-top-right-radius: 5px 5px;
		border-bottom-right-radius: 5px 5px;
	}
	
	.tfbutton:hover {
		text-decoration: none;
		background: #107FE5;
		background: -webkit-gradient(linear, left top, left bottom, from(#6fb2ef), to(#107FE5));
		background: -moz-linear-gradient(top,  #6fb2ef,  #107FE5);
	}
	
	/* Fixes submit button height problem in Firefox */
	.tfbutton::-moz-focus-inner {
	  border: 0;
	}
	
	.pratilipi-white-button {
		background: #FFFFFF;
		border: 1px solid #979797;
		font-size: 13px;
		color: #000000;
		max-width: 90px;
		width: auto;
		margin: 0;
		height: 30px;
		text-align: center;
		line-height: 0px;
		padding: 20px 10px;
		display: inline-block;
		outline: none;
	}
	
</style>

<div class="box" style="padding: 10px; height:60px;">
<table style="min-width: 100%; white-space: nowrap;">
	<tr>
		<td align="left" width="65px">
			<a href="/">
				<img style="max-width: 60px;" title="${ _strings.pratilipi }" alt="${ _strings.pratilipi }" src="https://storage.googleapis.com/devo-pratilipi.appspot.com/Logo-for-Site-Header.png" />
			</a>
		</td>
		
		<td width="auto" align="center">
			<div style="width: 100%; margin-top: 20px; margin-left: auto; margin-right: auto;">
				<div style="width: 100%; display: table;">
				    <form method="get" action="/search">
				    	<input type="text" class="tftextinput" name="q" style="display: table-cell; width:60%;" maxlength="120" /><input type="submit" value="search" class="tfbutton" style="display: table-cell; width:60px;" />
		        	</form>
				</div>
			</div>
		</td>
		
		<td align="right" width="90px">
			<#--<template is="dom-if" if="{{ user.isGuest }}">
				<button type="button" class="pratilipi-white-button" on-click="logInButtonClicked"><small>${ _strings.user_sign_in }</small></button>
			</template>
			<template is="dom-if" if="{{ !user.isGuest }}">
				<div class="dropdown">
					<button style="width: 90px;" class="pratilipi-white-button dropdown-toggle" type="button" data-toggle="dropdown">
						<span style="float: left; width: 70px; text-overflow: ellipsis; overflow: hidden; white-space: nowrap; display: inline-block;">{{ user.displayName }}</span>
					<span class="caret"></span></button>
					<ul class="dropdown-menu pull-right">
						<li>
							<a style="padding: 5px; margin: 0;" href="#">
								<paper-item on-click="myProfile">
									<paper-icon-item><iron-icon style="color: #107FE5;" icon="description"></iron-icon></paper-icon-item>
									&nbsp;&nbsp;
									<paper-item-body>${ _strings.user_my_profile }</paper-item-body>
								</paper-item>
							</a>
						</li>
						<li>
							<a style="padding: 5px; margin: 0;" href="#">
								<paper-item on-click="myAccount">
									<paper-icon-item><iron-icon style="color: #107FE5;" icon="account-circle"></iron-icon></paper-icon-item>
									&nbsp;&nbsp;
									<paper-item-body>${ _strings.user_my_account }</paper-item-body>
								</paper-item>
							</a>
						</li>
						<li>
							<a style="padding: 5px; margin: 0;" href="#">
								<paper-item on-click="logOut">
									<paper-icon-item><iron-icon style="color: #D0021B;" icon="remove-circle"></iron-icon></paper-icon-item>
									&nbsp;&nbsp;
									<paper-item-body>${ _strings.user_sign_out }</paper-item-body>
								</paper-item>
							</a>
						</li>
					</ul>
				</div>
			</template> -->
			<div style="margin-top: 12px;">
				<button type="button" style="cursor: pointer;" class="pratilipi-white-button">${ _strings.user_sign_in }</button>
			</div>
		</td>
	</tr>
</table>
</div>

















