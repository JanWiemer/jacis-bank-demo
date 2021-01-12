package org.jacis.examples.hostel.room;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
@Path("rooms")
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

}
