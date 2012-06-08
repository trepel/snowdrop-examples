package org.jboss.snowdrop.samples.sportsclub.dao.hibernate.support;

import org.hibernate.transaction.JNDITransactionManagerLookup;

/**
 * @author: Ryan Bradley
 */
public class JBossAs7TransactionManagerLookup extends JNDITransactionManagerLookup {

    @Override
    public String getUserTransactionName() {
        return "java:jboss/UserTransaction";
    }

    @Override
    protected String getName() {
        return "java:jboss/TransactionManager";
    }
}
