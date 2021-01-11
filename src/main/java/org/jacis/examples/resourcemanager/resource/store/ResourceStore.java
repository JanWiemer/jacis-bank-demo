package org.jacis.examples.resourcemanager.resource.store;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import org.jacis.container.JacisContainer;
import org.jacis.container.JacisObjectTypeSpec;
import org.jacis.examples.resourcemanager.resource.entity.Resource;
import org.jacis.examples.resourcemanager.resource.entity.ResourceType;
import org.jacis.store.JacisStore;

@ApplicationScoped
public class ResourceStore {

  private JacisStore<Long, Resource> store;

  public ResourceStore() {
    JacisContainer container = new JacisContainer();
    JacisObjectTypeSpec<Long, Resource, Resource> objectTypeSpec = new JacisObjectTypeSpec<>(Long.class, Resource.class);
    this.store = container.createStore(objectTypeSpec).getStore();
    container.withLocalTx(() -> {
      store.update(1l, new Resource(1, ResourceType.ROOM, "Meeting Room 1"));
      store.update(2l, new Resource(2, ResourceType.ROOM, "Meeting Room 2"));
      store.update(3l, new Resource(3, ResourceType.ROOM, "Meeting Room 3"));
    });
  }

  public Resource getReadOnly(long id) {
    return store.getReadOnly(id);
  }

  public List<Resource> getAllReadOnly() {
    return store.getAllReadOnly();
  }

}
