package com.claymus.module.pagecontent;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.claymus.data.transfer.PageContent;

public final class PageContentRegistry {

	private static final Logger logger = 
			Logger.getLogger( PageContentRegistry.class.getName() );

	
	private final Map<
			Class<? extends PageContent>,
			PageContentProcessor<? extends PageContent> > map = new HashMap<>();


	@SuppressWarnings("unchecked")
	public <
			P extends PageContent,
			Q extends PageContentProcessor<P>,
			R extends PageContentFactory<P, Q> > void register( Class<R> pageContentFactoryClass ) {
		
		ParameterizedType parameterizedType =
				(ParameterizedType) pageContentFactoryClass.getGenericInterfaces()[0];
		
		Class<P> pageContentClass =
				(Class<P>) parameterizedType.getActualTypeArguments()[0];
		Class<Q> pageContentProcessorClass = 
				(Class<Q>) parameterizedType.getActualTypeArguments()[1];
		
		try {
			Q pageContentProcessor = pageContentProcessorClass.newInstance();
			map.put( pageContentClass, pageContentProcessor );
		} catch ( InstantiationException | IllegalAccessException e ) {
			logger.log( Level.SEVERE, "Module registration failed.", e );
		}
	}
	
	@SuppressWarnings("unchecked")
	public <P extends PageContent> PageContentProcessor<P> getPageContentProcessor(
			Class<P> pageContentClass ) {
		
		if( pageContentClass.isInterface() )
			return (PageContentProcessor<P>) map.get( pageContentClass );
		else
			return (PageContentProcessor<P>) map.get( pageContentClass.getInterfaces()[0] );
	}
	
}
