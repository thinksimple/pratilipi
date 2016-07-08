package com.pratilipi.api.impl.author;

import com.google.gson.Gson;
import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Get;
import com.pratilipi.api.annotation.Post;
import com.pratilipi.api.annotation.Validate;
import com.pratilipi.api.impl.pratilipi.PratilipiApi;
import com.pratilipi.api.impl.pratilipi.PratilipiListApi;
import com.pratilipi.api.impl.user.UserApi;
import com.pratilipi.api.impl.userauthor.UserAuthorFollowListApi;
import com.pratilipi.api.shared.GenericRequest;
import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.exception.InsufficientAccessException;
import com.pratilipi.common.exception.InvalidArgumentException;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.common.type.AuthorState;
import com.pratilipi.common.type.Gender;
import com.pratilipi.common.type.Language;
import com.pratilipi.data.DataAccessor;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.client.AuthorData;
import com.pratilipi.data.type.Author;
import com.pratilipi.data.util.AuthorDataUtil;
import com.pratilipi.filter.UxModeFilter;
import com.pratilipi.taskqueue.Task;
import com.pratilipi.taskqueue.TaskQueueFactory;

@SuppressWarnings( "serial" )
@Bind( uri = "/author" )
public class AuthorApi extends GenericApi {
	
	public static class GetRequest extends GenericRequest {
		
		@Validate( required = true )
		private Long authorId;
		
		
		public void setAuthorId( Long authorId ) {
			this.authorId = authorId;
		}

	}
	
	public static class PostRequest extends GenericRequest {
		
		private Long authorId;
		
		private String firstName;
		private boolean hasFirstName;
		
		private String lastName;
		private boolean hasLastName;
		
		private String penName;
		private boolean hasPenName;
		
		private String firstNameEn;
		private boolean hasFirstNameEn;
		
		private String lastNameEn;
		private boolean hasLastNameEn;
		
		private String penNameEn;
		private boolean hasPenNameEn;
		

		private Gender gender;
		private boolean hasGender;
		
		private String dateOfBirth;
		private boolean hasDateOfBirth;

		
		private Language language;
		private boolean hasLanguage;
		
		private String summary;
		private boolean hasSummary;
		

		
		public Long getId() {
			return authorId;
		}
		

		public String getFirstName() {
			return firstName;
		}
		
		public boolean hasFirstName() {
			return hasFirstName;
		}
		
		public String getLastName() {
			return lastName;
		}
		
		public boolean hasLastName() {
			return hasLastName;
		}
		
		public String getPenName() {
			return penName;
		}
		
		public boolean hasPenName() {
			return hasPenName;
		}
		
		public String getFirstNameEn() {
			return firstNameEn;
		}
		
		public boolean hasFirstNameEn() {
			return hasFirstNameEn;
		}
		
		public String getLastNameEn() {
			return lastNameEn;
		}
		
		public boolean hasLastNameEn() {
			return hasLastNameEn;
		}
		
		public String getPenNameEn() {
			return penNameEn;
		}
		
		public boolean hasPenNameEn() {
			return hasPenNameEn;
		}
		
		
		public Gender getGender() {
			return gender;
		}
		
		public boolean hasGender() {
			return hasGender;
		}
		
		public String getDateOfBirth() {
			return dateOfBirth;
		}
		
		public boolean hasDateOfBirth() {
			return hasDateOfBirth;
		}
		
		
		public Language getLanguage() {
			return language;
		}

		public boolean hasLanguage() {
			return hasLanguage;
		}
		
		public String getSummary() {
			return summary;
		}

		public boolean hasSummary() {
			return hasSummary;
		}
		
	}
	
	public static class Response extends GenericResponse {

		private Long authorId;

		private UserApi.Response user;
		
		private String firstName;
		private String lastName;
		private String penName;
		private String name;
		private String fullName;

		private String firstNameEn;
		private String lastNameEn;
		private String penNameEn;
		private String nameEn;
		private String fullNameEn;

		private Gender gender;
		private String dateOfBirth;
		
		private Language language;
		private String summary;
		
		private String pageUrl;
		private String imageUrl;

		private Long registrationDateMillis;
		
		private Long followCount;
		private Integer contentPublished;
		private Integer contentDrafted;
		private Long totalReadCount;
		private Long totalFbLikeShareCount;
		
		private Boolean following;
		private Boolean hasAccessToUpdate;
		
		
		@SuppressWarnings("unused")
		private Response() { }
		
