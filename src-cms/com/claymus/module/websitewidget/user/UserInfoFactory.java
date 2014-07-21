package com.claymus.module.websitewidget.user;

import com.claymus.module.websitewidget.WebsiteWidgetFactory;

public class UserInfoFactory
		implements WebsiteWidgetFactory<UserInfo, UserInfoProcessor> {
	
	public static UserInfo newUserInfo() {
		
		return new UserInfo() {
			
			@Override
			public Long getId() {
				return null;
			}

			@Override
			public String getPosition() {
				return null;
			}
			
			@Override
			public void setPosition( String position ) { }

		};
		
	}

}
