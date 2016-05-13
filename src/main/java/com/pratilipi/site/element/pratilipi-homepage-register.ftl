<div class="modal modal-fullscreen fade" id="pratilipiUserRegister" role="dialog">
	<div class="modal-dialog" role="document">
		<div class="modal-content" on-keyup="onSubmit">
			<div class="modal-fullscreen-close-button">
				<paper-icon-button noink icon="close" data-dismiss="modal"></paper-icon-button>
			</div>
			<img title="${ _strings.pratilipi }" alt="${ _strings.pratilipi }" src="http://0.ptlp.co/resource-${ lang }/logo/pratilipi_logo.png" />
			<pratilipi-facebook-login></pratilipi-facebook-login>
			<div style="height: 2px; background-color: #FFF; text-align: center; margin-top: 30px; margin-bottom: 10px;"></div>
			<h6 class="modal-fullscreen-heading">${ _strings.user_sign_up_for_pratilipi }</h6>
			<pratilipi-input label="${ _strings.user_full_name }" value="{{ params.name::input }}" icon="icons:account-box"></pratilipi-input>
			<pratilipi-input label="${ _strings.user_email }" type="email" value="{{ params.email::input }}" icon="icons:mail"></pratilipi-input>
			<pratilipi-input label="${ _strings.user_password }" type="password" value="{{ params.password::input }}" icon="icons:lock"></pratilipi-input>
			<div class="display-message-div">
				<p>{{ message }}</p>
			</div>
			<button id="registerButton" class="pratilipi-blue-button" on-click="onSubmit">${ _strings.user_sign_up }</button>
			<p style="display: block; margin-top: 15px; text-align: center;">
				${ _strings.register_part_1 }
				<a class="pratilipi-blue" style="white-space: nowrap; font-size: 16px;" href="/privacy-policy">privacy policy</a>
				&nbsp;${ _strings.register_part_2 }&nbsp;
				<a class="pratilipi-blue" style="white-space: nowrap; font-size: 16px;" href="/terms-of-service">terms of service</a>
				&nbsp;${ _strings.register_part_3 }
			</p>
		</div>
	</div>
	<div class="modal-footer">
		<div class="text-center" style="display: block;">
			<span class="text-muted">${ _strings.user_is_registered }&nbsp;</span>
			<a class="pratilipi-blue" on-click="logIn">${ _strings.user_to_sign_in }</a>
		</div>
	</div>
</div>