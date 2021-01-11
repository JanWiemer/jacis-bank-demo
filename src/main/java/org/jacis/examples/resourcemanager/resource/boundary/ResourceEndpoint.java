package org.jacis.examples.resourcemanager.resource.boundary;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jacis.examples.resourcemanager.resource.entity.Resource;

@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
@Path("resources")
public class ResourceEndpoint {

  @Inject
  Resources resources;

  @GET
  @Path("{id}")
  public Resource find(@PathParam("id") long registrationId) {
    return resources.find(registrationId);
  }

  @GET
  @Path("all")
  public List<Resource> all() {
    return resources.all();
  }

}
