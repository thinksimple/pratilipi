package com.claymus.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.claymus.data.transfer.Page;
import com.claymus.data.transfer.PageContent;
import com.claymus.data.transfer.PageLayout;
import com.claymus.data.transfer.WebsiteLayout;
import com.claymus.data.transfer.WebsiteWidget;
import com.claymus.module.pagecontent.PageContentProcessor;
import com.claymus.module.pagecontent.PageContentRegistry;
import com.claymus.module.pagecontent.fileupload.FileUploadContentFactory;
import com.claymus.module.pagecontent.html.HtmlContent;
import com.claymus.module.pagecontent.html.HtmlContentFactory;
import com.claymus.module.websitewidget.WebsiteWidgetProcessor;
import com.claymus.module.websitewidget.WebsiteWidgetRegistry;
import com.claymus.module.websitewidget.footer.FooterWidgetFactory;
import com.claymus.module.websitewidget.header.HeaderWidgetFactory;
import com.claymus.module.websitewidget.navigation.NavigationWidgetFactory;
import com.claymus.module.websitewidget.user.UserWidgetFactory;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.Version;

@SuppressWarnings("serial")
public class ClaymusMain extends HttpServlet {
	
	private static final Logger logger = 
			Logger.getLogger( ClaymusMain.class.getName() );

	protected static final PageContentRegistry PAGE_CONTENT_REGISTRY;
	protected static final WebsiteWidgetRegistry WEBSITE_WIDGET_REGISTRY;

	public static final Configuration FREEMARKER_CONFIGURATION;
	
	static {
		PAGE_CONTENT_REGISTRY = new PageContentRegistry();
		WEBSITE_WIDGET_REGISTRY = new WebsiteWidgetRegistry();
		
		PAGE_CONTENT_REGISTRY.register( HtmlContentFactory.class );
		PAGE_CONTENT_REGISTRY.register( FileUploadContentFactory.class );
		
		WEBSITE_WIDGET_REGISTRY.register( HeaderWidgetFactory.class );
		WEBSITE_WIDGET_REGISTRY.register( FooterWidgetFactory.class );
		WEBSITE_WIDGET_REGISTRY.register( NavigationWidgetFactory.class );
		WEBSITE_WIDGET_REGISTRY.register( UserWidgetFactory.class );
		
		FREEMARKER_CONFIGURATION = new Configuration();
		try {
			FREEMARKER_CONFIGURATION.setDirectoryForTemplateLoading(
					new File( System.getProperty("user.dir") + "/WEB-INF/classes/" ) );
		} catch ( IOException e ) {
			logger.log(
					Level.SEVERE,
					"Failed to set directory for FreeMarker template loading.",
					e );
		}
		FREEMARKER_CONFIGURATION.setObjectWrapper( new DefaultObjectWrapper() );
		FREEMARKER_CONFIGURATION.setDefaultEncoding( "UTF-8" );
		FREEMARKER_CONFIGURATION.setTemplateExceptionHandler( TemplateExceptionHandler.RETHROW_HANDLER );
		FREEMARKER_CONFIGURATION.setIncompatibleImprovements( new Version(2, 3, 20) ); // FreeMarker 2.3.20
	}

	
	private HttpServlet fileUploadServlet;
	

	@Override
	protected void service(
			HttpServletRequest request,
			HttpServletResponse response ) throws ServletException, IOException {
		
		String requestUri = request.getRequestURI();
		
		if( requestUri.startsWith( "/service.upload/" ) ) {
			if( fileUploadServlet == null ) {
				fileUploadServlet = new FileUploadServlet();
				fileUploadServlet.init( this.getServletConfig() );
			}
			fileUploadServlet.service( request, response );
		
		} else {
			super.service( request, response );
		}
	}
	
	@Override
	public void doPost(
			HttpServletRequest request,
			HttpServletResponse response ) throws IOException {

			doGet( request, response );
	}
	
	@Override
	public void doGet(
			HttpServletRequest request,
			HttpServletResponse response ) throws IOException {

		Page page = getPage();
		List<PageContent> pageContentList = getPageContentList( request );
		List<WebsiteWidget> websiteWidgetList = getWebsiteWidgetList( request );
		PageLayout pageLayout = getPageLayout();
		WebsiteLayout websiteLayout = getWebsiteLayout();
		
		if( pageContentList.size() == 0 ) {
			response.setStatus( HttpServletResponse.SC_NOT_FOUND );
			HtmlContent htmlContent = HtmlContentFactory.newHtmlContent();
			htmlContent.setHtml( "Page not found !" );
			pageContentList.add( htmlContent );
		}
		
		PrintWriter out = response.getWriter();
		renderPage( page, pageContentList, websiteWidgetList, pageLayout, websiteLayout, out );
		out.close();
	}
	
