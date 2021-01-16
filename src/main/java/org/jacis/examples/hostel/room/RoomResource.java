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
  @Path("add")
  public Room add( //
      @FormParam("id") long id, //
      @FormParam("persons") int persons, //
      @FormParam("shower") boolean shower, //
      @FormParam("description") String description) {
    Room room = new Room(id, persons, shower, description);
    log.info("add room " + room);
    store.update(room);
    return room;
  }

  @Path("/{id}")
  @DELETE
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  public Response deleteRoom(@PathParam("id") Long id) {
    if (!store.containsKey(id)) {
      return Response.ok().status(Response.Status.NO_CONTENT).build();
    }
    log.info("remove room " + id);
    store.remove(id);
    return Response.notModified().build();
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
