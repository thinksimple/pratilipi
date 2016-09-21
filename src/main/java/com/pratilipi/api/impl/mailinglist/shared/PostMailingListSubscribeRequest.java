package com.pratilipi.api.impl.mailinglist.shared;

import com.pratilipi.api.annotation.Validate;
import com.pratilipi.api.shared.GenericRequest;
import com.pratilipi.common.type.MailingList;

public class PostMailingListSubscribeRequest extends GenericRequest {

	@Validate( required = true )
	private MailingList mailingList;

	@Validate( required = true, regEx = REGEX_EMAIL, regExErrMsg = ERR_EMAIL_INVALID )
	private String email;

	@Validate( regEx = REGEX_PHONE, regExErrMsg = ERR_PHONE_INVALID )
	private String phone;

	private String comment;


	public MailingList getMailingList() {
		return mailingList;
	}

	public String getEmail() {
		return email;
	}

	public String getPhone() {
		return phone;
	}

	public String getComment() {
		return comment;
	}

}
