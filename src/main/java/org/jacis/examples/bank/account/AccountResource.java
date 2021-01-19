package org.jacis.examples.bank.account;

import java.util.List;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("accounts")
@Transactional()
public class AccountResource {

  private static final java.util.logging.Logger log = java.util.logging.Logger.getLogger(AccountResource.class.getName());

  @Inject
  AccountStore store;

  @GET
  @Path("all")
  public List<Account> all() {
    return store.getAllReadOnly();
  }

  @GET
  @Path("{id}")
  public Account find(@PathParam("id") String id) {
    return store.getReadOnly(id);
  }

  @POST
  @Path("update")
  public void update( //
      @FormParam("id") String id, //
      @FormParam("owner") String owner, //
      @FormParam("lowerLimit") long lowerLimit) {
    Account account = new Account(id, owner, lowerLimit);
    log.info("add / uodate account " + account);
    store.update(account);
  }

  @POST
  @Path("remove")
  public void remove(@FormParam("id") String id) {
    log.info("remove account " + id);
    store.remove(id);
  }

  @DELETE
  @Path("delete/{id}")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  public Response delete(@PathParam("id") String id) {
    if (!store.containsKey(id)) {
      return Response.ok().status(Response.Status.NO_CONTENT).build();
    }
    log.info("delete account " + id);
    store.remove(id);
    return Response.accepted().header("refresh", 0).build();
  }

  @POST
  @Path("rebook")
  public void rebook( //
      @FormParam("fromId") String fromId, //
      @FormParam("toId") String toId, //
      @FormParam("amount") long amount) {
    log.info("rebook " + amount + " from " + fromId + " to " + toId);
    Account accFrom = store.get(fromId);
    Account accTo = store.get(toId);
    if (accFrom == null) {
      throw new WebApplicationException("Source account " + fromId + " not known!", Response.Status.METHOD_NOT_ALLOWED);
    } else if (accTo == null) {
      throw new WebApplicationException("Target account " + toId + " not known!", Response.Status.METHOD_NOT_ALLOWED);
    }
    try {
      store.update(accFrom.withdraw(amount));
      store.update(accTo.deposit(amount));
    } catch (IllegalArgumentException e) {
      throw new WebApplicationException("Rebook " + amount + " from " + fromId + " to " + toId + " not allowed!", Response.Status.METHOD_NOT_ALLOWED);
    }
  }

  @GET
  @Path("init")
  public List<Account> initData() {
    store.update(new Account("0001", "Duke Jacis", 0));
    store.update(new Account("0002", "Duke Jacis", 0));
    store.update(new Account("0003", "Duke Jacis", 0));
    store.update(new Account("0004", "Duke Jacis", 0));
    store.update(new Account("0005", "Don Heli", 0));
    store.update(new Account("0006", "Don Heli", 0));
    store.update(new Account("0007", "Don Heli", 0));
    store.update(new Account("0008", "Mik Rostream", 0));
    store.update(new Account("0009", "Mik Rostream", 0));
    store.update(new Account("0010", "Mik Rostream", 0));
    return store.getAll();
  }

}
