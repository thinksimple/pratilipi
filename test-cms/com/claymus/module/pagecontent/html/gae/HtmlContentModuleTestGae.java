package com.claymus.module.pagecontent.html.gae;

import org.junit.After;
import org.junit.Before;

import com.claymus.module.pagecontent.html.HtmlContentModuleTest;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

public class HtmlContentModuleTestGae extends HtmlContentModuleTest {

	private final LocalServiceTestHelper helper =
			new LocalServiceTestHelper( new LocalDatastoreServiceTestConfig() );
	
	@Before
    public void setUp() {
        helper.setUp();
    }

    @After
    public void tearDown() {
        helper.tearDown();
    }
	
}
