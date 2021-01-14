package org.jacis.examples.hostel.room;

import java.util.List;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("rooms")
@Transactional()
@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
public class RoomResource {

  private static final java.util.logging.Logger log = java.util.logging.Logger.getLogger(RoomResource.class.getName());

  @Inject
  RoomStore store;

  @GET
  @Path("{id}")
  public Room find(@PathParam("id") long id) {
    return store.getReadOnly(id);
  }

  @GET
  @Path("all")
  public List<Room> all() {
    return store.getAllReadOnly();
  }

  @GET
  @Path("add:{id}:{desc}")
  public Room add(@PathParam("id") long id, @PathParam("desc") String desc) {
    Room room = new Room(id, 2, true, desc);
    store.update(room);
    return room;
  }

  @POST
  @Path("add")
  public Response addRoom(Room room) {
    try {
      store.update(room);
      return Response.ok().status(Response.Status.CREATED).build();
    } catch (Exception e) {
      log.info("addRoom failed " + e);
      return Response.notModified().build();
    }
  }

  @Path("/{id}")
  @DELETE
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  public Response deleteRoom(@PathParam("id") Long id) {
    if (!store.containsKey(id)) {
      return Response.ok().status(Response.Status.NO_CONTENT).build();
    }
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
