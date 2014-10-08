package com.claymus.data.access;

import java.util.List;

import javax.jdo.JDOHelper;
import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

import com.claymus.data.access.gae.EmailTemplateEntity;
import com.claymus.data.access.gae.PageContentEntity;
import com.claymus.data.access.gae.PageEntity;
import com.claymus.data.access.gae.PageLayoutEntity;
import com.claymus.data.access.gae.RoleAccessEntity;
import com.claymus.data.access.gae.RoleEntity;
import com.claymus.data.access.gae.UserEntity;
import com.claymus.data.access.gae.UserRoleEntity;
import com.claymus.data.transfer.EmailTemplate;
import com.claymus.data.transfer.Page;
import com.claymus.data.transfer.PageContent;
import com.claymus.data.transfer.PageLayout;
import com.claymus.data.transfer.Role;
import com.claymus.data.transfer.RoleAccess;
import com.claymus.data.transfer.User;
import com.claymus.data.transfer.UserRole;

public class DataAccessorGaeImpl implements DataAccessor {

	private static final PersistenceManagerFactory pmfInstance =
			JDOHelper.getPersistenceManagerFactory( "transactions-optional" );


	protected final PersistenceManager pm;
	
	
	public DataAccessorGaeImpl() {
		this.pm = pmfInstance.getPersistenceManager();
	}

	
	protected <T> T getEntity( Class<T> clazz, Object id ) {
		T entity = (T) pm.getObjectById( clazz, id );
		return pm.detachCopy( entity );
	}

	protected <T> T createOrUpdateEntity( T entity ) {
		entity = pm.makePersistent( entity );
		return pm.detachCopy( entity );
	}
	
	protected <T> void deleteEntity( Class<T> clazz, Object id ) {
		T entity = (T) pm.getObjectById( clazz, id );
		pm.deletePersistent( entity );
	}

	
	@Override
	public User newUser() {
		return new UserEntity();
	}
	
	@Override
	public User getUser( Long id ) {
		return getEntity( UserEntity.class, id );
	}
	
	@Override
	public User getUserByEmail( String email ) {
		Query query =
				new GaeQueryBuilder( pm.newQuery( UserEntity.class ) )
						.addFilter( "email", email )
						.build();
		
		@SuppressWarnings("unchecked")
		List<User> userList = (List<User>) query.execute( email );
		return userList.size() == 0 ? null : pm.detachCopy( userList.get( 0 ) );
	}
	
	@Override
	public User createOrUpdateUser( User user ) {
		return createOrUpdateEntity( user );
	}

	
	@Override
	public Role newRole() {
		return new RoleEntity();
	}

	@Override
	public Role getRole( Long id ) {
		return getEntity( RoleEntity.class, id );
	}

	@Override
	public Role createOrUpdateRole( Role role ) {
		return createOrUpdateEntity( role );
	}


	@Override
	public UserRole newUserRole() {
		return new UserRoleEntity();
	}

	@Override
	public List<UserRole> getUserRoleList( Long userId ) {
		Query query =
				new GaeQueryBuilder( pm.newQuery( UserRoleEntity.class ) )
						.addFilter( "userId", userId )
						.build();
		
		@SuppressWarnings("unchecked")
		List<UserRole> userRoleList = (List<UserRole>) query.execute( userId );
		return (List<UserRole>) pm.detachCopyAll( userRoleList );
	}
	
	@Override
	public UserRole createOrUpdateUserRole( UserRole userRole ) {
		( (UserRoleEntity) userRole ).setId(
				userRole.getUserId() + "-" + userRole.getRoleId() );
		return createOrUpdateEntity( userRole );
	}

	
	@Override
	public RoleAccess newRoleAccess() {
		return new RoleAccessEntity();
	}

	@Override
	public RoleAccess getRoleAccess( String roleId, String accessId ) {
		try {
			return getEntity( RoleAccessEntity.class, roleId + "-"  + accessId );
		} catch( JDOObjectNotFoundException e ) {
			return null;
		}
	}

	@Override
	public RoleAccess createOrUpdateRoleAccess( RoleAccess roleAccess ) {
		( (RoleAccessEntity) roleAccess ).setId(
				roleAccess.getRoleId() + "-" + roleAccess.getAccessId() );
		return createOrUpdateEntity( roleAccess );
	}

	
	@Override
	public Page newPage() {
		return new PageEntity();
	}
	
	@Override
	public Page getPage( String uri ) {
		Query query =
				new GaeQueryBuilder( pm.newQuery( PageEntity.class ) )
						.addFilter( "uri", uri )
						.addOrdering( "creationDate", false )
						.setRange( 0, 1 )
						.build();
		
		@SuppressWarnings("unchecked")
		List<Page> pageList = (List<Page>) query.execute( uri );
		return pageList.size() == 0 ? null : pm.detachCopy( pageList.get( 0 ) );
	}
	
	@Override
	public Page createOrUpdatePage( Page page ) {
		return createOrUpdateEntity( page );
	}

	
	@Override
	public List<PageContent> getPageContentList( Long pageId ) {
		Query query =
				new GaeQueryBuilder( pm.newQuery( PageContentEntity.class ) )
						.addFilter( "pageId", pageId )
						.build();
		
		@SuppressWarnings("unchecked")
		List<PageContent> pageContentList = (List<PageContent>) query.execute( pageId );
		return pageContentList.size() == 0 ? null : (List<PageContent>) pm.detachCopyAll( pageContentList );
	}

	@Override
	public PageContent createOrUpdatePageContent( PageContent pageContent ) {
		return createOrUpdateEntity( pageContent );
	}


	@Override
	public PageLayout newPageLayout() {
		return new PageLayoutEntity();
	}

	@Override
	public PageLayout getPageLayout( Long id ) {
		return getEntity( PageLayoutEntity.class, id );
	}

	@Override
	public PageLayout createOrUpdatePageLayout( PageLayout pageLayout ) {
		return createOrUpdateEntity( pageLayout );
	}

	
	@Override
	public EmailTemplate newEmailTemplate() {
		return new EmailTemplateEntity();
	}
	

	@Override
	public void destroy() {
		this.pm.close();
	}

}
