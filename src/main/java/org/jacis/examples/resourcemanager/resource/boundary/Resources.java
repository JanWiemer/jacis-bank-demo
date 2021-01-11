package org.jacis.examples.resourcemanager.resource.boundary;

import javax.enterprise.context.RequestScoped;

import org.jacis.examples.resourcemanager.resource.entity.Resource;
import org.jacis.examples.resourcemanager.resource.entity.ResourceType;

@RequestScoped
public class Resources {

  public Resource find(long id) {
    return new Resource(id, ResourceType.ROOM, "Main Room");
  }

}
