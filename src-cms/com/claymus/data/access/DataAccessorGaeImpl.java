package com.claymus.data.access;

import java.util.List;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

import com.claymus.data.access.gae.BlobEntryEntity;
import com.claymus.data.access.gae.PageContentEntity;
import com.claymus.data.access.gae.PageEntity;
import com.claymus.data.access.gae.PageLayoutEntity;
import com.claymus.data.access.gae.UserEntity;
import com.claymus.data.access.gae.RoleEntity;
import com.claymus.data.transfer.BlobEntry;
import com.claymus.data.transfer.Page;
import com.claymus.data.transfer.PageContent;
import com.claymus.data.transfer.PageLayout;
import com.claymus.data.transfer.User;
import com.claymus.data.transfer.Role;

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
	
	
	@Override
	public User newUser() {
		return new UserEntity();
	}
	
	@Override
	public User getUser( String id ) {
		return getEntity( UserEntity.class, id );
	}
	
	@Override
	public User createUser( User user ) {
		// TODO: user transaction
		// TODO: throw exception if a user with given id already exists
		return createOrUpdateEntity( user );
	}

	@Override
	public User updateUser( User user ) {
		// TODO: throw exception if user id is not set
		// TODO: throw exception if entity with give id doesn't exist
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
	public BlobEntry newBlobEntry() {
		return new BlobEntryEntity();
	}

	@Override
	public BlobEntry getBlobEntry( String name ) {
		Query query =
				new GaeQueryBuilder( pm.newQuery( BlobEntryEntity.class ) )
						.addFilter( "name", name )
						.addOrdering( "creationDate", false )
						.setRange( 0, 1 )
						.build();
		
		@SuppressWarnings("unchecked")
		List<BlobEntry> blobEntryList = (List<BlobEntry>) query.execute( name );
		return blobEntryList.size() == 0 ? null : pm.detachCopy( blobEntryList.get( 0 ) );
	}

	@Override
	public BlobEntry createBlobEntry( BlobEntry blobEntry ) {
		( (BlobEntryEntity) blobEntry ).setId( null );
		return createOrUpdateEntity( blobEntry );
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
	public void destroy() {
		this.pm.close();
	}

}
