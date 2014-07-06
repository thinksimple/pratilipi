package com.claymus.data.access;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

import com.claymus.data.access.gae.UserEntity;
import com.claymus.data.access.gae.UserRoleEntity;
import com.claymus.data.transfer.User;
import com.claymus.data.transfer.UserRole;

public class DataAccessorGAEImpl implements DataAccessor {

	private static final PersistenceManagerFactory pmfInstance =
			JDOHelper.getPersistenceManagerFactory( "transactions-optional" );


	protected final PersistenceManager pm;
	
	
	public DataAccessorGAEImpl() {
		this.pm = pmfInstance.getPersistenceManager();
	}

	
	private <T> T getEntity( Class<T> clazz, Object id ) {
		T entity = (T) pm.getObjectById( clazz, id );
		return pm.detachCopy( entity );
	}

	private <T> T createOrUpdateEntity( T entity ) {
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
	public void destroy() {
		this.pm.close();
	}

}
