package com.claymus.module.pagecontent;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.claymus.data.transfer.PageContent;

public class PageContentRegistry {

	private static final Logger logger = 
			Logger.getLogger( PageContentRegistry.class.getName() );

	
	private static final Map<
			Class<? extends PageContent>,
			PageContentProcessor<? extends PageContent> > map = new HashMap<>();

	@SuppressWarnings("rawtypes")
	private static final List<PageContentFactory> list = new LinkedList<>();
	

	public static <
			P extends PageContent,
			Q extends PageContentProcessor<P>,
			R extends PageContentFactory<P, Q> > void register( Class<R> pageContentFactoryClass ) {
		
		ParameterizedType parameterizedType =
				(ParameterizedType) pageContentFactoryClass.getGenericInterfaces()[0];
		
		@SuppressWarnings("unchecked")
		Class<P> pageContentClass =
				(Class<P>) parameterizedType.getActualTypeArguments()[0];
		@SuppressWarnings("unchecked")
		Class<Q> pageContentProcessorClass = 
				(Class<Q>) parameterizedType.getActualTypeArguments()[1];
		
		try {
			Q pageContentProcessor = pageContentProcessorClass.newInstance();
			R pageContentHelper = pageContentFactoryClass.newInstance();
			map.put( pageContentClass, pageContentProcessor );
			list.add( pageContentHelper );
		} catch ( InstantiationException | IllegalAccessException e ) {
			logger.log( Level.SEVERE, "Module registration failed.", e );
		}
	}
	
	@SuppressWarnings("unchecked")
	public static <P extends PageContent> PageContentProcessor<P> getPageContentProcessor(
			Class<P> pageContentClass ) {
		
		if( pageContentClass.isInterface() )
			return (PageContentProcessor<P>) map.get( pageContentClass );
		else
			return (PageContentProcessor<P>) map.get( pageContentClass.getInterfaces()[0] );
	}
	
	@SuppressWarnings("rawtypes")
	public static List<PageContentFactory> getPageContentHelperList() {
		return list;
	}
	
}
