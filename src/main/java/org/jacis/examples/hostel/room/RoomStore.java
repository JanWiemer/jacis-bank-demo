package org.jacis.examples.hostel.room;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.jacis.container.JacisContainer;
import org.jacis.container.JacisObjectTypeSpec;
import org.jacis.extension.persistence.MicrostreamPersistenceAdapter;
import org.jacis.extension.persistence.MicrostreamStorage;
import org.jacis.store.JacisStore;

@ApplicationScoped
public class RoomStore {

  private static final java.util.logging.Logger log = java.util.logging.Logger.getLogger(RoomStore.class.getName());

  @Inject
  JacisContainer container;
  @Inject
  MicrostreamStorage storage;

  JacisStore<Long, Room> store;

  @PostConstruct
  public void initialize() {
    JacisObjectTypeSpec<Long, Room, Room> objectTypeSpec = new JacisObjectTypeSpec<>(Long.class, Room.class);
    objectTypeSpec.setPersistenceAdapter(new MicrostreamPersistenceAdapter<>(storage));
    this.store = container.createStore(objectTypeSpec).getStore();
    log.info("Jacis store initialized: " + store);
  }

  public RoomStore add(Room room) {
    store.update(room.getId(), room);
    return this;
  }

  public Room getReadOnly(long id) {
    return store.getReadOnly(id);
  }

  public List<Room> getAllReadOnly() {
    return store.getAllReadOnly();
  }

  public Room get(long id) {
    return store.get(id);
  }

  public List<Room> getAll() {
    return store.getAllReadOnly();
  }

  public void update(Room room) {
    store.update(room.getId(), room);
  }

}
