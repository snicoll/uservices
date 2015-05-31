package com.barinek.uservice.support;

import com.barinek.uservice.App;
import com.barinek.uservices.restsupport.RestTestSupport;

public abstract class AppRunner extends RestTestSupport {
    protected static App basicApp;

    static {
        try {
            basicApp = new App();
            basicApp.start();
        } catch (Exception ignore) {
        }
    }
}
