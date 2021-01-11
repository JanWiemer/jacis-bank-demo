package org.jacis.examples.resourcemanager.resource.entity;

import org.jacis.plugin.objectadapter.cloning.JacisCloneable;
import org.jacis.plugin.readonly.object.AbstractReadOnlyModeSupportingObject;

public class Resource extends AbstractReadOnlyModeSupportingObject implements JacisCloneable<Resource> {

  private long id;
  private ResourceType type;
  private String description;
  private long totalAmount;
  private long reservedAmount;

  public Resource(long id, ResourceType type, String description) {
    this.id = id;
    this.type = type;
    this.description = description;
  }

  @Override
  public Resource clone() {
    return (Resource) super.clone();
  }

  @Override
  public String toString() {
    return type + "|" + id + "|" + description;
  }

  public long getId() {
    return id;
  }

  public ResourceType getType() {
    return type;
  }

  public String getDescription() {
    return description;
  }

  public long getTotalAmount() {
    return totalAmount;
  }

  public long getAvailableAmount() {
    return totalAmount - reservedAmount;
  }

  public long getReservedAmount() {
    return reservedAmount;
  }

  public Resource reserve(int amount) {
    if (reservedAmount + amount > totalAmount) {
      throw new IllegalStateException("Failed to reserve " + amount + " of " + this + "! Total amount: " + totalAmount + ", already reserved: " + reservedAmount);
    }
    reservedAmount += amount;
    return this;
  }

}
