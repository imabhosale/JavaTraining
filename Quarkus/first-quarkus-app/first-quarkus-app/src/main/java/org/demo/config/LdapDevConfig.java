// src/main/java/org/demo/config/LdapDevConfig.java
package org.demo.config;

import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;

@ApplicationScoped
public class LdapDevConfig {

    void setup(@Observes StartupEvent ev) {
        System.setProperty("quarkus.ldap.devservices.ldif", "ldap-data.ldif");
    }
}