package com.google.appengine.tools.development;

import java.io.File;

import com.google.apphosting.api.ApiProxy;

public class LocalServiceTestCase {
//    @Before
    public void setUp() {
        ApiProxy.setEnvironmentForCurrentThread(new TestEnvironment());
        ApiProxy.setDelegate(new ApiProxyLocalImpl(new File(".")));
    }

//    @After
    public void tearDown() {
        ApiProxy.setDelegate(null);
        ApiProxy.setEnvironmentForCurrentThread(null);
    }
}
