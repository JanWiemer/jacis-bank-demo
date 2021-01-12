package org.jacis.examples.hostel.jacis;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.transaction.TransactionManager;

import org.jacis.container.JacisContainer;
import org.jacis.plugin.txadapter.jta.AbstractJacisTransactionAdapterJTA;

@ApplicationScoped
public class JacisProvider {

  @Produces
  @ApplicationScoped
  public JacisContainer produceJacisContainer(TransactionManager txManager) {
    return new JacisContainer(new JacisJtaAdapter(txManager));
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
