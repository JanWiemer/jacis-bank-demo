package org.jacis.examples.hostel.room;

import org.jacis.plugin.objectadapter.cloning.JacisCloneable;
import org.jacis.plugin.readonly.object.AbstractReadOnlyModeSupportingObject;

public class Room extends AbstractReadOnlyModeSupportingObject implements JacisCloneable<Room> {

  private long id;
  private long numberOfBeds;
  private boolean shower;
  private String description;

  public Room(long id, long numberOfBeds, boolean shower, String description) {
    this.id = id;
    this.numberOfBeds = numberOfBeds;
    this.shower = shower;
    this.description = description;
  }

  @Override
  public Room clone() {
    return (Room) super.clone();
  }

  @Override
  public String toString() {
    return +id + "(" + numberOfBeds + " beds " + (shower ? " with shower" : "") + ", " + description + ")";
  }

  public long getId() {
    return id;
  }

  public long getNumberOfBeds() {
    return numberOfBeds;
  }

  public boolean isShower() {
    return shower;
  }

  public String getDescription() {
    return description;
  }

}
