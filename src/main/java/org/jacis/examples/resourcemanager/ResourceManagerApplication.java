package org.jacis.examples.resourcemanager;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.jacis.examples.resourcemanager.greet.GreetResource;
import org.jacis.examples.resourcemanager.resource.boundary.ResourceEndpoint;

import io.helidon.microprofile.server.Server;

@ApplicationPath("/")
public class ResourceManagerApplication extends Application {

  public static void main(String... args) {
    Server server = Server.builder().addApplication(ResourceManagerApplication.class).port(9080).build();
    server.start();
  }

  @Override
  public Set<Class<?>> getClasses() {
    Set<Class<?>> endpoints = new HashSet<>();
    endpoints.add(GreetResource.class);
    endpoints.add(ResourceEndpoint.class);
    return endpoints;
  }

}
