package com.iig.cyberminer;

import com.iig.cyberminer.CyberminerConfiguration;
import com.iig.cyberminer.resources.CyberminerResource;
import com.iig.cyberminer.resources.HelloWorldResource;
import com.iig.cyberminer.resources.KWICResource;

import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;

public class CyberminerApplication extends Service<CyberminerConfiguration> {
    public static void main(String[] args) throws Exception {
        new CyberminerApplication().run(args);
    }

    @Override
    public void initialize(Bootstrap<CyberminerConfiguration> bootstrap) {
        bootstrap.setName("cyberminer");
    }

    @Override
    public void run(CyberminerConfiguration configuration,
                    Environment environment) {
        final String template = configuration.getTemplate();
        final String defaultName = configuration.getDefaultName();
        environment.addResource(new HelloWorldResource(template, defaultName));
        environment.addResource(new CyberminerResource()); //pass in DB config at a later date?
        environment.addResource(new KWICResource()); //pass in DB config at a later date?  
    }

}