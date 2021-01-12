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

  private HostelApplication() {
    // do not construct
  }

  public static void main(String... args) {
    Server server = Server.builder().addApplication(HostelApplication.class).build();
    server.start();
    System.out.println("Hostel Demo Application is running an listening on port " + server.port());
  }

  @Override
  public Set<Class<?>> getClasses() {
    Set<Class<?>> endpoints = new HashSet<>();
    endpoints.add(GreetResource.class);
    endpoints.add(RoomResource.class);
    return endpoints;
  }

}
