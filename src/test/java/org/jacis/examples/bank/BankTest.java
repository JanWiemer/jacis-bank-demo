
package org.jacis.examples.bank;

import javax.enterprise.inject.se.SeContainer;
import javax.enterprise.inject.spi.CDI;
import javax.json.JsonArray;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.core.MediaType;

import org.jacis.examples.bank.account.Account;
import org.jacis.examples.bank.account.AccountStore;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import io.helidon.microprofile.server.Server;

class BankTest {

  private static final java.util.logging.Logger log = java.util.logging.Logger.getLogger(AccountStore.class.getName());

  private static Server server;
  private static String serverUrl;

  @BeforeAll
  public static void startTheServer() throws Exception {
    server = Server.create(BankApplication.class).start();
    serverUrl = "http://localhost:" + server.port();
  }

  private Builder request(Client client, String path) {
    return client.target(serverUrl).path("accounts/" + path).request();
  }

  @Test
  void testGetRooms() {
    Client client = ClientBuilder.newClient();
    JsonArray jsonObject = request(client, "all").get(JsonArray.class);
    log.info("result: " + jsonObject);
  }

  @Test
  void addRoom() {
    Client client = ClientBuilder.newClient();
    request(client, "add").put(Entity.entity(new Account("001", "Duke Jacis", 0), MediaType.APPLICATION_JSON));
    JsonArray jsonObject = request(client, "all").get(JsonArray.class);
    log.info("result: " + jsonObject);
  }

  @AfterAll
  static void destroyClass() {
    CDI<Object> current = CDI.current();
    ((SeContainer) current).close();
  }
}
