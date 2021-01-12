package org.jacis.examples.hostel.room;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import org.jacis.container.JacisContainer;
import org.jacis.container.JacisObjectTypeSpec;
import org.jacis.store.JacisStore;

@ApplicationScoped
public class RoomStore {

  private JacisStore<Long, Room> store;

  public RoomStore() {
    JacisContainer container = new JacisContainer();
    JacisObjectTypeSpec<Long, Room, Room> objectTypeSpec = new JacisObjectTypeSpec<>(Long.class, Room.class);
    this.store = container.createStore(objectTypeSpec).getStore();
    container.withLocalTx(() -> {
      add(new Room(1, 4, true, "Ground Floor Room 1"));
      add(new Room(2, 4, true, "Ground Floor Room 2"));
      add(new Room(3, 4, true, "Ground Floor Room 3"));
      add(new Room(4, 4, true, "Ground Floor Room 4"));
      add(new Room(5, 4, true, "Ground Floor Room 5"));
    });
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

}
