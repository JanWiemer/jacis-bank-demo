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
import javax.ws.rs.Produces;
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

  @GET
  @Path("allAsHtmlTable")
  @Produces(MediaType.TEXT_HTML)
  public String allAsHtmlTable() {
    StringBuilder b = new StringBuilder();
    b.append("<table border='1'>");
    b.append("<tr><th>+/-</th><th>Id</th><th>Owner</th><th>Lower Limit</th><th>Balance</th></tr>");
    // -- add
    b.append("<form action=\"../accounts/update\" method=\"POST\" autocomplete=\"off\">");
    b.append(" <td><input type=\"submit\" value=\"+\"/></td>");
    b.append(" <td><input type=\"text\" name=\"id\" size=\"16\"/></td>");
    b.append(" <td><input type=\"text\" name=\"owner\" size=\"4\"/></td>");
    b.append(" <td><input type=\"number\" name=\"lowerLimit\"/></td>");
    b.append(" <td><label type=\"number\" name=\"balance\"/></td>");
    b.append("</tr>");
    b.append("</form>");
    // -- data
    for (Account room : store.getAllReadOnly()) {
      b.append("<tr>");
      b.append("<form action=\"../accounts/remove\" method=\"POST\">");
      b.append(" <td><input type=\"submit\" value=\"X\"/>");
      b.append("     <input type=\"hidden\" name=\"id\" value=\"").append(room.getId()).append("\"/></td>");
      b.append("</form>");
      b.append(" <td>").append(room.getId()).append("</td>");
      b.append(" <td>").append(room.getOwner()).append("</td>");
      b.append(" <td>").append(room.getLowerLimit()).append("</td>");
      b.append(" <td>").append(room.getBalance()).append("</td>");
      b.append("</tr>");
    }
    b.append("</table>");
    return b.toString();
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
