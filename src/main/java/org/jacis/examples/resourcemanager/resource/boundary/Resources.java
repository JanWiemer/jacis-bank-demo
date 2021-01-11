package org.jacis.examples.resourcemanager.resource.boundary;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.jacis.examples.resourcemanager.resource.entity.Resource;
import org.jacis.examples.resourcemanager.resource.store.ResourceStore;

@RequestScoped
public class Resources {

  @Inject
  ResourceStore store;

  public Resource find(long id) {
    return store.getReadOnly(id);
  }

  public List<Resource> all() {
    return store.getAllReadOnly();
  }

}
