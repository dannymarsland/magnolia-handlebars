package com.dannymarsland.magnolia.handlebars.setup;

import info.magnolia.module.ModuleLifecycle;
import info.magnolia.module.ModuleLifecycleContext;
import info.magnolia.module.blossom.module.BlossomModuleSupport;

public class HandlebarsModule extends BlossomModuleSupport implements ModuleLifecycle {

    public void start(ModuleLifecycleContext moduleLifecycleContext) {
        super.initBlossomDispatcherServlet("blossom", "classpath:/blossom-context.xml");
    }

    public void stop(ModuleLifecycleContext moduleLifecycleContext) {
        super.destroyDispatcherServlets();
        super.closeRootWebApplicationContext();
    }
}