		// TODO: change access level to package level ASAP
		public Response( AuthorData authorData ) {
			
			this.authorId = authorData.getId();
			
			this.user = authorData.getUser() == null ? null : new UserApi.Response( authorData.getUser(), AuthorApi.class );
			
			this.firstName = authorData.getFirstName();
			this.lastName = authorData.getLastName();
			this.penName = authorData.getPenName();
			this.name = authorData.getName();
			this.fullName = authorData.getFullName();
			
			this.firstNameEn = authorData.getFirstNameEn();
			this.lastNameEn = authorData.getLastNameEn();
			this.penNameEn = authorData.getPenNameEn();
			this.nameEn = authorData.getNameEn();
			this.fullNameEn = authorData.getFullNameEn();
			
			this.gender = authorData.getGender();
			this.dateOfBirth = authorData.getDateOfBirth();
			
			this.language = authorData.getLanguage();
			this.summary = authorData.getSummary();
			
			this.pageUrl = authorData.getPageUrl();
			this.imageUrl = authorData.getImageUrl();
			
			this.registrationDateMillis = authorData.getRegistrationDate().getTime();
			
			this.followCount = authorData.getFollowCount();
			this.contentPublished = authorData.getContentPublished();
			this.contentDrafted = authorData.getContentDrafted();
			this.totalReadCount = authorData.getTotalReadCount();
			this.totalFbLikeShareCount = authorData.getTotalFbLikeShareCount();

			this.hasAccessToUpdate = authorData.hasAccessToUpdate();
			
		}
		
		public Response( AuthorData authorData, Class<? extends GenericApi> clazz ) {
			
			if( clazz == PratilipiApi.class || clazz == PratilipiListApi.class ) {

				if( UxModeFilter.isAndroidApp() )
					this.authorId = authorData.getId();
				this.name = authorData.getName() == null
						? authorData.getNameEn()
						: authorData.getName();
				this.pageUrl = authorData.getPageUrl();
			
			} else if( clazz == UserAuthorFollowListApi.class ) {
				
				if( authorData.getUser() == null ) {
					this.followCount = authorData.getFollowCount();
					this.following = authorData.isFollowing();
				} else {
					this.user = new UserApi.Response( authorData.getUser(), clazz );
					this.name = authorData.getName() == null
							? authorData.getNameEn()
							: authorData.getName();
					this.pageUrl = authorData.getPageUrl();
					this.imageUrl = authorData.getImageUrl();
					this.followCount = authorData.getFollowCount();
					this.following = authorData.isFollowing();
				}
				
			}
			
		}
		

		public Long getId() {
			return authorId;
		}
		
		public UserApi.Response getUser() {
			return user;
		}
		
		public String getFirstName() {
			return firstName;
		}
		
		public String getLastName() {
			return lastName;
		}
			
		public String getPenName() {
			return penName;
		}
		
		public String getName() {
			return name;
		}
		
		public String getFullName() {
			return fullName;
		}
		
		
		public String getFirstNameEn() {
			return firstNameEn;
		}
		
		public String getLastNameEn() {
			return lastNameEn;
		}
		
		public String getPenNameEn() {
			return penNameEn;
		}
		
		public String getNameEn() {
			return nameEn;
		}
		
		public String getFullNameEn() {
			return fullNameEn;
		}
		
		
		public Gender getGender() {
			return gender;
		}
		
		public String getDateOfBirth() {
			return dateOfBirth;
		}
		
		
		public Language getLanguage() {
			return language;
		}
		
		public String getSummary() {
			return summary;
		}
		
		
		public String getPageUrl() {
			return pageUrl;
		}
		
		public String getImageUrl() {
			return imageUrl;
		}
		
		public String getImageUrl( int width ) {
			return imageUrl.indexOf( '?' ) == -1
					? imageUrl + "?width=" + width
					: imageUrl + "&width=" + width;
		}
		
		
		public Long getRegistrationDateMillis() {
			return registrationDateMillis;
		}
		
		
		public Long getFollowCount() {
			return followCount;
		}
		
		public Integer getContentDrafted() {
			return contentDrafted;
		}
		
		public Integer getContentPublished() {
			return contentPublished;
		}
		
		public Long getTotalReadCount() {
			return totalReadCount;
		}
		
		public Long getTotalFbLikeShareCount() {
			return totalFbLikeShareCount;
		}
		
		
		public boolean isFollowing() {
			return following;
		}
		
		public boolean hasAccessToUpdate() {
			return hasAccessToUpdate;
		}
		
	}
	
	
	@Get
	public Response get( GetRequest request ) {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		Author author = dataAccessor.getAuthor( request.authorId );
		AuthorData authorData = AuthorDataUtil.createAuthorData( author );
		return new Response( authorData );
		
	}
	
	@Post
	public Response post( PostRequest request ) 
			throws InvalidArgumentException, InsufficientAccessException, UnexpectedServerException {
		
		Gson gson = new Gson();

		AuthorData authorData = gson.fromJson( gson.toJson( request ), AuthorData.class );
		if( request.getId() == null ) // New authors (added by AEEs) are ACTIVE by default
			authorData.setState( AuthorState.ACTIVE );
		authorData = AuthorDataUtil.saveAuthorData( authorData );
		
		Task task = TaskQueueFactory.newTask()
				.setUrl( "/author/process" )
				.addParam( "authorId", authorData.getId().toString() )
				.addParam( "processData", "true" );
		TaskQueueFactory.getAuthorTaskQueue().add( task );

		return new Response( authorData );
		
	}
	
}
