package org.jacis.examples.hostel.room;

import java.util.List;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("rooms")
@Transactional()
@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
public class RoomResource {

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

  @GET
  @Path("init")
  public List<Room> initData() {
    store.add(new Room(1, 2, false, "Ground Floor Room 1"));
    store.add(new Room(2, 2, true, "Ground Floor Room 2"));
    store.add(new Room(3, 4, true, "Ground Floor Room 3"));
    store.add(new Room(4, 4, true, "Ground Floor Room 4"));
    store.add(new Room(5, 4, true, "Ground Floor Room 5"));
    store.add(new Room(6, 4, true, "Ground Floor Room 6"));
    return store.getAll();
  }

}
