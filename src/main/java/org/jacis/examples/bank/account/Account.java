package org.jacis.examples.bank.account;

import org.jacis.plugin.objectadapter.cloning.JacisCloneable;
import org.jacis.plugin.readonly.object.AbstractReadOnlyModeSupportingObject;

public class Account extends AbstractReadOnlyModeSupportingObject implements JacisCloneable<Account> {

  private String id;
  private String owner;
  private long lowerLimit;
  private long balance;

  public Account(String id, String owner, long lowerLimit) {
    this.id = id;
    this.owner = owner;
    this.lowerLimit = lowerLimit;
    this.balance = 0;
  }

  @Override
  public Account clone() {
    return (Account) super.clone();
  }

  @Override
  public String toString() {
    return id + "(" + owner + " balance: " + balance + ")";
  }

  public String getId() {
    return id;
  }

  public String getOwner() {
    return owner;
  }

  public long getLowerLimit() {
    return lowerLimit;
  }

  public long getBalance() {
    return balance;
  }

  public Account setLowerLimit(long lowerLimit) {
    checkWritable();
    this.lowerLimit = lowerLimit;
    return this;
  }

}
