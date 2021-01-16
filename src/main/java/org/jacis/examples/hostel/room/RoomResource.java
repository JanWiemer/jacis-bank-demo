package org.jacis.examples.hostel.room;

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

@Path("rooms")
@Transactional()
public class RoomResource {

  private static final java.util.logging.Logger log = java.util.logging.Logger.getLogger(RoomResource.class.getName());

  @Inject
  RoomStore store;

  @GET
  @Path("all")
  public List<Room> all() {
    return store.getAllReadOnly();
  }

  @GET
  @Path("{id}")
  public Room find(@PathParam("id") long id) {
    return store.getReadOnly(id);
  }

  @POST
  @Path("update")
  public void update( //
      @FormParam("id") long id, //
      @FormParam("persons") int persons, //
      @FormParam("shower") boolean shower, //
      @FormParam("description") String description) {
    Room room = new Room(id, persons, shower, description);
    log.info("add / uodate room " + room);
    store.update(room);
  }

  @POST
  @Path("remove")
  public void remove(@FormParam("id") long id) {
    log.info("remove room " + id);
    store.remove(id);
  }

  @DELETE
  @Path("delete/{id}")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  public Response delete(@PathParam("id") Long id) {
    if (!store.containsKey(id)) {
      return Response.ok().status(Response.Status.NO_CONTENT).build();
    }
    log.info("delete room " + id);
    store.remove(id);
    return Response.accepted().header("refresh", 0).build();
  }

  @GET
  @Path("allAsHtmlTable")
  @Produces(MediaType.TEXT_HTML)
  public String allAsHtmlTable() {
    StringBuilder b = new StringBuilder();
    b.append("<table border='1'>");
    b.append("<tr><th>+/-</th><th>Id</th><th>Beds</th><th>Shower</th><th>Description</th></tr>");
    // -- add
    b.append("<form action=\"../rooms/update\" method=\"POST\" autocomplete=\"off\">");
    b.append(" <td><input type=\"submit\" value=\"+\"/></td>");
    b.append(" <td><input type=\"number\" name=\"id\" size=\"4\"/></td>");
    b.append(" <td><input type=\"number\" name=\"persons\" size=\"4\"/></td>");
    b.append(" <td><input type=\"checkbox\" name=\"shower\"/></td>");
    b.append(" <td><TEXTAREA NAME=\"description\" COLS=80 ROWS=1></TEXTAREA></td>");
    b.append("</tr>");
    b.append("</form>");
    // -- data
    for (Room room : store.getAllReadOnly()) {
      b.append("<tr>");
      b.append("<form action=\"../rooms/remove\" method=\"POST\">");
      b.append(" <td><input type=\"submit\" value=\"X\"/>");
      b.append("     <input type=\"hidden\" name=\"id\" value=\"").append(room.getId()).append("\"/></td>");
      b.append("</form>");
      b.append(" <td>").append(room.getId()).append("</td>");
      b.append(" <td>").append(room.getNumberOfBeds()).append("</td>");
      b.append(" <td>").append(room.isShower()).append("</td>");
      b.append(" <td>").append(room.getDescription()).append("</td>");
      b.append("</tr>");
    }
    b.append("</table>");
    return b.toString();
  }

  @GET
  @Path("init")
  public List<Room> initData() {
    store.update(new Room(1, 2, false, "Ground Floor Room 1"));
    store.update(new Room(2, 2, true, "Ground Floor Room 2"));
    store.update(new Room(3, 4, true, "Ground Floor Room 3"));
    store.update(new Room(4, 4, true, "Ground Floor Room 4"));
    store.update(new Room(5, 4, true, "Ground Floor Room 5"));
    store.update(new Room(6, 4, true, "Ground Floor Room 6"));
    return store.getAll();
  }

}
