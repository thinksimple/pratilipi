package com.pratilipi.common.type;

import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.type.Notification;

public enum NotificationType {
	
	GENERIC( true, "NOTIFICATION_GENERIC" ) {
		@Override
		public boolean isValid( Notification notif ) {
			return true;
		}
	},

	
	PRATILIPI( true, "NOTIFICATION_BOOK" ) { // Custom notification by AEEs
		@Override
		public boolean isValid( Notification notif ) {
			return DataAccessorFactory.getDataAccessor()
					.getPratilipi( notif.getSourceIdLong() )
					.getState() == PratilipiState.PUBLISHED;
		}
	},

	
	PRATILIPI_PUBLISHED_AUTHOR( true, "NOTIFICATION_BOOK" ) { // Notification sent to the Author when a content is published
		@Override
		public boolean isValid( Notification notif ) {
			return DataAccessorFactory.getDataAccessor()
					.getPratilipi( notif.getSourceIdLong() )
					.getState() == PratilipiState.PUBLISHED;
		}
	},

	PRATILIPI_PUBLISHED_FOLLOWER( true, "NOTIFICATION_BOOK" ) { // Notification sent to Author's followers when a content is published.
		@Override
		public boolean isValid( Notification notif ) {
			return DataAccessorFactory.getDataAccessor()
					.getPratilipi( notif.getSourceIdLong() )
					.getState() == PratilipiState.PUBLISHED;
		}
	},

	
	AUTHOR_FOLLOW( true, "NOTIFICATION_FOLLOWERS" ) { // Notification sent to the Author when a user follows his profile.
		@Override
		public boolean isValid( Notification notif ) {
			return ! notif.getDataIds().isEmpty();
		}
	},
	
	
	USER_PRATILIPI_REVIEW( true, "NOTIFICATION_REVIEW" ) { // Notification sent to the Author when a user reviews his content.
		@Override
		public boolean isValid( Notification notif ) {
			return DataAccessorFactory.getDataAccessor()
					.getUserPratilipi( notif.getSourceId() )
					.getReviewState() == UserReviewState.PUBLISHED;
		}
	},

	
	COMMENT_REVIEW_REVIEWER( true, null ) { // Notification sent to the Reviewer when a user comments on it.
		@Override
		public boolean isValid( Notification notif ) {
			return false;
		}
	},
	
	COMMENT_REVIEW_AUTHOR( true, null ) { // Notification sent to the Author when a user comments on a (his content's) review.
		@Override
		public boolean isValid( Notification notif ) {
			return false;
		}
	},

	
	VOTE_REVIEW_REVIEWER( true, null ) { // Notification sent to the Reviewer when a user likes it.
		@Override
		public boolean isValid( Notification notif ) {
			return false;
		}
	},
	
	VOTE_REVIEW_AUTHOR( true, null ) { // Notification sent to the Author when a user likes a (his content's) review.
		@Override
		public boolean isValid( Notification notif ) {
			return false;
		}
	},
	
	
	VOTE_COMMENT_REVIEW_COMMENTOR( true, null ) { // Notification sent to the Commenter when a user likes a (content's review's) comment.
		@Override
		public boolean isValid( Notification notif ) {
			return false;
		}
	},

	;
	
	
	
	private boolean subscriptionDefault;
	private String androidHandler;
	
	public abstract boolean isValid( Notification notif );
	
	
	private NotificationType( boolean subscriptionDefault, String androidHandler ) {
		this.subscriptionDefault = subscriptionDefault;
		this.androidHandler = androidHandler;
	}
	

	public boolean getSubscriptionDefault() {
		return subscriptionDefault;
	}
	
	public String getAndroidHandler() {
		return androidHandler;
	}
	
}
