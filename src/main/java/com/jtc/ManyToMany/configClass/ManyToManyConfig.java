package com.jtc.ManyToMany.configClass;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ManyToManyConfig {

    @Bean
    public ServletWebServerFactory servletWebServerFactory()
    {
        TomcatServletWebServerFactory tomcatServletWebServerFactory = new TomcatServletWebServerFactory()
        {
            @Override
            protected void postProcessContext(Context context)
            {
                SecurityConstraint securityConstraint = new SecurityConstraint();
                securityConstraint.setUserConstraint("CONFIDENTIAL");
                SecurityCollection collection = new SecurityCollection();
                collection.addPattern("/");
                securityConstraint.addCollection(collection);
                context.addConstraint(securityConstraint);
            }

        };
        tomcatServletWebServerFactory.addAdditionalTomcatConnectors(redirectTOHttps());
        return tomcatServletWebServerFactory;
    }

    private Connector redirectTOHttps()
    {
        Connector connector = new Connector(TomcatServletWebServerFactory.DEFAULT_PROTOCOL);
        connector.setScheme("http");
        connector.setPort(8880);
        connector.setSecure(false);
        connector.setRedirectPort(8443);
        return connector;

    }
}


