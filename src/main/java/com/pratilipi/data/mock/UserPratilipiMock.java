package com.pratilipi.data.mock;

import static com.pratilipi.data.mock.PratilipiMock.*;
import static com.pratilipi.data.mock.UserMock.*;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.pratilipi.data.type.UserPratilipi;
import com.pratilipi.data.type.gae.UserPratilipiEntity;

public class UserPratilipiMock {

	public static final List<UserPratilipi> USER_PRATILIPI_TABLE = new LinkedList<>();

	public static final UserPratilipi user_pratilipi_1 = new UserPratilipiEntity( user_1.getId(), hiPratilipi_1.getId() );
	public static final UserPratilipi user_pratilipi_2 = new UserPratilipiEntity( user_2.getId(), hiPratilipi_1.getId() );
	public static final UserPratilipi user_pratilipi_3 = new UserPratilipiEntity( user_3.getId(), hiPratilipi_1.getId() );

	
	static {
		USER_PRATILIPI_TABLE.add( user_pratilipi_1 );
		USER_PRATILIPI_TABLE.add( user_pratilipi_2 );
		USER_PRATILIPI_TABLE.add( user_pratilipi_3 );

		
		user_pratilipi_1.setRating( 3 );
		user_pratilipi_1.setRatingDate( new Date() );
		user_pratilipi_1.setReviewTitle( "Review 1" );
		user_pratilipi_1.setReview( "Just love it !!!" );
		user_pratilipi_1.setReviewDate( new Date() );

		user_pratilipi_2.setRating( 3 );
		user_pratilipi_2.setRatingDate( new Date() );
		user_pratilipi_2.setReviewTitle( "Review 2" );
		user_pratilipi_2.setReview( "Just love it !!!" );
		user_pratilipi_2.setReviewDate( new Date() );

		user_pratilipi_3.setRating( 3 );
		user_pratilipi_3.setRatingDate( new Date() );
		user_pratilipi_3.setReviewTitle( "Review 3" );
		user_pratilipi_3.setReview( "Just love it !!!" );
		user_pratilipi_3.setReviewDate( new Date() );
	}
	
}
