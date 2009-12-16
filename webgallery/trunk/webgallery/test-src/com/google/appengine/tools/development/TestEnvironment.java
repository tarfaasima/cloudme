package com.google.appengine.tools.development;


import java.util.HashMap;
import java.util.Map;

import com.google.apphosting.api.ApiProxy;

public class TestEnvironment implements ApiProxy.Environment {
    public String getAppId() {
        return "test";
    }

    public Map<String, Object> getAttributes() {
        return new HashMap<String, Object>();
    }

    public String getAuthDomain() {
        throw new UnsupportedOperationException();
    }

    public String getEmail() {
        throw new UnsupportedOperationException();
    }

    public String getRequestNamespace() {
        return "";
    }

    public String getVersionId() {
        return "1.0";
    }

    public boolean isAdmin() {
        throw new UnsupportedOperationException();
    }

    public boolean isLoggedIn() {
        throw new UnsupportedOperationException();
    }
}
