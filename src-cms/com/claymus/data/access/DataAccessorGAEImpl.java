package com.claymus.data.access;

import java.util.List;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

import com.claymus.data.access.gae.BlobEntryEntity;
import com.claymus.data.access.gae.UserEntity;
import com.claymus.data.access.gae.UserRoleEntity;
import com.claymus.data.transfer.BlobEntry;
import com.claymus.data.transfer.User;
import com.claymus.data.transfer.UserRole;

public class DataAccessorGAEImpl implements DataAccessor {

	private static final PersistenceManagerFactory pmfInstance =
			JDOHelper.getPersistenceManagerFactory( "transactions-optional" );


	protected final PersistenceManager pm;
	
	
	public DataAccessorGAEImpl() {
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
	public UserRole newUserRole() {
		return new UserRoleEntity();
	}

	@Override
	public UserRole getUserRole( Long id ) {
		return getEntity( UserRoleEntity.class, id );
	}

	@Override
	public UserRole createOrUpdateUserRole( UserRole userRole ) {
		return createOrUpdateEntity( userRole );
	}


	@Override
	public BlobEntry newBlobEntry() {
		return new BlobEntryEntity();
	}

	@Override
	public BlobEntry getBlobEntry( String name ) {
		Query query =
				new GAEQueryBuilder( pm.newQuery( BlobEntryEntity.class ) )
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
	public void destroy() {
		this.pm.close();
	}

}