	private void renderPage(
			Page page,
			List<PageContent> pageContentList,
			List<WebsiteWidget> websiteWidgetList,
			PageLayout pageLayout,
			WebsiteLayout websiteLayout,
			PrintWriter out
			) {

		out.println( "<!doctype html>" );
		
		out.println( "<html>" );
		out.println( "<head>" );
		
		renderPageHead( out );
		
		out.println( "</head>" );
		out.println( "<body>" );

		
		List<String> websiteWidgetHtmlList = new LinkedList<>();
		for( WebsiteWidget websiteWidget : websiteWidgetList ) {
			@SuppressWarnings("rawtypes")
			WebsiteWidgetProcessor websiteWidgetProcessor = 
					WEBSITE_WIDGET_REGISTRY.getWebsiteWidgetProcessor( websiteWidget.getClass() );
			@SuppressWarnings("unchecked")
			String websiteWidgetHtml = websiteWidgetProcessor.getHtml( websiteWidget );
			websiteWidgetHtmlList.add( websiteWidgetHtml );
		}
		
		List<String> pageContentHtmlList = new LinkedList<>();
		for( PageContent pageContent : pageContentList ) {
			@SuppressWarnings("rawtypes")
			PageContentProcessor pageContentProcessor =
					PAGE_CONTENT_REGISTRY.getPageContentProcessor( pageContent.getClass() );
			@SuppressWarnings("unchecked")
			String pageContentHtml = pageContentProcessor.getHtml( pageContent );
			pageContentHtmlList.add( pageContentHtml );
		}
		
		Map<String, Object> input = new HashMap<String, Object>();
		input.put( "websiteWidgetList", websiteWidgetHtmlList );
		input.put( "pageContentList", pageContentHtmlList );
		
		try {
			Template template = new Template( null, pageLayout.getTemplate(), new Configuration() );
			template.process( input, out );

			template = new Template( null, websiteLayout.getTemplate(), new Configuration() );
			template.process( input, out );
		} catch ( IOException | TemplateException e ) {
			e.printStackTrace();
		}
		

		out.println( "</body>" );
		out.println( "</html>" );
	}
	
	protected void renderPageHead( Writer writer ) {
		try {
			Template template = FREEMARKER_CONFIGURATION
							.getTemplate( "com/claymus/servlet/content/WebsiteHead.ftl" );
			template.process( null, writer );
		} catch ( IOException | TemplateException e ) {
			logger.log( Level.SEVERE, "Template processing failed.", e );
		}
	}
	
	private Page getPage() {
		
		return new Page() {

			@Override
			public Long getId() {
				return null;
			}

			@Override
			public String getUri() {
				return null;
			}

			@Override
			public void setUri( String uri ) { }

			@Override
			public String getTitle() {
				return null;
			}

			@Override
			public void setTitle( String title ) { }

			@Override
			public Long getLayoutId() {
				return null;
			}

			@Override
			public void setLayout( Long layoutId ) { }

			@Override
			public Date getCreationDate() {
				return null;
			}

			@Override
			public void setCreationDate( Date creationDate ) { }
		
		};
		
	}
	
	protected List<PageContent> getPageContentList(
			HttpServletRequest request ) throws IOException {
		return new LinkedList<>();
	}

	protected List<WebsiteWidget> getWebsiteWidgetList(
			HttpServletRequest request ) {
		
		return new LinkedList<>();
	}

	private PageLayout getPageLayout() {
		
		return new PageLayout() {

			@Override
			public Long getId() {
				return null;
			}

			@Override
			public String getName() {
				return null;
			}

			@Override
			public void setName( String name ) { }

			@Override
			public String getTemplate() {
				return "<#list pageContentList as pageContent>"
						+ "${pageContent}"
						+ "</#list>";
			}

			@Override
			public void setTemplate( String template ) { }
			
		};
		
	}
	
	private WebsiteLayout getWebsiteLayout() {
		
		return new WebsiteLayout() {

			@Override
			public Long getId() {
				return null;
			}

			@Override
			public String getName() {
				return null;
			}

			@Override
			public void setName( String name ) { }

			@Override
			public String getTemplate() {
				return "<#list websiteWidgetList as websiteWidget>"
						+ "${websiteWidget}"
						+ "</#list>";
			}

			@Override
			public void setTemplate( String template ) { }
			
		};
		
	}

}
