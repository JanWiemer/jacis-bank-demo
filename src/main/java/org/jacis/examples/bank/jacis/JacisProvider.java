package org.jacis.examples.bank.jacis;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.transaction.TransactionManager;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jacis.container.JacisContainer;
import org.jacis.extension.persistence.microstream.MicrostreamStorage;
import org.jacis.plugin.txadapter.jta.AbstractJacisTransactionAdapterJTA;

import one.microstream.storage.configuration.Configuration;
import one.microstream.storage.restservice.StorageRestService;
import one.microstream.storage.restservice.StorageRestServiceResolver;
import one.microstream.storage.types.EmbeddedStorageManager;

@ApplicationScoped
public class JacisProvider {

  private static final java.util.logging.Logger log = java.util.logging.Logger.getLogger(JacisProvider.class.getName());

  @ConfigProperty(name = "app.startRestServer", defaultValue = "false")
  boolean startRestServer;

  @Produces
  @ApplicationScoped
  public JacisContainer produceJacisContainer(TransactionManager txManager) {
    JacisContainer container = new JacisContainer(new JacisJtaAdapter(txManager));
    log.info("created JacisContainer...");
    return container;
  }

  @Produces
  @ApplicationScoped
  public MicrostreamStorage produceMicrostreamStorage() {
    EmbeddedStorageManager storageManager = createMicroStreamStorageManager();
    storageManager.start();
    if (startRestServer) {
      StorageRestService restService = StorageRestServiceResolver.resolve(storageManager);
      restService.start();
    }
    MicrostreamStorage storage = new MicrostreamStorage(storageManager);
    log.info("created Microstream Storage...");
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
