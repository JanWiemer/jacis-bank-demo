package org.jacis.examples.hostel;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.jacis.container.JacisContainer;
import org.jacis.examples.hostel.greet.GreetResource;
import org.jacis.examples.hostel.room.RoomResource;
import org.jacis.examples.hostel.room.RoomStore;
import org.jacis.extension.persistence.MicrostreamStorage;

import io.helidon.microprofile.server.Server;

@ApplicationPath("/")
public class HostelApplication extends Application {

  private static final java.util.logging.Logger log = java.util.logging.Logger.getLogger(RoomStore.class.getName());

  @Inject
  JacisContainer container;
  @Inject
  MicrostreamStorage microstreamStorage;

  private HostelApplication() {
    // do not construct
  }

  @PostConstruct
  public void postConstruct() {
    log.info(" work with JacisContainer " + container.toString() + " and MicorostreamStorage " + microstreamStorage.toString());
  }

  public static void main(String... args) {
    Server server = Server.builder().addApplication(HostelApplication.class).build();
    server.start();
    log.info("Hostel Demo Application is running an listening on port " + server.port());
  }

  @Override
  public Set<Class<?>> getClasses() {
    Set<Class<?>> endpoints = new HashSet<>();
    endpoints.add(GreetResource.class);
    endpoints.add(RoomResource.class);
    return endpoints;
  }

}
