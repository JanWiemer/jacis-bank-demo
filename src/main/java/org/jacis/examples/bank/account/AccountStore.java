package org.jacis.examples.bank.account;

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
public class AccountStore {

  private static final java.util.logging.Logger log = java.util.logging.Logger.getLogger(AccountStore.class.getName());

  @Inject
  JacisContainer container;
  @Inject
  MicrostreamStorage storage;

  JacisStore<String, Account> store;

  @PostConstruct
  public void initialize() {
    JacisObjectTypeSpec<String, Account, Account> objectTypeSpec = new JacisObjectTypeSpec<>(String.class, Account.class);
    objectTypeSpec.setPersistenceAdapter(new MicrostreamPersistenceAdapter<>(storage));
    this.store = container.createStore(objectTypeSpec).getStore();
    log.info("Jacis store initialized: " + store);
  }

  public boolean containsKey(String id) {
    return store.containsKey(id);
  }

  public Account getReadOnly(String id) {
    return store.getReadOnly(id);
  }

  public List<Account> getAllReadOnly() {
    return store.getAllReadOnly();
  }

  public Account get(String id) {
    return store.get(id);
  }

  public List<Account> getAll() {
    return store.getAllReadOnly();
  }

  public AccountStore update(Account room) {
    store.update(room.getId(), room);
    return this;
  }

  public Account remove(String id) {
    Account room = store.get(id);
    if (room != null) {
      store.remove(id);
    }
    return room;
  }

}
