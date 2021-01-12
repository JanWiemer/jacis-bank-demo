package org.jacis.examples.hostel;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.jacis.examples.hostel.greet.GreetResource;
import org.jacis.examples.hostel.room.RoomResource;

import io.helidon.microprofile.server.Server;

@ApplicationPath("/")
public class HostelApplication extends Application {

  public static void main(String... args) {
    Server server = Server.builder().addApplication(HostelApplication.class).port(9080).build();
    server.start();
  }

  @Override
  public Set<Class<?>> getClasses() {
    Set<Class<?>> endpoints = new HashSet<>();
    endpoints.add(GreetResource.class);
    endpoints.add(RoomResource.class);
    return endpoints;
  }

}
