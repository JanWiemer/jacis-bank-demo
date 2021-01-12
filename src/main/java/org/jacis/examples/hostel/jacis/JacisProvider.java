package org.jacis.examples.hostel.jacis;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.transaction.TransactionManager;

import org.jacis.container.JacisContainer;
import org.jacis.extension.persistence.MicrostreamStorage;
import org.jacis.plugin.txadapter.jta.AbstractJacisTransactionAdapterJTA;

import one.microstream.storage.configuration.Configuration;
import one.microstream.storage.types.EmbeddedStorageManager;

@ApplicationScoped
public class JacisProvider {

  @Produces
  @ApplicationScoped
  public JacisContainer produceJacisContainer(TransactionManager txManager) {
    return new JacisContainer(new JacisJtaAdapter(txManager));
  }

  @Produces
  @ApplicationScoped
  public MicrostreamStorage produceMicrostreamStorage() {
    EmbeddedStorageManager storageManager = createMicroStreamStorageManager();
    storageManager.start();
    MicrostreamStorage storage = new MicrostreamStorage(storageManager);
    return storage;
  }

  private static EmbeddedStorageManager createMicroStreamStorageManager() {
    EmbeddedStorageManager storageManager = Configuration.Default() //
        .setBaseDirectory("var/data-dir") //
        .setBackupDirectory("var/backup-dir")//
        .createEmbeddedStorageFoundation() //
        .createEmbeddedStorageManager();
    return storageManager;
  }

  public static class JacisJtaAdapter extends AbstractJacisTransactionAdapterJTA {

    private final TransactionManager txManager;

    public JacisJtaAdapter(TransactionManager txManager) {
      this.txManager = txManager;
    }

    @Override
    protected TransactionManager getTransactionManager() {
      return txManager;
    }

  }

}
