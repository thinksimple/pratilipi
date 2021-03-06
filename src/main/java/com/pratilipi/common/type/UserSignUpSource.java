package com.pratilipi.common.type;

public enum UserSignUpSource {

	// Pre-Launch Website
	PRE_LAUNCH_WEBSITE,			// UserStatus.PRELAUNCH_REFERRAL || UserStatus.PRELAUNCH_SIGNUP

	// Mark-1/2/3/4/5
	WEBSITE,					// UserStatus.POSTLAUNCH_REFERRAL || UserStatus.POSTLAUNCH_SIGNUP
	WEBSITE_FACEBOOK,			// UserStatus.POSTLAUNCH_SIGNUP_SOCIALLOGIN

	// Mark-6/6+
	WEBSITE_M6,
	WEBSITE_M6_FACEBOOK,
	WEBSITE_M6_GOOGLE,
	@Deprecated
	WEBSITE_M6_TAMIL,
	@Deprecated
	WEBSITE_M6_TAMIL_FACEBOOK,

	// Android App
	ANDROID_APP,				// UserStatus.ANDROID_SIGNUP
	ANDROID_APP_FACEBOOK,		// UserStatus.ANDROID_SIGNUP_FACEBOOK
	ANDROID_APP_GOOGLE,			// UserStatus.ANDROID_SIGNUP_GOOGLE

}
