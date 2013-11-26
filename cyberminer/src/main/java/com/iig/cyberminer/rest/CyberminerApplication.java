package com.iig.cyberminer.rest;

import com.iig.cyberminer.rest.CyberminerConfiguration;
import com.iig.cyberminer.rest.resources.CyberminerResource;
import com.iig.cyberminer.rest.resources.KWICResource;

import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.assets.AssetsBundle;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;

public class CyberminerApplication extends Service<CyberminerConfiguration> {
    public static void main(String[] args) throws Exception {
        new CyberminerApplication().run(args);
    }

    @Override
    public void initialize(Bootstrap<CyberminerConfiguration> bootstrap) {
        bootstrap.setName("cyberminer");
        bootstrap.addBundle( new AssetsBundle("/cyberminer/", "/") );
    }

    @Override
    public void run(CyberminerConfiguration configuration,
                    Environment environment) {
        final String template = configuration.getTemplate();
        final String defaultName = configuration.getDefaultName();

        environment.addResource(new CyberminerResource());
        environment.addResource(new KWICResource());
    }

}