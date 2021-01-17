package org.jacis.examples.bank;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.jacis.container.JacisContainer;
import org.jacis.examples.bank.account.AccountResource;
import org.jacis.examples.bank.account.AccountStore;
import org.jacis.examples.bank.greet.GreetResource;
import org.jacis.extension.persistence.microstream.MicrostreamStorage;

import io.helidon.microprofile.server.Server;

@ApplicationPath("/")
public class BankApplication extends Application {

  private static final java.util.logging.Logger log = java.util.logging.Logger.getLogger(AccountStore.class.getName());

  @Inject
  JacisContainer container;
  @Inject
  MicrostreamStorage microstreamStorage;

  private BankApplication() {
    // do not construct
  }

  @PostConstruct
  public void postConstruct() {
    log.info(" work with JacisContainer " + container.toString() + " and MicorostreamStorage " + microstreamStorage.toString());
  }

  public static void main(String... args) {
    Server server = Server.builder().addApplication(BankApplication.class).build();
    server.start();
    log.info("Bank Demo Application is running an listening on port " + server.port());
  }

  @Override
  public Set<Class<?>> getClasses() {
    Set<Class<?>> endpoints = new HashSet<>();
    endpoints.add(GreetResource.class);
    endpoints.add(AccountResource.class);
    return endpoints;
  }

}
